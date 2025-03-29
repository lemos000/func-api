package br.com.fiap.dao;

import br.com.fiap.annotation.Tabela;
import br.com.fiap.entity.Funcionario;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Field;
import java.util.List;

@Tabela(nome = "TAB_FUNCIONARIO")
public class FuncionarioDao {

    private EntityManager em;

    public FuncionarioDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Funcionario funcionario) {
        em.persist(funcionario);
        System.out.println(generateInsertSQL(funcionario));
    }

    public void atualizar(Funcionario funcionario) {
        em.merge(funcionario);
        System.out.println(generateUpdateSQL(funcionario));
    }

    public void remover(int id) {
        Funcionario funcionario = em.find(Funcionario.class, id);
        em.remove(funcionario);
        System.out.println(generateDeleteSQL(id));
    }

    public Funcionario buscarPorId(int id) {
        Funcionario funcionario = em.find(Funcionario.class, id);
        System.out.println(generateSelectByIdSQL(id));
        return funcionario;
    }

    public List<Funcionario> buscarTodos() {
        String sql = generateSelectAllSQL();
        System.out.println(sql);
        return em.createNativeQuery(sql, Funcionario.class).getResultList();
    }

    private String generateInsertSQL(Funcionario funcionario) {
        Tabela tabela = this.getClass().getAnnotation(Tabela.class);
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(tabela.nome()).append(" (");

        StringBuilder values = new StringBuilder("VALUES (");

        Field[] fields = funcionario.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column coluna = field.getAnnotation(Column.class);
                sql.append(coluna.name()).append(", ");
                try {
                    field.setAccessible(true);
                    values.append("'").append(field.get(funcionario)).append("', ");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        sql.setLength(sql.length() - 2); // Remove the last comma
        values.setLength(values.length() - 2); // Remove the last comma

        sql.append(") ").append(values).append(")");

        return sql.toString();
    }

    private String generateUpdateSQL(Funcionario funcionario) {
        Tabela tabela = this.getClass().getAnnotation(Tabela.class);
        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(tabela.nome()).append(" SET ");

        Field[] fields = funcionario.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column coluna = field.getAnnotation(Column.class);
                try {
                    field.setAccessible(true);
                    sql.append(coluna.name()).append(" = '").append(field.get(funcionario)).append("', ");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        sql.setLength(sql.length() - 2); // Remove the last comma
        sql.append(" WHERE ID = ").append(funcionario.getId());

        return sql.toString();
    }

    private String generateDeleteSQL(int id) {
        Tabela tabela = this.getClass().getAnnotation(Tabela.class);
        return "DELETE FROM " + tabela.nome() + " WHERE ID = " + id;
    }

    private String generateSelectByIdSQL(int id) {
        Tabela tabela = this.getClass().getAnnotation(Tabela.class);
        return "SELECT * FROM " + tabela.nome() + " WHERE ID = " + id;
    }

    private String generateSelectAllSQL() {
        Tabela tabela = this.getClass().getAnnotation(Tabela.class);
        return "SELECT * FROM " + tabela.nome();
    }

    public static void main(String[] args) {
        EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("CLIENTE_ORACLE");
        EntityManager em = fabrica.createEntityManager();

        FuncionarioDao dao = new FuncionarioDao(em);

        try {
            Funcionario funcionario = new Funcionario();
            funcionario.setId(1);
            funcionario.setNome("John Doe");
            funcionario.setHorasTrabalhadas(500);
            funcionario.setValorPorHora(80);

            dao.cadastrar(funcionario);
            dao.buscarPorId(1);
            dao.buscarTodos();

            funcionario.setNome("John Smith");
            dao.atualizar(funcionario);

            dao.remover(1);
        } finally {
            if (em != null) em.close();
            if (fabrica != null) fabrica.close();
        }
    }
}