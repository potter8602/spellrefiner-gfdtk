package SpellRefiner;

import java.io.Serializable;
import javax.inject.Named;
import javax.ejb.EJB;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

@Named(value = "controller_table_dictionarypermissions")
@ViewScoped
public class Controller_Table_DictionaryPermissions implements Serializable {
    private static final String page_success = null;
    private static final String page_error = "error";
    
    @EJB
    private DAO_Interface_DictionaryPermission DAO_DictionaryPermission;        
    
    private DictionaryPermission dictionarypermission = new DictionaryPermission();   
    private List<DictionaryPermission> List_DictionaryPermission;
    
    public Controller_Table_DictionaryPermissions(){
        List_DictionaryPermission = new ArrayList<DictionaryPermission>();
    }
    
    @PostConstruct
    public void Init(){
        UpdateList();
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
            List_DictionaryPermission.remove(dictionarypermission);
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
    
    public List<DictionaryPermission> getList_DictionaryPermission(){
        return List_DictionaryPermission;
    }
    
    public String UpdateList(){
        try {
            List_DictionaryPermission = DAO_DictionaryPermission.findAll();
        }    
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;
    }
    
}
