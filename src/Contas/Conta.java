package Contas;

public abstract class Conta {

    protected int numeroConta;
    protected double saldo;

    public Conta(int numeroConta, double saldo) {
        this.numeroConta = numeroConta;
        this.saldo = saldo;
    }

    public abstract void sacar(double valor);

    public void depositar(double valor) {
        this.saldo += valor;
        System.out.println("Depósito realizado com sucesso!");
    }

    public abstract void transferir(double valor, Conta contaDestino);

    public int getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(int numeroConta) {
        this.numeroConta = numeroConta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}