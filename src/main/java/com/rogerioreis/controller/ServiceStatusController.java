package com.rogerioreis.controller;

import com.rogerioreis.model.ServiceStatus;
import com.rogerioreis.service.ServiceStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/service-status")
public class ServiceStatusController {

    @Autowired
    private ServiceStatusService service;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ServiceStatus> salvar(@RequestBody @Valid ServiceStatus serviceStatus, UriComponentsBuilder uriBuilder) {
        ServiceStatus serviceStatusSalvo = service.salvar(serviceStatus);
//        UserDto dto = mapper.map(serviceStatusSalvo, serviceStatusDto.class);
        URI uri = uriBuilder.path("/api/service-status/{id}").buildAndExpand(serviceStatusSalvo.getId()).toUri();
        return ResponseEntity.created(uri).body(serviceStatusSalvo);
    }
}
