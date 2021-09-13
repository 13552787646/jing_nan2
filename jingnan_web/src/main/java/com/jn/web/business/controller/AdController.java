package com.jn.web.business.controller;

import com.alibaba.fastjson.JSONObject;
import com.jn.entity.Result;
import com.jn.entity.StatusCode;
import com.jn.pojo.Ad;
import com.jn.web.business.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * @Author yaxiongliu
 **/
@RestController
@RequestMapping("/ad")
public class AdController {
    @Autowired
    private AdService adService;
    //根据广告位置查询广告列表
    @RequestMapping("/ad_read")
    public Result findAdListByPosition(String position){
        List<Ad> adList = adService.findAdListByPosition(position);
        return new Result(true, StatusCode.OK,"",JSONObject.toJSONString(adList));
    }
}
