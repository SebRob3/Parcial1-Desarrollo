package co.edu.uniandes.dse.parcial1.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class MercanciaEntity extends BaseEntity{
   private String nombre;
   private String codigoBarras;
   private LocalDate fechaRecepcion;
   private Integer cantidad;

    @PodamExclude
    @ManyToOne
    private UbicacionBodegaEntity ubicacionBodega;

}
