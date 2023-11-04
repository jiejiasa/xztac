package com.xzt.web.crk;

import com.github.pagehelper.PageInfo;
import com.xzt.inventory.common.PageUtil;
import com.xzt.inventory.domain.XztGaragePrice;
import com.xzt.inventory.rvo.GaragePiceListRVO;
import com.xzt.inventory.service.XztGaragePriceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/garagePrice")
public class XztGaragePriceController {


    @Resource
    private XztGaragePriceService xztGaragePriceService;


    @PostMapping("/getList")
    public PageInfo<GaragePiceListRVO> getList(@RequestBody PageUtil pageUtil){
        return xztGaragePriceService.getList(pageUtil);
    }


    @PostMapping("/insert")
    public Boolean insert(@RequestBody XztGaragePrice xztGaragePrice){
        return xztGaragePriceService.insert(xztGaragePrice);
    }


    @GetMapping("/getInfo")
    public XztGaragePrice getInfo(@RequestParam Integer id){


        return xztGaragePriceService.getInfo(id);

    }

    @PostMapping("/update")
    public Boolean getInfo(@RequestBody XztGaragePrice xztGaragePrice){


        return xztGaragePriceService.updatePrice(xztGaragePrice);

    }


}
