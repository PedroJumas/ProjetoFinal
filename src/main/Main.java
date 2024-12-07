package main;

import Utilitarios.GerenciadorArquivos;
import Usuarios.*;
import java.util.List;
import java.util.Scanner;

import Contas.Conta;

public class Main {

    private static final GerenciadorArquivos gerenciadorArquivos = new GerenciadorArquivos();
    public static List<Usuario> usuarios;
    public static List<Conta> contas;
    public static Scanner scanner = new Scanner(System.in); 

    public static void main(String[] args) {
        usuarios = gerenciadorArquivos.lerUsuarios();
        contas = gerenciadorArquivos.lerContas();

        boolean continuar;

        do {
            Usuario usuarioLogado = login();

            if (usuarioLogado != null) {
                exibirMenu(usuarioLogado);
            }

            System.out.print("\nDeseja fazer outro login (s/n)? ");
            String resposta = scanner.nextLine();
            continuar = resposta.equalsIgnoreCase("s");

            System.out.println();

        } while (continuar);

        gerenciadorArquivos.gravarUsuarios(usuarios);
        gerenciadorArquivos.gravarContas(contas);
        scanner.close();
    }

    private static Usuario login() {
    	String cpf,senha;
    	
        System.out.println("=== LOGIN ===");
        System.out.print("CPF: ");
        cpf = scanner.nextLine();
        System.out.print("Senha: ");
        senha = scanner.nextLine();

        for (Usuario usuario : usuarios) {
            if (usuario.login(cpf, senha)) {
                System.out.println("\nLogin efetuado com sucesso!");
                return usuario;
            }

        }
        System.out.println("\nCPF ou senha inválidos.");
        System.out.println();
        return null;
    }

    private static void exibirMenu(Usuario usuarioLogado) {
        if (usuarioLogado instanceof Cliente) {
            exibirMenuCliente((Cliente) usuarioLogado);
            return;
        } else if (usuarioLogado instanceof Gerente) {
            exibirMenuGerente((Gerente) usuarioLogado);
            return;
        } else if (usuarioLogado instanceof Bancario) {
            exibirMenuBancario((Bancario) usuarioLogado);
            return;
        }
    }

    private static void exibirMenuCliente(Cliente cliente) {
        int opcao;
        
        do {
            System.out.println("\n=== MENU CLIENTE ===");
            System.out.println("1. Ver saldo");
            System.out.println("2. Sacar");
            System.out.println("3. Depositar");
            System.out.println("4. Transferir");
            System.out.println("5. Sair");
            System.out.print("Opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    cliente.verSaldo(); 
                    break;
                case 2:
                    cliente.sacar(); 
                    break;
                case 3:
                    cliente.depositar(); 
                    break;
                case 4:
                    cliente.transferir();
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 5);
    }
   
    private static void exibirMenuBancario(Bancario bancario) {
        int opcao;
        
        do {
            System.out.println("\n=== MENU BANCÁRIO ===");
            System.out.println("1. Sacar de conta de cliente");
            System.out.println("2. Depositar em conta de cliente");
            System.out.println("3. Transferir entre contas de clientes");
            System.out.println("4. Sair");
            System.out.print("Opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                	bancario.sacarDeCliente();
                    break;
                case 2:
                	bancario.depositarEmCliente();
                    break;
                case 3:
                	bancario.transferirEntreClientes();
                    break;
                case 4:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 4);
    }


    public static void exibirMenuGerente(Gerente gerente) {
        int opcao;
        String cpfCliente;
        
        do {
            System.out.println("\n=== MENU GERENTE ===");
            System.out.println("1. Sacar de conta de cliente");
            System.out.println("2. Depositar em conta de cliente");
            System.out.println("3. Transferir entre contas de clientes");
            System.out.println("4. Criar novo usuario");
            System.out.println("5. Criar nova conta");
            System.out.println("6. Sair");
            System.out.print("Opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    gerente.sacarDeCliente(); 
                    break;
                case 2:
                    gerente.depositarEmCliente();
                    break;
                case 3:
                    gerente.transferirEntreClientes();
                    break;
                case 4:
                    gerente.criarUsuario();
                    break;
                case 5:
                    System.out.print("CPF do cliente: ");
                    cpfCliente = scanner.nextLine();
                    Cliente cliente = encontrarCliente(cpfCliente);
                    if (cliente != null) {
                        gerente.criarConta(cliente);
                    } else {
                        System.out.println("Cliente não encontrado.");
                    }
                    break;
                case 6:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 6);
    }

    public static Cliente encontrarCliente(String cpf) {
        for (Usuario usuario : usuarios) {
            if (usuario instanceof Cliente && usuario.getCpf().equals(cpf)) {
                return (Cliente) usuario;
            }
        }
        return null;
    }

    public static Conta encontrarConta(Cliente cliente, int numeroConta) {
        for (Conta conta : cliente.getContas()) {
            if (conta.getNumeroConta() == numeroConta) {
                return conta;
            }
        }
        return null;
    }
}