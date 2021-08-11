package com.wlgs.netty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author whb
 * @version 1.0
 * @date 2021-07-28 17:17
 */
@SpringBootApplication
public class NettyApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(NettyApplication.class, args);
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(new Class[]{NettyApplication.class});
    }
}
