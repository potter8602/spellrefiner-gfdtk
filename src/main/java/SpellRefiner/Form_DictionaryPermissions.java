package SpellRefiner;

import java.io.Serializable;
import javax.inject.Named;
import javax.ejb.EJB;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.faces.event.AjaxBehaviorEvent;



@Named(value = "form_dictionary_permissions")
@ViewScoped
public class Form_DictionaryPermissions implements Serializable {
    private static final String page_success = null;
    private static final String page_error = "error";
    
    @Inject
    private CurrentUser currentUser;      

    @EJB
    private DAO_Interface_Dictionary DAO_Dictionary;        
    
    //@EJB
    //private DAO_Interface_DictionaryError DAO_DictionaryError;    
    
    @EJB
    private DAO_Interface_DictionaryPermission DAO_DictionaryPermission;    
    
    @EJB
    private DAO_Interface_User DAO_User;       
    
    private DictionaryPermission dictPerm = null;   
    private List<DictionaryPermission> List_DictionaryPermission;
    private Dictionary dictionary = null;   
    private String login = "";   
    
    //private String errorMessage;
    //private Boolean error = false;
    
    
    public Form_DictionaryPermissions(){
        List_DictionaryPermission = new ArrayList<DictionaryPermission>();
    }
    
    
    @PostConstruct
    public void Init(){
        UpdateList();        
        Update_List_DictionaryPermission();
    }
    
    
    private void AddMessage(String clientId, FacesMessage.Severity severity, String summary, String detail){
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(severity, summary, detail));
    }
    
    
    public String Update(DictionaryPermission dp){
        try {
            DAO_DictionaryPermission.Update(dp);
        }
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;                
    }   
    
    public String Delete(DictionaryPermission dp){
        try {            
            DAO_DictionaryPermission.Delete(dp);
            List_DictionaryPermission.remove(dp);
        }
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;                
    }        
    
    
    public Dictionary getDictionary(){
        return dictionary;
    }
    
    public void setDictionary(Dictionary dict){        
        this.dictionary = dict;
        Update_List_DictionaryPermission();
    }
    
    
    public DictionaryPermission getDictPerm(){
        return dictPerm;
    }

    public void setDictPerm(DictionaryPermission dictPerm){
        this.dictPerm = dictPerm;
    }

    public String getLogin(){
        return login;
    }

    public void setLogin(String login){
        this.login = login;
    }
    
    
    /*
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public Boolean getError() {
        return error;
    }
    */
    
    public List<DictionaryPermission> getList_DictionaryPermission(){
        return List_DictionaryPermission;
    }

    
    public void Update_List_DictionaryPermission(){               
        List_DictionaryPermission = DAO_DictionaryPermission.findByDict(dictionary);
    }
    
    
    
    public String UpdateList(){
               
        if(dictionary == null){
            return page_error;
        };
        
        try {
            
            Update_List_DictionaryPermission();
        }    
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;
    }
    
    
    
    public String NewDictionaryPermission(){
        dictPerm = new DictionaryPermission();
        
        return page_success;                
    }
    
    public String SaveNew(){
        //System.out.println("--------------------dictionary = " + dictionary);
        //System.out.println("--------------------dictPerm = " + dictPerm);
        //System.out.println("--------------------login = " + login);
        
        //errorMessage = "";
        //error = false;                
        
        if(dictionary == null){
            return page_error;
        };
        
        if(dictPerm == null){
            return page_error;
        };
        
        
        /*
        if (login.isEmpty()){
            errorMessage = "Введите логин";
            error = true;            
            return null;                
        } 
        */        
        
        List<User> users = DAO_User.findByUserLogin(login);
        if (users.isEmpty()){
            //errorMessage = "Неверный логин";
            //error = true;            
            
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Неверный логин", "");
            
            return null;                
        }
        
        List<DictionaryPermission> list = DAO_DictionaryPermission.findByDictAndUser(dictionary, users.get(0)); 
        if (!list.isEmpty()){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Такой логин уже добавлен в права", "");
            
            return null;                            
        }
        
        
        
        try {
            
            dictPerm.setDictID(dictionary.getDictID());    
            dictPerm.setUserID(users.get(0).getUserID());            
            
            Integer id = DAO_DictionaryPermission.SaveNew(dictPerm);
            dictPerm.setDictPermID(id);
            
            dictPerm = null;
            login = "";            
            
            Update_List_DictionaryPermission();
            
        }
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;                
    }    
    
    
    
}
