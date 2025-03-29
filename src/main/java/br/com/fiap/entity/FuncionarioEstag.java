package br.com.fiap.entity;


import javax.persistence.*;

@Table(name="TAB_FUNCIONARIOESTAG")
@Entity
public class FuncionarioEstag extends Funcionario {
    @Column(name = "DESCONTO")
    private double desconto;

    public FuncionarioEstag(String nome, int horasTrabalhadas, double valorPorHora, double desconto) {
        super(nome, horasTrabalhadas, valorPorHora);
        this.desconto = desconto;
    }

    public FuncionarioEstag() {
        super();
    }

    @Override
    public double calcularSalario(){
        return super.calcularSalario() - desconto;
    }

    @Override
    public void imprimirInformacao(){
        super.imprimirInformacao();
        System.out.println("Desconto aplicado: R$ " + this.desconto);
        System.out.println("Salário final com desconto: R$ " + calcularSalario());
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }
}
