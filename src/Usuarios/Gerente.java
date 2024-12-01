package Usuarios;

import Contas.*;
import main.Main;

import java.util.Random;
import java.util.Scanner;

public class Gerente extends Bancario {

    private static Scanner scanner = new Scanner(System.in);

    public Gerente(String nome, String cpf, String senha) {
        super(nome, cpf, senha);
    }

    public void criarConta(Cliente cliente) {
        int numeroConta = gerarNumeroConta();
        Conta novaConta = null;

        System.out.println("\nTipo de conta:");
        System.out.println("1. Conta Corrente Principal");
        System.out.println("2. Conta Poupança");
        System.out.println("3. Conta Corrente Adicional");
        System.out.print("Opção: ");

        int opcaoTipoConta = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        TipoConta tipoConta;
        if (opcaoTipoConta > 0 && opcaoTipoConta <= TipoConta.values().length) {
            tipoConta = TipoConta.values()[opcaoTipoConta - 1];
        } else {
            System.out.println("Opção inválida.");
            return;
        }

        switch (tipoConta) {
            case CORRENTE_PRINCIPAL:
                novaConta = new ContaCorrentePrincipal(numeroConta, 0);
                break;
            case POUPANCA:
                novaConta = new ContaPoupanca(numeroConta, 0);
                break;
            case CORRENTE_ADICIONAL:
                System.out.print("Número da conta principal: ");
                int numeroContaPrincipal = scanner.nextInt();
                scanner.nextLine(); // Consumir a quebra de linha
                System.out.print("Limite de gastos: ");
                double limite = scanner.nextDouble();
                scanner.nextLine(); // Consumir a quebra de linha

                Conta contaPrincipal = encontrarConta(cliente, numeroContaPrincipal);
                if (contaPrincipal instanceof ContaCorrentePrincipal) {
                    novaConta = new ContaCorrenteAdicional(numeroConta, 0, limite, (ContaCorrentePrincipal) contaPrincipal);
                } else {
                    System.out.println("Conta principal não encontrada ou não é do tipo Conta Corrente Principal.");
                    return;
                }
                break;
        }

        if (novaConta != null) {
            cliente.adicionarConta(novaConta);
            Main.contas.add(novaConta);
            System.out.println("Conta criada com sucesso! Número da conta: " + numeroConta);
        }
    }

    private int gerarNumeroConta() {
        Random random = new Random();
        int numeroConta;
        do {
            numeroConta = random.nextInt(10000);
        } while (contaExiste(numeroConta));
        return numeroConta;
    }

    private boolean contaExiste(int numeroConta) {
        for (Conta conta : Main.contas) {
            if (conta.getNumeroConta() == numeroConta) {
                return true;
            }
        }
        return false;
    }

    private static Conta encontrarConta(Cliente cliente, int numeroConta) {
        for (Conta conta : cliente.getContas()) {
            if (conta.getNumeroConta() == numeroConta) {
                return conta;
            }
        }
        return null;
    }
}