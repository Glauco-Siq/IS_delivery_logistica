package br.com.delivery.frota.entities.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EntregadorRequestDto {
    @NotBlank(message = "O cpf do entregador é obrigatório")
    String cpf;
    @NotBlank(message = "O nome do entregador é obrigatório")
    String nome;
    @NotBlank(message = "O telefone do entregador é obrigatório")
    String telefone;
    @NotBlank(message = "O tipo de veiculo do entregador é obrigatório")
    String tipoVeiculo;
    @NotBlank(message = "A placa do entregador é obrigatória")
    String placa;
}
