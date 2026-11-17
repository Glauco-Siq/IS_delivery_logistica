package br.com.delivery.tracking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "localizacoes", indexes = {
        @Index(name = "idx_localizacoes_despacho_data", columnList = "despacho_id, registrado_em")
})
public class Localizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "despacho_id", nullable = false, length = 64)
    private String despachoId;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(name = "registrado_em", nullable = false)
    private Instant registradoEm;

    protected Localizacao() {
    }

    public Localizacao(String despachoId, Double latitude, Double longitude, Instant registradoEm) {
        this.despachoId = despachoId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.registradoEm = registradoEm;
    }

    public UUID getId() {
        return id;
    }

    public String getDespachoId() {
        return despachoId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Instant getRegistradoEm() {
        return registradoEm;
    }
}
