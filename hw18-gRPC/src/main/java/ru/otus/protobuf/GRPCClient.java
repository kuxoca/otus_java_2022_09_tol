package ru.otus.protobuf;

import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import ru.otus.protobuf.generated.Empty;
import ru.otus.protobuf.generated.RemoteServiceGrpc;


@Slf4j
public class GRPCClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    public static void main(String[] args) {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        var stub = RemoteServiceGrpc.newBlockingStub(channel);

        var streamInt = stub.getInt(Empty.getDefaultInstance());
        log.info("Отправлен запрос на сервер!!!");

        streamInt.forEachRemaining(um ->
                log.info("CLIENT. get: {}", um.getId())
        );

        channel.shutdown();
    }
}
