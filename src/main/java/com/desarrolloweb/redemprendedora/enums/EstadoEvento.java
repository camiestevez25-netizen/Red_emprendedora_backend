package com.desarrolloweb.redemprendedora.enums;

public enum EstadoEvento {
    ACTIVO("Activo"),
    AGOTADO("Agotado"),
    CANCELADO("Cancelado");

    private final String valor;

    EstadoEvento(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
