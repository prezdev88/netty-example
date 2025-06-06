package cl.spring.tcp.example;

import org.springframework.stereotype.Component;

// Importaciones necesarias de Netty, Jakarta y Lombok
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

@Slf4j // Lombok: genera automáticamente un logger llamado 'log'
@Component // Indica que esta clase es un componente de Spring y se puede inyectar
public class NettyTcpServer {

    private static final int PORT = 6060; // Puerto donde escuchará el servidor

    // Grupo de hilos para aceptar conexiones (uno solo)
    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    // Grupo de hilos para manejar el tráfico de las conexiones aceptadas
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    private Channel serverChannel; // Canal principal del servidor

    public void start() {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap(); // Bootstrap para configurar el servidor

            // Configuración del bootstrap: grupos de hilos, tipo de canal y pipeline
            bootstrap.group(bossGroup, workerGroup)
                     .channel(NioServerSocketChannel.class) // Canal para sockets TCP no bloqueantes
                     .childHandler(new ChannelInitializer<SocketChannel>() { // Inicializador del pipeline para cada conexión
                         @Override
                         protected void initChannel(SocketChannel ch) {
                             // Agrega un handler de logging para registrar eventos de red
                             ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                             // Handler personalizado para procesar los mensajes recibidos
                             ch.pipeline().addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                                 @Override
                                 protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
                                     // Lee los bytes recibidos y los convierte a String
                                     byte[] data = new byte[msg.readableBytes()];
                                     msg.readBytes(data);
                                     String received = new String(data);
                                     log.debug("Received: {}", received); // Loguea el mensaje recibido
                                 }

                                 @Override
                                 public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                                     // Si ocurre un error en la conexión, se loguea y se cierra el canal
                                     log.error("Connection error", cause);
                                     ctx.close();
                                 }
                             });
                         }
                     })
                     .option(ChannelOption.SO_BACKLOG, 128) // Tamaño de la cola de conexiones pendientes
                     .childOption(ChannelOption.SO_KEEPALIVE, true); // Mantiene las conexiones activas

            // Liga el servidor al puerto y espera a que esté listo
            ChannelFuture future = bootstrap.bind(PORT).sync();
            serverChannel = future.channel(); // Guarda el canal principal

            log.info("TCP server started on port {}", PORT); // Loguea que el servidor está listo

            // Espera a que el canal principal se cierre (bloqueante)
            serverChannel.closeFuture().sync();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restaura el estado de interrupción
            log.error("TCP server interrupted", e); // Loguea la interrupción
        } catch (Exception e) {
            log.error("Error starting TCP server", e); // Loguea cualquier otro error
        } finally {
            shutdown(); // Siempre intenta cerrar los recursos al final
        }
    }

    @PreDestroy // Indica que este método se ejecuta antes de destruir el bean (al cerrar la app)
    public void shutdown() {
        log.info("Shutting down TCP server..."); // Loguea el cierre
        if (serverChannel != null) {
            serverChannel.close(); // Cierra el canal principal si está abierto
        }
        bossGroup.shutdownGracefully(); // Libera los recursos del grupo boss
        workerGroup.shutdownGracefully(); // Libera los recursos del grupo worker
    }
}