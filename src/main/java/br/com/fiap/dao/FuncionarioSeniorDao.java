package br.com.fiap.dao;

            import br.com.fiap.annotation.Tabela;
            import br.com.fiap.entity.FuncionarioSenior;

            import javax.persistence.Column;
            import javax.persistence.EntityManager;
            import javax.persistence.EntityManagerFactory;
            import javax.persistence.Persistence;
            import java.lang.reflect.Field;
            import java.util.List;

            @Tabela(nome = "TAB_FUNCIONARIO_SENIOR")
            public class FuncionarioSeniorDao {

                private EntityManager em;

                public FuncionarioSeniorDao(EntityManager em) {
                    this.em = em;
                }

                public void cadastrar(FuncionarioSenior funcionarioSenior) {
                    em.persist(funcionarioSenior);
                    System.out.println(generateInsertSQL(funcionarioSenior));
                }

                public void atualizar(FuncionarioSenior funcionarioSenior) {
                    em.merge(funcionarioSenior);
                    System.out.println(generateUpdateSQL(funcionarioSenior));
                }

                public void remover(int id) {
                    FuncionarioSenior funcionarioSenior = em.find(FuncionarioSenior.class, id);
                    em.remove(funcionarioSenior);
                    System.out.println(generateDeleteSQL(id));
                }

                public FuncionarioSenior buscarPorId(int id) {
                    FuncionarioSenior funcionarioSenior = em.find(FuncionarioSenior.class, id);
                    System.out.println(generateSelectByIdSQL(id));
                    return funcionarioSenior;
                }

                public List<FuncionarioSenior> buscarTodos() {
                    String sql = generateSelectAllSQL();
                    System.out.println(sql);
                    return em.createNativeQuery(sql, FuncionarioSenior.class).getResultList();
                }

                private String generateInsertSQL(FuncionarioSenior funcionarioSenior) {
                    Tabela tabela = this.getClass().getAnnotation(Tabela.class);
                    StringBuilder sql = new StringBuilder("INSERT INTO ");
                    sql.append(tabela.nome()).append(" (");

                    StringBuilder values = new StringBuilder("VALUES (");

                    Field[] fields = funcionarioSenior.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(Column.class)) {
                            Column coluna = field.getAnnotation(Column.class);
                            sql.append(coluna.name()).append(", ");
                            try {
                                field.setAccessible(true);
                                values.append("'").append(field.get(funcionarioSenior)).append("', ");
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

                private String generateUpdateSQL(FuncionarioSenior funcionarioSenior) {
                    Tabela tabela = this.getClass().getAnnotation(Tabela.class);
                    StringBuilder sql = new StringBuilder("UPDATE ");
                    sql.append(tabela.nome()).append(" SET ");

                    Field[] fields = funcionarioSenior.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(Column.class)) {
                            Column coluna = field.getAnnotation(Column.class);
                            try {
                                field.setAccessible(true);
                                sql.append(coluna.name()).append(" = '").append(field.get(funcionarioSenior)).append("', ");
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    sql.setLength(sql.length() - 2); // Remove the last comma
                    sql.append(" WHERE ID = ").append(funcionarioSenior.getId());

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

                    FuncionarioSeniorDao dao = new FuncionarioSeniorDao(em);

                    try {
                        FuncionarioSenior funcionarioSenior = new FuncionarioSenior();
                        funcionarioSenior.setId(1);
                        funcionarioSenior.setNome("Jane Doe");
                        funcionarioSenior.setHorasTrabalhadas(300);
                        funcionarioSenior.setValorPorHora(50);

                        dao.cadastrar(funcionarioSenior);
                        dao.buscarPorId(1);
                        dao.buscarTodos();

                        funcionarioSenior.setNome("Jane Smith");
                        dao.atualizar(funcionarioSenior);

                        dao.remover(1);
                    } finally {
                        if (em != null) em.close();
                        if (fabrica != null) fabrica.close();
                    }
                }
            }