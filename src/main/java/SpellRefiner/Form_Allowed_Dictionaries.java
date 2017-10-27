package SpellRefiner;

import java.io.Serializable;
import javax.inject.Named;
import javax.ejb.EJB;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named(value = "form_allowed_dictionaries")
@ViewScoped
public class Form_Allowed_Dictionaries implements Serializable {
    private static final String page_success = null;
    private static final String page_error = "error";
    
    @EJB
    private DAO_Interface_Dictionary DAO_Dictionary;        
    
    //private Dictionary dict = new Dictionary();   
    private List<Dictionary> List_Dictionary;
    
    @Inject
    private CurrentUser currentUser;      
    
    public Form_Allowed_Dictionaries(){
        List_Dictionary = new ArrayList<Dictionary>();
    }
    
    @PostConstruct
    public void Init(){
        UpdateList();
    }
    
    /*
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
    
    */
    
    public List<Dictionary> getList_Dictionary(){
        return List_Dictionary;
    }
    
    /*
    public String UpdateList_Old(){
        try {
            List_Dictionary.clear();
            if(currentUser.getAuthorized()){                
                List<Object[]> list = DAO_Dictionary.Allowed_Old(currentUser.getUser().getUserLogin());
                
                for (Object item[] : list) {
                    Dictionary dict = (Dictionary) item[0];
                    DictionaryPermission dp = (DictionaryPermission) item[1];
                    dict.setDictWriteAccessCurrentUser(dp.getDictPermWriteAccess());
                    List_Dictionary.add(dict);                    
                }                
            }
                
        }    
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;
    }
    */

    public void UpdateList(){
        List_Dictionary.clear();
        if(currentUser.getAuthorized()){                
            List<Object[]> list = DAO_Dictionary.Allowed(currentUser.getUser());
                
            for (Object item[] : list) {
                Dictionary dict = (Dictionary) item[0];
                DictionaryPermission dp = (DictionaryPermission) item[1];
                dict.setDictWriteAccessCurrentUser(dp.getDictPermWriteAccess());
                List_Dictionary.add(dict);                    
            }                
        }
        
        UpdateList_Info(List_Dictionary);
    }
    
    public void UpdateList_Info(List<Dictionary> list){
        for (Dictionary dict: list) {
            Map<String, Object> Info = DAO_Dictionary.GetDictionaryInfo(dict);
            dict.setNumberOfWords((Integer)Info.get("NumberOfWords"));
            dict.setNumberOfWordsWithSound((Integer)Info.get("NumberOfWordsWithSound"));
	}    
    }
    
    
    
}
