package br.edu.ifpr.foz.ifprstore.models;

public enum BookStatus {
    disponivel(1), emprestado(2), indisponivel(3);

    private final int code; // Campo para armazenar o valor associado

    BookStatus(int code) {
        this.code = code; // Atribui o valor ao campo
    }

    public int getCode() {
        return code; // Retorna o valor associado ao status
    }
}
