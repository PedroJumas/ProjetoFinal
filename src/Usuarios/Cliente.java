package Usuarios;

import Contas.Conta;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {

    private List<Conta> contas;

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

    public void sacar(Conta conta, double valor) {
        conta.sacar(valor);
    }

    public void depositar(Conta conta, double valor) {
        conta.depositar(valor);
    }

    public void transferir(Conta contaOrigem, Conta contaDestino, double valor) {
        contaOrigem.transferir(valor, contaDestino);
    }
}