package br.com.delivery.frota;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StatusEntregador {
    DISPONIVEL,
    OCUPADO,
    INATIVO;
    @JsonCreator
    public static StatusEntregador fromValue(String value){
        if (value == null){
            return null;
        }
        return StatusEntregador.valueOf(value.trim().toUpperCase());
    }
}
