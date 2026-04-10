package com.yupi.yuaicodemother;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.yupi.yuaicodemother.Mapper")
public class YuAiCoderMotherApplication {

    public static void main(String[] args) {
        SpringApplication.run(YuAiCoderMotherApplication.class, args);
    }

}
