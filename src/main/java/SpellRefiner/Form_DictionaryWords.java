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
import java.util.HashMap;
import java.util.Map;




@Named(value = "form_dictionary_words")
@ViewScoped
public class Form_DictionaryWords implements Serializable {
    private static final String page_success = null;
    private static final String page_error = "error";
    
    @Inject
    private CurrentUser currentUser;      
    

    @EJB
    private DAO_Interface_Dictionary DAO_Dictionary;        
    
    @EJB
    private DAO_Interface_Word DAO_Word;        
    
    @EJB
    private DAO_Interface_DictionaryError DAO_DictionaryError;      
    
    
    //private Word word = new Word();   
    private List<Word> List_Word;
    private Dictionary dictionary = null;   
    
    private boolean AscendingOrder;
    
    private String WordsOrder; //wordID, wordSpelling, wordRandomNumber
    
    
    private boolean DeletedOnly = false;
    private boolean WithSound = true;
    private boolean WithoutSound = true;
    private boolean WithErrorsOnly = false;
    private boolean WithNotProcessedErrorsOnly = false;
    
    private String WordSpellingFilter="";
    
    private Boolean microphoneMuted;
    
    
    private Paginator paginator;
    
    public Form_DictionaryWords(){
    	microphoneMuted = true;
    	
        List_Word = new ArrayList<Word>();
        WordsOrder = "wordID";
        
        paginator = new Paginator();
        paginator.RecordsOnPage = 10;         
        paginator.SetNumberOfPagesByRecordsCount(0);
        paginator.Update();
        
    }
    
    
    @PostConstruct
    public void Init(){
        UpdateList();        
    }
    
