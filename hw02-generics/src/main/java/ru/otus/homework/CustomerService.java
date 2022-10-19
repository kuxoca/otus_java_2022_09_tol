package ru.otus.homework;


import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private final TreeMap<Customer, String> treeMap = new TreeMap<>((o1, o2) -> (int) (o1.getScores() - o2.getScores()));

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        Map.Entry<Customer, String> customerEntry = treeMap.firstEntry();
        Customer temp = new Customer(
                customerEntry.getKey().getId(),
                customerEntry.getKey().getName(),
                customerEntry.getKey().getScores());

        return Map.entry(temp, customerEntry.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> customerEntry = treeMap.higherEntry(customer);
        if (customerEntry == null) {
            return null;
        }
        Customer temp = new Customer(
                customerEntry.getKey().getId(),
                customerEntry.getKey().getName(),
                customerEntry.getKey().getScores());

        return Map.entry(temp, customerEntry.getValue());
    }

    public void add(Customer customer, String data) {
        treeMap.put(customer, data);
    }
}
