package Contas;

public class ContaCorrenteAdicional extends Conta {

    private double limite;
    private ContaCorrentePrincipal contaPrincipal;

    public ContaCorrenteAdicional(int numeroConta, double saldo, double limite, ContaCorrentePrincipal contaPrincipal) {
        super(numeroConta, saldo);
        this.limite = limite;
        this.contaPrincipal = contaPrincipal;
    }

    @Override
    public void sacar(double valor) {
        if (valor <= this.saldo + this.limite) {
            this.saldo -= valor;
            System.out.println("Saque realizado com sucesso!");
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }

    @Override
    public void transferir(double valor, Conta contaDestino) {
        if (valor <= this.saldo + this.limite) {
            this.saldo -= valor;
            contaDestino.depositar(valor);
            System.out.println("Transferência realizada com sucesso!");
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public ContaCorrentePrincipal getContaPrincipal() {
        return contaPrincipal;
    }

    public void setContaPrincipal(ContaCorrentePrincipal contaPrincipal) {
        this.contaPrincipal = contaPrincipal;
    }
}