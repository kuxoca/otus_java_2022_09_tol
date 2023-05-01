package ru.otus.mapper;

import ru.otus.crm.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData {
    private final Class<?> clazz;
    private Constructor<T> constructor;
    private List<Field> fields;
    private List<Field> fieldsWithoutId;

    private Field field;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        return clazz.getSimpleName().toLowerCase();
    }

    @Override
    public Constructor<T> getConstructor()  {
        if (constructor == null) {
            List<Field> listField = getAllFields();
            Class[] classes = new Class[listField.size()];
            for (int i = 0; i < classes.length; i++) {
                classes[i] = listField.get(i).getType();
            }
            try {
                constructor = (Constructor<T>) clazz.getConstructor(classes);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        return constructor;
    }

    @Override
    public Field getIdField() {
        if (field == null) {
            for (Field el : clazz.getDeclaredFields()) {
                if (el.isAnnotationPresent(Id.class)) {
                    field = el;
                }
            }
        }
        return field;
    }

    @Override
    public List<Field> getAllFields() {
        if (fields == null) {
            fields = Arrays.stream(clazz.getDeclaredFields())
                    .toList();
        }
        return fields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        if (fieldsWithoutId == null) {
            fieldsWithoutId = Arrays.stream(clazz.getDeclaredFields())
                    .filter(el -> !el.equals(getIdField()))
                    .toList();
        }
        return fieldsWithoutId;
    }
}


































