package co.edu.uniandes.dse.parcial1.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.MercanciaEntity;
import co.edu.uniandes.dse.parcial1.repositories.MercanciaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MercanciaService {
    @Autowired
    private MercanciaRepository mercanciaRepository;

    @Transactional
    public List<MercanciaEntity> getAllMercanciaEntities() {
        log.info("Fetching all competencias");
        return mercanciaRepository.findAll();
    }
    
    @Transactional
    public MercanciaEntity createMercanciaEntity(MercanciaEntity mercanciaEntity) {
        log.info("Creating mercancia with name: {}", mercanciaEntity.getNombre());
        
        if (mercanciaEntity.getFechaRecepcion().isAfter(LocalDate.now())) { //Verificar fecha
                throw new IllegalArgumentException("Fecha invalida");
            }
        if (mercanciaEntity.getCodigoBarras() == null || mercanciaEntity.getCodigoBarras().isEmpty() || 
            getAllMercanciaEntities().stream().anyMatch(r -> r.getCodigoBarras().equals(mercanciaEntity.getCodigoBarras()))) { // verificar Codigo de Barras
                throw new IllegalArgumentException("Codigo de Barras Invalido");
            }
        if (mercanciaEntity.getNombre() == null || mercanciaEntity.getNombre().isEmpty() ||
            getAllMercanciaEntities().stream().anyMatch(r -> r.getNombre().equals(mercanciaEntity.getNombre()))) { // verificar Nombre
                throw new IllegalArgumentException("Nombre Invalido");
            }

        log.info("Mercancia created successfully: {}, id: {}", mercanciaEntity.getNombre(), mercanciaEntity.getId());

        return mercanciaRepository.save(mercanciaEntity);
    }
}
