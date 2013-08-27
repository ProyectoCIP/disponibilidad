package dom.disponibilidad;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Audited;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Bookmarkable;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberGroups;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ObjectType;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.PublishedObject;
import org.apache.isis.applib.annotation.Where;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import dom.estados.Estados;
import dom.habitacion.Habitacion;
import dom.reserva.Reserva;

@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY)
@javax.jdo.annotations.Queries( {
    @javax.jdo.annotations.Query(
            name="disponibilidad_disponible", language="JDOQL",  
            value="SELECT FROM dom.disponibilidad.Disponible WHERE ownedBy == :ownedBy")
})
@javax.jdo.annotations.Version(strategy=VersionStrategy.VERSION_NUMBER, column="VERSION")
//@javax.jdo.annotations.Unique(name="ToDoItem_description_must_be_unique", members={"ownedBy","description"})
@ObjectType("DISPONIBILIDAD")
@Audited
//@PublishedObject(ToDoItemChangedPayloadFactory.class)
//@AutoComplete(repository=.class, action="autoComplete")
//@Bookmarkable
public class Disponibilidad {

	//@javax.jdo.annotations.Persistent(defaultFetchGroup="true")
    private LocalDate fechaDesde;

    @Hidden(where=Where.ANYWHERE)
    public LocalDate getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(final LocalDate fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

/*    private LocalDate fechaHasta;

    @Hidden(where=Where.ANYWHERE)
    public LocalDate getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(final LocalDate fechaHasta) {
        this.fechaHasta = fechaHasta;
    }
  */
    
    private List<Reserva> reservas;    
    
    public List<Reserva> getReservas() { return reservas; }    

    public void setReservas(List<Reserva> reservas) {
    	this.reservas = reservas;
    }
    
	// {{ injected: DomainObjectContainer
    private DomainObjectContainer container;

    public void injectDomainObjectContainer(final DomainObjectContainer container) {
        this.container = container;
    }
}
