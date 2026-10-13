package com.desarrolloweb.redemprendedora.repository;

import com.desarrolloweb.redemprendedora.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
}