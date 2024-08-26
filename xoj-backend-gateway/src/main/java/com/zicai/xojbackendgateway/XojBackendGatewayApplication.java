package com.zicai.xojbackendgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient // Nacos 注册服务
public class XojBackendGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(XojBackendGatewayApplication.class, args);
    }

}
