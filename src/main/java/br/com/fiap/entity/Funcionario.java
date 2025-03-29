package br.com.fiap.entity;


import javax.persistence.*;

@Entity
@Table(name = "TAB_FUNCIONARIO")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "funcionarioSeq")
    @SequenceGenerator(name = "funcionarioSeq", sequenceName = "FUNCIONARIO_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private int id;
    @Column(name = "NOME", length = 120, nullable = false)
    private String nome;
    @Column(name = "HORAS_TRABALHADAS", nullable = false)
    private int horasTrabalhadas;
    @Column(name = "VALOR_POR_HORA", nullable = false)
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