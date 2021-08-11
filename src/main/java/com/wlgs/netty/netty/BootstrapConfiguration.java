package com.wlgs.netty.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author whb
 * @version 1.0
 * @date 2021-07-28 17:33
 */
@Configuration
public class BootstrapConfiguration {

    @Autowired
    @Qualifier("nettyServerInitializer")
    private NettyServerInitializer nettyServerInitializer;

    @Bean(name = {"nettyServerBootstrap"})
    public ServerBootstrap bootstrap() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup(), workerGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(nettyServerInitializer);
        return serverBootstrap;
    }

    @Bean(name = {"bossGroup"}, destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup() {
        return new NioEventLoopGroup();
    }

    @Bean(name = {"workerGroup"}, destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup() {
        return new NioEventLoopGroup();
    }
}
