package com.example.springwebflux20250811001.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("users")
public class User {
    Integer id;
    String name;
    Integer age;
    String email;
}
