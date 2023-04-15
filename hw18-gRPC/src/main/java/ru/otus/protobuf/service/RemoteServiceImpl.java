package ru.otus.protobuf.service;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import ru.otus.protobuf.generated.NumberGeneratorServiceGrpc;
import ru.otus.protobuf.generated.NumberMessage;
import ru.otus.protobuf.generated.RequestMessage;

@Slf4j
public class RemoteServiceImpl extends NumberGeneratorServiceGrpc.NumberGeneratorServiceImplBase {


    @Override
    public void getNumberRequest(RequestMessage message,
                                 StreamObserver<NumberMessage> responseObserver) {

        log.info("first {}, last {}", message.getFirst(), message.getLast());
        for (long i = message.getFirst(); i <= message.getLast(); i++) {
            try {
                Thread.sleep(1980);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            responseObserver.onNext(NumberMessage.newBuilder().setId(i).build());
            log.info("SERVER. send: {}", i);
        }

        responseObserver.onCompleted();

    }
}
