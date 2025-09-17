package co.edu.uniandes.dse.parcial1.services;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcial1.entities.MercanciaEntity;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(MercanciaService.class)
public class MercanciaServiceTest {
    
    @Autowired
    private MercanciaService mercanciaService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    @BeforeEach
    public void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
		entityManager.getEntityManager().createQuery("delete from MercanciaEntity").executeUpdate();
	}

    private void insertData() {
		MercanciaEntity entity = factory.manufacturePojo(MercanciaEntity.class);
		entityManager.persist(entity);
	}

    @Test
    public void testCreateMercancia() {
        MercanciaEntity newEntity = factory.manufacturePojo(MercanciaEntity.class);
        newEntity.setFechaRecepcion(LocalDate.now().minusDays(2));

        MercanciaEntity createdEntity = mercanciaService.createMercanciaEntity(newEntity);
        Assertions.assertNotNull(createdEntity);
        
        MercanciaEntity testedEntity = entityManager.find(MercanciaEntity.class, createdEntity.getId());
        Assertions.assertNotNull(testedEntity);
        Assertions.assertEquals(newEntity.getNombre(), testedEntity.getNombre());
        Assertions.assertEquals(newEntity.getCodigoBarras(), testedEntity.getCodigoBarras());
        Assertions.assertEquals(newEntity.getFechaRecepcion(), testedEntity.getFechaRecepcion());
        Assertions.assertEquals(newEntity.getCantidad(), testedEntity.getCantidad());
    }

    @Test
    public void testCreateMercanciaAfterActualDate() {
        MercanciaEntity newEntity = factory.manufacturePojo(MercanciaEntity.class);
        newEntity.setFechaRecepcion(LocalDate.now().plusDays(2));

        // test with null name
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            newEntity.setNombre(null);
            mercanciaService.createMercanciaEntity(newEntity);
        });
    }

    @Test
    public void testCreateMercanciaWithNotDate() {
        MercanciaEntity newEntity = factory.manufacturePojo(MercanciaEntity.class);
        newEntity.setFechaRecepcion(null);

        // test with null name
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            newEntity.setNombre(null);
            mercanciaService.createMercanciaEntity(newEntity);
        });
    }
    
}
