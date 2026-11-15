package br.com.delivery.frota.services.interfaces;

import br.com.delivery.frota.StatusEntregador;
import br.com.delivery.frota.entities.DTOs.EntregadorAtualizacaoDto;
import br.com.delivery.frota.entities.DTOs.EntregadorRequestDto;
import br.com.delivery.frota.entities.DTOs.EntregadorResponse;
import br.com.delivery.frota.entities.Entregador;
import java.util.List;
import java.util.UUID;

public interface EntregadorService {
    EntregadorResponse cadastrarEntregador(EntregadorRequestDto entregadorRequestDto);
    List<EntregadorResponse> listarEntregadores();
    List<EntregadorResponse> listarEntregadoresPorStatus(StatusEntregador statusEntregador);
    EntregadorResponse consultarEntregador(UUID idEntregador);
    EntregadorResponse atualizarDadosEntregador(UUID idEntregador, EntregadorAtualizacaoDto entregadorAtualizacaoDto);
    EntregadorResponse alterarStatusEntregador(UUID idEntregador, StatusEntregador statusEntregador);
    void removerEntregador(UUID idEntregador);
}
