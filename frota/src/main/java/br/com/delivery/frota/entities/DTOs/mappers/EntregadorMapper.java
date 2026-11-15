package br.com.delivery.frota.entities.DTOs.mappers;

import br.com.delivery.frota.entities.DTOs.EntregadorResponse;
import br.com.delivery.frota.entities.Entregador;
import java.util.Locale;
import org.springframework.stereotype.Component;

@Component

public class EntregadorMapper {
    public EntregadorResponse entityToResponseDto(Entregador entregador){
        if (entregador == null){
            return null;
        }
        return new EntregadorResponse(
            entregador.getId(),
            entregador.getNome(),
            entregador.getTelefone(),
            entregador.getTipoVeiculo(),
            entregador.getPlaca(),
            entregador.getStatusEntregador()
        );
    }


}
