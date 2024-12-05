package Usuarios;

import Contas.Conta;
import main.Main;
import java.util.Scanner;

public class Bancario extends Usuario {

    private static Scanner scanner = new Scanner(System.in);

    public Bancario(String nome, String cpf, String senha) {
        super(nome, cpf, senha);
    }

    public void sacarDeCliente() {
    	String cpfCliente;
    	int numeroConta;
    	double valor;
    	
        System.out.println("\n=== SAQUE DE CONTA DE CLIENTE ===");
        System.out.print("CPF do cliente: ");
        cpfCliente = scanner.nextLine();
        System.out.print("Número da conta: ");
        numeroConta = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        System.out.print("Valor a sacar: ");
        valor = scanner.nextDouble();
        scanner.nextLine(); // Consumir a quebra de linha

        Cliente cliente = Main.encontrarCliente(cpfCliente);
        if (cliente != null) {
            Conta conta = Main.encontrarConta(cliente, numeroConta);
            if (conta != null) {
                if (valor > 0 && valor <= conta.getSaldo()) {
                    conta.sacar(valor);
                    System.out.println("Saque de R$" + valor + " da conta do cliente " + cliente.getNome() + " realizado com sucesso.");
                } else {
                    System.out.println("Saldo insuficiente ou valor inválido.");
                }
            } else {
                System.out.println("Conta não encontrada.");
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    public void depositarEmCliente() {
    	String cpfCliente;
    	int numeroConta;
    	double valor;
    	
        System.out.println("\n=== DEPÓSITO EM CONTA DE CLIENTE ===");
        System.out.print("CPF do cliente: ");
        cpfCliente = scanner.nextLine();
        System.out.print("Número da conta: ");
        numeroConta = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        System.out.print("Valor a depositar: ");
        valor = scanner.nextDouble();
        scanner.nextLine(); // Consumir a quebra de linha

        Cliente cliente = Main.encontrarCliente(cpfCliente);
        if (cliente != null) {
            Conta conta = Main.encontrarConta(cliente, numeroConta);
            if (conta != null) {
                if (valor > 0) {
                    conta.depositar(valor);
                    System.out.println("Depósito de R$" + valor + " na conta do cliente " + cliente.getNome() + " realizado com sucesso.");
                } else {
                    System.out.println("Valor inválido para depósito.");
                }
            } else {
                System.out.println("Conta não encontrada.");
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    public void transferirEntreClientes() {
    	String cpfClienteOrigem,cpfClienteDestino;
    	int numeroContaOrigem,numeroContaDestino;
    	double valor;
    	
        System.out.println("\n=== TRANSFERÊNCIA ENTRE CONTAS DE CLIENTES ===");
        System.out.print("CPF do cliente de origem: ");
        cpfClienteOrigem = scanner.nextLine();
        System.out.print("Número da conta de origem: ");
        numeroContaOrigem = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        System.out.print("CPF do cliente de destino: ");
        cpfClienteDestino = scanner.nextLine();
        System.out.print("Número da conta de destino: ");
        numeroContaDestino = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        System.out.print("Valor a transferir: ");
        valor = scanner.nextDouble();
        scanner.nextLine(); // Consumir a quebra de linha

        Cliente clienteOrigem = Main.encontrarCliente(cpfClienteOrigem);
        Cliente clienteDestino = Main.encontrarCliente(cpfClienteDestino);
        if (clienteOrigem != null && clienteDestino != null) {
            Conta contaOrigem = Main.encontrarConta(clienteOrigem, numeroContaOrigem);
            Conta contaDestino = Main.encontrarConta(clienteDestino, numeroContaDestino);
            if (contaOrigem != null && contaDestino != null) {
                if (valor > 0 && valor <= contaOrigem.getSaldo()) {
                    contaOrigem.transferir(valor, contaDestino);
                    System.out.println("Transferência de R$" + valor + " da conta do cliente " + clienteOrigem.getNome() + " para a conta do cliente " + clienteDestino.getNome() + " realizada com sucesso.");
                } else {
                    System.out.println("Saldo insuficiente ou valor inválido.");
                }
            } else {
                System.out.println("Conta de origem ou destino não encontrada.");
            }
        } else {
            System.out.println("Cliente de origem ou destino não encontrado.");
        }
    }

}