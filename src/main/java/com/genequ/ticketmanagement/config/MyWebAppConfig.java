package com.genequ.ticketmanagement.config;

import com.github.pagehelper.PageHelper;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Properties;

@Configuration
public class MyWebAppConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置模板资源路径
        registry.addResourceHandler("/**").addResourceLocations("classpath:/");
        registry.addResourceHandler("/static").addResourceLocations("classpath:/static");
    }

    /**
     * 配置pageHelper分页支持
     * @return
     */
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");//将RowBounds第一个参数offset当成pageNum页码使用
        p.setProperty("rowBoundsWithCount", "true");//使用RowBounds分页会进行count查询
        p.setProperty("reasonable", "true");//合理化
        p.setProperty("dialect", "mysql");
        p.setProperty("supportMethodsArguments", "false");
        p.setProperty("pageSizeZero", "true"); //设置为true时，如果pageSize=0或者RowBounds.limit = 0就会查询出全部的结果
        pageHelper.setProperties(p);
        return pageHelper;
    }


}