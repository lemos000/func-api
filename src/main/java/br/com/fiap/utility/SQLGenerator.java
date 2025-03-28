package br.com.fiap.utility;

import br.com.fiap.annotation.Coluna;
import br.com.fiap.annotation.Tabela;

import java.lang.reflect.Field;

public class SQLGenerator {
    public static String generateCreateTableSQL(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Tabela.class)) {
            throw new IllegalArgumentException("A classe não possui a anotação @Tabela");
        }

        Tabela tabela = clazz.getAnnotation(Tabela.class);
        StringBuilder sql = new StringBuilder("CREATE TABLE ");
        sql.append(tabela.nome()).append(" (");

        boolean hasId = false;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Coluna.class)) {
                Coluna coluna = field.getAnnotation(Coluna.class);
                sql.append(coluna.nome()).append(" ");

                // Tratamento específico para o campo ID
                if (field.getName().equalsIgnoreCase("id")) {
                    sql.append("NUMBER(19,0)"); // Tipo NUMBER para IDs no Oracle
                    if (!hasId) {
                        sql.append(" PRIMARY KEY"); // Define como chave primária
                        hasId = true;
                    }
                } else if (field.getType() == int.class) {
                    sql.append("NUMBER(10,0)"); // Tipo NUMBER para inteiros
                } else if (field.getType() == long.class) {
                    sql.append("NUMBER(19,0)"); // Tipo NUMBER para longs
                } else if (field.getType() == double.class) {
                    sql.append("NUMBER(19,4)"); // Tipo NUMBER para doubles com precisão
                } else if (field.getType() == String.class) {
                    int size = coluna.tamanho() > 0 ? coluna.tamanho() : 255;
                    sql.append("VARCHAR2(").append(size).append(")"); // Tipo VARCHAR2 com tamanho
                } else if (field.getType().isEnum()) {
                    sql.append("VARCHAR2(50)"); // Tipo VARCHAR2 para enums
                } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                    sql.append("NUMBER(1)"); // Use NUMBER(1) para booleanos (0 ou 1)
                } else if (field.getType() == java.util.Date.class || field.getType() == java.sql.Date.class) {
                    sql.append("DATE"); // Tipo DATE para datas
                } else {
                    throw new IllegalArgumentException("Tipo de dado não suportado: " + field.getType());
                }

                if (coluna.obrigatorio()) {
                    sql.append(" NOT NULL"); // Define como NOT NULL se for obrigatório
                }

                sql.append(", ");
            }
        }

        // Remove a última vírgula e espaço
        sql.setLength(sql.length() - 2);
        sql.append(")");

        return sql.toString();
    }
}