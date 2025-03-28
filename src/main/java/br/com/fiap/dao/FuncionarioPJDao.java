package br.com.fiap.dao;

import br.com.fiap.entity.Funcionario;
import br.com.fiap.exception.CommitException;
import br.com.fiap.exception.IdNaoEncontradoException;

import javax.persistence.EntityManager;

public class FuncionarioPJDao {

    private EntityManager enm;

    public FuncionarioPJDao(EntityManager em) {
        this.enm = em;
    }

    public void cadastrar(Funcionario funcionario) {
        enm.persist(funcionario);
    }

    public void atualizar(Funcionario funcionario) throws IdNaoEncontradoException {
        buscarPorId(funcionario.getId());
        enm.merge(funcionario);
    }

    public void remover(int id) throws IdNaoEncontradoException {
        Funcionario funcionario = buscarPorId(id);
        enm.remove(funcionario);
    }

    public Funcionario buscarPorId(int id) throws IdNaoEncontradoException {
        Funcionario funcionario = enm.find(Funcionario.class, id);
        if (funcionario == null)
            throw new IdNaoEncontradoException("Funcionario nao encontrado");
        return funcionario;
    }

    public void commit() throws CommitException {
        try {
            enm.getTransaction().begin();
            enm.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            enm.getTransaction().rollback();
            throw new CommitException();
        }
    }
}