package cl.prezdev.handler.idle;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Sharable
@Component
public class HeartbeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent event) {
            if (event.state() == IdleState.READER_IDLE) {
                String message = "⏱️ Read timeout: cerrando conexión con channel id " + ctx.channel().id().asLongText();
                log.warn(message);
                ctx.writeAndFlush(message).addListener(ChannelFutureListener.CLOSE);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