    private void AddMessage(String clientId, FacesMessage.Severity severity, String summary, String detail){
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(severity, summary, detail));
    }
    
    
    
    public void Update(Word w){
        
        List<Word> list = DAO_Word.findByDictionaryAndSpelling(dictionary, w.getWordSpelling());
        for (Word w1: list) {
            if (!w1.getWordID().equals(w.getWordID())){
                AddMessage(null, FacesMessage.SEVERITY_ERROR, "Такое слово уже есть в словаре", "");
                return;
            }
	}         
        
        DAO_Word.Update(w);
    }   
    
    public void MarkDeleted(Word w){        
        w.setWordDeleted(Boolean.TRUE);
        DAO_Word.Update(w);
    }        
    
    
    public void UnMarkDeleted(Word w){
        w.setWordDeleted(Boolean.FALSE);
        DAO_Word.Update(w);
    }        
    
    public Dictionary getDictionary(){
        return dictionary;
    }
    
    public void setDictionary(Dictionary dict){
        this.dictionary = dict;
    }
    
    /*
    public Word getWord(){
        return word;
    }

    public void setWord(Word word){
        this.word = word;
    }
    */
    
    public List<Word> getList_Word(){
        return List_Word;
    }

    
    public void UpdatePaginator(){
               
        Integer RecordsCount = DAO_Word.DictionaryWordsWithParams_Count(dictionary, WordsOrder, AscendingOrder, DeletedOnly, WithSound, WithoutSound, WithErrorsOnly, WithNotProcessedErrorsOnly);

        paginator.SetNumberOfPagesByRecordsCount(RecordsCount);
        paginator.Update();
        
    }
    
    public void Update_List_Word(){
    	//System.out.println("Update_List_Word");
        
        //List_Word = DAO_Word.DictionaryWordsWithParamsInInterval(dictionary, WordsOrder, AscendingOrder, DeletedOnly, WithSound, WithoutSound, WithErrorsOnly, WithNotProcessedErrorsOnly, paginator.getFirstRecord(), paginator.RecordsOnPage);
    	List_Word = DAO_Word.DictionaryWordsWithParams(dictionary, WordsOrder, AscendingOrder, DeletedOnly, WithSound, WithoutSound, WithErrorsOnly, WithNotProcessedErrorsOnly, WordSpellingFilter);
        
    	Map<Integer, Word> Map_Word_WordId = new HashMap<Integer, Word>();
    	
        for (Word w: List_Word) {
        	Map_Word_WordId.put(w.getWordID(), w);
        }    
        
        List<Integer> list_word_id = DAO_DictionaryError.GetList_WordID_From_DictionaryError(dictionary.getDictID());
        
        
        //System.out.println("-------------------list_word_id.size() = " + list_word_id.size());

        for (Integer id: list_word_id) {    	
        	
        	Word w = Map_Word_WordId.get(id);
        	
        	if (w == null){
        		continue;
        	}
        	
        	//System.out.println("-------------------id = " + id);
        	//System.out.println("--------------------w = " + w);
        	
        	List<DictionaryError> List_DictionaryError = DAO_DictionaryError.findErrorsByWord(w);
        	w.setWordDictionaryErrors(List_DictionaryError);
        	w.setWordHasErrors(!List_DictionaryError.isEmpty());
        	
        	//System.out.println("--------------------List_DictionaryError.isEmpty() = " + List_DictionaryError.isEmpty());
        	
        	List<DictionaryError> List_DictionaryError_NotProcessed = DAO_DictionaryError.findNotProcessedErrorsByWord(w);
        	w.setWordHasNotProcessedErrors(!List_DictionaryError_NotProcessed.isEmpty());
        	
        	//System.out.println("--------------------List_DictionaryError_NotProcessed.isEmpty() = " + List_DictionaryError_NotProcessed.isEmpty());
        	
        }    
        
        //w.setWordDictionaryErrors(DAO_DictionaryError.findByWord(w));
       //List<DictionaryError> List_DictionaryError = DAO_DictionaryError.findNotProcessedErrorsByWord(w);
       //w.setWordHasNotProcessedErrors(!List_DictionaryError.isEmpty());
       //List<DictionaryError> List_DictionaryError = DAO_DictionaryError.findErrorsByWord(w);
       //w.setWordHasErrors(!List_DictionaryError.isEmpty());
        
        
        /*
        for (Map.Entry<Word,Integer> entry : words_ids.entrySet()) {
            Word w = new Word();
            w.setWordSpelling(entry.getKey());
            w.setWordCount(entry.getValue());
        }
        */
    	
    }

    
    /*
    public List<Word> Filter_List_Word(List<Word> list_word){
        List<Word> list = new ArrayList<Word>();
        
        
        for (Word w: list_word) {
            if (WithNotProcessedErrorsOnly){
                if (!w.getWordHasNotProcessedErrors()){
                    continue;                        
                }
            }
                
            list.add(w);
        }         
        
        
        return list;
    }
    */
    
    public String UpdateList(){
               
        if(dictionary == null){
            return page_error;
        };
        
        try {
            
            //UpdatePaginator();
            Update_List_Word();
            //UpdateListWord_WordDictionaryErrors(List_Word);
            //UpdateListWord_WordHasNotProcessedErrors(List_Word);
            //UpdateListWord_WordHasErrors(List_Word);
            
            
            
        }    
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;
    }
    
    
    /*
    public String NewWord(){
        word = new Word();
        
        return page_success;                
    }

    public void Save(){
        
        if(dictionary == null){
            return;
        };
        
        if (word.getWordID() == null){
            word.setDictID(dictionary.getDictID());    
            word.setWordDeleted(Boolean.FALSE);
            word.GenerateAndSetWordRandomNumber();
            Integer id = DAO_Word.SaveNew(word);
            word.setWordID(id);
        }
        else{
            DAO_Word.Update(word);    
        }
            
            
    }    
    */
    
    public boolean getAscendingOrder(){
        return AscendingOrder;
    }

    public void setAscendingOrder(boolean order){
        this.AscendingOrder = order;
        UpdateList();
    }
    
    public boolean getDescendingOrder(){
        return !AscendingOrder;
    }

    public void setDescendingOrder(boolean order){
        this.AscendingOrder = !order;
        UpdateList();
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
    
    public String getWordsOrder(){
        return WordsOrder;
    }

    public void setWordsOrder(String wordsOrder){
        this.WordsOrder = wordsOrder;
    }
    
    public void ChangeWordsOrder(String wordsOrder) {
        this.WordsOrder = wordsOrder;
        UpdateList();
    } 
    
    public boolean getDeletedOnly(){        
        return DeletedOnly;
    }

    public void setDeletedOnly(boolean deletedOnly){
        this.DeletedOnly = deletedOnly;
        UpdateList();
    }

    public boolean getWithSound(){        
        return WithSound;
    }

    public void setWithSound(boolean withSound){
        this.WithSound = withSound;
        UpdateList();
    }

    public boolean getWithoutSound(){        
        return WithoutSound;
    }

    public void setWithoutSound(boolean withoutSound){
        this.WithoutSound = withoutSound;
        UpdateList();
    }

    public boolean getWithErrorsOnly(){        
        return WithErrorsOnly;
    }

    public void setWithErrorsOnly(boolean withErrorsOnly){
        this.WithErrorsOnly = withErrorsOnly;
        
        if (withErrorsOnly){
        	WithNotProcessedErrorsOnly = false;        	
        }
        
        UpdateList();
    }

    
    public boolean getWithNotProcessedErrorsOnly(){        
        return WithNotProcessedErrorsOnly;
    }

    public void setWithNotProcessedErrorsOnly(boolean withNotProcessedErrorsOnly){
        this.WithNotProcessedErrorsOnly = withNotProcessedErrorsOnly;
        
        if (withNotProcessedErrorsOnly){
        	WithErrorsOnly = false;
        };
        
        UpdateList();
    }
    
    
    public void setWordSpellingFilter(String WordSpellingFilter){
        this.WordSpellingFilter = WordSpellingFilter;
        UpdateList();
    }

    public String getWordSpellingFilter(){        
        return WordSpellingFilter;
    }
    
    
    public Paginator getPaginator(){
        return paginator;
    }
    
    public String GotoPage(){
        UpdatePaginator();
        UpdateList();
        return page_success;
    }

    public String GotoFirstPage(){
        paginator.GotoFirstPage();
        return UpdateList();
    }
    
    public String GotoLastPage(){
        
        if(dictionary == null){
            return page_error;
        };
        
        try {
            
            UpdatePaginator();
            paginator.GotoLastPage();
            Update_List_Word();
            
        }    
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;
    }
    
    public String GotoNextPage(){
        paginator.GotoNextPage();
        return UpdateList();
    }
    
    public String GotoPreviousPage(){
        paginator.GotoPreviousPage();
        return UpdateList();
    }
    
    public void Update_DictionaryErrors(Word w){
        w.setWordDictionaryErrors(DAO_DictionaryError.findByWord(w));
    }    
    
    
    public void UpdateListWord_WordDictionaryErrors(List<Word> List_Word){        
        for (Word w: List_Word) {
             w.setWordDictionaryErrors(DAO_DictionaryError.findByWord(w));
        }            
    }        
    
    public void UpdateListWord_WordHasNotProcessedErrors(List<Word> List_Word){
        for (Word w: List_Word) {
            List<DictionaryError> List_DictionaryError = DAO_DictionaryError.findNotProcessedErrorsByWord(w);
            w.setWordHasNotProcessedErrors(!List_DictionaryError.isEmpty());
        }    
        
    }    
    
    public void UpdateListWord_WordHasErrors(List<Word> List_Word){
        for (Word w: List_Word) {
            List<DictionaryError> List_DictionaryError = DAO_DictionaryError.findErrorsByWord(w);
            w.setWordHasErrors(!List_DictionaryError.isEmpty());
        }    
        
    }    
    
    
    
    public String UpdateDictinaryError(DictionaryError dictError){
        try {
            DAO_DictionaryError.Update(dictError);
        }
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;                
    }   
    
    
    
    public void EraseSound(Word w){

    	
    	Integer wordID = w.getWordID();
    	//System.out.println("EraseSound--------------------wordID = " + wordID);
    	
    	List<Word> list = DAO_Word.findByWordID(wordID);
    	
    	if (list.isEmpty()){
    		return;
    	}
    	
    	//System.out.println("EraseSound-------------------- 2");
    	
    	w.setWordHasSound(Boolean.FALSE);
    	
    	Word w1 = list.get(0);
    	w1.setWordHasSound(Boolean.FALSE);
    	w1.setWordSound(null);
    	
        DAO_Word.Update(w1);
    }   
    
    public Boolean getMicrophoneMuted(){
        return microphoneMuted;
    }
    
    public void setMicrophoneMuted(Boolean microphoneMuted){
        this.microphoneMuted = microphoneMuted;
    }   
    
    public void SetMuted(){
        setMicrophoneMuted(true);
        
    }   

    public void SetUnmuted(){
        setMicrophoneMuted(false);
    }
    
    
    
}
