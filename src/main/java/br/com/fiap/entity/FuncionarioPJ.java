package br.com.fiap.entity;

import javax.persistence.*;

@Table(name = "TAB_FUNCIONARIO_PJ")
@Entity
public class FuncionarioPJ extends Funcionario {
    @Column(name = "VALOR_PROJETO")
    private double valorProjeto;


    public FuncionarioPJ(String nome, int horasTrabalhadas, double valorHora, double valorProjeto) {
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

