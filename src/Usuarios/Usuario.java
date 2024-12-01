package Usuarios;

import Utilitarios.GerenciadorArquivos;
import java.util.List;

public abstract class Usuario {

    protected String nome;
    protected String cpf;
    protected String senha;

    public Usuario(String nome, String cpf, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
    }

    public boolean login(String cpf, String senha) {
        boolean logado, cpfIgual,senhaIgual;
        
        logado = false;
        cpfIgual = this.cpf.equals(cpf);
        senhaIgual = this.senha.equals(senha);
        
        logado = cpfIgual && senhaIgual;
        return logado;
    }
        

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}