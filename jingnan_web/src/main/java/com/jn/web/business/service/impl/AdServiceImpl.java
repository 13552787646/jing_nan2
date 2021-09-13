package com.jn.web.business.service.impl;

import com.jn.pojo.Ad;
import com.jn.web.business.dao.AdMapper;
import com.jn.web.business.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * @Author yaxiongliu
 **/
@Service
public class AdServiceImpl implements AdService {
    @Autowired
    private AdMapper adMapper;

    @Override
    public List<Ad> findAdListByPosition(String position) {
        return adMapper.findAdListByPosition(position);
    }
}
