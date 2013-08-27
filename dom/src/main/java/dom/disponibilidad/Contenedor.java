package dom.disponibilidad;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Audited;
import org.apache.isis.applib.annotation.ObjectType;
import org.apache.isis.applib.annotation.Title;
import org.joda.time.LocalDate;

@ObjectType("CONTENEDOR")
@Audited
public class Contenedor {
	
	/*private LocalDate fecha;

	@Title
	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}*/

	/*private List<Disponibilidad> listaDisponibilidad;

	public List<Disponibilidad> getListaDisponibilidad() {
		return listaDisponibilidad;
	}

	public void setListaDisponibilidad(List<Disponibilidad> listaDisponibilidad) {
		this.listaDisponibilidad = listaDisponibilidad;
	}	*/

	// {{ injected: DomainObjectContainer
    private DomainObjectContainer container;

    public void injectDomainObjectContainer(final DomainObjectContainer container) {
        this.container = container;
    }
}
