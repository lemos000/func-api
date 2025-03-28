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


    public FuncionarioPJ(String nome, int horasTrabalhadas, double valorHora, double valorProjeto, StatusFuncionario statusFuncionario) {
        super(nome, horasTrabalhadas, valorHora);
        this.valorProjeto = valorProjeto;
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
    }

    public double getValorProjeto() {
        return valorProjeto;
    }

    public void setValorProjeto(double valorProjeto) {
        this.valorProjeto = valorProjeto;
    }



}

