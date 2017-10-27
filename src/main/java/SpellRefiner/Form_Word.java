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


@Named(value = "form_word")
@ViewScoped
public class Form_Word implements Serializable {
    private static final String page_success = null;
    private static final String page_error = "error";
    
    //@Inject
    //private CurrentUser currentUser;          

    @EJB
    private DAO_Interface_Dictionary DAO_Dictionary;        
    
    @EJB
    private DAO_Interface_Word DAO_Word;        
    
    //@EJB
    //private DAO_Interface_DictionaryError DAO_DictionaryError;      
    
    
    //private Word word = new Word();
    private Word word = null;
    private Dictionary dictionary = null;    
    private Boolean microphoneMuted;
    
    
    public Form_Word(){
    	microphoneMuted = true;
    }
    
    
    @PostConstruct
    public void Init(){
    }
    
    private void AddMessage(String clientId, FacesMessage.Severity severity, String summary, String detail){
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(severity, summary, detail));
    }    
    
    /*
    public void Update(Word w){
        DAO_Word.Update(w);
    }   
    */
    
    public Dictionary getDictionary(){
        return dictionary;
    }
    
    public void setDictionary(Dictionary dict){
        this.dictionary = dict;
    }
    
    
    public Word getWord(){
        return word;
    }

    public void setWord(Word word){
        this.word = word;
    }

    public Boolean getMicrophoneMuted(){
        return microphoneMuted;
    }

    public void setMicrophoneMuted(Boolean microphoneMuted){
        this.microphoneMuted = microphoneMuted;
    }   
    
    public String NewWord(){
        word = new Word();
        
        return page_success;                
    }
    public void Save(){
        
        if(dictionary == null){
            return;
        };
        
        if (word.getWordSpelling().isEmpty()){
        	AddMessage(null, FacesMessage.SEVERITY_ERROR, "Слово (написание): Введите значение поля", "");
        	return;
        }
        
        
        /*
        List<Word> list = DAO_Word.DictionaryWords(dictionary);
        
        System.out.println("--------------------word = " + word);
        System.out.println("--------------------word.getWordID() = " + word.getWordID());
        System.out.println("--------------------word.getWordSpelling() = " + word.getWordSpelling());
        for (Word w: list) {
            
            System.out.println("--------------------w = " + w);
            System.out.println("--------------------w.getWordID() = " + w.getWordID());
            System.out.println("--------------------w.getWordSpelling() = " + w.getWordSpelling());
            
            if (!w.getWordID().equals(word.getWordID())){
                if (w.getWordSpelling().equals(word.getWordSpelling())){
                    AddMessage(null, FacesMessage.SEVERITY_ERROR, "Такое слово уже есть в словаре", "");
                    return;
                }    
            }
	} 
        */        
        
        List<Word> list = DAO_Word.findByDictionaryAndSpelling(dictionary, word.getWordSpelling());
        for (Word w: list) {
            if (!w.getWordID().equals(word.getWordID())){
                AddMessage(null, FacesMessage.SEVERITY_ERROR, "Такое слово уже есть в словаре", "");
                return;
            }
        }         
        
        
        
        
        
        if (word.getWordID() == null){
            word.setDictID(dictionary.getDictID());    
            //word.setWordDeleted(Boolean.FALSE);
            //word.setWordHasSound(Boolean.FALSE);
            word.GenerateAndSetWordRandomNumber();
            
            
            
            Integer id = DAO_Word.SaveNew(word);
            word.setWordID(id);
        }
        else{
            DAO_Word.Update(word);    
        }
            
            
    }    
    
    public void UpdateForm(){
    	//System.out.println("UpdateForm--------------------word = " + word);
    	if (word == null){
    		return;
    	}
    	
    	//System.out.println("UpdateForm--------------------word.getWordID() = " + word.getWordID());
    	
        if (word.getWordID() != null){
        	List<Word> list = DAO_Word.findByWordID(word.getWordID());
        	if (!list.isEmpty()){
        		//System.out.println("--------------------list.get(0).getWordHasSound() = " + list.get(0).getWordHasSound());
        		word.setWordHasSound(list.get(0).getWordHasSound());
        	}
        	
        }
    	
    }
    
    public void SetMuted(){
        setMicrophoneMuted(true);
        UpdateForm();
        
    }   

    public void SetUnmuted(){
        setMicrophoneMuted(false);
        UpdateForm();
    }
    
    
    
}
