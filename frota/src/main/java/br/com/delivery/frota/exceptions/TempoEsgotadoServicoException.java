package br.com.delivery.frota.exceptions;

public class TempoEsgotadoServicoException extends RuntimeException {
    public TempoEsgotadoServicoException(String message) {
        super(message);
    }
}
