package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class DemoClass {
    String name;

    @Before
    void Before1() {
        System.out.println("Before1: " + this.hashCode() + " hashCode");
    }

    @Before
    void Before2() {
        System.out.println("Before2: " + this.hashCode() + " hashCode");
    }

    @Test
    void test1() {
        System.out.print("Test1...");
    }

    @Test
    void test2() {
        System.out.print("Test2... ");
    }

    @Test
    void test3() {
        System.out.print("Test3... ");
        throw new NumberFormatException("Что-то пошло не так!!!");
    }

    @After
    void After1() {
        System.out.println("After1: " + this.hashCode() + " hashCode");
    }

    @After
    void After2() {
        System.out.println("After2: " + this.hashCode() + " hashCode");
    }

    @After
    void After3() {
        System.out.println("After3: " + this.hashCode() + " hashCode");
    }

    @Override
    public String toString() {
        return "DemoClass{" + "name='" + name + '\'' + '}';
    }
}
