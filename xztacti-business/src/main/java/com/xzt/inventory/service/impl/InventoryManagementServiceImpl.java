package com.xzt.inventory.service.impl;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzt.common.core.domain.AjaxResult;
import com.xzt.common.core.domain.entity.SysUser;
import com.xzt.common.utils.HutoolUtils;
import com.xzt.common.utils.SecurityUtils;
import com.xzt.common.utils.StringUtils;
import com.xzt.common.utils.bean.BeanUtils;
import com.xzt.inventory.domain.InventoryManagement;
import com.xzt.inventory.domain.OutInventory;
import com.xzt.inventory.domain.XztGaragePrice;
import com.xzt.inventory.importPackage.InventoryManagementImport;
import com.xzt.inventory.importPackage.InventoryManagementOut;
import com.xzt.inventory.mapper.InventoryManagementMapper;
import com.xzt.inventory.mapper.XztGaragePriceMapper;
import com.xzt.inventory.rvo.AuditHistoryRVO;
import com.xzt.inventory.rvo.GaragePiceListRVO;
import com.xzt.inventory.rvo.GoOutInventoryRVO;
import com.xzt.inventory.rvo.PriceAllInfoRVO;
import com.xzt.inventory.service.InInventoryService;
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
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class InventoryManagementServiceImpl extends ServiceImpl<InventoryManagementMapper, InventoryManagement> implements InventoryManagementService {

    @Resource
    private InventoryManagementMapper inventoryManagementMapper;



    @Resource
    private InInventoryService inInventoryService;



    @Resource
    private XztGaragePriceMapper xztGaragePriceMapper;



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
        QueryWrapper<InventoryManagementSelectVO> queryWrapper = new QueryWrapper<>();
        if (!"null".equals(vo.getStatus()) && !ObjectUtils.isEmpty(vo.getStatus()))
            queryWrapper.eq("im.status",vo.getStatus());
        if (!"null".equals(vo.getSettleStatus()) && !ObjectUtils.isEmpty(vo.getSettleStatus()))
            queryWrapper.eq("im.settle_status",vo.getSettleStatus());
        if (!"null".equals(vo.getRegion()) && StringUtils.isNotBlank(vo.getRegion()) && !vo.getRegion().isEmpty())
            queryWrapper.like("im.region",vo.getRegion());
        if (!"null".equals(vo.getBusinessType()) && StringUtils.isNotBlank(vo.getBusinessType()) && !vo.getBusinessType().isEmpty())
            queryWrapper.like("im.business_type",vo.getBusinessType());
        if (!"null".equals(vo.getClearanceTeam()) && StringUtils.isNotBlank(vo.getClearanceTeam()) && !vo.getClearanceTeam().isEmpty())
            queryWrapper.like("im.clearance_team",vo.getClearanceTeam());
        if (!"null".equals(vo.getCustomerName()) && StringUtils.isNotBlank(vo.getCustomerName()) && !vo.getCustomerName().isEmpty())
            queryWrapper.like("im.customer_name",vo.getCustomerName());
        if (!ObjectUtils.isEmpty(vo.getInboundDates()))
            queryWrapper.ge("im.inbound_date",vo.getInboundDates().get(0)).le("im.inbound_date",vo.getInboundDates().get(1));
        if (!ObjectUtils.isEmpty(vo.getOutDates()))
            queryWrapper.ge("oi.out_date",vo.getOutDates().get(0)).le("oi.out_date",vo.getOutDates().get(1));
        queryWrapper.eq("im.del_flag",0)
                        .orderByDesc("time");

        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<InventoryManagement> inventoryManagementList = inventoryManagementMapper.selectByParam(queryWrapper);
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean inventoryManagementImport(MultipartFile file ) throws Exception {

        if (file.isEmpty()){
            throw new Exception("文件不能为空");
        }


        QueryWrapper<XztGaragePrice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag",0);
        queryWrapper.ne("p_id",0);
        List<GaragePiceListRVO> xztGaragePrices = xztGaragePriceMapper.geList(queryWrapper);


        List<InventoryManagementImport> objects = EasyExcel.read(file.getInputStream(), InventoryManagementImport.class, null).sheet().doReadSync();


        for (InventoryManagementImport i: objects) {


            InventoryManagement inventoryManagement = new InventoryManagement();


            try {


                BeanUtils.copyProperties(i, inventoryManagement);


                if ("正常".equals(i.getIsNormalName()))
                    inventoryManagement.setIsNormal(0);
                if ("异常".equals(i.getIsNormalName()))
                    inventoryManagement.setIsNormal(1);
                if ("未知".equals(i.getIsNormalName()))
                    inventoryManagement.setIsNormal(2);

                if ("未支付".equals(i.getSettleStatusName()))
                    inventoryManagement.setSettleStatus(0);
                if ("已支付".equals(i.getSettleStatusName()))
                    inventoryManagement.setSettleStatus(1);
            }catch (Exception e){
                throw new Exception("文件内容错误");
            }


            for (GaragePiceListRVO x: xztGaragePrices) {
                if (x.getGarageTypeOrName().equals(i.getParkingGarageName())){
                    inventoryManagement.setParkingGarage(x.getId());
                    break;
                }
                if (x.getId().equals(xztGaragePrices.get(xztGaragePrices.size() -1).getId())){
                    throw new Exception("停放车库未找到");
                }
            }


            Boolean inster = this.inster(inventoryManagement);
            Boolean inster1 = inInventoryService.inster(inventoryManagement);
            return inster1 && inster;


        }

        return true;
    }






    public void getTemplate( HttpServletResponse response) throws IOException {


        String filePath = "C:\\Users\\19177\\Desktop\\导入模板.xls";

        File file = FileUtil.file(filePath);
        BufferedInputStream bufferedInputStream = null;

        ServletOutputStream out = null;
            bufferedInputStream= FileUtil.getInputStream(file);
            int buffer = 1024 * 10;
            byte[] data = new byte[buffer];
            try {
                String fileName = URLEncoder.encode("导入模板.xlsx", "UTF-8");
                response.setContentType("multipart/form-data");
                response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
                int read;
                out = response.getOutputStream();
                while ((read = bufferedInputStream.read(data)) != -1) {
                    out.write(data, 0, read);
                }
                out.flush();
            } catch (Exception ex) {
                IoUtil.close(out);

            } finally {
                IoUtil.close(out);
            }

    }




    @Override
    public void exportExcle(InventoryManagementSelectVO vo,HttpServletResponse response) throws Exception {

        ArrayList<InventoryManagementOut> inventoryManagementOuts = new ArrayList<>();


        QueryWrapper<InventoryManagementSelectVO> queryWrapper = new QueryWrapper<>();
        if (!"null".equals(vo.getStatus()) && !ObjectUtils.isEmpty(vo.getStatus()))
            queryWrapper.eq("im.status",vo.getStatus());
        if (!"null".equals(vo.getSettleStatus()) && !ObjectUtils.isEmpty(vo.getSettleStatus()))
            queryWrapper.eq("im.settle_status",vo.getSettleStatus());
        if (!"null".equals(vo.getRegion()) && StringUtils.isNotBlank(vo.getRegion()) && !vo.getRegion().isEmpty())
            queryWrapper.like("im.region",vo.getRegion());
        if (!"null".equals(vo.getBusinessType()) && StringUtils.isNotBlank(vo.getBusinessType()) && !vo.getBusinessType().isEmpty())
            queryWrapper.like("im.business_type",vo.getBusinessType());
        if (!"null".equals(vo.getClearanceTeam()) && StringUtils.isNotBlank(vo.getClearanceTeam()) && !vo.getClearanceTeam().isEmpty())
            queryWrapper.like("im.clearance_team",vo.getClearanceTeam());
        if (!"null".equals(vo.getCustomerName()) && StringUtils.isNotBlank(vo.getCustomerName()) && !vo.getCustomerName().isEmpty())
            queryWrapper.like("im.customer_name",vo.getCustomerName());
        if (!ObjectUtils.isEmpty(vo.getInboundDates()))
            queryWrapper.ge("im.inbound_date",vo.getInboundDates().get(0)).le("im.inbound_date",vo.getInboundDates().get(1));
        if (!ObjectUtils.isEmpty(vo.getOutDates()))
            queryWrapper.ge("oi.out_date",vo.getOutDates().get(0)).le("oi.out_date",vo.getOutDates().get(1));
        queryWrapper.eq("im.del_flag",0)
                .orderByDesc("time");


        List<InventoryManagement> inventoryManagementList = inventoryManagementMapper.selectByParam(queryWrapper);
        if (!inventoryManagementList.isEmpty()) {
            for (InventoryManagement i : inventoryManagementList) {

                Integer integer = selectPrice(i.getPId(), i.getInDay(), i.getDayOrMonthPrice(), i.getFixedPrice());
                InventoryManagementOut inventoryManagementOut = new InventoryManagementOut();
                BeanUtils.copyProperties(i,inventoryManagementOut);
                if (i.getStatus() == 0)
                    inventoryManagementOut.setStatus("在库");
                if (i.getStatus() == 1)
                    inventoryManagementOut.setStatus("审核中");
                if (i.getStatus() == 2)
                    inventoryManagementOut.setStatus("审核未通过");
                if (i.getStatus() == 3)
                    inventoryManagementOut.setStatus("出库");

                if (i.getSettleStatus() == 0)
                    inventoryManagementOut.setSettleStatus("未支付");
                if (i.getSettleStatus() == 1)
                    inventoryManagementOut.setSettleStatus("已支付");
                inventoryManagementOut.setInDayMony(integer);

                if(ObjectUtils.isEmpty(i.getPaid()))
                    inventoryManagementOut.setPaid("");
                else {
                    if (i.getPaid() == 0)
                        inventoryManagementOut.setPaid("未支付");
                    if (i.getPaid() == 1)
                        inventoryManagementOut.setPaid("已支付");
                }
                if(ObjectUtils.isEmpty(i.getIsNormal()))
                    inventoryManagementOut.setIsNormal("");
                else {
                if (i.getIsNormal() == 0)
                    inventoryManagementOut.setIsNormal("正常");
                if (i.getIsNormal() == 1)
                    inventoryManagementOut.setIsNormal("异常");
                if (i.getIsNormal() == 2)
                    inventoryManagementOut.setIsNormal("未知");
                }
                inventoryManagementOut.setInDayMony(integer);
                inventoryManagementOuts.add(inventoryManagementOut);
            }

        }
        HutoolUtils.downloadData(inventoryManagementOuts,InventoryManagementOut.class,response,"库存信息导出.xls");

    }

}
