package cl.prezdev.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import cl.prezdev.component.ClientInfoLogger;

@Slf4j
@Sharable
@Component
@AllArgsConstructor
public class ConnectionLoggingHandler extends ChannelInboundHandlerAdapter {

    private final ClientInfoLogger clientInfoLogger;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        clientInfoLogger.logClientInfo(ctx);
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        clientInfoLogger.logClientDisconnection(ctx);
        ctx.fireChannelInactive();
    }
}
