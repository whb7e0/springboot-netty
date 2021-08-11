package com.wlgs.netty.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author whb
 * @version 1.0
 * @date 2021-07-28 17:21
 */
@Component
@Data
public class NettyServer {

    private ChannelFuture channelFuture;

    @Autowired
    @Qualifier("nettyServerBootstrap")
    private ServerBootstrap nettyServerBootstrap;

    @PostConstruct
    public void start() throws Exception {
        this.channelFuture = this.nettyServerBootstrap.bind(8888).sync();
    }

    @PreDestroy
    public void destroy() throws Exception {
        this.channelFuture.channel().closeFuture().sync();
    }
}
