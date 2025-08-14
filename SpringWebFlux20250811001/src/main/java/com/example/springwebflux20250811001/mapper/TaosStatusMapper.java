package com.example.springwebflux20250811001.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springwebflux20250811001.entity.tdengine.TestXY;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

@Mapper
public interface TaosStatusMapper extends BaseMapper<TestXY> {
    @Select("SHOW TABLES")
    Set<String> getTables();

    @Insert("create table ${tableName} using ${sTable} (name) tags(#{tag})")
    void createTable(String tableName,String sTable,String tag);

    @Insert("INSERT INTO ${table} (ts,x,y) VALUES (#{testxy.ts},#{testxy.x},#{testxy.y})")
    void saveToTable(TestXY testxy,String table);
}
