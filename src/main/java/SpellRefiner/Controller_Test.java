package SpellRefiner;

import java.io.Serializable;
import javax.inject.Named;
import javax.ejb.EJB;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

@Named(value = "controller_test")
@ViewScoped
public class Controller_Test implements Serializable {
    private static final String page_success = null;
    private static final String page_error = "error";
    
    @EJB
    private DAO_Interface_Word DAO_Word;      

    @EJB
    private DAO_Interface_DictionaryError DAO_DictionaryError;      
    
    
    private Word word = new Word();   
    private List<Word> List_Word;
    private Paginator paginator;
    private List<String> List_Detail;
    
    //private Integer currentWordId;   
    private Word currentWord;   
    
    private List<String> themes;
    
    public Controller_Test(){
        List_Word = new ArrayList<Word>();
        paginator = new Paginator();
        paginator.RecordsOnPage = 10;         
        paginator.SetNumberOfPagesByRecordsCount(500);
        paginator.Update();
        
        List_Detail = new ArrayList<String>();
        
        themes = new ArrayList<String>();
        
        
        String themes_str = "afterdark, afternoon, afterwork, aristo, black-tie, blitzer, bluesky, bootstrap, casablanca, cupertino, cruze, dark-hive, delta, dot-luv, eggplant, excite-bike, flick, glass-x, home, hot-sneaks, humanity, le-frog, midnight, mint-choc, overcast, pepper-grinder, redmond, rocket, sam, smoothness, south-street, start, sunny, swanky-purse, trontastic, ui-darkness, ui-lightness, vader";        
        String[] themes_arr = themes_str.split(", ");
        for (int i = 0; i < themes_arr.length; i++) {
           themes.add(themes_arr[i]);
        }    
        
        
    }
    
    @PostConstruct
    public void Init(){
        UpdateList();
        
    }
    
    public List<Word> getList_Word(){
        return List_Word;
    }

    public List<String> List_Detail(){
        return List_Detail;
    }
    
    public Paginator getPaginator(){
        //System.out.println("--------------------Controller_Test.getPaginator()");        
        //System.out.println("--------------------Controller_Test.paginator.getTestString1() " + paginator.getTestString1());
        return paginator;
    }
    
    
    public void UpdateListWord_WordDictionaryErrors(List<Word> List_Word){
        
        for (Word w: List_Word) {
             //List<DictionaryError> List_DictionaryError = DAO_DictionaryError.findByWord(w);  
             //w.setWordDictionaryErrors(List_DictionaryError);
             
             w.setWordDictionaryErrors(DAO_DictionaryError.findByWord(w));
             
             //System.out.println("--------------------UpdateListWord_WordDictionaryErrors word.getWordID() " + w.getWordID());
             //System.out.println("--------------------UpdateListWord_WordDictionaryErrors List_DictionaryError.size() " + List_DictionaryError.size());
             //System.out.println("--------------------UpdateListWord_WordDictionaryErrors w.getWordDictionaryErrors().size() " + w.getWordDictionaryErrors().size());
        }    
        
    }    
    
    public void UpdateListWord_WordHasNotProcessedErrors(List<Word> List_Word){
        
        for (Word w: List_Word) {
            List<DictionaryError> List_DictionaryError = DAO_DictionaryError.findNotProcessedErrorsByWord(w);
            w.setWordHasNotProcessedErrors(!List_DictionaryError.isEmpty());
        }    
        
    }    
    
    public String UpdateList(){
        //System.out.println("--------------------Controller_Test.paginator.getTestString1() " + paginator1.getTestString1());
        try {
            
            List_Word = DAO_Word.findAll();
            UpdateListWord_WordDictionaryErrors(List_Word);
            UpdateListWord_WordHasNotProcessedErrors(List_Word);
            //DAO_DictionaryError.UpdateListWord_WordDictionaryErrors(List_Word);
            //List_Word = DAO_Word.Test1();
            //List_Word = DAO_Word.Test2();
            
            //for (Word w: List_Word) {
                //List<DictionaryError> List_DictionaryError = w.getWordDictionaryErrors();
                //System.out.println("--------------------Controller_Test.UpdateList word.getWordID() " + w.getWordID());
                //System.out.println("--------------------Controller_Test. w.getWordDictionaryErrors().size() " +  w.getWordDictionaryErrors().size());
            //}                
        }    
        catch(Exception e){
            return page_error;               
        }                 
        
        return page_success;
    }

    public String GotoPage(){
        //System.out.println("--------------------Controller_Test.paginator.CurrentItem.PageNumber " + paginator.CurrentItem.PageNumber);
        //System.out.println("--------------------Controller_Test.paginator.CurrentItem.LastPage " + paginator.CurrentItem.LastPage);
        //System.out.println("--------------------Controller_Test.paginator.CurrentItem.NextPage " + paginator.CurrentItem.NextPage);
        
        return page_success;
    }

    public String GotoFirstPage(){
        paginator.GotoFirstPage();
        return page_success;
    }
    
    public String GotoLastPage(){
        paginator.GotoLastPage();
        return page_success;
    }
    
    public String GotoNextPage(){
        paginator.GotoNextPage();
        return page_success;
    }
    
    public String GotoPreviousPage(){
        paginator.GotoPreviousPage();
        return page_success;
    }
    
    public String UpdatePaginator(){
        paginator.Update();
        return page_success;
    }
    
    public String UpdateDetail(){
        List_Detail.clear();
        List_Detail.add("detail1");
        List_Detail.add("detail2");
        List_Detail.add("detail3");
        return page_success;
    }
    
    
    public void setCurrentWord(Word currentWord) {
        this.currentWord = currentWord;
    }
    
    
    public void Update_CurrentWord_DictionaryErrors(){
        //Word w = DAO_Word.GetWordByID(currentWordId);
        currentWord.setWordDictionaryErrors(DAO_DictionaryError.findByWord(currentWord));
    }    
    
    
  public List<String> getThemes() {
        return themes;
    }     
    
}

