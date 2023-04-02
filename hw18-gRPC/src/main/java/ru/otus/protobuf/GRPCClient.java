package ru.otus.protobuf;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import ru.otus.protobuf.generated.Empty;
import ru.otus.protobuf.generated.IntMessage;
import ru.otus.protobuf.generated.RemoteServiceGrpc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


@Slf4j
public class GRPCClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    private static long lastFromServer;
    private static long firstValue = 0;
    private static long lastValue = 30;
    private static long currentValue = 0;

    public static void main(String[] args) throws InterruptedException {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        var stub = RemoteServiceGrpc.newBlockingStub(channel);

        var streamInt = stub.getInt(Empty.getDefaultInstance());
        log.info("Отправлен запрос на сервер!!!");

//        streamInt.forEachRemaining(um -> {
//                    i = um.getId();
//                    log.info("CLIENT. get: {}", i);
//                }
//        );
        var latch = new CountDownLatch(1);
        var newStub = RemoteServiceGrpc.newStub(channel);
        newStub.getInt(Empty.getDefaultInstance(), new StreamObserver<IntMessage>() {

            @Override
            public void onNext(IntMessage value) {
                lastFromServer = value.getId();
                log.info("data from server: {}", lastFromServer);
            }

            @Override
            public void onError(Throwable t) {
                System.err.println(t);
            }

            @Override
            public void onCompleted() {
                System.out.println("\n\nЯ все!");
                latch.countDown();
            }
        });


        for (int j = 0; j < 50; j++) {
            currentValue = currentValue + 1 + lastFromServer;
            lastFromServer = 0;
            log.info("count {}", currentValue);
            sleep();
        }

        latch.await();

        channel.shutdown();
    }

    private static void sleep() {
        try {
            Thread.sleep(TimeUnit.MILLISECONDS.toMillis(1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
