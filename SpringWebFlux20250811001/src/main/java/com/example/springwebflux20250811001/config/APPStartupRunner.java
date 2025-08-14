package com.example.springwebflux20250811001.config;

import com.example.springwebflux20250811001.entity.SpringStatus;
import com.example.springwebflux20250811001.mapper.TaosStatusMapper;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class APPStartupRunner implements CommandLineRunner {

    @Resource
    TaosStatusMapper taosStatusMapper;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("加载数据库tag列表");
        SpringStatus.TABLE_LIST = taosStatusMapper.getTables();
        System.out.println(SpringStatus.TABLE_LIST);
    }
}
