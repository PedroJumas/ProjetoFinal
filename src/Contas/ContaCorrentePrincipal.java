package Contas;

public class ContaCorrentePrincipal extends Conta {

    public double limiteChequeEspecial;

    public ContaCorrentePrincipal(int numeroConta, double saldo, double limiteChequeEspecial) {
        super(numeroConta, saldo);
        this.limiteChequeEspecial = limiteChequeEspecial;
    }

    public double getLimiteChequeEspecial() {
        return limiteChequeEspecial;
    }

    public void setLimiteChequeEspecial(double limiteChequeEspecial) {
        this.limiteChequeEspecial = limiteChequeEspecial;
    }

    @Override
    public void sacar(double valor) {
    	double valorUsadoDoLimite;
    	
        if (valor <= (this.getSaldo() + this.limiteChequeEspecial)) {
            if (valor <= this.getSaldo()) {
                this.setSaldo(this.getSaldo() - valor);
            } else {
                valorUsadoDoLimite = valor - this.getSaldo();
                this.setSaldo(0);
                this.limiteChequeEspecial -= valorUsadoDoLimite;
            }
            System.out.println("Saque realizado com sucesso! \n Você ainda possui R$");
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }

    @Override
    public void transferir(double valor, Conta contaDestino) {
    	double valorUsadoDoLimite;
    	
        if (valor <= (this.getSaldo() + this.limiteChequeEspecial) && contaDestino != null) {
            if (valor <= this.getSaldo()) {
                this.setSaldo(this.getSaldo() - valor);
            } else {
                valorUsadoDoLimite = valor - this.getSaldo();
                this.setSaldo(0);
                this.limiteChequeEspecial -= valorUsadoDoLimite;
            }
            if (contaDestino instanceof ContaCorrentePrincipal || contaDestino instanceof ContaPoupanca) { // Verificação instanceof
                contaDestino.depositar(valor);
                System.out.println("Transferência realizada com sucesso!\nainda possui R$ "+limiteChequeEspecial+ " do seu cheque especial!");
            } else {
                System.out.println("A conta de destino não é uma conta corrente principal.");
            }
        } else {
            System.out.println("Saldo insuficiente ou conta de destino inválida.");
        }
    }
}