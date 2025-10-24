package br.com.feluz.dao.generics;

import br.com.feluz.annotations.ColunaTabela;
import br.com.feluz.annotations.Tabela;
import br.com.feluz.annotations.TipoChave;
import br.com.feluz.dao.interfaces.Persistence;
import br.com.feluz.dao.jdbc.ConnectionDB;
import br.com.feluz.exceptions.*;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class GenericDAO<T extends Persistence, V extends Serializable> implements IGenericDAO<T, V> {
    public GenericDAO() {
    }

    public abstract Class<T> getTipoClasse();

    public abstract void updateData(T entity, T entityCadastrado);

    protected abstract String getQueryInsert();

    protected abstract void setParamsInsert(PreparedStatement stmInsert, T entity) throws SQLException;

    protected abstract void setParamsSelect(PreparedStatement stmSelect, V value) throws SQLException;

    protected abstract String getQueryUpdate();

    protected abstract void setParamsUpdate(PreparedStatement stmUpdate, T entity) throws SQLException;

    protected abstract String getQueryDelete();

    protected abstract void setParamsDelete(PreparedStatement stmDelete, V value) throws SQLException;

    public V getChave(T entity) throws TipoChaveNaoEncontradaException {
        Field[] fields = entity.getClass().getDeclaredFields();
        V returnValue = null;

        for (Field field : fields) {
            if (field.isAnnotationPresent(TipoChave.class)) {

                TipoChave tipoChave = field.getAnnotation(TipoChave.class);
                String nomeMetodo = tipoChave.value();

                try {
                    Method method = entity.getClass().getMethod(nomeMetodo);
                    returnValue = (V) method.invoke(entity);
                    return returnValue;
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    throw new TipoChaveNaoEncontradaException("Chave principal do objeto " + entity.getClass() + " não encontrada", e);
                }
            }
        }
        if (returnValue == null) {
            String msg = "Chave principal do objeto " + entity.getClass() + " não encontrada";
            System.out.println("******** ERRO ********" + "/n" + msg);
            throw new TipoChaveNaoEncontradaException(msg);
        }
        return null;
    }

    public String getNomeCampoChave(Class clazz) throws TipoChaveNaoEncontradaException {
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(TipoChave.class) && field.isAnnotationPresent(ColunaTabela.class)) {
                ColunaTabela coluna = field.getAnnotation(ColunaTabela.class);
                return coluna.dbName();
            }
        }
        return null;
    }

    private void setValueByType(T entity, Method method, Class<?> classField, ResultSet rs, String fieldName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException, TipoElementoNaoConhecidoException {
        if (classField.equals(Integer.class)) {
            Integer val = rs.getInt(fieldName);
            method.invoke(entity, val);
        } else if (classField.equals(Long.class)) {
            Long val = rs.getLong(fieldName);
            method.invoke(entity, val);
        } else if (classField.equals(Double.class)) {
            Double val = rs.getDouble(fieldName);
            method.invoke(entity, val);
        } else if (classField.equals(Short.class)) {
            Short val = rs.getShort(fieldName);
            method.invoke(entity, val);
        } else if (classField.equals(BigDecimal.class)) {
            BigDecimal val = rs.getBigDecimal(fieldName);
            method.invoke(entity, val);
        } else if (classField.equals(String.class)) {
            String val = rs.getString(fieldName);
            method.invoke(entity, val);
        } else if (classField.equals(Instant.class)) {
            Timestamp ts = rs.getTimestamp(fieldName);
            Instant val = (ts != null) ? ts.toInstant() : null;
            method.invoke(entity, val);
        } else {
            throw new TipoElementoNaoConhecidoException("TIPO DE CLASSE NÃO CONHECIDO: " + classField);
        }
    }

    private Object getValueByType(Class<?> typeField, ResultSet rs, String fieldName) throws SQLException, TipoElementoNaoConhecidoException {
        if (typeField.equals(Integer.TYPE)) {
            return rs.getInt(fieldName);
        } else if (typeField.equals(Long.TYPE)) {
            return rs.getLong(fieldName);
        } else if (typeField.equals(Double.TYPE)) {
            return rs.getDouble(fieldName);
        } else if (typeField.equals(Short.TYPE)) {
            return rs.getShort(fieldName);
        } else if (typeField.equals(BigDecimal.class)) {
            return rs.getBigDecimal(fieldName);
        } else if (typeField.equals(String.class)) {
            return rs.getString(fieldName);
        } else {
            throw new TipoElementoNaoConhecidoException("TIPO DE CLASSE NÃO CONHECIDO: " + typeField);
        }
    }

    private Long validarMaisDeUmRegistro(V value) throws MoreThanOneRegisterException, TableException, TipoChaveNaoEncontradaException, DAOException, SQLException {
        Long count = 0L;

        try (Connection dataBase = ConnectionDB.getConnection();
             PreparedStatement stm = dataBase.prepareStatement("SELECT count(*) FROM " + getTableName() + " WHERE " + getNomeCampoChave(getTipoClasse()) + " = ?")) {
            setParamsSelect(stm, value);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    count = rs.getLong(1);
                    if (count > 1) {
                        throw new MoreThanOneRegisterException("ENCONTRADO MAIS DE UM REGISTRO DE " + getTableName());
                    }
                }
            }
            return count;

        } catch (SQLException e) {
            throw new DAOException("ERRO AO EXECUTAR VALIDAÇÃO DE REGISTRO", e);
        }
    }

    private String getTableName() throws TableException {
        if (getTipoClasse().isAnnotationPresent(Tabela.class)) {
            Tabela table = getTipoClasse().getAnnotation(Tabela.class);
            return table.value();
        } else {
            throw new TableException("TABELA NO TIPO " + getTipoClasse().getName() + " NÃO FOI ENCONTRADA");
        }
    }

    protected void closeConnectionDB(Connection dataBase, PreparedStatement stm, ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
            if (stm != null && !stm.isClosed()) {
                stm.close();
            }
            if (dataBase != null && !dataBase.isClosed()) {
                dataBase.close();
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }


    @Override
    public Boolean register(T entity) throws SQLException, TipoChaveNaoEncontradaException, DAOException {

        try (Connection dataBase = ConnectionDB.getConnection();
             PreparedStatement stm = dataBase.prepareStatement(getQueryInsert(), Statement.RETURN_GENERATED_KEYS)) {
            setParamsInsert(stm, entity);
            int rowsAffected = stm.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stm.getGeneratedKeys()) {
                    if (rs.next()) {
                        Persistence per = (Persistence) entity;
                        per.setId(rs.getLong(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            throw new DAOException("ERRO AO CADASTRAR O OBJETO", e);
        }
        return false;
    }

    @Override
    public Boolean register(T entity, Connection dataBase) throws DAOException {

        try (PreparedStatement stm = dataBase.prepareStatement(getQueryInsert(), Statement.RETURN_GENERATED_KEYS)) {
            setParamsInsert(stm, entity);
            int rowsAffected = stm.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stm.getGeneratedKeys()) {
                    if (rs.next()) {
                        Persistence per = (Persistence) entity;
                        per.setId(rs.getLong(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new DAOException("ERRO AO CADASTRAR OBJETO", e);
        }
    }

    @Override
    public T find(V value) throws MoreThanOneRegisterException, TableException, DAOException, SQLException {

        try (Connection dataBase = ConnectionDB.getConnection();
             PreparedStatement stm = dataBase.prepareStatement("SELECT * FROM " + getTableName() + " WHERE " + getNomeCampoChave(getTipoClasse()) + " = ?")) {
            setParamsSelect(stm, value);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    T entity = getTipoClasse().getConstructor(null).newInstance(null);
                    Field[] fields = entity.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(ColunaTabela.class)) {
                            ColunaTabela coluna = field.getAnnotation(ColunaTabela.class);
                            String dbName = coluna.dbName();
                            String javaSetName = coluna.setJavaName();
                            Class<?> classField = field.getType();
                            try {
                                Method method = entity.getClass().getMethod(javaSetName, classField);
                                setValueByType(entity, method, classField, rs, dbName);
                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                                     TipoElementoNaoConhecidoException e) {
                                throw new DAOException("ERRO AO CONSULTAR O OBJETO", e);
                            }
                        }
                    }
                    return entity;
                }
            }
        } catch (SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException | NoSuchMethodException | SecurityException |
                 TipoChaveNaoEncontradaException e) {
            throw new DAOException("ERRO AO CONSULTAR O OBJETO", e);
        }
        return null;
    }

    @Override
    public T find(V value, Connection dataBase) throws TableException, DAOException {

        try (PreparedStatement stm = dataBase.prepareStatement("SELECT * FROM " + getTableName() + " WHERE " + getNomeCampoChave(getTipoClasse()) + " = ?")) {

            setParamsSelect(stm, value);

            try (ResultSet rs = stm.executeQuery()) {

                if (rs.next()) {
                    T entity = getTipoClasse().getConstructor(null).newInstance(null);
                    Field[] fields = entity.getClass().getDeclaredFields();

                    for (Field field : fields) {
                        if (field.isAnnotationPresent(ColunaTabela.class)) {
                            ColunaTabela coluna = field.getAnnotation(ColunaTabela.class);
                            String dbName = coluna.dbName();
                            String javaSetName = coluna.setJavaName();
                            Class<?> classField = field.getType();

                            try {
                                Method method = entity.getClass().getMethod(javaSetName, classField);
                                setValueByType(entity, method, classField, rs, dbName);
                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                                     TipoElementoNaoConhecidoException e) {
                                throw new DAOException("ERRO AO CONSULTAR OBJETO DURANTE O MAPEAMENTO", e);
                            }
                        }
                    }
                    return entity;
                }
            }

        } catch (SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException | NoSuchMethodException | SecurityException |
                 TipoChaveNaoEncontradaException e) {
            throw new DAOException("ERRO AO CONSULTAR OBJETO OU PREPARAR QUERY", e);
        }
        return null;
    }

    @Override
    public void update(T entity) throws TipoChaveNaoEncontradaException, DAOException, SQLException {

        try (Connection dataBase = ConnectionDB.getConnection();
             PreparedStatement stm = dataBase.prepareStatement(getQueryUpdate())) {
            setParamsUpdate(stm, entity);
            int rowsAffected = stm.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("ERRO AO ALTERAR O OBJETO", e);
        }
    }

    @Override
    public void update(T entity, Connection dataBase) throws DAOException {
        try (PreparedStatement stm = dataBase.prepareStatement(getQueryUpdate())) {
            setParamsUpdate(stm, entity);
            int rowsAffected = stm.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("ERRO AO ALTERAR OBJETO", e);
        }
    }

    @Override
    public void remove(V value) throws DAOException, SQLException {

        try (Connection dataBase = ConnectionDB.getConnection();
             PreparedStatement stm = dataBase.prepareStatement(getQueryDelete())) {
            setParamsDelete(stm, value);
            int rowsAffected = stm.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("ERRO AO EXCLUIR O OBJETO", e);
        }
    }

    @Override
    public void remove(V value, Connection dataBase) throws DAOException {
        try (PreparedStatement stm = dataBase.prepareStatement(getQueryDelete())) {
            setParamsDelete(stm, value);
            int rowsAffected = stm.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("ERRO AO REMOVER OBJETO", e);
        }
    }

    @Override
    public Collection<T> findAll() throws DAOException, SQLException {
        List<T> list = new ArrayList<>();


        try (Connection dataBase = ConnectionDB.getConnection();
             PreparedStatement stm = dataBase.prepareStatement("SELECT * FROM " + getTableName())) {

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    T entity = getTipoClasse().getConstructor(null).newInstance(null);
                    Field[] fields = entity.getClass().getDeclaredFields();

                    for (Field field : fields) {

                        if (field.isAnnotationPresent(ColunaTabela.class)) {
                            ColunaTabela coluna = field.getAnnotation(ColunaTabela.class);
                            String dbName = coluna.dbName();
                            String javaSetName = coluna.setJavaName();
                            Class<?> classField = field.getType();

                            try {
                                Method method = entity.getClass().getMethod(javaSetName, classField);
                                setValueByType(entity, method, classField, rs, dbName);
                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                                     TipoElementoNaoConhecidoException e) {
                                throw new DAOException("ERRO AO LISTAR OBJETOS", e);
                            }
                        }
                    }
                    list.add(entity);
                }
            }

        } catch (SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException | NoSuchMethodException | SecurityException | TableException e) {
            throw new DAOException("ERRO AO LISTAR OBJETOS", e);
        }
        return list;
    }
}
