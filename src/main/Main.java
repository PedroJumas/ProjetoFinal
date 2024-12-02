package main;

import Contas.Conta;
import Utilitarios.GerenciadorArquivos;
import Usuarios.*;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final GerenciadorArquivos gerenciadorArquivos = new GerenciadorArquivos();
    private static List<Usuario> usuarios;
    public static List<Conta> contas; // Tornando a lista contas pública
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        usuarios = gerenciadorArquivos.lerUsuarios();
        contas = gerenciadorArquivos.lerContas(); 

        Usuario usuarioLogado = login();

        if (usuarioLogado != null) {
            exibirMenu(usuarioLogado);
        
        }

        gerenciadorArquivos.gravarUsuarios(usuarios);
        gerenciadorArquivos.gravarContas(contas);
        scanner.close();
    }

    private static Usuario login() {
        System.out.println("=== LOGIN ===");
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        for (Usuario usuario : usuarios) {
            if (usuario.login(cpf, senha)) {
                System.out.println("Login efetuado com sucesso!");
                return usuario;
            }
        
        }
        System.out.println("CPF ou senha inválidos.");
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
                    verSaldo(cliente);
                    break;
                case 2:
                    sacar(cliente);
                    break;
                case 3:
                    depositar(cliente);
                    break;
                case 4:
                    transferir(cliente);
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 5);
    }

    private static void verSaldo(Cliente cliente) {
        System.out.println("\n=== SALDO DAS CONTAS ===");
        for (Conta conta : cliente.getContas()) {
            System.out.println("Conta " + conta.getNumeroConta() + ": R$" + conta.getSaldo());
        }
    }

    private static void sacar(Cliente cliente) {
        System.out.println("\n=== SAQUE ===");
        System.out.print("Número da conta: ");
        int numeroConta = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        System.out.print("Valor a sacar: ");
        double valor = scanner.nextDouble();
        scanner.nextLine(); // Consumir a quebra de linha

        Conta conta = encontrarConta(cliente, numeroConta);
        if (conta != null) {
            conta.sacar(valor);
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private static void depositar(Cliente cliente) {
        System.out.println("\n=== DEPÓSITO ===");
        System.out.print("Número da conta: ");
        int numeroConta = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        System.out.print("Valor a depositar: ");
        double valor = scanner.nextDouble();
        scanner.nextLine(); // Consumir a quebra de linha

        Conta conta = encontrarConta(cliente, numeroConta);
        if (conta != null) {
            conta.depositar(valor);
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private static void transferir(Cliente cliente) {
        System.out.println("\n=== TRANSFERÊNCIA ===");
        System.out.print("Número da conta de origem: ");
        int numeroContaOrigem = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        System.out.print("Número da conta de destino: ");
        int numeroContaDestino = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        System.out.print("Valor a transferir: ");
        double valor = scanner.nextDouble();
        scanner.nextLine(); // Consumir a quebra de linha

        Conta contaOrigem = encontrarConta(cliente, numeroContaOrigem);
        Conta contaDestino = encontrarConta(cliente, numeroContaDestino);

        if (contaOrigem != null && contaDestino != null) {
            contaOrigem.transferir(valor, contaDestino);
        } else {
            System.out.println("Conta de origem ou destino não encontrada.");
        }
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
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    sacarDeCliente(bancario);
                    break;
                case 2:
                    depositarEmCliente(bancario);
                    break;
                case 3:
                    transferirEntreClientes(bancario);
                    break;
                case 4:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 4);
    }

    private static void sacarDeCliente(Bancario bancario) {
        System.out.println("\n=== SAQUE DE CONTA DE CLIENTE ===");
        System.out.print("CPF do cliente: ");
        String cpfCliente = scanner.nextLine();
        System.out.print("Número da conta: ");
        int numeroConta = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        System.out.print("Valor a sacar: ");
        double valor = scanner.nextDouble();
        scanner.nextLine(); // Consumir a quebra de linha

        Cliente cliente = encontrarCliente(cpfCliente);
        if (cliente != null) {
            Conta conta = encontrarConta(cliente, numeroConta);
            if (conta != null) {
                bancario.sacar(cliente, conta, valor); 
            } else {
                System.out.println("Conta não encontrada.");
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private static void depositarEmCliente(Bancario bancario) {
        System.out.println("\n=== DEPÓSITO EM CONTA DE CLIENTE ===");
        System.out.print("CPF do cliente: ");
        String cpfCliente = scanner.nextLine();
        System.out.print("Número da conta: ");
        int numeroConta = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        System.out.print("Valor a depositar: ");
        double valor = scanner.nextDouble();
        scanner.nextLine(); // Consumir a quebra de linha

        Cliente cliente = encontrarCliente(cpfCliente);
        if (cliente != null) {
            Conta conta = encontrarConta(cliente, numeroConta);
            if (conta != null) {
                bancario.depositar(cliente, conta, valor); 
            } else {
                System.out.println("Conta não encontrada.");
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private static void transferirEntreClientes(Bancario bancario) {
        System.out.println("\n=== TRANSFERÊNCIA ENTRE CONTAS DE CLIENTES ===");
        System.out.print("CPF do cliente de origem: ");
        String cpfClienteOrigem = scanner.nextLine();
        System.out.print("Número da conta de origem: ");
        int numeroContaOrigem = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        System.out.print("CPF do cliente de destino: ");
        String cpfClienteDestino = scanner.nextLine();
        System.out.print("Número da conta de destino: ");
        int numeroContaDestino = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        System.out.print("Valor a transferir: ");
        double valor = scanner.nextDouble();
        scanner.nextLine(); // Consumir a quebra de linha

        Cliente clienteOrigem = encontrarCliente(cpfClienteOrigem);
        Cliente clienteDestino = encontrarCliente(cpfClienteDestino);
        if (clienteOrigem != null && clienteDestino != null) {
            Conta contaOrigem = encontrarConta(clienteOrigem, numeroContaOrigem);
            Conta contaDestino = encontrarConta(clienteDestino, numeroContaDestino);
            if (contaOrigem != null && contaDestino != null) {
                bancario.transferir(clienteOrigem, contaOrigem, contaDestino, valor); 
            } else {
                System.out.println("Conta de origem ou destino não encontrada.");
            }
        } else {
            System.out.println("Cliente de origem ou destino não encontrado.");
        }
    }

    private static void exibirMenuGerente(Gerente gerente) {
        int opcao;
        do {
            System.out.println("\n=== MENU GERENTE ===");
            System.out.println("1. Sacar de conta de cliente");
            System.out.println("2. Depositar em conta de cliente");
            System.out.println("3. Transferir entre contas de clientes");
            System.out.println("4. Criar nova conta");
            System.out.println("5. Sair");
            System.out.print("Opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    sacarDeCliente(gerente); // Reutiliza o método do Bancario
                    break;
                case 2:
                    depositarEmCliente(gerente); // Reutiliza o método do Bancario
                    break;
                case 3:
                    transferirEntreClientes(gerente); // Reutiliza o método do Bancario
                    break;
                case 4:
                    System.out.print("CPF do cliente: ");
                    String cpfCliente = scanner.nextLine();
                    Cliente cliente = encontrarCliente(cpfCliente);
                    if (cliente != null) {
                        gerente.criarConta(cliente);
                    } else {
                        System.out.println("Cliente não encontrado.");
                    }
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 5);
    }

    private static Conta encontrarConta(Cliente cliente, int numeroConta) {
        for (Conta conta : cliente.getContas()) {
            if (conta.getNumeroConta() == numeroConta) {
                return conta;
            }
        }
        return null;
    }

    private static Cliente encontrarCliente(String cpf) {
        for (Usuario usuario : usuarios) {
            if (usuario instanceof Cliente && usuario.getCpf().equals(cpf)) {
                return (Cliente) usuario;
            }
        }
        return null;
    }
    // --- Outros métodos auxiliares, se necessário ---
}