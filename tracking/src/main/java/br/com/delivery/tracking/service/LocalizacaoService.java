package br.com.delivery.tracking.service;

import br.com.delivery.tracking.dto.LocalizacaoRequest;
import br.com.delivery.tracking.dto.LocalizacaoResponse;
import br.com.delivery.tracking.entity.Localizacao;
import br.com.delivery.tracking.repository.LocalizacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@Service
public class LocalizacaoService {

    private static final Logger log = LoggerFactory.getLogger(LocalizacaoService.class);

    private final LocalizacaoRepository repository;
    private final Clock clock;

    @Autowired
    public LocalizacaoService(LocalizacaoRepository repository) {
        this(repository, Clock.systemUTC());
    }

    /** Construtor com Clock: usado nos testes para controlar o "agora". */
    LocalizacaoService(LocalizacaoRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }

    @Transactional
    public LocalizacaoResponse registrar(String despachoId, LocalizacaoRequest request) {
        Instant registradoEm = request.registradoEm() != null ? request.registradoEm() : Instant.now(clock);

        Localizacao salva = repository.save(
                new Localizacao(despachoId, request.latitude(), request.longitude(), registradoEm));

        log.info("[tracking] despacho={} nova localizacao lat={} lng={}",
                despachoId, salva.getLatitude(), salva.getLongitude());
        return LocalizacaoResponse.de(salva);
    }

    @Transactional(readOnly = true)
    public LocalizacaoResponse ultima(String despachoId) {
        return repository.findFirstByDespachoIdOrderByRegistradoEmDesc(despachoId)
                .map(LocalizacaoResponse::de)
                .orElseThrow(() -> new RuntimeException());
    }

    @Transactional(readOnly = true)
    public List<LocalizacaoResponse> historico(String despachoId) {
        return repository.findByDespachoIdOrderByRegistradoEmAsc(despachoId).stream()
                .map(LocalizacaoResponse::de)
                .toList();
    }
}
