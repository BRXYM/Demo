package com.example.springwebflux20250811001.entity.tdengine;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestXY {
    @TableId(value = "ts")
    private Long ts;

    // tag
    @TableField("name")
    private String name;

    @TableField("x")
    private Double x;

    @TableField("y")
    private Double y;
}
