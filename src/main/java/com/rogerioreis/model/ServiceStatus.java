package com.rogerioreis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_SERVICE_STATUS")
public class ServiceStatus {

    public ServiceStatus(String authorizing, String statusServico) {
        this.authorizing = authorizing;
        this.statusServico = statusServico;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "UF_AUTORIZADOR")
    private String authorizing;

    @Column(name = "STATUS_SERVICO")
    private String statusServico;

    @Column(name = "DATA_CONSULTA")
    private LocalDateTime consultationDate;

    @Column(name = "INDISPONIVEL")
    private Long unavailable;

    @PrePersist
    public void onSave() {
        this.consultationDate = LocalDateTime.now();
    }
}
