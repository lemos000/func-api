package br.com.fiap.entity;


import javax.persistence.*;


@Table(name="TAB_FUNCIONARIOSENIOR")
@Entity
public class FuncionarioSenior extends Funcionario {
    @Column(name = "BONUS")
    private double bonus;

    public FuncionarioSenior(String nome, int horasTrabalhadas, double valorPorHora, double bonus) {
        super(nome, horasTrabalhadas, valorPorHora);
        this.bonus = bonus;
    }

    public FuncionarioSenior() {
        super();
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    public double  calcularSalario() {
        double bonusTotal = (super.getHorasTrabalhadas() / 15) * bonus;
        return super.calcularSalario() + bonusTotal;
    }

    @Override
    public void imprimirInformacao() {
        System.out.println("Nome: " + this.getNome());
        System.out.println("Horas Trabalhadas: " + this.getHorasTrabalhadas());
        System.out.println("Valor por Hora: " + this.getValorPorHora());
        System.out.println("Bonus: " + this.getBonus());
        System.out.println("Salário Final: " + calcularSalario());
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
}
