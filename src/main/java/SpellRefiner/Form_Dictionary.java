package SpellRefiner;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named(value = "form_dictionary")
@ViewScoped
public class Form_Dictionary implements Serializable {
    private static final String page_success = null;//"success";
    private static final String page_error = "error";
    
    @Inject
    private CurrentUser currentUser;      

    @EJB
    private DAO_Interface_Dictionary DAO_Dictionary;    
    
    //@EJB
    //private DAO_Interface_DictionaryPermission DAO_DictionaryPermission;
    
    private Dictionary dictionary = null;    
    //private Dictionary dictionary = new Dictionary();    
    
    
    @PostConstruct
    public void Init(){
    }
    
    
    public void NewDictionary(){
        dictionary = new Dictionary();
    }    
    
    /*
    public void SaveNew(){
        DAO_Dictionary.SaveNew(dictionary);
    } 
    */
    
    public void Save(){
    	
    	//System.out.println("dictionary.getDictName()-------------------- = " + dictionary.getDictName());

    	//AddMessage(null, FacesMessage.SEVERITY_ERROR, "asdfsadfsadf", "");
    	//if(true) return;
    	
        if (dictionary.getDictName().isEmpty()){
        	AddMessage(null, FacesMessage.SEVERITY_ERROR, "Название: Введите значение поля", "");
        	return;
        }
    	
    	
        List<Dictionary> list = DAO_Dictionary.findByDictNameAndUser(dictionary.getDictName(), currentUser.getUser());
        
        for (Dictionary dict: list) {
            if (!dict.getDictID().equals(dictionary.getDictID())){
                AddMessage(null, FacesMessage.SEVERITY_ERROR, "Словарь с таким названием уже есть", "");
                return;
            }
        }            
        
        if (dictionary.getDictID() == null){
            dictionary.setDictDeleted(Boolean.FALSE);
            
            Date date = new Date();
            dictionary.setDictDateTime(date);
            dictionary.setUserID(currentUser.getUser().getUserID());
        
            Integer id = DAO_Dictionary.SaveNew(dictionary);
            dictionary.setDictID(id);
        }
        else{
            DAO_Dictionary.Update(dictionary);
        }
        
    }    
    
    
    
    private void AddMessage(String clientId, FacesMessage.Severity severity, String summary, String detail){
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(severity, summary, detail));
    }
    
    public void Update(){
        
        List<Dictionary> list = DAO_Dictionary.findByDictNameAndUser(dictionary.getDictName(), currentUser.getUser());
        
        for (Dictionary dict: list) {
            if (!dict.getDictID().equals(dictionary.getDictID())){
                AddMessage(null, FacesMessage.SEVERITY_ERROR, "Словарь с таким названием уже есть", "");
                return;
            }
	}            
        
        
        DAO_Dictionary.Update(dictionary);
    }        
  
    
    public Dictionary getDictionary(){
        return dictionary;
    }

    public void setDictionary(Dictionary dict){
        this.dictionary = dict;
    }   
    
    
    /*
    public void Update_Dictionary_DictPermissions(){
        dictionary.setDictPermissions(DAO_DictionaryPermission.findByDict(dictionary));
    } 
    */
    
}
