package com.rogerioreis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ServiceStatus implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public ServiceStatus(String authorizing, String statusServico, LocalDateTime consultationDate,
                         LocalDateTime updateConsultationDate) {
        this.authorizing = authorizing;
        this.statusServico = statusServico;
        this.consultationDate = consultationDate;
        this.updateConsultationDate = updateConsultationDate;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "UF_AUTORIZADOR")
    private String authorizing;


    @Column(name = "STATUS_SERVICO")
    private String statusServico;

    @Column(name = "DATA_CONSULTA")
    private LocalDateTime consultationDate;

    @Column(name = "DATA_CONSULTA_ATUALIZADA")
    private LocalDateTime updateConsultationDate;

    @Column(name = "INDISPONIVEL")
    private Long unavailable;

    @PrePersist
    public void onSave() {
        this.consultationDate = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updateConsultationDate = LocalDateTime.now();
    }
}
