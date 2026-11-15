package br.com.delivery.frota.entities.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntregadorAtualizacaoDto {
    String nome;
    String telefone;
    String tipoVeiculo;
    String placa;
}
