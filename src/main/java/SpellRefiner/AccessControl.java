package SpellRefiner;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ejb.EJB;
//import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;


@Named(value = "accessControl")
//@RequestScoped
@ViewScoped
public class AccessControl implements Serializable {
    
    @Inject
    private CurrentUser currentUser;                
    
    @EJB
    private DAO_Interface_Check DAO_Check;
    
    @EJB
    private DAO_Interface_Dictionary DAO_Dictionary;
    
    private Dictionary dictionary;
    private Check check = null;
    
    private Boolean ReadAccess_Dictionary = false;
    private Boolean WriteAccess_Dictionary = false;
    private Boolean WriteAccess_Check = false;
    
    @PostConstruct
    public void Init(){
    }
    
    public Dictionary getDictionary(){
        return dictionary;
    }

    public void setDictionary(Dictionary dict){
        this.dictionary = dict;
    } 
    
    public Check getCheck(){
        return check;
    }

    public void setCheck(Check check){
        this.check = check;
    }   
    
    
    
    public Boolean getReadAccess_Dictionary(){
        return ReadAccess_Dictionary;
    }

    public Boolean getWriteAccess_Dictionary(){
        return WriteAccess_Dictionary;
    }
    
    public Boolean getWriteAccess_Check(){
        return WriteAccess_Check;
    }
    
    
    
    public void Update_Access_Dictionary(){
    	
        ReadAccess_Dictionary = false;
        WriteAccess_Dictionary = false;
        
        if (dictionary==null){
        	return;
        }
    	
        ReadAccess_Dictionary = DAO_Dictionary.ReadAccess(dictionary.getDictID(), currentUser.getUser().getUserID());
        WriteAccess_Dictionary = DAO_Dictionary.WriteAccess(dictionary.getDictID(), currentUser.getUser().getUserID()); 	
    }
    
    public void Update_Access_Check(){
    	
    	WriteAccess_Check = false;
        
        if (check==null){
        	return;
        }
        
        if (currentUser.getUser().getUserID()!= check.getUserID()){
        	return;
        }
        
        WriteAccess_Check = true;
    }	
    
    
    
    
}

