package com.desarrolloweb.redemprendedora.repository;
import com.desarrolloweb.redemprendedora.entity.Localidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalidadRepository extends JpaRepository<Localidad, Long> {
    boolean existsByNombreLocalidadAndProvinciaLocalidad(
            String nombreLocalidad, String provinciaLocalidad);

}
