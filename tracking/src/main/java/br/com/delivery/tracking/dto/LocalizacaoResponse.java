package br.com.delivery.tracking.dto;

import br.com.delivery.tracking.entity.Localizacao;

import java.time.Instant;
import java.util.UUID;

/** O que a API devolve: nunca a entidade JPA diretamente. */
public record LocalizacaoResponse(
        UUID id,
        String despachoId,
        Double latitude,
        Double longitude,
        Instant registradoEm
) {

    public static LocalizacaoResponse de(Localizacao localizacao) {
        return new LocalizacaoResponse(
                localizacao.getId(),
                localizacao.getDespachoId(),
                localizacao.getLatitude(),
                localizacao.getLongitude(),
                localizacao.getRegistradoEm()
        );
    }
}
