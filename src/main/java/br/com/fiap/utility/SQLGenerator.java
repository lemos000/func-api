package br.com.fiap.utility;

import br.com.fiap.annotation.Coluna;
import br.com.fiap.annotation.Tabela;

import java.lang.reflect.Field;

@SuppressWarnings("ALL")
public class SQLGenerator {

    public static String generateSelectSQL(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Tabela.class)) {
            throw new IllegalArgumentException("A classe não está anotada com @Tabela");
        }

        Tabela tabela = clazz.getAnnotation(Tabela.class);
        StringBuilder sql = new StringBuilder("SELECT ");

        Class<?> currentClass = clazz;
        while (currentClass != null) {
            Field[] fields = currentClass.getDeclaredFields();
            currentClass = currentClass.getSuperclass();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (field.isAnnotationPresent(Coluna.class)) {
                    Coluna coluna = field.getAnnotation(Coluna.class);
                    sql.append(coluna.nome());
                    sql.append(", ");
                }
            }
        }

        // Remove the last comma and space
        if (sql.length() > 7) {
            sql.setLength(sql.length() - 2);
        }

        sql.append(" FROM ").append(tabela.nome());
        return sql.toString();
    }
}