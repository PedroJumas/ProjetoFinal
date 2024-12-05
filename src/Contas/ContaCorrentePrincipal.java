package Contas;

public class ContaCorrentePrincipal extends Conta {
    
    private double limiteChequeEspecial;

    // Modificação do construtor para aceitar o limite do cheque especial
    public ContaCorrentePrincipal(int numeroConta, double saldo, double limiteChequeEspecial) {
        super(numeroConta, saldo);
        this.limiteChequeEspecial = limiteChequeEspecial;
    }

    // Getter e Setter para o limite do cheque especial
    public double getLimiteChequeEspecial() {
        return limiteChequeEspecial;
    }

    public void setLimiteChequeEspecial(double limiteChequeEspecial) {
        this.limiteChequeEspecial = limiteChequeEspecial;
    }

    @Override
    public void sacar(double valor) {
        if (valor <= (this.saldo + this.limiteChequeEspecial)) {
            if (valor <= this.saldo) {
                this.saldo -= valor;
            } else {
                double valorUsadoDoLimite = valor - this.saldo;
                this.saldo = 0;
                this.limiteChequeEspecial -= valorUsadoDoLimite;
            }
            System.out.println("Saque realizado com sucesso!");
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }

    @Override
    public void transferir(double valor, Conta contaDestino) {
        if (valor <= (this.saldo + this.limiteChequeEspecial)) {
            if (valor <= this.saldo) {
                this.saldo -= valor;
            } else {
                double valorUsadoDoLimite = valor - this.saldo;
                this.saldo = 0;
                this.limiteChequeEspecial -= valorUsadoDoLimite;
            }
            contaDestino.depositar(valor);
            System.out.println("Transferência realizada com sucesso!");
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }
}
