package ru.otus.protobuf;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import ru.otus.protobuf.generated.IntMessage;
import ru.otus.protobuf.generated.RemoteServiceGrpc;
import ru.otus.protobuf.generated.RequestMessage;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


@Slf4j
public class GRPCClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    private static long lastFromServer;
    private static long firstValue = 1;
    private static long lastValue = 30;
    private static long currentValue = 0;

    public static void main(String[] args) throws InterruptedException {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        var latch = new CountDownLatch(1);
        var newStub = RemoteServiceGrpc.newStub(channel);

        log.info("Отправляется запрос: first {}, last {}", firstValue, lastValue);
        newStub.getInt(RequestMessage.newBuilder()
                .setFirst(firstValue)
                .setLast(lastValue)
                .build(), new StreamObserver<IntMessage>() {

            @Override
            public void onNext(IntMessage value) {
                setLastFromServer(value.getId());
                log.info("number from server: {}", lastFromServer);
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


        for (int j = 0; j <= 50; j++) {
            currentValue = currentValue + 1 + getLastFromServer();
            setLastFromServer(0L);
            log.info("currentValue {}", currentValue);
            sleep();
        }

        latch.await();

        channel.shutdown();
    }

    private static synchronized void setLastFromServer(Long number) {
        lastFromServer = number;
    }

    private static synchronized Long getLastFromServer() {
        return lastFromServer;
    }

    private static void sleep() {
        try {
            Thread.sleep(TimeUnit.MILLISECONDS.toMillis(1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
