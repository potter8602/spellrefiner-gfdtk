package SpellRefiner;

import java.io.Serializable;
import javax.inject.Named;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

@Named(value = "controller_word")
@RequestScoped
public class Controller_Word implements Serializable {
    private static final String page_success = null;//"success";
    private static final String page_error = "error";
    
    @EJB
    private DAO_Interface_Word DAO_Word;        
    
    private Word word = new Word();   
    
    
    public String SaveNew(){
        try {
            DAO_Word.SaveNew(word);
        }
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;                
    }    
    
    
    public String Update(){
        try {
            DAO_Word.Update(word);
        }
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;                
    }    
    
    public String Delete(){
        try {            
            DAO_Word.Delete(word);
        }
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;                
    }        
    
    public Word getWord(){
        return word;
    }

    public void setWord(Word word){
        this.word = word;
    }    
    
}
