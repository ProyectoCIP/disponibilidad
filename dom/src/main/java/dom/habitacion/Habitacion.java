package dom.habitacion;

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
@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY)
@javax.jdo.annotations.Queries( {
    @javax.jdo.annotations.Query(
            name="habitacion_estados", language="JDOQL",  
            value="SELECT FROM dom.habitacion.Habitacion WHERE estado == :estado and fecha == :fecha orderBy fecha")
    })
@javax.jdo.annotations.Version(strategy=VersionStrategy.VERSION_NUMBER, column="VERSION")
//@javax.jdo.annotations.Unique(name="ToDoItem_description_must_be_unique", members={"ownedBy","description"})
@ObjectType("HABITACION")
@Audited
//@PublishedObject(ToDoItemChangedPayloadFactory.class)
//@AutoComplete(repository=.class, action="autoComplete")
@Bookmarkable
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
