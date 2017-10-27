package SpellRefiner;

import java.io.Serializable;
import javax.inject.Named;
import javax.ejb.EJB;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named(value = "form_dictionary_errors")
@ViewScoped
public class Form_DictionaryErrors  implements Serializable{
    @Inject
    private CurrentUser currentUser;          

    //@EJB
    //private DAO_Interface_Word DAO_Word;        
    
    @EJB
    private DAO_Interface_DictionaryError DAO_DictionaryError;      
    
    private Word word = null;
    
    private List<DictionaryError> List_DictionaryError;
    
    public Form_DictionaryErrors(){
        List_DictionaryError = new ArrayList<DictionaryError>();
    }
    
    
    @PostConstruct
    public void Init(){
          Update_List_DictionaryError();
    }
    
    public Word getWord(){
        return word;
    }
    
    public void setWord(Word word){
    	//System.out.println("--------------------Form_DictionaryErrors.setWord, word = " + word);
        this.word = word;
    }   
    
    public List<DictionaryError> getList_DictionaryError(){
        return List_DictionaryError;
    }
    
    public User getCurrentUser() {
        return currentUser.getUser();
    }
    
    
    public void Update_List_DictionaryError(){
        
        List_DictionaryError = DAO_DictionaryError.findErrorsByWord(word);
    }
    
    
    public void UpdateForm(){
    	//System.out.println("--------------------Form_DictionaryErrors.UpdateForm, word = " + word);
        Update_List_DictionaryError();
    }

    /*
    public void UpdateFormByWord(Word word){
        System.out.println("--------------------UpdateFormByWord, word = " + word);
        setWord(word);
        Update_List_DictionaryError();
    }
    */
    
    public void Delete(DictionaryError de){       
        DAO_DictionaryError.Delete(de);
        Update_List_DictionaryError();
    }        
    
    
}
