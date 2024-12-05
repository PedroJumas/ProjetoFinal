package Contas;

public abstract class Conta {

    private int numeroConta;
    private double saldo;

    public Conta(int numeroConta, double saldo) {
        this.numeroConta = numeroConta;
        this.saldo = saldo;
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public double getSaldo() {
        return saldo;
    }
    
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void sacar(double valor) {
        if (valor > 0 && valor <= this.saldo) {
            this.saldo -= valor;
        } else {
            System.out.println("Saldo insuficiente ou valor inválido.");
        }
    }

    public void depositar(double valor) {
        if (valor > 0) {
            this.saldo += valor;
        } else {
            System.out.println("Valor inválido para depósito.");
        }
    }

    public void transferir(double valor, Conta contaDestino) {
        if (valor > 0 && valor <= this.saldo && contaDestino != null) {
            this.saldo -= valor;
            contaDestino.saldo += valor;
     
        } else {
            System.out.println("Saldo insuficiente, valor inválido ou conta de destino inválida.");
        }
    }
}