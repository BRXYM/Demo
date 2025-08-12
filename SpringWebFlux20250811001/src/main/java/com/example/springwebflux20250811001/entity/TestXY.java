package com.example.springwebflux20250811001.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "test_data")
public class TestXY {
    String name;
    Double x;
    Double y;
}
