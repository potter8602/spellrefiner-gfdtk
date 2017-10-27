package SpellRefiner;

import java.io.Serializable;
import javax.inject.Named;
import javax.ejb.EJB;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

@Named(value = "controller_table_dictionaryerrors")
@ViewScoped
public class Controller_Table_DictionaryErrors implements Serializable {
    private static final String page_success = null;
    private static final String page_error = "error";
    
    @EJB
    private DAO_Interface_DictionaryError DAO_DictionaryError;        
    
    private DictionaryError dictionaryerror = new DictionaryError();   
    private List<DictionaryError> List_DictionaryError;
    
    public Controller_Table_DictionaryErrors(){
        List_DictionaryError = new ArrayList<DictionaryError>();
    }
    
    @PostConstruct
    public void Init(){
        UpdateList();
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
            List_DictionaryError.remove(dictionaryerror);
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
    
    public List<DictionaryError> getList_DictionaryError(){
        return List_DictionaryError;
    }
    
    public String UpdateList(){
        try {
            List_DictionaryError = DAO_DictionaryError.findAll();
        }    
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;
    }
    
}
