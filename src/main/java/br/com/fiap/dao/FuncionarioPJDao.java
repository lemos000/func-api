package br.com.fiap.dao;

import br.com.fiap.annotation.Tabela;
import br.com.fiap.entity.FuncionarioPJ;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Field;
import java.util.List;

@Tabela(nome = "TAB_FUNCIONARIO_PJ")
public class FuncionarioPJDao {

    private EntityManager em;

    public FuncionarioPJDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(FuncionarioPJ funcionarioPJ) {
        em.persist(funcionarioPJ);
        System.out.println(generateInsertSQL(funcionarioPJ));
    }

    public void atualizar(FuncionarioPJ funcionarioPJ) {
        em.merge(funcionarioPJ);
        System.out.println(generateUpdateSQL(funcionarioPJ));
    }

    public void remover(int id) {
        FuncionarioPJ funcionarioPJ = em.find(FuncionarioPJ.class, id);
        em.remove(funcionarioPJ);
        System.out.println(generateDeleteSQL(id));
    }

    public FuncionarioPJ buscarPorId(int id) {
        FuncionarioPJ funcionarioPJ = em.find(FuncionarioPJ.class, id);
        System.out.println(generateSelectByIdSQL(id));
        return funcionarioPJ;
    }

    public List<FuncionarioPJ> buscarTodos() {
        String sql = generateSelectAllSQL();
        System.out.println(sql);
        return em.createNativeQuery(sql, FuncionarioPJ.class).getResultList();
    }

    private String generateInsertSQL(FuncionarioPJ funcionarioPJ) {
        Tabela tabela = this.getClass().getAnnotation(Tabela.class);
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(tabela.nome()).append(" (");

        StringBuilder values = new StringBuilder("VALUES (");

        Field[] fields = funcionarioPJ.getClass().getDeclaredFields();
        while (funcionarioPJ.getClass().getSuperclass() != null) {
            fields = concatenate(fields, funcionarioPJ.getClass().getSuperclass().getDeclaredFields());
            funcionarioPJ = (FuncionarioPJ) funcionarioPJ.getClass().getSuperclass().cast(funcionarioPJ);
        }
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column coluna = field.getAnnotation(Column.class);
                sql.append(coluna.name()).append(", ");
                try {
                    field.setAccessible(true);
                    values.append("'").append(field.get(funcionarioPJ)).append("', ");
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

    private String generateUpdateSQL(FuncionarioPJ funcionarioPJ) {
        Tabela tabela = this.getClass().getAnnotation(Tabela.class);
        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(tabela.nome()).append(" SET ");

        Field[] fields = funcionarioPJ.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column coluna = field.getAnnotation(Column.class);
                try {
                    field.setAccessible(true);
                    sql.append(coluna.name()).append(" = '").append(field.get(funcionarioPJ)).append("', ");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        sql.setLength(sql.length() - 2); // Remove the last comma
        sql.append(" WHERE ID = ").append(funcionarioPJ.getId());

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

        FuncionarioPJDao dao = new FuncionarioPJDao(em);

        try {
            FuncionarioPJ funcionarioPJ = new FuncionarioPJ();
            funcionarioPJ.setId(1);
            funcionarioPJ.setNome("John Doe");
            funcionarioPJ.setHorasTrabalhadas(400);
            funcionarioPJ.setValorPorHora(100);

            dao.cadastrar(funcionarioPJ);
            dao.buscarPorId(1);
            dao.buscarTodos();

            funcionarioPJ.setNome("John Smith");
            dao.atualizar(funcionarioPJ);

            dao.remover(1);
        } finally {
            if (em != null) em.close();
            if (fabrica != null) fabrica.close();
        }
    }
}