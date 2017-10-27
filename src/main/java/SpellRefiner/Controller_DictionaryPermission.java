package SpellRefiner;

import java.io.Serializable;
import javax.inject.Named;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

@Named(value = "controller_dictionarypermission")
@RequestScoped
public class Controller_DictionaryPermission implements Serializable {
    private static final String page_success = null;//"success";
    private static final String page_error = "error";
    
    @EJB
    private DAO_Interface_DictionaryPermission DAO_DictionaryPermission;        
    
    private DictionaryPermission dictionarypermission = new DictionaryPermission();   
    
    
    public String SaveNew(){
        try {
            DAO_DictionaryPermission.SaveNew(dictionarypermission);
        }
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;                
    }    
    
    
    public String Update(){
        try {
            DAO_DictionaryPermission.Update(dictionarypermission);
        }
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;                
    }    
    
    public String Delete(){
        try {            
            DAO_DictionaryPermission.Delete(dictionarypermission);
        }
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;                
    }        
    
    public DictionaryPermission getDictionaryPermission(){
        return dictionarypermission;
    }

    public void setDictionaryPermission(DictionaryPermission dictionarypermission){
        this.dictionarypermission = dictionarypermission;
    }    
    
}
