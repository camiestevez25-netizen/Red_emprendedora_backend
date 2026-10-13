package com.desarrolloweb.redemprendedora.repository;

import com.desarrolloweb.redemprendedora.entity.Stand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StandRepository extends JpaRepository<Stand, Integer>, JpaSpecificationExecutor<Stand> {

    @Query("SELECT s FROM Stand s WHERE s.evento.idEvento = :idEvento")
    List<Stand> findByEventoId(@Param("idEvento") Integer idEvento);

    @Query("SELECT s FROM Stand s WHERE s.evento.idEvento = :idEvento AND s.estadoStand = true")
    List<Stand> findDisponiblesByEventoId(@Param("idEvento") Integer idEvento);

    @Query("SELECT s FROM Stand s WHERE s.evento.idEvento = :idEvento AND s.estadoStand = false")
    List<Stand> findOcupadosByEventoId(@Param("idEvento") Integer idEvento);

    @Query("SELECT s FROM Stand s WHERE s.evento.idEvento = :idEvento AND s.numeroStand = :numeroStand")
    Optional<Stand> findByEventoIdEventoAndNumeroStand(Integer idEvento, Integer numeroStand);
    @Query("SELECT COUNT(s) FROM Stand s WHERE s.evento.idEvento = :idEvento AND s.estadoStand = false")
    Long countOcupadosByEventoId(@Param("idEvento") Integer idEvento);

    @Query("SELECT s FROM Stand s WHERE s.usuario.idUsuario = :idUsuario")
    List<Stand> findByUsuarioId(@Param("idUsuario") Integer idUsuario);
}