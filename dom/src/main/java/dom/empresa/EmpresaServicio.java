package dom.empresa;

import java.math.BigDecimal;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.clock.Clock;
import org.joda.time.LocalDate;

import com.google.common.base.Objects;

import dom.enumeradores.FormaPago;
import dom.todo.ToDoItem;
import dom.todo.ToDoItem.Category;

@Named("Empresa")
public class EmpresaServicio extends AbstractFactoryAndRepository {

	@Override
    public String getId() {
        return "empresa";
    }

    public String iconName() {
        return "Empresa";
    }
    
    @Named("Nueva")
    @MemberOrder(sequence = "1")
    public Empresa nuevaEmpresa(
            @RegEx(validation = "\\w[@&:\\-\\,\\.\\+ \\w]*") // words, spaces and selected punctuation
            @Named("CUIT") String cuit, 
            @RegEx(validation = "\\w[@&:\\-\\,\\.\\+ \\w]*") // words, spaces and selected punctuation
            @Named("Razón Social") String razonSocial,
            @Named("Tarifa") float tarifa,
            @Named("Forma de Pago") FormaPago fPago
            ) {
        final String ownedBy = currentUserName();
        return nEmpresa(cuit, razonSocial, tarifa, fPago,ownedBy);
    }
    
    @Hidden
    public Empresa nEmpresa(
            final String cuit, 
            final String razonSocial, 
            final float tarifa,
            final FormaPago fPago, 
            final String usuario) {
        final Empresa empresa = newTransientInstance(Empresa.class);
        empresa.setCuit(cuit);
        empresa.setRazonSocial(razonSocial);
        empresa.setTarifa(tarifa);
        empresa.setEstado(true);
        empresa.setFormaPago(fPago);
        
        return empresa;
       
    
    }
    
    protected boolean ownedByCurrentUser(final ToDoItem t) {
        return Objects.equal(t.getOwnedBy(), currentUserName());
    }
    protected String currentUserName() {
        return getContainer().getUser().getName();
    }
	
}
