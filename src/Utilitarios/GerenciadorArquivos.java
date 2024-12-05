package Utilitarios;

import Usuarios.*;
import main.Main;
import Contas.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorArquivos {

    public List<Usuario> lerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("usuarios.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",");
                String tipo = campos[0];
                String nome = campos[1];
                String cpf = campos[2];
                String senha = campos[3];

                Usuario usuario = null;
                switch (tipo) {
                    case "cliente":
                        usuario = new Cliente(nome, cpf, senha);
                        break;
                    case "bancario":
                        usuario = new Bancario(nome, cpf, senha);
                        break;
                    case "gerente":
                        usuario = new Gerente(nome, cpf, senha);
                        break;
                }
                if (usuario != null) {
                    usuarios.add(usuario);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de usuários: " + e.getMessage());
        }
        return usuarios;
    }

    public void gravarUsuarios(List<Usuario> usuarios) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("usuarios.txt"))) { // true ativa o append
            for (Usuario usuario : usuarios) {
                String tipo = "";
                if (usuario instanceof Cliente) {
                    tipo = "cliente";
                } else if (usuario instanceof Gerente) {
                    tipo = "gerente"; 
                } else if (usuario instanceof Bancario) {
                    tipo = "bancario";
                }
                bw.write(tipo + "," + usuario.getNome() + "," + usuario.getCpf() + "," + usuario.getSenha());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao gravar o arquivo de usuários: " + e.getMessage());
        }
    }

    public List<Conta> lerContas() {
        List<Conta> contas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("contas.txt"))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",");
                String tipo = campos[0];

                // Verifica o número de campos de acordo com o tipo de conta
                if ((tipo.equals("corrente_principal") || tipo.equals("poupanca")) && campos.length == 4 || 
                    (tipo.equals("corrente_adicional") && campos.length == 6)) {

                    int numeroConta = Integer.parseInt(campos[1]);
                    double saldo = Double.parseDouble(campos[2]);
                    String cpfCliente = campos[3];

                    Conta conta = null;
                    switch (tipo) {
                        case "corrente_principal":
                            double limiteChequeEspecial = 1000; // Aqui você pode pegar o limite do arquivo se necessário
                            conta = new ContaCorrentePrincipal(numeroConta, saldo, limiteChequeEspecial);  // Passando o limite
                            break;
                        case "poupanca":
                            conta = new ContaPoupanca(numeroConta, saldo);
                            break;
                        case "corrente_adicional":
                            double limite = Double.parseDouble(campos[4]);
                            int contaPrincipal = Integer.parseInt(campos[5]);
                            Conta contaPrincipalObj = encontrarConta(contas, contaPrincipal);
                            if (contaPrincipalObj instanceof ContaCorrentePrincipal) {
                                conta = new ContaCorrenteAdicional(numeroConta, saldo, limite, (ContaCorrentePrincipal) contaPrincipalObj);
                            } else {
                                System.err.println("Conta principal não encontrada ou não é do tipo Conta Corrente Principal.");
                            }
                            break;
                    }
                    if (conta != null) {
                        Cliente cliente = Main.encontrarCliente(cpfCliente); // Busca o cliente pelo CPF
                        if (cliente != null) {
                            cliente.adicionarConta(conta); // Adiciona a conta à lista do cliente
                        } else {
                            System.err.println("Cliente não encontrado para a conta " + numeroConta);
                        }
                        contas.add(conta);
                    }
                } else {
                    System.err.println("Erro ao ler linha do arquivo: formato inválido. Linha: " + linha);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de contas: " + e.getMessage());
        }
        return contas;
    }

    public Conta encontrarConta(List<Conta> contas, int numeroConta) {
        for (Conta conta : contas) {
            if (conta.getNumeroConta() == numeroConta) {
                return conta;
            }
        }
        return null;
    }

    public void gravarContas(List<Conta> contas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("contas.txt"))) {
            for (Conta conta : contas) {
                String tipo = "";
                if (conta instanceof ContaCorrentePrincipal) {
                    tipo = "corrente_principal";
                } else if (conta instanceof ContaPoupanca) {
                    tipo = "poupanca";
                } else if (conta instanceof ContaCorrenteAdicional) {
                    tipo = "corrente_adicional";
                }

                // Obtém o CPF do cliente da conta (se disponível)
                String cpfCliente = "";
                for (Usuario usuario : Main.usuarios) {
                    if (usuario instanceof Cliente) {
                        Cliente cliente = (Cliente) usuario;
                        if (cliente.getContas().contains(conta)) {
                            cpfCliente = cliente.getCpf();
                            break;
                        }
                    }
                }

                // Escreve os dados da conta no arquivo, incluindo o CPF do cliente
                bw.write(tipo + "," + conta.getNumeroConta() + "," + conta.getSaldo() + "," + cpfCliente);

                if (conta instanceof ContaCorrenteAdicional) {
                    ContaCorrenteAdicional contaAdicional = (ContaCorrenteAdicional) conta;
                    bw.write(","+contaAdicional.getLimite() + "," + contaAdicional.getContaPrincipal().getNumeroConta());
                }
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao gravar o arquivo de contas: " + e.getMessage());
        }
    }
}
