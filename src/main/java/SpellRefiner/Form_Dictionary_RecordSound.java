package SpellRefiner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named(value = "form_dictionary_recordSound")
@ViewScoped
public class Form_Dictionary_RecordSound  implements Serializable{
    @Inject
    private CurrentUser currentUser;      

    @EJB
    private DAO_Interface_Dictionary DAO_Dictionary;    
    
    @EJB
    private DAO_Interface_Word DAO_Word;        
    
    
    private Boolean microphoneMuted;
    //private Dictionary dictionary = new Dictionary();    
	private Dictionary dictionary = null;    
    private List<Word> List_Word;
    private Word word;
    
    
    //sort
    private boolean AscendingOrder = true;    
    private String WordsOrder; //wordID, wordSpelling, wordRandomNumber
    

    public Form_Dictionary_RecordSound(){
        List_Word = new ArrayList<Word>();
        microphoneMuted = true;
        
        WordsOrder = "wordRandomNumber";        
    }
    
    @PostConstruct
    public void Init(){
        Update_List_Word();
        GetLastWord();
        
    }
    
    public Dictionary getDictionary(){
        return dictionary;
    }

    public void setDictionary(Dictionary dict){
        this.dictionary = dict;
    }   
    
    public List<Word> getList_Word(){
        return List_Word;
    }
    
    public Word getWord(){
        return word;
    }

    public void setWord(Word w){
        this.word = w;
    }   

    public Boolean getMicrophoneMuted(){
        return microphoneMuted;
    }

    public void setMicrophoneMuted(Boolean microphoneMuted){
        this.microphoneMuted = microphoneMuted;
    }   
    
    
    
    //sort
    public boolean getAscendingOrder(){
        return AscendingOrder;
    }

    public void setAscendingOrder(boolean order){
        this.AscendingOrder = order;
        GetLastWord();
    }
    
    public boolean getDescendingOrder(){
        return !AscendingOrder;
    }

    public void setDescendingOrder(boolean order){
        this.AscendingOrder = !order;
        GetLastWord();
    }        

    public boolean getAlphabeticalOrder(){
        return WordsOrder.equals("wordSpelling");
    }

    
    //empty function
    public void setAlphabeticalOrder(boolean order){        
    }
    

    public boolean getRandomNumberOrder(){
        return WordsOrder.equals("wordRandomNumber");
    }

    public void setRandomNumberOrder(boolean order){
    }
    

    public boolean getWordIDOrder(){        
        return WordsOrder.equals("wordID");
    }

    //empty function
    public void setWordIDOrder(boolean order){
    }
    
    public void ChangeWordsOrder(String wordsOrder) {
        this.WordsOrder = wordsOrder;
        GetLastWord();
    } 
    
    
    
    public void Update_List_Word(){        
        List_Word = DAO_Word.findWordsByDictionaryByEditionDateTime(dictionary);
    }

    public void GetLastWord(){
        //word = DAO_Word.GetLastWordWithoutSound(dictionary);
    	word = DAO_Word.GetLastWordWithoutSoundWithParams(dictionary, WordsOrder, AscendingOrder);
        //System.out.println("--------------------GetLastWord word = " + word);
        
    }   
    
    public void EraseSound(Word w){
        w.setWordHasSound(Boolean.FALSE);
        w.setWordSound(null);
        Date date = new Date();
        w.setWordEditionDateTime(date);
        DAO_Word.Update(w);
        
        UpdateForm();
        
    }
    
    public void Refresh_Word(){
    	Word w = DAO_Word.GetWordByID(word.getWordID());
    	if (w==null){
    		return;
    	};
    	
		word.setWordDeleted(w.getWordDeleted());
		word.setWordHasSound(w.getWordHasSound());
		word.setWordDescription(w.getWordDescription());
		word.setWordSpelling(w.getWordSpelling());
		word.setWordTranslation(w.getWordTranslation());
    	
    	
    }
    
    
    public void UpdateForm(){
        Update_List_Word();
        Refresh_Word();
    }
    
    public void SetMuted(){
        setMicrophoneMuted(true);
        
        Update_List_Word();
        GetLastWord();
        
    }   

    public void SetUnmuted(){
        setMicrophoneMuted(false);
        
        Update_List_Word();
        GetLastWord();
    }   
    
    public void MarkDeleted(Word w){        
        w.setWordDeleted(Boolean.TRUE);
        
        Date date = new Date();
        w.setWordEditionDateTime(date);
        
        DAO_Word.Update(w);
        
        
        UpdateForm();
    }        
    
    
    public void UnMarkDeleted(Word w){
        w.setWordDeleted(Boolean.FALSE);
        
        Date date = new Date();
        w.setWordEditionDateTime(date);
        
        DAO_Word.Update(w);        
        
        UpdateForm();
    }     
    
    public void Postpone(Word w){        
        w.setWordPostponed(Boolean.TRUE);
        
        Date date = new Date();
        w.setWordEditionDateTime(date);
        
        DAO_Word.Update(w);
        
        
        UpdateForm();
    }        
    
    
    
    private void AddMessage(String clientId, FacesMessage.Severity severity, String summary, String detail){
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(severity, summary, detail));
    }    
    
    
    public void Save(Word word, String form_id){
        
        if(dictionary == null){
            return;
        };
        
        if (word.getWordSpelling().isEmpty()){
        	AddMessage(form_id, FacesMessage.SEVERITY_ERROR, "Слово (написание): Введите значение поля", "");
        	return;
        }       
        
        
        List<Word> list = DAO_Word.findByDictionaryAndSpelling(dictionary, word.getWordSpelling());
        for (Word w: list) {
            if (!w.getWordID().equals(word.getWordID())){
                AddMessage(form_id, FacesMessage.SEVERITY_ERROR, "Такое слово уже есть в словаре", "");
                return;
            }
        }    
        
        Date date = new Date();
        word.setWordEditionDateTime(date);
        
        
        DAO_Word.Update(word);    
        
        UpdateForm();
            
            
    }   
    
    //public void WordMarkDeleted(){
    //	MarkDeleted(word);
        //word.setWordDeleted(Boolean.TRUE);
        //DAO_Word.Update(word);
        
        //Date date = new Date();
        //word.setWordEditionDateTime(date);
        
        //UpdateForm();
    //}        
    
    
    public void EraseSound_Word(){
    	EraseSound(word);     
    	Update_List_Word();
    }      
    
    public void MarkDeleted_Word(){
    	MarkDeleted(word);
    	Update_List_Word();
    }       
    
    
    public void UnMarkDeleted_Word(){
    	UnMarkDeleted(word);
    	Update_List_Word();
    }     
    
    public void Postpone_Word(){
    	Postpone(word);
    	Update_List_Word();
    }        
    
    public void Save_Word(){
    	Save(word, "form_word_recordsound");
    	Update_List_Word();
    }
    
    
    
    public void EraseSound_LastWords(Word w){
    	EraseSound(w);
    	//Update_List_Word();
    	//GetLastWord();
    	UpdateForm();
    }
    
    public void MarkDeleted_LastWords(Word w){
    	MarkDeleted(w);
    	//Update_List_Word();
    	//GetLastWord();
    	UpdateForm();
    }       
    
    
    public void UnMarkDeleted_LastWords(Word w){
    	UnMarkDeleted(w);
    	UpdateForm();
    	//Update_List_Word();
    	//GetLastWord();
    }     
    
    public void Save_LastWords(Word w){
    	Save(w, "form_last_words");
    	UpdateForm();
    	//Update_List_Word();
    	//GetLastWord();
    }
    
    
}
