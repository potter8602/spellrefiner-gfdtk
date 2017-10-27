package SpellRefiner;

import java.io.Serializable;
import javax.inject.Named;
import javax.ejb.EJB;
import java.util.List;
import java.util.ArrayList;
//import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import java.util.Map;
//import javax.faces.application.FacesMessage;
//import javax.faces.context.FacesContext;


@Named(value = "form_my_dictionaries")
@ViewScoped
public class Form_My_Dictionaries implements Serializable {
    //private static final String page_success = null;
    //private static final String page_error = "error";
    
    @EJB
    private DAO_Interface_Dictionary DAO_Dictionary;        
    
    //private Dictionary dictionary = new Dictionary();   
    private List<Dictionary> List_Dictionary;
    
    @Inject
    private CurrentUser currentUser;      
    
    private Boolean onlyDeleted;
    //private General general;
    
    public Form_My_Dictionaries(){
       List_Dictionary = new ArrayList<Dictionary>();
       onlyDeleted = false;
       //general = new General();
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
    */
    
    /*
    public void NewDictionary(){
        dictionary = new Dictionary();
    } 
    */
    /*
    public void SaveNew(){
        
        dictionary.setDictDeleted(Boolean.FALSE);
        //dictionary.setDictDateTime(null);
        dictionary.setUserID(currentUser.getUser().getUserID());
        
        Integer id = DAO_Dictionary.SaveNew(dictionary);
        dictionary.setDictID(id);
        
    } 
    */
    
    /*
    private void AddMessage(String clientId, FacesMessage.Severity severity, String summary, String detail){
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(severity, summary, detail));
    }
    */
    
    /*
    public void Save(){
        
        //if (dictionary.getDictName().isEmpty()){
            //AddMessage(null, FacesMessage.SEVERITY_ERROR, "Введите название", "");
            //return;
        //};    
        
        //System.out.println("--------------------Save");
        //System.out.println("--------------------dictionary.getDictID()=" + dictionary.getDictID());
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
    */
    
    
    /*
    public void Update(Dictionary d){
        DAO_Dictionary.Update(d);
    } 
    */
    
    /*
    public Dictionary getDictionary(){
        return dictionary;
    }

    
    public void setDictionary(Dictionary dict){
        this.dictionary = dictionary;
    }
    */
    
    public Boolean getOnlyDeleted(){
        return onlyDeleted;
    }

    
    public void setOnlyDeleted(Boolean onlyDeleted){
        this.onlyDeleted = onlyDeleted;
    }
    
    
    public List<Dictionary> getList_Dictionary(){
        return List_Dictionary;
    }
    
    /*
    public String MarkDeleted(){
        try {
            dict.setDictDeleted(Boolean.TRUE);
            DAO_Dictionary.Update(dict);
            UpdateList();
        }
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;                
    }        
    */
    
    public void MarkDeleted(Dictionary d){
        d.setDictDeleted(Boolean.TRUE);
        DAO_Dictionary.Update(d);
        UpdateList();
    }        
    
    public void UnMarkDeleted(Dictionary d){
        d.setDictDeleted(Boolean.FALSE);
        DAO_Dictionary.Update(d);
        UpdateList();
    }        
    
    
    /*
    public String UpdateList(){
        try {
            List_Dictionary.clear();
            if(currentUser.getAuthorized()){                
                List_Dictionary = DAO_Dictionary.MyDictionaries(currentUser.getUser());
            }
                
        }    
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;
    }
    */
    
    public void UpdateList_Info(List<Dictionary> list){
        for (Dictionary dict: list) {
            Map<String, Object> Info = DAO_Dictionary.GetDictionaryInfo(dict);
            dict.setNumberOfWords((Integer)Info.get("NumberOfWords"));
            dict.setNumberOfWordsWithSound((Integer)Info.get("NumberOfWordsWithSound"));
	}    
    }
    
    
    
    public void UpdateList(){
        //List_Dictionary.clear();
        //if(currentUser.getAuthorized()){                
        //    List_Dictionary = DAO_Dictionary.MyDictionaries(currentUser.getUser());
        //}
        List_Dictionary = DAO_Dictionary.MyDictionaries(currentUser.getUser(), onlyDeleted);
        UpdateList_Info(List_Dictionary);
        //List_Dictionary = general.GetDictinariesInfo(List_Dictionary);
        //DAO_Dictionary.GetDictionariesInfo(List_Dictionary);
        
        //System.out.println("--------------------UpdateList");
        //for (Dictionary dict: List_Dictionary) {
            //Map<String, Object> Info = DAO_Dictionary.GetDictionaryInfo(dict);
            //dict.setNumberOfWords((Integer)Info.get("NumberOfWords"));
            //dict.setNumberOfWordsWithSound((Integer)Info.get("NumberOfWordsWithSound"));
            //System.out.println("--------------------dict.getDictID() = " + dict.getDictID());
            //System.out.println("--------------------dict.getNumberOfWords() = " + dict.getNumberOfWords());
            
	//}    
        
                
    }
    
    
}
