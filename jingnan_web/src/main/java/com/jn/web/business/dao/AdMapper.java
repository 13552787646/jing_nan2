package com.jn.web.business.dao;

import com.jn.pojo.Ad;
import com.jn.pojo.Address;
import com.jn.pojo.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface AdMapper extends Mapper<Ad> {
    @Select("select url,image from tb_ad where status ='1' and position=#{position} and start_time<= NOW() AND end_time>= NOW()")
    List<Ad> findAdListByPosition(@Param("position") String position);
}
