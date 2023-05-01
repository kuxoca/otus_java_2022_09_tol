package ru.otus.mapper;

import java.lang.reflect.Field;
import java.util.List;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private final EntityClassMetaData<T> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }
    public EntityClassMetaData<T> getEntityClassMetaData() {
        return entityClassMetaData;
    }
    @Override
    public String getSelectAllSql() {
        List<String> fieldsName = entityClassMetaData.getAllFields().stream()
                .map(Field::getName)
                .toList();

        String sql = String.format(
                "select %s from %s",
                String.join(", ", fieldsName),
                entityClassMetaData.getName()
        );
        return sql;
    }

    @Override
    public String getSelectByIdSql() {
        List<String> fieldsName = entityClassMetaData.getAllFields().stream()
                .map(Field::getName)
                .toList();

        String sql = String.format(
                "select %s from %s where %s = ?",
                String.join(", ", fieldsName),
                entityClassMetaData.getName(),
                entityClassMetaData.getIdField().getName()
        );
        return sql;
    }

    @Override
    public String getInsertSql() {
        List<String> fieldsName = entityClassMetaData.getFieldsWithoutId().stream()
                .map(Field::getName)
                .toList();
        List<String> q = fieldsName.stream()
                .map(str -> "?")
                .toList();

        String sql = String.format(
                "insert into %s(%s) values (%s)",
                entityClassMetaData.getName(),
                String.join(", ", fieldsName),
                String.join(", ", q)
        );
        return sql;
    }

    @Override
    public String getUpdateSql() {

        List<String> fieldsName = entityClassMetaData.getFieldsWithoutId().stream()
                .map(Field::getName)
                .toList();
        List<String> collect = fieldsName.stream()
                .map(str -> str + " = ?")
                .toList();

        String sql = String.format(
                "update %s set %s where %s = ?",
                entityClassMetaData.getName().toLowerCase(),
                collect,
                entityClassMetaData.getIdField().getName()
        );
        return sql;
    }
}




























