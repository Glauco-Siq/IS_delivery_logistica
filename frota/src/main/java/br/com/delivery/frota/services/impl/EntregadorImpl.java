package br.com.delivery.frota.services.impl;

import br.com.delivery.frota.StatusEntregador;
import br.com.delivery.frota.entities.DTOs.EntregadorAtualizacaoDto;
import br.com.delivery.frota.entities.DTOs.EntregadorRequestDto;
import br.com.delivery.frota.entities.DTOs.EntregadorResponse;
import br.com.delivery.frota.entities.DTOs.mappers.EntregadorMapper;
import br.com.delivery.frota.entities.Entregador;
import br.com.delivery.frota.repositories.EntregadorRepository;
import br.com.delivery.frota.services.interfaces.EntregadorService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntregadorImpl implements EntregadorService {
    @Autowired
    private final EntregadorRepository entregadorRepository;
    @Autowired
    private final EntregadorMapper entregadorMapper;

    @Override
    public EntregadorResponse cadastrarEntregador(EntregadorRequestDto entregadorRequestDto) {
        if (entregadorRepository.existsByCpf(entregadorRequestDto.getCpf())){
            throw new IllegalArgumentException("Não é permitida a criação de outro entregador com o mesmo cpf.");
        }
        if (entregadorRepository.existsByTelefone(entregadorRequestDto.getTelefone())){
            throw new IllegalArgumentException("Não é permitida a criação de outro entregador com o mesmo telefone.");
        }

        Entregador entregador = new Entregador();
        entregador.setCpf(entregadorRequestDto.getCpf());
        entregador.setNome(entregadorRequestDto.getNome());
        entregador.setTelefone(entregadorRequestDto.getTelefone());
        entregador.setTipoVeiculo(entregadorRequestDto.getTipoVeiculo());
        entregador.setPlaca(entregadorRequestDto.getPlaca());
        entregador.setStatusEntregador(StatusEntregador.DISPONIVEL);
        entregadorRepository.save(entregador);
        return entregadorMapper.entityToResponseDto(entregador);
    }

    @Override
    public List<EntregadorResponse> listarEntregadores() {
        List<Entregador> entregadoresList = entregadorRepository.findAll();
        return entregadoresList.stream().map(entregadorMapper::entityToResponseDto).collect(Collectors.toList());
    }

    @Override
    public List<EntregadorResponse> listarEntregadoresPorStatus(StatusEntregador statusEntregador) {
        List<Entregador> entregadoresList = entregadorRepository.findByStatusEntregador(statusEntregador);
        return entregadoresList.stream().map(entregadorMapper::entityToResponseDto).collect(Collectors.toList());
    }

    @Override
    public EntregadorResponse consultarEntregador(UUID idEntregador) {
        Entregador entregador = entregadorRepository.findById(idEntregador).orElseThrow(() ->new RuntimeException("Entregador não encontrado"));
        return entregadorMapper.entityToResponseDto(entregador);
    }

    @Override
    public EntregadorResponse atualizarDadosEntregador(UUID idEntregador, EntregadorAtualizacaoDto entregadorAtualizacaoDto) {
        Entregador entregador = entregadorRepository.findById(idEntregador).orElseThrow(() -> new RuntimeException("Entregador não encontrado"));
        entregador.setNome(entregadorAtualizacaoDto.getNome());
        entregador.setTelefone(entregadorAtualizacaoDto.getTelefone());
        entregador.setTipoVeiculo(entregadorAtualizacaoDto.getTipoVeiculo());
        entregador.setPlaca(entregadorAtualizacaoDto.getPlaca());
        Entregador entregadorAtualizado = entregadorRepository.save(entregador);
        return entregadorMapper.entityToResponseDto(entregadorAtualizado);
    }

    @Override
    public EntregadorResponse alterarStatusEntregador(UUID idEntregador, StatusEntregador statusEntregador) {
        Entregador entregador = entregadorRepository.findById(idEntregador).orElseThrow(() -> new RuntimeException("Entregador não encontrado"));
        entregador.setStatusEntregador(statusEntregador);
        Entregador entregadorAtualizado = entregadorRepository.save(entregador);
        return entregadorMapper.entityToResponseDto(entregadorAtualizado);
    }

    @Override
    public void removerEntregador(UUID idEntregador) {
        Entregador entregador = entregadorRepository.findById(idEntregador).orElseThrow(() -> new RuntimeException("Entregador não encontrado"));
        entregadorRepository.delete(entregador);
    }
}
