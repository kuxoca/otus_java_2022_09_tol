package ru.otus.homework;


import java.util.LinkedList;

public class CustomerReverseOrder {

    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"

    private final LinkedList<Customer> linkedList = new LinkedList<>();

    public void add(Customer customer) {
        linkedList.add(customer);
    }

    public Customer take() {
        return linkedList.pollLast();
    }
}
