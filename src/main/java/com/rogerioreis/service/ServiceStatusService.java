package com.rogerioreis.service;

import com.rogerioreis.model.ServiceStatus;
import com.rogerioreis.repository.ServiceStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceStatusService {

    @Autowired
    private ServiceStatusRepository repository;

    public ServiceStatus salvar(ServiceStatus serviceStatus) {
        return repository.save(serviceStatus);
    }

}
