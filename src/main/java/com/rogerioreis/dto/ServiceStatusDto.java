package com.rogerioreis.dto;

import com.rogerioreis.model.ServiceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceStatusDto {

    public ServiceStatusDto(String authorizing, String statusServico) {
        this.authorizing = authorizing;
        this.statusServico = statusServico;
    }

    public ServiceStatusDto(ServiceStatus entity) {
        this.authorizing = entity.getAuthorizing();
        this.statusServico = entity.getStatusServico();
        this.consultationDate = entity.getConsultationDate();
        this.unavailable = entity.getUnavailable();
    }

    private String authorizing;
    private String statusServico;
    private LocalDateTime consultationDate;
    private String unavailable;

}
