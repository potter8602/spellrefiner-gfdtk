package SpellRefiner;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.application.FacesMessage;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;



@Named(value = "dictionaryConverter")
@RequestScoped
//@FacesConverter(value = "dictionaryConverter")
public class DictionaryConverter implements Converter {
    
    @EJB
    private DAO_Interface_Dictionary DAO_Dictionary;        
    
  @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        
        if (modelValue == null) {
            return "";
        }

        if (modelValue instanceof Dictionary) {
            return String.valueOf(((Dictionary) modelValue).getDictID());
        } else {
            throw new ConverterException(new FacesMessage(modelValue + "is not a valid Dictionary entity"));
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        
        //System.out.println("--------------------------------------------------------------------");
        
        if (submittedValue == null || submittedValue.isEmpty()) {
            return null;
        }

        try {
            List<Dictionary> list = DAO_Dictionary.findByDictID(Integer.valueOf(submittedValue));
            if (list.isEmpty()) { 
                return null;
            };
            
            return list.get(0);
            
        } catch (NumberFormatException e) {
            throw new ConverterException(new FacesMessage(submittedValue + " is not a valid Dictionary ID"));
        }
    }
    
    
}
