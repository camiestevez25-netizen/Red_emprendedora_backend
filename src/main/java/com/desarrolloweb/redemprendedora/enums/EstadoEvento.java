package com.desarrolloweb.redemprendedora.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

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

    // Busca el estado por su texto, sin importar mayusculas: "activo", "ACTIVO" y "Activo" devuelven ACTIVO.
    // Si el texto no corresponde a ningún estado, devuelve null.
    public static EstadoEvento buscarPorValor(String texto) {
        for (EstadoEvento estado : EstadoEvento.values()) {
            if (estado.valor.equalsIgnoreCase(texto.trim())) {
                return estado;
            }
        }
        return null;
    }

    // Devuelve "Activo, Agotado, Cancelado", para usar en los mensajes de error
    public static String valoresValidos() {
        return Arrays.stream(values())
                .map(EstadoEvento::getValor)
                .collect(Collectors.joining(", "));
    }
}
