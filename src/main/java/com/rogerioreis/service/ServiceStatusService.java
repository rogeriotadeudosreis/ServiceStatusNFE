package com.rogerioreis.service;

import com.rogerioreis.model.ServiceStatus;
import com.rogerioreis.repository.ServiceStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceStatusService {

    @Autowired
    private ServiceStatusRepository repository;

    public ServiceStatus salvar(ServiceStatus status) {
        if (!status.getStatusServico().contains("verde"))
            status.setUnavailable("Indisponivel");
        return repository.save(status);
    }

    public Page<ServiceStatus> consultar(Pageable pageable) {
        Page<ServiceStatus> lista = repository.findAll(pageable);
        if (lista.isEmpty()){
            return null;
        }
        return lista;
    }

    public Page<ServiceStatus> consultarPorUf(String uf, Pageable pageable) {
        Page<ServiceStatus> lista = repository.findByAuthorizingIgnoreCase(uf, pageable);
        if (lista.isEmpty()){
            return null;
        }
        return lista;
    }


}
