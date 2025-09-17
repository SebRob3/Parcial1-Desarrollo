package co.edu.uniandes.dse.parcial1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.MercanciaEntity;
import co.edu.uniandes.dse.parcial1.entities.UbicacionBodegaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.repositories.MercanciaRepository;
import co.edu.uniandes.dse.parcial1.repositories.UbicacionBodegaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MercanciaUbicacionBodegaService {

    @Autowired
    private MercanciaRepository mercanciaRepository;

    @Autowired
    private UbicacionBodegaRepository ubicacionBodegaRepository;

    @Transactional
    public MercanciaEntity addUbicacionBodega(Long mercanciaId, Long ubicacionBodegaId)
            throws EntityNotFoundException{
        log.info("Inicia proceso de asociar ubicacionBodega {} al mercancia {}", ubicacionBodegaId, mercanciaId);

        MercanciaEntity mercancia = mercanciaRepository.findById(mercanciaId)
            .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado la mercancia con id: " +  mercanciaId));

        UbicacionBodegaEntity ubicacionBodega = ubicacionBodegaRepository.findById(ubicacionBodegaId)
            .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado la ubicacion de la bodega con id: " + ubicacionBodegaId));

        mercancia.setUbicacionBodega(ubicacionBodega);
        ubicacionBodega.getMercacias().add(mercancia);

        return mercanciaRepository.save(mercancia);
    }

}
