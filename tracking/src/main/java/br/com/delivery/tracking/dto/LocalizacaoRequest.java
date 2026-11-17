package br.com.delivery.tracking.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

/**
 * Corpo do POST /api/v1/entregas/{despachoId}/localizacoes.
 * registradoEm é opcional: se não vier, o serviço usa o horário atual.
 */
public record LocalizacaoRequest(

        @NotNull(message = "latitude é obrigatória")
        @DecimalMin(value = "-90.0", message = "latitude deve estar entre -90 e 90")
        @DecimalMax(value = "90.0", message = "latitude deve estar entre -90 e 90")
        Double latitude,

        @NotNull(message = "longitude é obrigatória")
        @DecimalMin(value = "-180.0", message = "longitude deve estar entre -180 e 180")
        @DecimalMax(value = "180.0", message = "longitude deve estar entre -180 e 180")
        Double longitude,

        Instant registradoEm
) {
}
