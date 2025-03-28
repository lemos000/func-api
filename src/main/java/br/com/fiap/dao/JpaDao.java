package br.com.fiap.dao;

import br.com.fiap.annotation.Tabela;

public class JpaDao {

    public void pesquisar(Object obj) {
        Tabela anotacao = obj.getClass().getAnnotation(Tabela.class);
        System.out.println("SELECT * FROM " + anotacao.nome());
    }

}