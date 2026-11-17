package br.com.delivery.tracking.controller;

import br.com.delivery.tracking.dto.LocalizacaoRequest;
import br.com.delivery.tracking.dto.LocalizacaoResponse;
import br.com.delivery.tracking.service.LocalizacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/entregas/{despachoId}")
public class LocalizacaoController {

    private final LocalizacaoService service;

    public LocalizacaoController(LocalizacaoService service) {
        this.service = service;
    }

    /** Registra a posição atual do entregador. */
    @PostMapping("/localizacoes")
    @ResponseStatus(HttpStatus.CREATED)
    public LocalizacaoResponse registrar(@PathVariable String despachoId,
                                         @Valid @RequestBody LocalizacaoRequest request) {
        return service.registrar(despachoId, request);
    }

    /** Última posição conhecida. */
    @GetMapping("/localizacao")
    public LocalizacaoResponse ultima(@PathVariable String despachoId) {
        return service.ultima(despachoId);
    }

    /** Histórico completo, da mais antiga para a mais recente. */
    @GetMapping("/rastreamento")
    public List<LocalizacaoResponse> historico(@PathVariable String despachoId) {
        return service.historico(despachoId);
    }
}
