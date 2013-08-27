package dom.reserva;

import java.text.SimpleDateFormat;
import java.util.logging.SimpleFormatter;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Audited;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Bookmarkable;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberGroups;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NotPersisted;
import org.apache.isis.applib.annotation.ObjectType;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.PublishedObject;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.filter.Filter;
import org.joda.time.LocalDate;

import com.google.common.base.Objects;

import dom.estados.Estados;
import dom.habitacion.Habitacion;
@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY)
@javax.jdo.annotations.Queries( {
    @javax.jdo.annotations.Query(
            name="reservas_estado", language="JDOQL",  
            value="SELECT FROM dom.reserva.Reserva WHERE estado == :estado and fecha == :fecha orderBy fecha")
    })
@javax.jdo.annotations.Version(strategy=VersionStrategy.VERSION_NUMBER, column="VERSION")
@ObjectType("RESERVA")
@Audited
@AutoComplete(repository=dom.habitacion.HabitacionServicio.class, action="completarHabitaciones")
@Bookmarkable
public class Reserva {
	
	public String title() {
		return "reserva";
	}
	

    public String iconName() {
        return "Reserva";
    }

	
	private long numero;
	
	public long getNumero() {
		return numero;
	}
	
	public void setNumero(long numero) {
		this.numero = numero;
	}
	
	private Estados estado;
    	
	public Estados getEstado() {
		return estado;
	}
	
	public void setEstado(Estados estado) {
		this.estado = estado;
	}
	
	//Es necesario verlo en el formato dd/MM/yyyy
	private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	
	public String getFechaFormateada() {
		return formato.format(getFecha().toDate());
	}
	
	private LocalDate fecha;
	
	@Hidden
	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(final LocalDate fecha) {
	    this.fecha = fecha;
	}		
	
	// {{ injected: DomainObjectContainer
	private DomainObjectContainer container;

	public void injectDomainObjectContainer(final DomainObjectContainer container) {
	    this.container = container;
	}

	private Habitacion habitacion;
	
	public Habitacion getHabitacion() {
		return habitacion;
	}
	
	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}
	 // {{ OwnedBy (property)
    private String usuario;

    @Hidden
    // not shown in the UI
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(final String usuario) {
        this.usuario = usuario;
    }
	
}
