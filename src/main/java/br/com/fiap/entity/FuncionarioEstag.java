package br.com.fiap.entity;


import br.com.fiap.annotation.Coluna;
import br.com.fiap.annotation.Tabela;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Tabela(nome="TAB_FUNCIONARIOESTAG")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public class FuncionarioEstag extends Funcionario {
    @Coluna(nome = "DESCONTO")
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

}
