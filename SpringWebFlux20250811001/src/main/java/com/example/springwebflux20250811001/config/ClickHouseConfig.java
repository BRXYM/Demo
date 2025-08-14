//package com.example.springwebflux20250811001.config;
//
//import io.r2dbc.spi.ConnectionFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
//import org.springframework.data.r2dbc.dialect.R2dbcDialect;
//import org.springframework.data.relational.core.dialect.AnsiDialect;
//import org.springframework.data.relational.core.dialect.Dialect;
//import org.springframework.r2dbc.core.DatabaseClient;
//import org.springframework.r2dbc.core.binding.BindMarkersFactory;
//
//
//@Configuration
//public class ClickHouseConfig {
//
//    @Bean
//    public R2dbcEntityTemplate r2dbcEntityTemplate(ConnectionFactory connectionFactory) {
//        Dialect dialect = AnsiDialect.INSTANCE; // ClickHouse 比较接近 ANSI SQL
//
//        DatabaseClient client = DatabaseClient.builder()
//                .connectionFactory(connectionFactory)
//                .bindMarkers(BindMarkersFactory.anonymous("?")) // ClickHouse 用 ?
//                .build();
//
//        return new R2dbcEntityTemplate(client, (R2dbcDialect) dialect);
//    }
//}