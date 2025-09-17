package co.edu.uniandes.dse.parcial1.services;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcial1.entities.UbicacionBodegaEntity;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(UbicacionBodegaService.class)
public class UbicacionBodegaServiceTest {

    @Autowired
    private UbicacionBodegaService ubicacionBodegaService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    @BeforeEach
    public void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
		entityManager.getEntityManager().createQuery("delete from UbicacionBodegaEntity").executeUpdate();
	}

    private void insertData() {
		UbicacionBodegaEntity entity = factory.manufacturePojo(UbicacionBodegaEntity.class);
		entityManager.persist(entity);
	}

    @Test
    public void testCreateUbicacionBodega() {
        UbicacionBodegaEntity newEntity = factory.manufacturePojo(UbicacionBodegaEntity.class);
        newEntity.setNumeroEstante(1);

        UbicacionBodegaEntity createdEntity = ubicacionBodegaService.createUbicacionBodegaEntity(newEntity);
        Assertions.assertNotNull(createdEntity);
        
        UbicacionBodegaEntity testedEntity = entityManager.find(UbicacionBodegaEntity.class, createdEntity.getId());
        Assertions.assertNotNull(testedEntity);
        Assertions.assertEquals(newEntity.getNumeroEstante(), testedEntity.getNumeroEstante());
        Assertions.assertEquals(newEntity.getCanastaAsignada(), testedEntity.getCanastaAsignada());
        Assertions.assertEquals(newEntity.getPesoMaximoGramos(), testedEntity.getPesoMaximoGramos());
    }

    @Test
    public void testCreateUbicacionBodegaAfterActualDate() {
        UbicacionBodegaEntity newEntity = factory.manufacturePojo(UbicacionBodegaEntity.class);
        newEntity.setNumeroEstante(-1);

        // test with null name
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            newEntity.setId(null);
            ubicacionBodegaService.createUbicacionBodegaEntity(newEntity);
        });
    }
    
}
