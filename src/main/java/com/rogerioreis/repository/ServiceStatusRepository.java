package com.rogerioreis.repository;

import com.rogerioreis.model.ServiceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceStatusRepository extends JpaRepository<ServiceStatus, Long> {

    Page<ServiceStatus> findByAuthorizingIgnoreCase(String string, Pageable pageable);
}
