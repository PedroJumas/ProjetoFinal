package Usuarios;

import Contas.*;
import Utilitarios.GerenciadorArquivos;
import main.Main;

import java.util.Random;
import java.util.Scanner;

public class Gerente extends Bancario {

    private static Scanner scanner = new Scanner(System.in);

    public Gerente(String nome, String cpf, String senha) {
        super(nome, cpf, senha);
    }

    public void criarUsuario() {
    	String nome,cpf,senha;
    	int tipoUsuario;
    	
        System.out.println("\n=== CRIAR NOVO USUÁRIO ===");
        System.out.print("Nome: ");
        nome = scanner.nextLine();
        System.out.print("CPF: ");
        cpf = scanner.nextLine();
        System.out.print("Senha: ");
        senha = scanner.nextLine();

        System.out.println("\nTipo de usuário:");
        System.out.println("1. Cliente");
        System.out.println("2. Bancário");
        System.out.println("3. Gerente");
        System.out.print("Opção: ");
        tipoUsuario = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        Usuario novoUsuario = null;
        switch (tipoUsuario) {
            case 1:
                novoUsuario = new Cliente(nome, cpf, senha);
                break;
            case 2:
                novoUsuario = new Bancario(nome, cpf, senha);
                break;
            case 3:
                novoUsuario = new Gerente(nome, cpf, senha);
                break;
            default:
                System.out.println("Opção inválida.");
                return;
        }

        if (novoUsuario != null) {
            Main.usuarios.add(novoUsuario);
            System.out.println("\nUsuário criado com sucesso!");
        }

        GerenciadorArquivos gerenciadorArquivos = new GerenciadorArquivos();
        gerenciadorArquivos.gravarUsuarios(Main.usuarios);
    }

    public void criarConta(Cliente cliente) {
    	int opcaoTipoConta,numeroConta,numeroContaPrincipal;
    	double limiteChequeEspecial,limite;
    	
    	numeroConta = gerarNumeroConta();
        Conta novaConta = null;

        System.out.println("\nTipo de conta:");
        System.out.println("1. Conta Corrente Principal");
        System.out.println("2. Conta Poupança");
        System.out.println("3. Conta Corrente Adicional");
        System.out.print("Opção: ");

        opcaoTipoConta = scanner.nextInt();
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
                System.out.print("Digite o limite do cheque especial: ");
                limiteChequeEspecial = scanner.nextDouble();
                scanner.nextLine(); // Consumir a quebra de linha
                novaConta = new ContaCorrentePrincipal(numeroConta, 0, limiteChequeEspecial);
                break;
            case POUPANCA:
                novaConta = new ContaPoupanca(numeroConta, 0);
                break;
            case CORRENTE_ADICIONAL:
                System.out.print("Número da conta principal: ");
                numeroContaPrincipal = scanner.nextInt();
                scanner.nextLine(); // Consumir a quebra de linha
                System.out.print("Limite de gastos: ");
                limite = scanner.nextDouble();
                scanner.nextLine(); // Consumir a quebra de linha

                Conta contaPrincipal = cliente.encontrarConta(numeroContaPrincipal); 
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
}