package ru.otus.protobuf.service;

import java.util.List;

public class RealServiceImpl implements RealService {
    @Override
    public void sendRequest() {
        System.out.println("1!!!");
    }

    @Override
    public List<Long> getInt() {
        System.out.println("2!!!");
        return null;
    }
}
