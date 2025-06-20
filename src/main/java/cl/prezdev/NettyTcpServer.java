package cl.prezdev;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Component
@RequiredArgsConstructor
public class NettyTcpServer {

    @Value("${tcp.server.port:9090}")
    private int port;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private final TcpChannelInitializer tcpChannelInitializer;

    @PostConstruct
    public void start() throws InterruptedException {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(tcpChannelInitializer);

        ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
        log.info("Servidor TCP iniciado en el puerto {}", port);

        registerCloseListener(channelFuture);
    }

    private void registerCloseListener(ChannelFuture channelFuture) {
        channelFuture.channel().closeFuture().addListener((ChannelFuture future) -> {
            if (future.isSuccess()) {
                log.info("Canal cerrado exitosamente: {}", future.channel().remoteAddress());
            } else {
                log.warn("Error al cerrar el canal", future.cause());
            }
        });
    }

    @PreDestroy
    public void shutdown() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        log.info("Servidor TCP detenido");
    }
}
