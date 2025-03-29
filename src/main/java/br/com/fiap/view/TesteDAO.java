package br.com.fiap.view;

        import br.com.fiap.dao.FuncionarioDao;
        import br.com.fiap.dao.FuncionarioEstagDao;
        import br.com.fiap.dao.FuncionarioPJDao;
        import br.com.fiap.dao.FuncionarioSeniorDao;
        import br.com.fiap.entity.Funcionario;
        import br.com.fiap.entity.FuncionarioEstag;
        import br.com.fiap.entity.FuncionarioPJ;
        import br.com.fiap.entity.FuncionarioSenior;

        import javax.persistence.EntityManager;
        import javax.persistence.EntityManagerFactory;
        import javax.persistence.Persistence;

        public class TesteDAO {

            public static void main(String[] args) {
                EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("CLIENTE_ORACLE");
                EntityManager em = fabrica.createEntityManager();

                FuncionarioDao funcionarioDao = new FuncionarioDao(em);
                FuncionarioPJDao funcionarioPJDao = new FuncionarioPJDao(em);
                FuncionarioEstagDao funcionarioEstagDao = new FuncionarioEstagDao(em);
                FuncionarioSeniorDao funcionarioSeniorDao = new FuncionarioSeniorDao(em);

                try {
                    System.out.println("Iniciando teste de FuncionarioPJ...");
                    testCreateFuncionarioPJ(funcionarioPJDao);
                    System.out.println("Teste de FuncionarioPJ concluído.");
                    System.out.println("Iniciando teste de FuncionarioEstagiario...");
                    testCreateFuncionarioEstagiario(funcionarioEstagDao);
                    System.out.println("Teste de FuncionarioEstagiario concluído.");
                    System.out.println("Iniciando teste de Funcionario...");
                    testCreateFuncionario(funcionarioDao);
                    System.out.println("Teste de Funcionario concluído.");
                    System.out.println("Iniciando teste de FuncionarioSenior...");
                    testCreateFuncionarioSenior(funcionarioSeniorDao);
                    System.out.println("Teste de FuncionarioSenior concluído.");
                } catch (Exception e) {
                    System.err.println("Erro durante a execução dos testes: " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    if (em != null) em.close();
                    if (fabrica != null) fabrica.close();
                }
            }

            public static void testCreateFuncionarioPJ(FuncionarioPJDao daoPj) {
                try {
                    FuncionarioPJ funcionarioPJ = new FuncionarioPJ();
                    funcionarioPJ.setNome("Ana Clara");
                    funcionarioPJ.setHorasTrabalhadas(160);
                    funcionarioPJ.setValorPorHora(50);
                    funcionarioPJ.setValorProjeto(1000);

                    daoPj.cadastrar(funcionarioPJ);

                    System.out.println("FuncionarioPJ persistido com sucesso. ID: " + funcionarioPJ.getId());

                    FuncionarioPJ foundPJ = daoPj.buscarPorId(funcionarioPJ.getId());
                    System.out.println("FuncionarioPJ encontrado: " + foundPJ.getNome());
                    funcionarioPJ.imprimirInformacao();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public static void testCreateFuncionarioEstagiario(FuncionarioEstagDao daoEstag) {
                try {
                    FuncionarioEstag funcionarioEstagiario = new FuncionarioEstag();
                    funcionarioEstagiario.setNome("Pedro Santos");
                    funcionarioEstagiario.setHorasTrabalhadas(120);
                    funcionarioEstagiario.setValorPorHora(20);
                    funcionarioEstagiario.setDesconto(500);

                    daoEstag.cadastrar(funcionarioEstagiario);

                    System.out.println("FuncionarioEstagiario persistido com sucesso. ID: " + funcionarioEstagiario.getId());

                    FuncionarioEstag foundEstagiario = daoEstag.buscarPorId(funcionarioEstagiario.getId());
                    System.out.println("FuncionarioEstagiario encontrado: " + foundEstagiario.getNome());
                    funcionarioEstagiario.imprimirInformacao();
                } catch (Exception e) {
                    e.printStackTrace();
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

            public static void testCreateFuncionarioSenior(FuncionarioSeniorDao daoSenior) {
                try {
                    FuncionarioSenior funcionarioSenior = new FuncionarioSenior();
                    funcionarioSenior.setNome("Carlos Alberto");
                    funcionarioSenior.setHorasTrabalhadas(180);
                    funcionarioSenior.setValorPorHora(100);
                    funcionarioSenior.setBonus(2000);

                    daoSenior.cadastrar(funcionarioSenior);

                    System.out.println("FuncionarioSenior persistido com sucesso. ID: " + funcionarioSenior.getId());

                    FuncionarioSenior foundSenior = daoSenior.buscarPorId(funcionarioSenior.getId());
                    System.out.println("FuncionarioSenior encontrado: " + foundSenior.getNome());
                    funcionarioSenior.imprimirInformacao();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }