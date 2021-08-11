package com.wlgs.netty.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


/**
 * @author whb
 * @version 1.0
 * @date 2021-07-28 17:26
 */
@Component
@Qualifier("nettyServerInitializer")
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private NettyServerHandler nettyServerHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("logging", new LoggingHandler(LogLevel.INFO));
        pipeline.addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
        pipeline.addLast("nettyServerHandler", nettyServerHandler);
    }
}
