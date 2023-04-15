package ru.otus.protobuf;


import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import ru.otus.protobuf.service.RemoteServiceImpl;

import java.io.IOException;

@Slf4j
public class GRPCServer {

    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {

        var remoteService = new RemoteServiceImpl();

        var server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(remoteService).build();
        server.start();
        log.info("server waiting for client connections...");
        server.awaitTermination();
    }
}
