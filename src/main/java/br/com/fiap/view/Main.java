package br.com.fiap.view;

import br.com.fiap.dao.FuncionarioPJDao;
import br.com.fiap.entity.*;
import br.com.fiap.exception.IdNaoEncontradoException;
import br.com.fiap.utility.SQLGenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) throws IdNaoEncontradoException {

        Funcionario funcionarioPJ = new FuncionarioPJ("Lucas", 55, 100, 1000, StatusFuncionario.ATIVO);
        funcionarioPJ.imprimirInformacao();
        String sqlPJ = SQLGenerator.generateSelectSQL(funcionarioPJ.getClass());
        System.out.println(sqlPJ);

        Funcionario funcionarioEstag = new FuncionarioEstag("Maria", 30, 50, 200);
        funcionarioEstag.imprimirInformacao();
        String sqlEstag = SQLGenerator.generateSelectSQL(funcionarioEstag.getClass());
        System.out.println(sqlEstag);

        Funcionario funcionarioSenior = new FuncionarioSenior("João", 80, 150, 500);
        funcionarioSenior.imprimirInformacao();
        String sqlSenior = SQLGenerator.generateSelectSQL(funcionarioSenior.getClass());
        System.out.println(sqlSenior);

    }
}

