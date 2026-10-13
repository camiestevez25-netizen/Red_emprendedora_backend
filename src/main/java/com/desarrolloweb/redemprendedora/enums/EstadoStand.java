package com.desarrolloweb.redemprendedora.enums;

public enum EstadoStand { DISPONIBLE(true),
    OCUPADO(false);

    private final boolean valor;

    EstadoStand(boolean valor) {
        this.valor = valor;
    }

    public boolean getValor() {
        return valor;
    }

    public static EstadoStand fromValor(boolean valor) {
        return valor ? DISPONIBLE : OCUPADO;
    }

    public static String getEstadoTexto(boolean valor) {
        return valor ? "Disponible" : "Ocupado";
    }
}
