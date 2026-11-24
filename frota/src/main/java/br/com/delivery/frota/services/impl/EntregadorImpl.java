package br.com.delivery.frota.services.impl;

import br.com.delivery.frota.StatusEntregador;
import br.com.delivery.frota.entities.DTOs.EntregadorAtualizacaoDto;
import br.com.delivery.frota.entities.DTOs.EntregadorRequestDto;
import br.com.delivery.frota.entities.DTOs.EntregadorResponse;
import br.com.delivery.frota.entities.DTOs.mappers.EntregadorMapper;
import br.com.delivery.frota.entities.Entregador;
import br.com.delivery.frota.exceptions.CampoNaoInformadoException;
import br.com.delivery.frota.exceptions.RecursoNaoEncontradoException;
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
    private final EntregadorRepository entregadorRepository;
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
        Entregador entregador = entregadorRepository.findById(idEntregador).orElseThrow(() ->new RecursoNaoEncontradoException("Entregador não encontrado."));
        return entregadorMapper.entityToResponseDto(entregador);
    }

    @Override
    public EntregadorResponse atualizarDadosEntregador(UUID idEntregador, EntregadorAtualizacaoDto entregadorAtualizacaoDto) {
        Entregador entregador = entregadorRepository.findById(idEntregador).orElseThrow(() -> new RecursoNaoEncontradoException("Entregador não encontrado"));
        if (entregadorAtualizacaoDto.getNome()  == null || entregadorAtualizacaoDto.getNome().isBlank()){
            throw new CampoNaoInformadoException("O campo de nome do entregador não foi preenchido.");
        }
        entregador.setNome(entregadorAtualizacaoDto.getNome());
        if (entregadorAtualizacaoDto.getTelefone()  == null ||entregadorAtualizacaoDto.getTelefone().isBlank()){
            throw new CampoNaoInformadoException("O campo de telefone do entregador não foi preenchido.");
        }
        entregador.setTelefone(entregadorAtualizacaoDto.getTelefone());
        if (entregadorAtualizacaoDto.getTipoVeiculo()  == null || entregadorAtualizacaoDto.getTipoVeiculo().isBlank()){
            throw new CampoNaoInformadoException("O campo de tipo de veículo do entregador não foi preenchido.");
        }
        entregador.setTipoVeiculo(entregadorAtualizacaoDto.getTipoVeiculo());
        if (entregadorAtualizacaoDto.getPlaca()  == null || entregadorAtualizacaoDto.getPlaca().isBlank()){
            throw new CampoNaoInformadoException("O campo de placa do veículo do entregador não foi preenchido");
        }
        entregador.setPlaca(entregadorAtualizacaoDto.getPlaca());
        Entregador entregadorAtualizado = entregadorRepository.save(entregador);
        return entregadorMapper.entityToResponseDto(entregadorAtualizado);
    }

    @Override
    public EntregadorResponse alterarStatusEntregador(UUID idEntregador, StatusEntregador statusEntregador) {
        Entregador entregador = entregadorRepository.findById(idEntregador).orElseThrow(() -> new RecursoNaoEncontradoException("Entregador não encontrado"));
        entregador.setStatusEntregador(statusEntregador);
        Entregador entregadorAtualizado = entregadorRepository.save(entregador);
        return entregadorMapper.entityToResponseDto(entregadorAtualizado);
    }

    @Override
    public void removerEntregador(UUID idEntregador) {
        Entregador entregador = entregadorRepository.findById(idEntregador).orElseThrow(() -> new RecursoNaoEncontradoException("Entregador não encontrado"));
        entregadorRepository.delete(entregador);
    }
}
