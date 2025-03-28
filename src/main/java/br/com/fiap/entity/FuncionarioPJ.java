package br.com.fiap.entity;

import br.com.fiap.entity.StatusFuncionario;
import br.com.fiap.annotation.Coluna;
import br.com.fiap.annotation.Tabela;

import javax.persistence.*;

@Tabela(nome = "TAB_FUNCIONARIO_PJ")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public class FuncionarioPJ extends Funcionario {
    @Coluna(nome = "VALOR_PROJETO")
    private double valorProjeto;

    @Enumerated(EnumType.STRING)
    @Coluna(nome = "STATUS_FUNCIONARIO")
    private StatusFuncionario statusFuncionario;

    public FuncionarioPJ(String nome, int horasTrabalhadas, double valorHora, double valorProjeto, StatusFuncionario statusFuncionario) {
        super(nome, horasTrabalhadas, valorHora);
        this.valorProjeto = valorProjeto;
        this.statusFuncionario = statusFuncionario;
    }

    public FuncionarioPJ() {
        super();
    }

    @Override
    public double calcularSalario() {

        return super.calcularSalario() + valorProjeto;
    }

    @Override
    public void imprimirInformacao() {
        super.imprimirInformacao();
        System.out.println("Valor do Projeto: R$ " + String.format("%.2f", valorProjeto));
        System.out.println("Status: " + statusFuncionario);
    }

    public double getValorProjeto() {
        return valorProjeto;
    }

    public void setValorProjeto(double valorProjeto) {
        this.valorProjeto = valorProjeto;
    }

    public StatusFuncionario getStatusFuncionario() {
        return statusFuncionario;
    }

    public void setStatusFuncionario(StatusFuncionario statusFuncionario) {
        this.statusFuncionario = statusFuncionario;
    }
}

