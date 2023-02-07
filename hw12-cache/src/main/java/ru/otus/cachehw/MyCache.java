package ru.otus.cachehw;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    //Надо реализовать эти методы
    private final Map<K, V> cashe;
    private final List<HwListener<K, V>> listeners;

    public MyCache() {
        this.cashe = new WeakHashMap<>();
        this.listeners = new ArrayList<>();
    }

    @Override
    public void put(K key, V value) {
        event(key, value, "put");
        cashe.put(key, value);
    }

    @Override
    public void remove(K key) {
        event(key, cashe.get(key), "remove");
        cashe.remove(key);
    }

    @Override
    public V get(K key) {
        event(key, cashe.get(key), "get");
        return cashe.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }


    private void event(K key, V value, String strEvent) {
        listeners.forEach(el ->
                el.notify(key, value, strEvent)
        );
    }
}