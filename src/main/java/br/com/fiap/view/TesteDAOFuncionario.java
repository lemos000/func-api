package br.com.fiap.view;

import br.com.fiap.dao.FuncionarioDao;
import br.com.fiap.entity.Funcionario;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TesteDAOFuncionario {

    public static void main(String[] args) {
        EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("CLIENTE_ORACLE");
        EntityManager em = fabrica.createEntityManager();

        FuncionarioDao funcionarioDao = new FuncionarioDao(em);

        try {
            System.out.println("Iniciando teste de Funcionario...");
            testCreateFuncionario(funcionarioDao);
            System.out.println("Teste de Funcionario concluído.");
        } catch (Exception e) {
            System.err.println("Erro durante a execução dos testes: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
            if (fabrica != null) fabrica.close();
        }
    }





    public static void testCreateFuncionario(FuncionarioDao dao) {
        try {
            Funcionario funcionario = new Funcionario();
            funcionario.setNome("John Doe");
            funcionario.setHorasTrabalhadas(500);
            funcionario.setValorPorHora(80);

            dao.cadastrar(funcionario);

            System.out.println("Funcionario persistido com sucesso. ID: " + funcionario.getId());

            Funcionario found = dao.buscarPorId(funcionario.getId());
            System.out.println("Funcionario encontrado: " + found.getNome());
            funcionario.imprimirInformacao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}