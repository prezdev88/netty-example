package cl.prezdev;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cl.prezdev.handler.ConnectionLoggingHandler;
import cl.prezdev.handler.TcpServerHandler1;
import cl.prezdev.handler.TcpServerHandler2;
import cl.prezdev.handler.idle.HeartbeatHandler;

@Component
@RequiredArgsConstructor
public class TcpChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Value("${tcp.idle.read.timeout:10}")
    private int readTimeout;

    @Value("${tcp.idle.write.timeout:0}")
    private int writeTimeout;

    @Value("${tcp.idle.all.timeout:0}")
    private int allTimeout;

    private final TcpServerHandler1 tcpServerHandler;
    private final TcpServerHandler2 tcpServerHandler2;
    private final ConnectionLoggingHandler connectionLoggingHandler;
    private final HeartbeatHandler heartbeatHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline p = socketChannel.pipeline();

        p.addLast(new LineBasedFrameDecoder(1024));
        p.addLast(new StringDecoder());
        p.addLast(new StringEncoder());

        p.addLast(new IdleStateHandler(readTimeout, writeTimeout, allTimeout, TimeUnit.SECONDS));
        p.addLast(heartbeatHandler);

        p.addLast(connectionLoggingHandler); 
        p.addLast(tcpServerHandler);
        p.addLast(tcpServerHandler2);
    }

}
