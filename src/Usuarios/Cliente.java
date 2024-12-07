package Usuarios;

import Contas.Conta;
import Contas.ContaCorrenteAdicional;
import Contas.ContaCorrentePrincipal;
import Contas.ContaPoupanca;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cliente extends Usuario {

    private List<Conta> contas;
    private static Scanner scanner = new Scanner(System.in); 

    public Cliente(String nome, String cpf, String senha) {
        super(nome, cpf, senha);
        this.contas = new ArrayList<>();
    }

    public void adicionarConta(Conta conta) {
        this.contas.add(conta);
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void verSaldo() {
        System.out.println("\n=== SALDO DAS CONTAS ===");
        for (Conta conta : this.getContas()) {
            if (conta instanceof ContaCorrentePrincipal) {
                ContaCorrentePrincipal contaCorrente = (ContaCorrentePrincipal) conta;
                System.out.println("Conta " + contaCorrente.getNumeroConta() + ": R$" + contaCorrente.getSaldo() + " - Limite do Cheque Especial: R$" + contaCorrente.getLimiteChequeEspecial());
            }
            if (conta instanceof ContaPoupanca) {
            	ContaPoupanca contaPoupanca = (ContaPoupanca) conta;
                System.out.println("Conta " + contaPoupanca.getNumeroConta() +  ": R$" + contaPoupanca.getSaldo());
            }
            if (conta instanceof ContaCorrenteAdicional) {
            	ContaCorrenteAdicional contaAdicional = (ContaCorrenteAdicional) conta;
                System.out.println("Conta " + contaAdicional.getNumeroConta() +  ": R$" + contaAdicional.getSaldo() + " - Limite: R$" + contaAdicional.getLimite());
            }
        }
    }

    public void sacar() {
    	int numeroConta;
    	double valor;
    	
        System.out.println("\n=== SAQUE ===");
        System.out.print("Número da conta: ");
        numeroConta = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        System.out.print("Valor a sacar: ");
        valor = scanner.nextDouble();
        scanner.nextLine(); // Consumir a quebra de linha

        Conta conta = encontrarConta(numeroConta);
        if (conta != null) {
            conta.sacar(valor); 
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    public void depositar() {
    	int numeroConta;
    	double valor;
    	
        System.out.println("\n=== DEPÓSITO ===");
        System.out.print("Número da conta: ");
        numeroConta = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        System.out.print("Valor a depositar: ");
        valor = scanner.nextDouble();
        scanner.nextLine(); // Consumir a quebra de linha

        Conta conta = encontrarConta(numeroConta);
        if (conta != null) {
            conta.depositar(valor); 
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    public void transferir() {
    	int numeroContaDestino,numeroContaOrigem;
    	double valor;
    	
        System.out.println("\n=== TRANSFERÊNCIA ===");
        System.out.print("Número da conta de origem: ");
        numeroContaOrigem = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        System.out.print("Número da conta de destino: ");
        numeroContaDestino = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        System.out.print("Valor a transferir: ");
        valor = scanner.nextDouble();
        scanner.nextLine(); // Consumir a quebra de linha

        Conta contaOrigem = encontrarConta(numeroContaOrigem);
        Conta contaDestino = encontrarConta(numeroContaDestino);

        if (contaOrigem != null && contaDestino != null) {
            contaOrigem.transferir(valor, contaDestino); 
        } else {
            System.out.println("Conta de origem ou destino não encontrada.");
        }
    }

    public Conta encontrarConta(int numeroConta) {
        for (Conta conta : this.getContas()) {
            if (conta.getNumeroConta() == numeroConta) {
                return conta;
            }
        }
        return null;
    }
}