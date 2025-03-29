package br.com.fiap.dao;

import br.com.fiap.annotation.Tabela;
import br.com.fiap.entity.FuncionarioEstag;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Field;
import java.util.List;

@Tabela(nome = "TAB_FUNCIONARIO_ESTAG")
public class FuncionarioEstagDao {

    private EntityManager em;

    public FuncionarioEstagDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(FuncionarioEstag funcionarioEstag) {
        em.persist(funcionarioEstag);
        System.out.println(generateInsertSQL(funcionarioEstag));
    }

    public void atualizar(FuncionarioEstag funcionarioEstag) {
        em.merge(funcionarioEstag);
        System.out.println(generateUpdateSQL(funcionarioEstag));
    }

    public void remover(int id) {
        FuncionarioEstag funcionarioEstag = em.find(FuncionarioEstag.class, id);
        em.remove(funcionarioEstag);
        System.out.println(generateDeleteSQL(id));
    }

    public FuncionarioEstag buscarPorId(int id) {
        FuncionarioEstag funcionarioEstag = em.find(FuncionarioEstag.class, id);
        System.out.println(generateSelectByIdSQL(id));
        return funcionarioEstag;
    }

    public List<FuncionarioEstag> buscarTodos() {
        String sql = generateSelectAllSQL();
        System.out.println(sql);
        return em.createNativeQuery(sql, FuncionarioEstag.class).getResultList();
    }

    private String generateInsertSQL(FuncionarioEstag funcionarioEstag) {
        Tabela tabela = this.getClass().getAnnotation(Tabela.class);
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(tabela.nome()).append(" (");

        StringBuilder values = new StringBuilder("VALUES (");

        Field[] fields = funcionarioEstag.getClass().getDeclaredFields();
        while (funcionarioEstag.getClass().getSuperclass() != null) {
            fields = concatenate(fields, funcionarioEstag.getClass().getSuperclass().getDeclaredFields());
            funcionarioEstag = (FuncionarioEstag) funcionarioEstag.getClass().getSuperclass().cast(funcionarioEstag);
        }
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column coluna = field.getAnnotation(Column.class);
                sql.append(coluna.name()).append(", ");
                try {
                    field.setAccessible(true);
                    values.append("'").append(field.get(funcionarioEstag)).append("', ");
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

    private String generateUpdateSQL(FuncionarioEstag funcionarioEstag) {
        Tabela tabela = this.getClass().getAnnotation(Tabela.class);
        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(tabela.nome()).append(" SET ");

        Field[] fields = funcionarioEstag.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column coluna = field.getAnnotation(Column.class);
                try {
                    field.setAccessible(true);
                    sql.append(coluna.name()).append(" = '").append(field.get(funcionarioEstag)).append("', ");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        sql.setLength(sql.length() - 2); // Remove the last comma
        sql.append(" WHERE ID = ").append(funcionarioEstag.getId());

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

    private Field[] concatenate(Field[] a, Field[] b) {
        int aLen = a.length;
        int bLen = b.length;
        Field[] c = new Field[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }

    public static void main(String[] args) {
        EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("CLIENTE_ORACLE");
        EntityManager em = fabrica.createEntityManager();

        FuncionarioEstagDao dao = new FuncionarioEstagDao(em);

        try {
            FuncionarioEstag funcionarioEstag = new FuncionarioEstag();
            funcionarioEstag.setId(1);
            funcionarioEstag.setNome("Jane Doe");
            funcionarioEstag.setHorasTrabalhadas(300);
            funcionarioEstag.setValorPorHora(50);

            dao.cadastrar(funcionarioEstag);
            dao.buscarPorId(1);
            dao.buscarTodos();

            funcionarioEstag.setNome("Jane Smith");
            dao.atualizar(funcionarioEstag);

            dao.remover(1);
        } finally {
            if (em != null) em.close();
            if (fabrica != null) fabrica.close();
        }
    }
}