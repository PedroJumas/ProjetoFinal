package Usuarios;

import Contas.Conta;

public class Bancario extends Usuario {

    public Bancario(String nome, String cpf, String senha) {
        super(nome, cpf, senha);
    }

    public void sacar(Cliente cliente, Conta conta, double valor) {
        conta.sacar(valor);
    }

    public void depositar(Cliente cliente, Conta conta, double valor) {
        conta.depositar(valor);
    }

    public void transferir(Cliente cliente, Conta contaOrigem, Conta contaDestino, double valor) {
        contaOrigem.transferir(valor, contaDestino);
    }
}