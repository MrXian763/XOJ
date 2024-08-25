package com.zicai.xojbackendjudgeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.zicai") // 加载到不同服务下的 bean
@EnableDiscoveryClient // Nacos 注册服务
@EnableFeignClients(basePackages = {"com.zicai.xojbackendserviceclient.service"}) // Feign 调用服务
public class XojBackendJudgeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(XojBackendJudgeServiceApplication.class, args);
    }

}
