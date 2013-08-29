package objstore.jdo.habitacion;

import java.util.List;

import org.apache.isis.applib.query.QueryDefault;

import dom.habitacion.Habitacion;
import dom.habitacion.HabitacionServicio;
import dom.todo.ToDoItem;

public class HabitacionJdo extends HabitacionServicio {

	/*
	@Override
	protected List<Habitacion> traerTodas() {
		return allMatches(
				new QueryDefault<Habitacion>(Habitacion.class, 
                        "habitacion_todas", 
                        "ownedBy", currentUserName())
		);
	}
	*/
}
