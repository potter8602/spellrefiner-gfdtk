package SpellRefiner;

import java.io.Serializable;
import javax.inject.Named;
import javax.ejb.EJB;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

@Named(value = "controller_table_dictionaries")
@ViewScoped
public class Controller_Table_Dictionaries implements Serializable {
    private static final String page_success = null;
    private static final String page_error = "error";
    
    @EJB
    private DAO_Interface_Dictionary DAO_Dictionary;        
    
    private Dictionary dict = new Dictionary();   
    private List<Dictionary> List_Dictionary;
    
    public Controller_Table_Dictionaries(){
        List_Dictionary = new ArrayList<Dictionary>();
    }
    
    @PostConstruct
    public void Init(){
        UpdateList();
    }
    
    
    public String Update(){
        try {
            DAO_Dictionary.Update(dict);
        }
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;                
    }    
    
    public String Delete(){
        try {            
            DAO_Dictionary.Delete(dict);
            List_Dictionary.remove(dict);
        }
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;                
    }        
    
    public Dictionary getDictionary(){
        return dict;
    }

    public void setDictionary(Dictionary dict){
        this.dict = dict;
    }
    
    public List<Dictionary> getList_Dictionary(){
        return List_Dictionary;
    }
    
    public String UpdateList(){
        try {
            List_Dictionary = DAO_Dictionary.findAll();
        }    
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;
    }
    
}
