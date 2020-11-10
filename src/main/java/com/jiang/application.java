package com.jiang;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Description TODO
 * @Created jiang
 */
@SpringBootApplication
@Slf4j
public class application {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ConfigurableApplicationContext run = SpringApplication.run(application.class, args);
        long duration = (System.currentTimeMillis() - start) / 1000;
//        String info = "启动完成，共花费{}秒，访问地址：http://{}:{}/doc.html";
        String info = "启动完成，共花费{}秒，访问地址：http://localhost:{}/doc.html";
        String address = run.getEnvironment().getProperty("spring.cloud.client.ip-address");
        String port = run.getEnvironment().getProperty("server.port");
//        log.info(info, duration, address, port);
        log.info(info, duration, port);
    }

}
