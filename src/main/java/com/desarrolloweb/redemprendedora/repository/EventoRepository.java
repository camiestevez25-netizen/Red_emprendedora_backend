package com.desarrolloweb.redemprendedora.repository;

import com.desarrolloweb.redemprendedora.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
    List<Evento> findByEstadoIgnoreCase(String estado);
}

