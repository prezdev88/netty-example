package cl.spring.tcp.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyAppRunner implements CommandLineRunner {

    private final NettyTcpServer tcpServer;

    @Override
    public void run(String... args) {
        tcpServer.start();
    }
}