package com.wlgs.netty.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author whb
 * @version 1.0
 * @date 2021-07-28 17:26
 */
@Component
@Qualifier("nettyServerHandler")
@ChannelHandler.Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    //日志
    private static Logger logger = LoggerFactory.getLogger("NettyServerHandler.class");

    /**
     * 服务器读取数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        logger.info("接收到数据 {} " + msg);
    }

    /**
     * 表示连接建立，一旦连接，第一个被执行
     */

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        logger.info("ctx " + ctx);
    }

    /**
     * 断开连接提示
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        logger.info("channel断开连接 {} " + channel.toString());
    }

    /**
     * 异步发送数据给客户端
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("上线了~ [ id= " + ctx.channel().id().asLongText() + "]" + "[ address= " + ctx.channel().remoteAddress() + "] ");
    }

    /**
     * 表示channel 处于不活动状态, 提示 xx离线了
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info(ctx.channel().remoteAddress() + " 离线了~");
    }

    /**
     * 关闭通道
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("异常捕获，关闭通道 {} " + ctx + " " + cause);
        ctx.close();
    }

    /**
     * 心跳检测
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        boolean flag = false;
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = null;
            switch (event.state()) {
                case READER_IDLE:
                    flag = true;
                    break;
            }
        }
        if (flag) {
            Channel channel = ctx.channel();
            if (channel != null && !channel.isActive() && !channel.isOpen() && !channel.isWritable()) {
                ctx.channel().close();
            }
        }
    }
}
