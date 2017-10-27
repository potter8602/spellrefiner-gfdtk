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

@Named(value = "form_dictionary_error")
@ViewScoped
public class Form_DictionaryError implements Serializable {
    @Inject
    private CurrentUser currentUser;          

    //@EJB
    //private DAO_Interface_Dictionary DAO_Dictionary;        
    
    @EJB
    private DAO_Interface_Word DAO_Word;        
    
    @EJB
    private DAO_Interface_DictionaryError DAO_DictionaryError;      
    
    //private Dictionary dictionary = null;   
    private Word word = null;
    private DictionaryError dictionaryError = new DictionaryError();
    
    public Form_DictionaryError(){
    }
    
    
    @PostConstruct
    public void Init(){
    }
    
    private void AddMessage(String clientId, FacesMessage.Severity severity, String summary, String detail){
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(severity, summary, detail));
    }
    
    
    public Word getWord(){
        return word;
    }
    
    public void setWord(Word word){
        this.word = word;
    }   
    
    
    public DictionaryError getDictionaryError(){
        return dictionaryError;
    }
    
    
    public void NewDictionaryError(){
        dictionaryError = new DictionaryError();
    }    
    
    public void AddDictionaryError(Word word){
        dictionaryError = new DictionaryError();
        this.word = word;
    }    
    
    
    public void Save(){
        
        
        if (word == null){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Не выбрано слово", "");
            return;
        }                

        
        //System.out.println("--------------------dictionaryError.getDictErrorDescription() = " + dictionaryError.getDictErrorDescription());
        //System.out.println("--------------------dictionaryError.getDictErrorComment().isEmpty() = " + dictionaryError.getDictErrorComment().isEmpty());        
        //System.out.println("--------------------dictionaryError.getDictErrorComment() = " + dictionaryError.getDictErrorComment());
        
        if (!dictionaryError.getDictErrorDescription() &&
        	!dictionaryError.getDictErrorOther() &&
        	!dictionaryError.getDictErrorPronunciation() &&
        	!dictionaryError.getDictErrorSpelling() &&
        	!dictionaryError.getDictErrorTranslation() &&
        	dictionaryError.getDictErrorComment().isEmpty()
        	)
        {
            AddMessage("form_dictionary_error", FacesMessage.SEVERITY_ERROR, "Заполните один из флагов ошибки или комментарий", "");
            return;
        	
        }
        
        if (dictionaryError.getDictErrorID() == null){
            dictionaryError.setUserID(currentUser.getUser().getUserID());
            dictionaryError.setDictID(word.getDictID());
            dictionaryError.setWordID(word.getWordID());
            
            Date date = new Date();
            dictionaryError.setDictErrorDateTime(date);
            
            dictionaryError.setDictErrorProcessed(Boolean.FALSE);
            
            Integer id = DAO_DictionaryError.SaveNew(dictionaryError);
            dictionaryError.setDictErrorID(id);
        }
        else{
            DAO_DictionaryError.Update(dictionaryError);
        }
        
    }   
    
    public void Close(){        
    	word = null;
    }
    
}
