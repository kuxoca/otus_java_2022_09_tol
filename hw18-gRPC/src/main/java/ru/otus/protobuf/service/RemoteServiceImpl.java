package ru.otus.protobuf.service;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import ru.otus.protobuf.generated.IntMessage;
import ru.otus.protobuf.generated.RemoteServiceGrpc;
import ru.otus.protobuf.generated.RequestMessage;

@Slf4j
public class RemoteServiceImpl extends RemoteServiceGrpc.RemoteServiceImplBase {


    @Override
    public void getInt(RequestMessage message, StreamObserver<IntMessage> responseObserver) {
        log.info("first {}, last {}",message.getFirst(), message.getLast());
        for (long i = message.getFirst(); i <= message.getLast(); i++) {
            try {
                Thread.sleep(1980);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            responseObserver.onNext(IntMessage.newBuilder().setId(i).build());
            log.info("SERVER. send: {}", i);
        }

        responseObserver.onCompleted();

    }
}
