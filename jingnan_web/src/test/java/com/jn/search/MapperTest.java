package com.jn.search;


import com.jn.WebApplication;
import com.jn.web.search.mapper.SkuBigMapper;
import com.jn.web.search.pojo.SkuBig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest(classes = WebApplication.class)
@Slf4j
public class MapperTest {
    @Autowired
    private SkuBigMapper skuMapper;

    @Test
    public void tableCount() {
        Long count = skuMapper.countAll("tb_sku");
        System.out.println(count);
    }

    @Test
    public void testFindListByPage() {
        log.info("开始查询");
        List<SkuBig> list = skuMapper.getListByPage(1359385554453651495l, 10);
        System.out.println(list.size());
        log.info("结束查询");
    }


}
