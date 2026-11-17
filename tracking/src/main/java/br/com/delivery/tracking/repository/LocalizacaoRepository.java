package br.com.delivery.tracking.repository;

import br.com.delivery.tracking.entity.Localizacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocalizacaoRepository extends JpaRepository<Localizacao, UUID> {

    /** Última posição registrada de um despacho. */
    Optional<Localizacao> findFirstByDespachoIdOrderByRegistradoEmDesc(String despachoId);

    /** Todas as posições de um despacho, da mais antiga para a mais recente. */
    List<Localizacao> findByDespachoIdOrderByRegistradoEmAsc(String despachoId);
}
