package com.jn.search;


import com.jn.WebApplication;
import com.jn.web.search.pojo.SkuInfo;
import com.jn.web.search.service.SearchManagerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@SpringBootTest(classes = WebApplication.class)
public class IndexManagerTest {
    @Autowired
    private SearchManagerService searchManagerService;
    @Autowired
    private ElasticsearchRestTemplate template;

    //重新构建索引库: 删除索引、新建索引库、配置映射
    @Test
    public void deleteIndex() {
        searchManagerService.deleteIndex();
    }

    @Test
    public void createIndex() {
        searchManagerService.createIndexAndMapping();
        template.putMapping(SkuInfo.class);
    }

    //初始化导入所有商品搜索数据
    @Test
    public void importData() {
        //searchManagerService.importAllByPage(1000);
    }

}
