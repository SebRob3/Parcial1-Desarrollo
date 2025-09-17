package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcial1.entities.MercanciaEntity;
import co.edu.uniandes.dse.parcial1.entities.UbicacionBodegaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(MercanciaUbicacionBodegaService.class)
public class MercanciaUbicacionBodegaServiceTest {
    @Autowired
    private MercanciaUbicacionBodegaService mercanciaUbicacionBodegaService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<MercanciaEntity> mercanciaList = new ArrayList<>();
    private List<UbicacionBodegaEntity> ubicacionBodegaList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from MercanciaEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from UbicacionBodegaEntity").executeUpdate();
        }

    private void insertData() {
        mercanciaList.clear();
        ubicacionBodegaList.clear();

        for (int i = 0; i < 3; i++) {
            UbicacionBodegaEntity ubicacionBodega = factory.manufacturePojo(UbicacionBodegaEntity.class);
            entityManager.persist(ubicacionBodega);
            ubicacionBodegaList.add(ubicacionBodega);

            MercanciaEntity mercancia = factory.manufacturePojo(MercanciaEntity.class);
            entityManager.persist(mercancia);
            mercanciaList.add(mercancia);
        }
    }

    @Test
    void testAddUbicacionBodega() throws EntityNotFoundException {
        MercanciaEntity mercancia = mercanciaList.get(0);
        UbicacionBodegaEntity ubicacionBodega = ubicacionBodegaList.get(0);

        MercanciaEntity asociado = mercanciaUbicacionBodegaService.addUbicacionBodega(mercancia.getId(), ubicacionBodega.getId());

        assertNotNull(asociado.getUbicacionBodega());
        assertEquals(ubicacionBodega.getId(), asociado.getUbicacionBodega().getId());
    }
    
    @Test
    void testAddUbicacionBodegaMercanciaNotFound() {
        assertThrows(EntityNotFoundException.class, () -> {
            mercanciaUbicacionBodegaService.addUbicacionBodega(0L, ubicacionBodegaList.get(0).getId());
        });
    }

    @Test
    void testAddUbicacionBodegaUbicacionBodegaNotFound() {
        assertThrows(EntityNotFoundException.class, () -> {
            mercanciaUbicacionBodegaService.addUbicacionBodega(mercanciaList.get(0).getId(), 0L);
        });
    }
}
