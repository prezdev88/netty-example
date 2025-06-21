package cl.prezdev.component;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import cl.prezdev.devtour.DevTour;

@Slf4j
@DevTour(order = 3, description = "Logs client connection and disconnection information")
@Component
public class ClientInfoLogger {

    public void logClientInfo(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();

        log.info("📡 New client connected");
        log.info("Remote Address      : {}", channel.remoteAddress());
        log.info("Local Address       : {}", channel.localAddress());
        log.info("Channel ID          : {}", channel.id().asShortText());
        log.info("Is Active?          : {}", channel.isActive());
        log.info("Is Open?            : {}", channel.isOpen());
        log.info("Is Writable?        : {}", channel.isWritable());
        log.info("Is Registered?      : {}", channel.isRegistered());
        log.info("Pipeline Handlers   : {}", channel.pipeline().names());
    }

    public void logClientDisconnection(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();

        log.info("🔌 Client disconnected");
        log.info("Remote Address      : {}", channel.remoteAddress());
        log.info("Channel ID          : {}", channel.id().asShortText());
        log.info("Was Active?         : {}", channel.isActive());
        log.info("Was Open?           : {}", channel.isOpen());
    }
}
