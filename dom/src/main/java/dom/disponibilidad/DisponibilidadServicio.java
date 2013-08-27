package dom.disponibilidad;

import java.util.ArrayList;
import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.clock.Clock;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import com.google.common.base.Objects;

import dom.estados.Estados;
import dom.habitacion.Habitacion;
import dom.reserva.Reserva;
import dom.todo.ToDoItem;

	@Named("Disponibilidad")
	public class DisponibilidadServicio extends AbstractFactoryAndRepository {
		
		@MemberOrder(sequence = "1")
		@Named("Por Fechas")
	    public List<Reserva> porFechas(
	            @Named("Fecha desde:") LocalDate desde,
	            @Named("Fecha hasta:") LocalDate hasta
	        ){
			
	        return consultar(desde,hasta);
	    }
		
		private int getDiferenciaDesdeHasta(LocalDate desde, LocalDate hasta) {
	      	//calcula la diferencia entre la fecha desde y hasta
	    	Days d = Days.daysBetween(desde, hasta);
	    	
	    	return d.getDays();
	    }
	    
	    private List<Reserva> consultar(LocalDate fechaDesde, LocalDate hasta) {
			
	    	List<Reserva> reservas = new ArrayList<Reserva>();
			
	    	Habitacion habitacion = newTransientInstance(Habitacion.class);
	    	habitacion.setId(1);
	    	habitacion.setNombre("Dorada");
	    	//habitacion.setUsuario(container.getUser().getName());
	    	LocalDate fechaAuxiliar = fechaDesde;
	    	
	    	for(int i=0; i <= getDiferenciaDesdeHasta(fechaDesde, hasta); i++) {

		    		Reserva reservaRelleno = newTransientInstance(Reserva.class);
		    		reservaRelleno.setFecha(fechaAuxiliar);
		    		reservaRelleno.setEstado(Estados.DISPONIBLE);
		    		reservaRelleno.setNumero(0);
		    		//reservaRelleno.setUsuario(container.getUser().getName());
		    		reservaRelleno.setHabitacion(habitacion);    		
		    		reservas.add(reservaRelleno);
		    		fechaAuxiliar = fechaDesde.plusDays(i+1);
		    	
			    	reservas.add(reservaRelleno);
			    	
			}
	    	
	    	return reservas;
	    }
		
		/*
		 // {{ helpers
	    protected boolean creadoPorUsuario(final Habitacion habitacion) {
	        return Objects.equal(habitacion.getUsuario(), actualUsuario());
	    }
	    protected String actualUsuario() {
	        return getContainer().getUser().getName();
	    }
	    // }}
		*/
	}