package dom.habitacion;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Audited;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Bookmarkable;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberGroups;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.NotPersisted;
import org.apache.isis.applib.annotation.ObjectType;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.PublishedAction;
import org.apache.isis.applib.annotation.PublishedObject;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.filter.Filter;
import org.joda.time.LocalDate;

import com.google.common.base.Objects;

import dom.todo.ToDoItem;
@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY)
@javax.jdo.annotations.Queries({
	@javax.jdo.annotations.Query(
        name="habitacion_todas", language="JDOQL",
        value="SELECT FROM dom.habitacion.Habitacion WHERE ownedBy == :ownedBy"),
	@javax.jdo.annotations.Query(
            name="habitacion_id", language="JDOQL",
            value="SELECT FROM dom.habitacion.Habitacion WHERE id == :id")
})
@javax.jdo.annotations.Version(strategy=VersionStrategy.VERSION_NUMBER, column="VERSION")
@ObjectType("HABITACION")
@Audited
public class Habitacion {
	
	//ID de la habitacion
	private long id;
	
	@Hidden
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String iconName() {
		return "Habitacion";
	}
	
	//Nombre de la habitacion
	private String nombre;

	@Title
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

    public static Filter<Habitacion> creadosPor(final String usuarioActual) {
        return new Filter<Habitacion>() {
            @Override
            public boolean accept(final Habitacion habitacion) {
                return Objects.equal(habitacion.getUsuario(), usuarioActual);
            }
        };
    }   
    
    /*
     * Frigobar para las habitaciones
     * 
    private Frigobar frigobar;
    
    public Frigobar getFrigobar() {
    	return frigobar;
    }
    
    public void setFrigobar(Frigobar frigobar) {
    	this.frigobar = frigobar;
    }
    */
    @Named("Done")
    @PublishedAction
    @Bulk
    @MemberOrder(name="complete", sequence = "1")
    public void completed() {
        container.warnUser("Sisisi");
    }
    // disable action dependent on state of object
    //public String disableCompleted() {
    //    return complete ? "Already completed" : null;
    //}


    @Named("Undo")
    @PublishedAction
    @MemberOrder(name="complete", sequence = "2")
    public void notYetCompleted() {
    	container.warnUser("Nonono");
    }
    // disable action dependent on state of object
    //public String disableNotYetCompleted() {
      //  return !complete ? "Not yet completed" : null;
    //}
    // }}

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
	
 // {{ injected: DomainObjectContainer
    private DomainObjectContainer container;

    public void injectDomainObjectContainer(final DomainObjectContainer container) {
        this.container = container;
    }
	
}
