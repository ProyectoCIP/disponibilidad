package dom.habitacion;

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.filter.Filter;

import com.google.common.base.Objects;

import dom.habitacion.Habitacion;

@Named("Habitaciones")
public class HabitacionServicio extends AbstractFactoryAndRepository {
	
	@MemberOrder(sequence = "1")
	@Named("Nueva Habitación")
    public Habitacion nueva(
            @RegEx(validation = "\\w[@&:\\-\\,\\.\\+ \\w]*") // words, spaces and selected punctuation
            @Named("Nombre") String nombre
        ) {
        final String usuario = actualUsuario();
        return nuevaHabitacion(nombre, usuario);
    }
	
	@MemberOrder(sequence = "2")
	@Named("Listar Habitaciones")
	public List<Habitacion> listaHabitaciones() {
		List<Habitacion> habitaciones = traerTodas();
			if(habitaciones.isEmpty()) {
				getContainer().informUser("Aún no existen habitaciones cargadas");
			}
		return habitaciones;
	}
	
	@Programmatic
	public List<Habitacion> traerTodas() {
            final List<Habitacion> habitaciones = allMatches(Habitacion.class, Habitacion.creadosPor(usuarioActual()));
            return habitaciones;
	}

	@Hidden
    public Habitacion nuevaHabitacion(
            final String nombre, 
            final String usuario
            ) {
        final Habitacion habitacion = newTransientInstance(Habitacion.class);
        habitacion.setNombre(nombre);
        habitacion.setUsuario(usuario);
        
        persistIfNotAlready(habitacion);
        
        return habitacion;
    }

	 // {{ helpers
    protected boolean creadoPorUsuario(final Habitacion habitacion) {
        return Objects.equal(habitacion.getUsuario(), actualUsuario());
    }
    protected String actualUsuario() {
        return getContainer().getUser().getName();
    }
    // }}
    
    @Hidden
    public List<Habitacion> completarHabitaciones(final String nombre) {
        return allMatches(Habitacion.class, new Filter<Habitacion>() {
            @Override
            public boolean accept(final Habitacion h) {
                return creadoPorUsuario(h) && h.getNombre().contains(nombre);
            }

        });
    }
    
    protected String usuarioActual() {
        return getContainer().getUser().getName();
    }

}
