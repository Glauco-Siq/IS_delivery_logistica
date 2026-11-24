package br.com.delivery.frota.controllers;

import br.com.delivery.frota.StatusEntregador;
import br.com.delivery.frota.entities.DTOs.EntregadorAtualizacaoDto;
import br.com.delivery.frota.entities.DTOs.EntregadorRequestDto;
import br.com.delivery.frota.entities.DTOs.EntregadorResponse;
import br.com.delivery.frota.services.interfaces.EntregadorService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/entregadores")
public class EntregadorController {
    @Autowired
    EntregadorService entregadorService;
    @PostMapping
    public ResponseEntity<EntregadorResponse> cadastrarEntregador(@RequestBody EntregadorRequestDto entregadorRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(entregadorService.cadastrarEntregador(entregadorRequestDto));
    }
    @GetMapping
    public ResponseEntity<List<EntregadorResponse>> listaEntregadoresPorStatus(@RequestParam(required = false) StatusEntregador status){
        return ResponseEntity.ok(entregadorService.listarEntregadoresPorStatus(status));
    }
    @GetMapping
    public ResponseEntity<List<EntregadorResponse>> listaEntregadores(){
        List<EntregadorResponse> responses = entregadorService.listarEntregadores();
        return ResponseEntity.ok(responses);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EntregadorResponse> consultarEntregadorPorId(@PathVariable UUID id){
        EntregadorResponse response = entregadorService.consultarEntregador(id);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<EntregadorResponse> atualizarDados(@PathVariable UUID id, @RequestBody EntregadorAtualizacaoDto entregadorAtualizacaoDto){
        EntregadorResponse response = entregadorService.atualizarDadosEntregador(id, entregadorAtualizacaoDto);
        return ResponseEntity.ok(response);
    }
    @PatchMapping("/{id}/status")
    public ResponseEntity<EntregadorResponse> alteraStatus(@PathVariable UUID id, @RequestBody StatusEntregador status){
        EntregadorResponse response = entregadorService.alterarStatusEntregador(id, status);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/entregadores/{id}")
    public ResponseEntity<Void> removeEntregador(@PathVariable UUID id){
        entregadorService.removerEntregador(id);
        return ResponseEntity.noContent().build();
    }
}
