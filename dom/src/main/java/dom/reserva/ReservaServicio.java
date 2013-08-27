package dom.reserva;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.clock.Clock;
import org.joda.time.LocalDate;
import com.google.common.base.Objects;

import dom.estados.Estados;
import dom.habitacion.Habitacion;

	@Named("Reservar")
	public class ReservaServicio extends AbstractFactoryAndRepository {
		
		@MemberOrder(sequence = "1")
	    public Reserva Reservar(
	            @RegEx(validation = "\\w[@&:\\-\\,\\.\\+ \\w]*") // words, spaces and selected punctuation
	            @Named("Estado") Estados estado,
	            @Named("Habitación") Habitacion habitacion
	        ) {
	        final String usuario = actualUsuario();
	        return nuevaReserva(estado, habitacion, usuario);
	    }
		
		@Hidden // for use by fixtures
	    public Reserva nuevaReserva(
	            final Estados estado, 
	            final Habitacion habitacion,
	            final String usuario
	            ) {
	        final Reserva reserva = newTransientInstance(Reserva.class);
	        reserva.setEstado(estado);
	        reserva.setHabitacion(habitacion);
	        reserva.setFecha(LocalDate.now());
	        
	        persistIfNotAlready(reserva);
	        
	        return reserva;
	    }

		 // {{ helpers
	    protected boolean creadoPorUsuario(final Habitacion habitacion) {
	        return Objects.equal(habitacion.getUsuario(), actualUsuario());
	    }
	    protected String actualUsuario() {
	        return getContainer().getUser().getName();
	    }
	    // }}

	}