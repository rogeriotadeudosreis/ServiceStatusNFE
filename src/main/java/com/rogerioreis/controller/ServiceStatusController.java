package com.rogerioreis.controller;

import com.rogerioreis.dto.ServiceStatusDto;
import com.rogerioreis.model.ServiceStatus;
import com.rogerioreis.service.ServiceStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/service-status")
public class ServiceStatusController {

    @Autowired
    private ServiceStatusService service;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ServiceStatusDto> salvar(@RequestBody @Valid ServiceStatusDto dto, UriComponentsBuilder uriBuilder) {

        ServiceStatus entity = new ServiceStatus();
        entity.setAuthorizing(dto.getAuthorizing());
        entity.setStatusServico(dto.getStatusServico());

        service.salvar(entity);
        URI uri = uriBuilder.path("/api/service-status/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(new ServiceStatusDto(entity));
    }

    @GetMapping(path = "/consultar", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<ServiceStatusDto>> consultar(
            @RequestParam(required = false) String authorizing,
            @PageableDefault(sort = "authorizing",  direction = Sort.Direction.ASC, page = 0, size = 1000) Pageable paginacao) {
        if (authorizing == null) {
            Page<ServiceStatus> lista = service.consultar(paginacao);
            return ResponseEntity.ok(lista.map(ServiceStatusDto::new));
        } else {
            Page<ServiceStatus> lista = service.consultarPorUf(authorizing, paginacao);
            return ResponseEntity.ok(lista.map(ServiceStatusDto::new));
        }
    }
}
