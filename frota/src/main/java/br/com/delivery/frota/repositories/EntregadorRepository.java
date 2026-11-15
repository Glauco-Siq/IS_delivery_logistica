package br.com.delivery.frota.repositories;

import br.com.delivery.frota.StatusEntregador;
import br.com.delivery.frota.entities.Entregador;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntregadorRepository extends JpaRepository<Entregador, UUID> {
    List<Entregador> findByStatusEntregador(StatusEntregador statusEntregador);
    boolean existsByCpf(String cpf);
    boolean existsByTelefone(String telefone);
}
