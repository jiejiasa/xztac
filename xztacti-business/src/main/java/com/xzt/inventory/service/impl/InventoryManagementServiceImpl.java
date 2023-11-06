package com.xzt.inventory.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzt.common.core.domain.AjaxResult;
import com.xzt.common.core.domain.entity.SysUser;
import com.xzt.common.utils.SecurityUtils;
import com.xzt.common.utils.bean.BeanUtils;
import com.xzt.inventory.domain.InventoryManagement;
import com.xzt.inventory.domain.OutInventory;
import com.xzt.inventory.mapper.InventoryManagementMapper;
import com.xzt.inventory.rvo.AuditHistoryRVO;
import com.xzt.inventory.rvo.GoOutInventoryRVO;
import com.xzt.inventory.rvo.PriceAllInfoRVO;
import com.xzt.inventory.service.InventoryManagementService;
import com.xzt.inventory.service.OutInventoryService;
import com.xzt.inventory.service.XztGaragePriceService;
import com.xzt.inventory.vo.GoOutInfo;
import com.xzt.inventory.vo.InventoryManagementSelectVO;
import com.xzt.inventory.vo.UpdateInventoryVO;
import com.xzt.rvo.AuditUserInfo;
import com.xzt.service.IProcessService;
import com.xzt.system.service.ISysUserService;
import com.xzt.vo.HandleAuditParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class InventoryManagementServiceImpl extends ServiceImpl<InventoryManagementMapper, InventoryManagement> implements InventoryManagementService {

    @Resource
    private InventoryManagementMapper inventoryManagementMapper;



    @Resource
    private OutInventoryService outInventoryService;


    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IProcessService processService;

    @Resource
    private XztGaragePriceService xztGaragePriceService;

    /**
     * 库存信息列表
     * @param vo
     * @return
     */
    @Override
    public PageInfo<InventoryManagement> selectList(InventoryManagementSelectVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<InventoryManagement> inventoryManagementList = inventoryManagementMapper.selectByParam();
        PageInfo<InventoryManagement> inventoryManagementPageInfo = new PageInfo<>(inventoryManagementList);


        for (InventoryManagement i: inventoryManagementList) {


            Integer integer = selectPrice(i.getPId(), i.getInDay(), i.getDayOrMonthPrice(), i.getFixedPrice());

            i.setInDayMony(integer);

        }

        return inventoryManagementPageInfo;
    }



    /**
     * 查询库存详细信息
     * @param id
     * @return
     */
    @Override
    public AjaxResult getInventoryInfo(Integer id) {

        InventoryManagement inventoryManagement = inventoryManagementMapper.selectById(id);
        return AjaxResult.success(inventoryManagement);
    }

    @Override
    public AjaxResult delInventory(Integer id) {

        return AjaxResult.success(inventoryManagementMapper.deleteById(id));
    }

    @Override
    public GoOutInventoryRVO getGoOut(Integer id) {

        GoOutInventoryRVO goOutInventoryRVO = new GoOutInventoryRVO();
        InventoryManagement byId = this.getById(id);
        goOutInventoryRVO.setInventoryManagement(byId);

        List<SysUser> userListone = sysUserService.selectByRoleKey("first");
        List<SysUser> userListtwo = sysUserService.selectByRoleKey("two");

        goOutInventoryRVO.setFirstPeople(userListone);
        goOutInventoryRVO.setTwoPeople(userListtwo);

        return goOutInventoryRVO;
    }

    @Override
    public Boolean goOut(GoOutInfo goOutInfo) {

        UpdateWrapper<OutInventory> outupdateWrapper = new UpdateWrapper<>();
        outupdateWrapper.eq("in_man_id",goOutInfo.getId())
                .set("del_flag",1);
        outInventoryService.update(outupdateWrapper);
        OutInventory outInventory = new OutInventory();
        BeanUtils.copyProperties(goOutInfo,outInventory);
        outInventory.setInManId(Integer.valueOf(goOutInfo.getId()));
        outInventory.setCreatorId(SecurityUtils.getUserId().intValue());

        boolean save = outInventoryService.save(outInventory);

        if (!save) {
           return false;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("assignee0", goOutInfo.getFirstPeopleId());
        map.put("assignee1", goOutInfo.getTwoPeopleId());
        String auditId = processService.startProcessInstance("outInventory", goOutInfo.getId(), map);

        UpdateWrapper<InventoryManagement> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",goOutInfo.getId())
                .set("status",1);
        boolean update = this.update(null, updateWrapper);
        return update;
    }

    public Boolean auditFlow(HandleAuditParam param){
        UpdateWrapper<InventoryManagement> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",param.getAuditId());

        Boolean aBoolean = processService.handleAudit(param);

        if (!param.isPassed()){
            updateWrapper.set("status",2);
            this.update(null,updateWrapper);
        }
        if (param.isPassed() && aBoolean){
            updateWrapper.set("status",3);
            this.update(null,updateWrapper);
        }
        return true;
    }

    @Override
    public Boolean inster(InventoryManagement inventoryManagement) {

        inventoryManagement.setCreatorId(SecurityUtils.getUserId());
        return this.save(inventoryManagement);
    }

    @Override
    public AjaxResult deleteById(Integer id) {

        UpdateWrapper<InventoryManagement> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("del_flag",1);
        boolean update = this.update(null, updateWrapper);
        return  AjaxResult.success(update);

    }

    @Override
    public Boolean updateInventory(UpdateInventoryVO vo) {


        UpdateWrapper<InventoryManagement> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",vo.getId());

        updateWrapper.set("remark",vo.getRemark())
                .set("is_normal",vo.getIsNormal())
                .set("settle_status",vo.getSettleStatus());

        return this.update(updateWrapper);

    }

    @Override
    public PriceAllInfoRVO getAllInfo(Integer id) {

        PriceAllInfoRVO priceAllInfoRVO = new PriceAllInfoRVO();
        InventoryManagement inventoryManagement = inventoryManagementMapper.selectById(id);
        priceAllInfoRVO.setInventoryManagement(inventoryManagement);
        QueryWrapper<OutInventory> outInventoryQueryWrapper = new QueryWrapper<>();
        outInventoryQueryWrapper.eq("in_man_id",id)
                .eq("del_flag",0);
        OutInventory one = outInventoryService.getOne(outInventoryQueryWrapper);
        priceAllInfoRVO.setPaid(one.getPaid());
        priceAllInfoRVO.setOutRemark(one.getOutRemark());
        priceAllInfoRVO.setOutboundReason(one.getOutboundReason());
        priceAllInfoRVO.setParkingFees(one.getParkingFees());
        priceAllInfoRVO.setVehicleRecipient(one.getVehicleRecipient());
        priceAllInfoRVO.setOutDate(one.getOutDate());

        List<SysUser> userListone = sysUserService.selectByRoleKey("first");
        List<SysUser> userListtwo = sysUserService.selectByRoleKey("two");

        priceAllInfoRVO.setFirstPeople(userListone);
        priceAllInfoRVO.setTwoPeople(userListtwo);
        List<String> historyPeopleId = processService.getHistoryPeopleId(id.toString());
        if (historyPeopleId.size() == 2){
        priceAllInfoRVO.setFirstPeopleId(Integer.valueOf(historyPeopleId.get(0)));
        priceAllInfoRVO.setTwoPeopleId(Integer.valueOf(historyPeopleId.get(1)));}
        return priceAllInfoRVO;
    }






    @Override
    public Boolean updatePriceStatus(GoOutInfo goOutInfo){
        return outInventoryService.updatePriceStatus(goOutInfo);
    }

    @Override
    public AuditHistoryRVO getAuditHistoryRVO(Integer id) {


        AuditHistoryRVO auditHistoryRVO = new AuditHistoryRVO();
        PriceAllInfoRVO allInfo = this.getAllInfo(id);
        auditHistoryRVO.setInventoryManagement(allInfo.getInventoryManagement());
        BeanUtils.copyProperties(allInfo,auditHistoryRVO);

        List<AuditUserInfo> historyInfo = processService.getHistoryInfo(id);
        auditHistoryRVO.setAuditUserInfoList(historyInfo);
        return auditHistoryRVO;
    }




    private Integer selectPrice(Integer s, Integer day,Integer money,Integer seven){


        Integer prices = 0;

        if(s == 1){
            prices = day * money;

        }
        if (s == 2){
            double time = day/31;
            prices = (int)Math.ceil(time) * money;
        }
        if (s == 3){
            if (day <= 7){
                prices = seven;
            }else {
                double time = day/31;
                prices = (int)Math.ceil(time) * money;
            }

        }
        return prices;

    }


}
