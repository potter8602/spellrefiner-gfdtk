package SpellRefiner;

import java.io.Serializable;
import javax.inject.Named;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

@Named(value = "controller_dictionaryerror")
@RequestScoped
public class Controller_DictionaryError implements Serializable {
    private static final String page_success = null;//"success";
    private static final String page_error = "error";
    
    @EJB
    private DAO_Interface_DictionaryError DAO_DictionaryError;        
    
    private DictionaryError dictionaryerror = new DictionaryError();   
    
    
    public String SaveNew(){
        try {
            DAO_DictionaryError.SaveNew(dictionaryerror);
        }
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;                
    }    
    
    
    public String Update(){
        try {
            DAO_DictionaryError.Update(dictionaryerror);
        }
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;                
    }    
    
    public String Delete(){
        try {            
            DAO_DictionaryError.Delete(dictionaryerror);
        }
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;                
    }        
    
    public DictionaryError getDictionaryError(){
        return dictionaryerror;
    }

    public void setDictionaryError(DictionaryError dictionaryerror){
        this.dictionaryerror = dictionaryerror;
    }    
    
}
