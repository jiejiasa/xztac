package com.xzt.inventory.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzt.common.core.domain.AjaxResult;
import com.xzt.common.core.domain.entity.SysUser;
import com.xzt.inventory.domain.InventoryManagement;
import com.xzt.inventory.mapper.InventoryManagementMapper;
import com.xzt.inventory.rvo.GoOutInventoryRVO;
import com.xzt.inventory.service.InventoryManagementService;
import com.xzt.inventory.vo.GoOutInfo;
import com.xzt.inventory.vo.InventoryManagementSelectVO;
import com.xzt.service.IProcessService;
import com.xzt.system.service.ISysUserService;
import com.xzt.vo.HandleAuditParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class InventoryManagementServiceImpl extends ServiceImpl<InventoryManagementMapper, InventoryManagement> implements InventoryManagementService {

    @Resource
    private InventoryManagementMapper inventoryManagementMapper;


    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IProcessService processService;

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


        HashMap<String, Object> map = new HashMap<>();
        map.put("assignee0",goOutInfo.getFirstPeopleId());
        map.put("assignee1","101");

        String outInventory = processService.startProcessInstance("outInventory", goOutInfo.getId(), map);



        return true;
    }



    public Boolean auditFlow(HandleAuditParam param){
        Boolean aBoolean = processService.handleAudit(param);
        return aBoolean;
    }


}
