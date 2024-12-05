package Contas;

import java.time.LocalDate;

public class ContaPoupanca extends Conta {
    private LocalDate dataCriacao;
    private LocalDate ultimaAlteracao;

    public ContaPoupanca(int numeroConta, double saldo) {
        super(numeroConta, saldo);
        this.dataCriacao = LocalDate.now();
        this.ultimaAlteracao = LocalDate.now();
    }

    // ... (outros métodos) ...

    // Método para simular a passagem de tempo por meio de Thread.sleep() (em milissegundos)
    public void simularPassagemDeTempo(long dias) {
    	long milissegundosPorDia, tempoDeEspera;
    	
        // Calcula o número de milissegundos para simular o número de dias
        milissegundosPorDia = 1000 * 60 * 60 * 24;
        tempoDeEspera = dias * milissegundosPorDia;

        try {
            System.out.println("Simulando passagem de " + dias + " dias...");
            Thread.sleep(tempoDeEspera); // Simula a espera do número de dias em milissegundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Atualiza a data da última alteração como se o tempo tivesse passado
        this.ultimaAlteracao = this.ultimaAlteracao.plusDays(dias);
    }
}