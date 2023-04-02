package ru.otus.protobuf.service;


import java.util.List;

public interface RealService {
    void sendRequest();

    List<Long> getInt();
}
