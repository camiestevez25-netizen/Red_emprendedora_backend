package com.desarrolloweb.redemprendedora.security;

import com.desarrolloweb.redemprendedora.entity.Usuario;
import com.desarrolloweb.redemprendedora.repository.EventoRepository;
import com.desarrolloweb.redemprendedora.repository.StandRepository;
import com.desarrolloweb.redemprendedora.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

// Métodos de pertenencia que se usan desde @PreAuthorize, por ejemplo:
//   @PreAuthorize("@seguridad.esDuenioEvento(#id, authentication)")
// authentication.getName() es el email del usuario logueado (sale del token).
//
// Si el registro no existe se devuelve true a propósito: así la petición llega al service
// y responde 404 "no encontrado" en vez de un 403 confuso.
@Component("seguridad")
public class Seguridad {

    private final EventoRepository eventoRepository;
    private final StandRepository standRepository;
    private final UsuarioRepository usuarioRepository;

    public Seguridad(EventoRepository eventoRepository,
                     StandRepository standRepository,
                     UsuarioRepository usuarioRepository) {
        this.eventoRepository = eventoRepository;
        this.standRepository = standRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // El usuario logueado es el organizador del evento
    @Transactional(readOnly = true)
    public boolean esDuenioEvento(Integer idEvento, Authentication authentication) {
        if (idEvento == null) {
            return true;
        }
        return eventoRepository.findById(idEvento)
                .map(evento -> esElUsuarioLogueado(evento.getUsuario(), authentication))
                .orElse(true);
    }

    // El usuario logueado es el organizador del evento al que pertenece el stand
    @Transactional(readOnly = true)
    public boolean esDuenioStand(Integer idStand, Authentication authentication) {
        return standRepository.findById(idStand)
                .map(stand -> stand.getEvento() != null
                        && esElUsuarioLogueado(stand.getEvento().getUsuario(), authentication))
                .orElse(true);
    }

    // Puede cancelar la reserva quien reservó el stand o el organizador del evento
    @Transactional(readOnly = true)
    public boolean puedeCancelarReserva(Integer idStand, Authentication authentication) {
        return standRepository.findById(idStand)
                .map(stand -> esElUsuarioLogueado(stand.getUsuario(), authentication)
                        || (stand.getEvento() != null
                        && esElUsuarioLogueado(stand.getEvento().getUsuario(), authentication)))
                .orElse(true);
    }

    // El usuario logueado solo puede editar o borrar su propia cuenta
    @Transactional(readOnly = true)
    public boolean esMismoUsuario(Long idUsuario, Authentication authentication) {
        return usuarioRepository.findById(idUsuario)
                .map(usuario -> esElUsuarioLogueado(usuario, authentication))
                .orElse(true);
    }

    private boolean esElUsuarioLogueado(Usuario usuario, Authentication authentication) {
        return usuario != null
                && authentication != null
                && usuario.getEmail() != null
                && usuario.getEmail().equalsIgnoreCase(authentication.getName());
    }
}