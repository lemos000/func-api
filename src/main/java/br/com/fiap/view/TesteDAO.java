package br.com.fiap.view;

import br.com.fiap.entity.Funcionario;
import br.com.fiap.entity.FuncionarioPJ;
import br.com.fiap.entity.StatusFuncionario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TesteDAO {

    public static void main(String[] args) {
        EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("CLIENTE_ORACLE");

        EntityManager em = fabrica.createEntityManager();

        try {
            testCreateFuncionarioPJ(em);
            testCreateFuncionario(em);
        } finally {
            if (em != null) em.close();
            if (fabrica != null) fabrica.close();
        }
    }

    public static void testCreateFuncionario(EntityManager em) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("John Doe");
        funcionario.setHorasTrabalhadas(500);
        funcionario.setValorPorHora(80);

        em.getTransaction().begin();
        try {
            em.merge(funcionario);
            em.getTransaction().commit();

            Funcionario found = em.find(Funcionario.class, 1);
            System.out.println("Funcionario encontrado: " + found.getNome());
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public static void testCreateFuncionarioPJ(EntityManager em) {
        FuncionarioPJ funcionarioPJ = new FuncionarioPJ();
        funcionarioPJ.setNome("Maria Silva");
        funcionarioPJ.setHorasTrabalhadas(200);
        funcionarioPJ.setValorPorHora(150);
        funcionarioPJ.setValorProjeto(1000);
        funcionarioPJ.setStatusFuncionario(StatusFuncionario.ATIVO);

        em.getTransaction().begin();
        try {
            em.persist(funcionarioPJ);
            em.getTransaction().commit();

            FuncionarioPJ foundPJ = em.find(FuncionarioPJ.class, funcionarioPJ.getId());
            System.out.println("FuncionarioPJ encontrado: " + foundPJ.getNome());
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
