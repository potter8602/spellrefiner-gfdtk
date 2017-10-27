package SpellRefiner;

import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;


@FacesConverter(value = "TestConverter")
@ManagedBean(value = "TestConverter")
public class TestConverter implements Converter {
    
    @EJB
    private DAO_Interface_Dictionary DAO_Dictionary;     

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent component,
            String value) {
        
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            List<Dictionary> list = DAO_Dictionary.findByDictID(Integer.valueOf(value));
            if (list.isEmpty()) { 
                return null;
            };
            
            return list.get(0);
            
        } catch (NumberFormatException e) {
            throw new ConverterException(new FacesMessage(value + " is not a valid Dictionary ID"));
        }
        
        //return "TestConverter1234"; 
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        
        if (o == null) {
            return "";
        }

        if (o instanceof Dictionary) {
            return String.valueOf(((Dictionary) o).getDictID());
        } else {
            throw new ConverterException(new FacesMessage(o + "is not a valid Dictionary entity"));
        } 
        
        //return "TestConverter5678"; 
    }
}
