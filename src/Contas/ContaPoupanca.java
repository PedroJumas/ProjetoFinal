package Contas;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ContaPoupanca extends Conta {
    private LocalDate dataCriacao;  // A data em que a conta foi criada
    private LocalDate ultimaAlteracao;  // Data do último depósito ou alteração

    public ContaPoupanca(int numeroConta, double saldo) {
        super(numeroConta, saldo);
        this.dataCriacao = LocalDate.now();  // Define a data de criação como a data atual
        this.ultimaAlteracao = LocalDate.now();  // A primeira alteração ocorre na criação
    }

    @Override
    public void sacar(double valor) {
        if (valor <= this.saldo) {
            this.saldo -= valor;
            System.out.println("Saque realizado com sucesso!");
            // Atualiza a data da última alteração
            this.ultimaAlteracao = LocalDate.now();
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }

    @Override
    public void transferir(double valor, Conta contaDestino) {
        if (valor <= this.saldo) {
            this.saldo -= valor;
            contaDestino.depositar(valor);
            System.out.println("Transferência realizada com sucesso!");
            // Atualiza a data da última alteração
            this.ultimaAlteracao = LocalDate.now();
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }

    @Override
    public void depositar(double valor) {
        this.saldo += valor;
        System.out.println("Depósito de R$" + valor + " realizado com sucesso!");

        // Atualiza a data da última alteração
        this.ultimaAlteracao = LocalDate.now();

        // Aplica o rendimento após o depósito, calculando o tempo desde o último depósito
        calcularRendimento();
    }

    // Método para calcular o rendimento com base no tempo desde o último depósito (em dias)
    private void calcularRendimento() {
        // Calculando o tempo em dias entre a última alteração e a data atual
        long dias = ChronoUnit.DAYS.between(this.ultimaAlteracao, LocalDate.now());

        // Calculando o rendimento com juros compostos em função de dias
        double taxaAnual = 5.0;  // Exemplo de taxa de juros anual (pode ser modificada)
        double rendimento = this.saldo * Math.pow(1 + (taxaAnual / 365) / 100, dias) - this.saldo;
        this.saldo += rendimento;  // Atualizando o saldo com o rendimento

        // Exibindo o resultado
        System.out.println("Rendimento de " + taxaAnual + "% aplicado por " + dias + " dias. Novo saldo: R$" + this.saldo);
    }

    // Método para exibir o saldo
    public double getSaldo() {
        return this.saldo;
    }

    // Método para simular a passagem de tempo por meio de Thread.sleep() (em milissegundos)
    public void simularPassagemDeTempo(long dias) {
        // Calcula o número de milissegundos para simular o número de dias
        long milissegundosPorDia = 1000 * 60 * 60 * 24;
        long tempoDeEspera = dias * milissegundosPorDia;

        try {
            System.out.println("Simulando passagem de " + dias + " dias...");
            Thread.sleep(tempoDeEspera);  // Simula a espera do número de dias em milissegundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Atualiza a data da última alteração como se o tempo tivesse passado
        this.ultimaAlteracao = this.ultimaAlteracao.plusDays(dias);
    }
}
