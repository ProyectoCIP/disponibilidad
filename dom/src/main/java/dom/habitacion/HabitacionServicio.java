package dom.habitacion;

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.clock.Clock;
import org.apache.isis.applib.filter.Filter;
import org.joda.time.LocalDate;

import com.google.common.base.Objects;

import dom.habitacion.Habitacion;
import dom.todo.ToDoItem;

@Named("Habitaciones")
public class HabitacionServicio extends AbstractFactoryAndRepository {
	
	@MemberOrder(sequence = "1")
    public Habitacion NuevaHabitacion(
            @RegEx(validation = "\\w[@&:\\-\\,\\.\\+ \\w]*") // words, spaces and selected punctuation
            @Named("Nombre") String nombre
        ) {
        final String usuario = actualUsuario();
        return nuevaHabitacion(nombre, usuario);
    }
	
	/*public LocalDate default2NewToDo() {
        return new LocalDate(Clock.getTime()).plusDays(14);
    }*/
    // }}
	@Hidden // for use by fixtures
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
    
    //{{ autoComplete (hidden)
    @Hidden
    public List<Habitacion> completarHabitaciones(final String nombre) {
        return allMatches(Habitacion.class, new Filter<Habitacion>() {
            @Override
            public boolean accept(final Habitacion h) {
                return creadoPorUsuario(h) && h.getNombre().contains(nombre);
            }

        });
    }
    // }}

}
