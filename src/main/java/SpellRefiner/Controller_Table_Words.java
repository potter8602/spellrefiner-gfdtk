package SpellRefiner;

import java.io.Serializable;
import javax.inject.Named;
import javax.ejb.EJB;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

@Named(value = "controller_table_words")
@ViewScoped
public class Controller_Table_Words implements Serializable {
    private static final String page_success = null;
    private static final String page_error = "error";
    
    @EJB
    private DAO_Interface_Word DAO_Word;        
    
    private Word word = new Word();   
    private List<Word> List_Word;
    
    public Controller_Table_Words(){
        List_Word = new ArrayList<Word>();
    }
    
    @PostConstruct
    public void Init(){
        UpdateList();
    }
    
    
    public String Update(){
        //System.out.println("--------------------Update " + word);
        try {
            DAO_Word.Update(word);
        }
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;                
    }    
    
    public String Delete(){
        //System.out.println("--------------------Delete " + word);
        try {            
            DAO_Word.Delete(word);
            List_Word.remove(word);
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
    
    public List<Word> getList_Word(){
        return List_Word;
    }
    
    public String UpdateList(){
        try {
            List_Word = DAO_Word.findAll();
        }    
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;
    }
    
}
