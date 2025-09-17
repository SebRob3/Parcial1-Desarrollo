package co.edu.uniandes.dse.parcial1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.UbicacionBodegaEntity;
import co.edu.uniandes.dse.parcial1.repositories.UbicacionBodegaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UbicacionBodegaService {

    @Autowired
    private UbicacionBodegaRepository ubicacionBodegaRepository;

    public UbicacionBodegaEntity createUbicacionBodegaEntity(UbicacionBodegaEntity ubicacionBodegaEntity) {
        log.info("Creating ubicacionBodega with name: {}", ubicacionBodegaEntity.getId());
        
        if (ubicacionBodegaEntity.getNumeroEstante() == null || ubicacionBodegaEntity.getNumeroEstante() < 0) {
                throw new IllegalArgumentException("La entidad no cumple con las reglas de negocio");
            }

        log.info("UbicacionBodega created successfully: id: {}", ubicacionBodegaEntity.getId());

        return ubicacionBodegaRepository.save(ubicacionBodegaEntity);
    }

}
