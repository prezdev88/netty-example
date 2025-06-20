package cl.prezdev.handler;

import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Sharable
@Component
@AllArgsConstructor
public class TcpServerHandler2 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String message = (String) msg;
        log.info("Mensaje recibido: {}", message);
        ctx.writeAndFlush("Handler 2: " + message + "\n");
        ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        cause.printStackTrace();
        channelHandlerContext.close();
    }
}

