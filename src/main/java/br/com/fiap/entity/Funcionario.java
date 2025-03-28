package br.com.fiap.entity;


import br.com.fiap.annotation.Coluna;
import br.com.fiap.annotation.Tabela;
import jdk.jshell.Snippet;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Tabela(nome="TAB_FUNCIONARIO")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Coluna(nome = "ID")
    private int id;
    @Coluna(nome = "NOME", tamanho = 120, obrigatorio = true)
    private String nome;
    @Coluna(nome = "HORAS_TRABALHADAS", obrigatorio = true)
    private int horasTrabalhadas;
    @Coluna(nome = "VALOR_POR_HORA", obrigatorio = true)
    private double valorPorHora;




    public Funcionario(String nome, int horasTrabalhadas, double valorPorHora) {
        this.nome = nome;
        this.horasTrabalhadas = horasTrabalhadas;
        this.valorPorHora = valorPorHora;
    }

    public Funcionario() {
        super();
    }

    public double calcularSalario() {
        return horasTrabalhadas * valorPorHora;
    }

    public void  imprimirInformacao() {
        System.out.println("Nome: " + nome);
        System.out.println("Horas Trabalhadas: " + horasTrabalhadas);
        System.out.println("Valor por Hora: " + valorPorHora);
        System.out.println("Salário Final: " + calcularSalario());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getHorasTrabalhadas() {
        return horasTrabalhadas;
    }

    public void setHorasTrabalhadas(int horasTrabalhadas) {
        this.horasTrabalhadas = horasTrabalhadas;
    }

    public double getValorPorHora() {
        return valorPorHora;
    }

    public void setValorPorHora(double valorPorHora) {
        this.valorPorHora = valorPorHora;
    }
}