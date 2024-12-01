package Utilitarios;

import Usuarios.*;
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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("usuarios.txt"))) {
            for (Usuario usuario : usuarios) {
                String tipo = "";
                if (usuario instanceof Cliente) {
                    tipo = "cliente";
                } else if (usuario instanceof Bancario) {
                    tipo = "bancario";
                } else if (usuario instanceof Gerente) {
                    tipo = "gerente";
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

                if (campos.length == 6) {  
                    String tipo = campos[0];
                    int numeroConta = Integer.parseInt(campos[1]);
                    double saldo = Double.parseDouble(campos[2]);
                    String cpfCliente = campos[3];
                    double limite = Double.parseDouble(campos[4]);
                    int contaPrincipal = Integer.parseInt(campos[5]);

                    Conta conta = null;
                    switch (tipo) {
                        case "corrente_principal":
                            conta = new ContaCorrentePrincipal(numeroConta, saldo);
                            break;
                        case "poupanca":
                            conta = new ContaPoupanca(numeroConta, saldo);
                            break;
                        case "corrente_adicional":
                            Conta contaPrincipalObj = encontrarConta(contas, contaPrincipal); 
                            if (contaPrincipalObj instanceof ContaCorrentePrincipal) {
                                conta = new ContaCorrenteAdicional(numeroConta, saldo, limite, (ContaCorrentePrincipal) contaPrincipalObj);
                            } else {
                                System.err.println("Conta principal não encontrada ou não é do tipo Conta Corrente Principal.");
                            }
                            break;
                    }
                    if (conta != null) {
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

    private Conta encontrarConta(List<Conta> contas, int numeroConta) {
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
                bw.write(tipo + "," + conta.getNumeroConta() + "," + conta.getSaldo() + ","); 

                if (conta instanceof ContaCorrenteAdicional) {
                    ContaCorrenteAdicional contaAdicional = (ContaCorrenteAdicional) conta;
                    bw.write(contaAdicional.getLimite() + "," + contaAdicional.getContaPrincipal().getNumeroConta());
                }
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao gravar o arquivo de contas: " + e.getMessage());
        }
    }
}