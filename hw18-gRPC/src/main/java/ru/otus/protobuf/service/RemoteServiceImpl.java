package ru.otus.protobuf.service;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import ru.otus.protobuf.generated.Empty;
import ru.otus.protobuf.generated.IntMessage;
import ru.otus.protobuf.generated.RemoteServiceGrpc;

@Slf4j
public class RemoteServiceImpl extends RemoteServiceGrpc.RemoteServiceImplBase {


    @Override
    public void getInt(Empty request, StreamObserver<IntMessage> responseObserver) {
        for (long i = 1; i <= 30; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            responseObserver.onNext(IntMessage.newBuilder().setId(i).build());
            log.info("SERVER. send {}", i);
        }

        responseObserver.onCompleted();

    }
}
