package com.xzt.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzt.inventory.common.PageUtil;
import com.xzt.inventory.domain.XztGaragePrice;
import com.xzt.inventory.mapper.XztGaragePriceMapper;
import com.xzt.inventory.rvo.GaragePiceListRVO;
import com.xzt.inventory.service.XztGaragePriceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class XztGaragePriceServiceImpl extends ServiceImpl<XztGaragePriceMapper, XztGaragePrice> implements XztGaragePriceService {


    @Resource
    private XztGaragePriceMapper xztGaragePriceMapper;

    @Override
    public PageInfo<GaragePiceListRVO> getList(PageUtil pageUtil) {


        QueryWrapper<XztGaragePrice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag",0);
        List<GaragePiceListRVO> xztGaragePrices = xztGaragePriceMapper.geList(queryWrapper);
        queryWrapper.eq("p_id",0);
        PageHelper.startPage(pageUtil.getPageNum(),pageUtil.getPageSize());
        List<GaragePiceListRVO> list = xztGaragePriceMapper.geList(queryWrapper);
        PageInfo<GaragePiceListRVO> garagePiceListRVOPageInfo = new PageInfo<>(list);
        for (GaragePiceListRVO g :
                list) {
            for (GaragePiceListRVO l:
                 xztGaragePrices) {
                if(l.getPId() == g.getId())
                    g.getChileds().add(l);
            }
        }



        return garagePiceListRVOPageInfo;
    }

    @Override
    public Boolean insert(XztGaragePrice xztGaragePrice) {
        return this.save(xztGaragePrice);
    }


    public Boolean delById(Integer id){
        UpdateWrapper<XztGaragePrice> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id)
                .set("del_flag",1);
        return this.update(null,updateWrapper);
    }



    public XztGaragePrice getInfo(Integer id){


        return this.getById(id);

    }

    @Override
    public Boolean updatePrice(XztGaragePrice xztGaragePrice) {


        UpdateWrapper<XztGaragePrice> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",xztGaragePrice.getId())
                .set("del_flag",1);
        boolean update = this.update(updateWrapper);
        xztGaragePrice.setId(null);
        boolean save = this.save(xztGaragePrice);
        return true;
    }

    @Override
    public List<XztGaragePrice> selectList() {
        QueryWrapper<XztGaragePrice> xztGaragePriceQueryWrapper = new QueryWrapper<>();
        xztGaragePriceQueryWrapper.ne("p_id",0)
                .eq("del_flag",0);
        List<XztGaragePrice> xztGaragePrices = xztGaragePriceMapper.selectList(xztGaragePriceQueryWrapper);
        return xztGaragePrices;
    }
}
