package com.jn.web.business.service;

import com.jn.pojo.Ad;

import java.util.List;

/*
 * @Author yaxiongliu
 **/
public interface AdService {
    //根据ad的位置查询广告列表
    List<Ad> findAdListByPosition(String position);
}
