package br.com.delivery.frota.entities.DTOs;

import br.com.delivery.frota.StatusEntregador;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntregadorResponse {
    private UUID id;
    private String nome;
    private String telefone;
    private String tipoVeiculo;
    private String placa;
    private StatusEntregador statusEntregador;
}
