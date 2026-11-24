package br.com.delivery.frota.exceptions;

public record ApiErrorResponse(
    boolean erro,
    String mensagem
) {
    public static ApiErrorResponse of(String mensagem){
        return new ApiErrorResponse(true, mensagem);
    }
}
