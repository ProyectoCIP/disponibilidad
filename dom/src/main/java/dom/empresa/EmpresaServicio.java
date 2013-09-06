package dom.empresa;

import java.math.BigDecimal;
import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.clock.Clock;
import org.apache.isis.applib.filter.Filter;
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
    
    @Named("Crear")
    @MemberOrder(sequence = "1")
    public Empresa nuevaEmpresa(
            @RegEx(validation = "\\w[@&:\\-\\,\\.\\+ \\w]*") // words, spaces and selected punctuation
            @Named("CUIT") String cuit, 
            @RegEx(validation = "\\w[@&:\\-\\,\\.\\+ \\w]*") // words, spaces and selected punctuation
            @Named("Razón Social") String razonSocial,
            @Named("Tarifa") float tarifa,
            @Named("Forma de Pago") FormaPago fPago
            ) {
        final String creadoPor = usuarioActual();
        return nEmpresa(cuit, razonSocial, tarifa, fPago,creadoPor);
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
        empresa.setUsuario(usuario);
        
        persistIfNotAlready(empresa);
        
        return empresa;
    }
    
    @Named("Listar")
    @ActionSemantics(Of.SAFE)
    @MemberOrder(sequence = "2")
    public List<Empresa> ListaEmpresas() {
        final String usuario = usuarioActual();
        final List<Empresa> listaEmpresas = allMatches(Empresa.class, Empresa.creadoPor(usuario));
        return listaEmpresas;
    }    

    /*
     * Método para llenar el DropDownList de empresas, con la posibilidad de que te autocompleta las coincidencias al ir tipeando
     */
    @Hidden
    public List<Empresa> autoComplete(final String nombre) {
        return allMatches(Empresa.class, new Filter<Empresa>() {
        	@Override
            public boolean accept(final Empresa e) {
                return creadoPorActualUsuario(e) && e.getRazonSocial().contains(nombre);
            }
        });
    }
    
    protected boolean creadoPorActualUsuario(final Empresa e) {
        return Objects.equal(e.getUsuario(), usuarioActual());
    }
    protected String usuarioActual() {
        return getContainer().getUser().getName();
    }
	
}
