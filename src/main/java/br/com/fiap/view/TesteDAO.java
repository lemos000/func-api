package br.com.fiap.view;

import br.com.fiap.annotation.Tabela;
import br.com.fiap.entity.Funcionario;
import br.com.fiap.entity.FuncionarioPJ;
import br.com.fiap.entity.StatusFuncionario;
import br.com.fiap.utility.SQLGenerator;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            ensureTableExists(em, Funcionario.class);
            persistOrUpdate(em, funcionario);
            em.getTransaction().commit();

            Funcionario found = em.find(Funcionario.class, funcionario.getId());
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

        em.getTransaction().begin();
        try {
            ensureTableExists(em, FuncionarioPJ.class);
            persistOrUpdate(em, funcionarioPJ);
            em.getTransaction().commit();

            FuncionarioPJ foundPJ = em.find(FuncionarioPJ.class, funcionarioPJ.getId());
            System.out.println("FuncionarioPJ encontrado: " + foundPJ.getNome());
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    private static void ensureTableExists(EntityManager em, Class<?> clazz) {
        Session session = em.unwrap(Session.class);
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                DatabaseMetaData metaData = connection.getMetaData();
                Tabela tabela = clazz.getAnnotation(Tabela.class);
                ResultSet tables = metaData.getTables(null, null, tabela.nome(), null);

                if (!tables.next()) {
                    String createTableSQL = SQLGenerator.generateCreateTableSQL(clazz);
                    em.createNativeQuery(createTableSQL).executeUpdate();
                }
            }
        });
    }

    private static void persistOrUpdate(EntityManager em, Object entity) {
        Object id = em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
        if (id == null || em.find(entity.getClass(), id) == null) {
            em.persist(entity);
        } else {
            em.merge(entity);
        }
    }
}