package ru.otus.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    var entityClassMetaData = entitySQLMetaData.getEntityClassMetaData();
                    List<Field> listField = entityClassMetaData.getAllFields();
                    Object[] arr = new Object[listField.size()];

                    for (int i = 0; i < arr.length; i++) {
                        arr[i] = (rs.getObject(listField.get(i).getName()));
                    }

                    Constructor<T> constructor = (Constructor<T>) entityClassMetaData.getConstructor();
                    return constructor.newInstance(arr);
                }
                return null;
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        List<T> ret = new ArrayList<>();
        dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), new ArrayList<Object>(), rs -> {
            // return clntConsr.newInstance(prmtArr);
            try {
                while (rs.next()) {
                    var entityClassMetaData = entitySQLMetaData.getEntityClassMetaData();
                    List<Field> listField = entityClassMetaData.getAllFields();
                    Object[] arr = new Object[listField.size()];

                    for (int i = 0; i < arr.length; i++) {
                        arr[i] = (rs.getObject(listField.get(i).getName()));
                    }

                    Constructor<T> constructor = (Constructor<T>) entityClassMetaData.getConstructor();
                    ret.add(constructor.newInstance(arr));

                }
                return null;
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        });
        return ret;
    }


    @Override
    public long insert(Connection connection, T client) {
        var entityClassMetaData = entitySQLMetaData.getEntityClassMetaData();
        List<Field> listField = entityClassMetaData.getFieldsWithoutId();
        List<Object> list = new ArrayList<>();
        for (Field field : listField) {
            field.setAccessible(true);
            try {
                list.add(field.get(client));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), list);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        var entityClassMetaData = entitySQLMetaData.getEntityClassMetaData();
        List<Field> listField = entityClassMetaData.getFieldsWithoutId();
        List<Object> list = new ArrayList<>();
        for (Field field : listField) {
            field.setAccessible(true);
            try {
                list.add(field.get(client));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), list);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
