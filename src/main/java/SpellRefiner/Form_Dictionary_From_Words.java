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

@Named(value = "form_dictionary_from_words")
@ViewScoped
public class Form_Dictionary_From_Words  implements Serializable{
    
    @Inject
    private CurrentUser currentUser;      

    @EJB
    private DAO_Interface_Dictionary DAO_Dictionary;    
    
    @EJB
    private DAO_Interface_Word DAO_Word;        
    
    
    private Dictionary dictionary = new Dictionary();    
    private String text;   
    private List<Word> List_Word;
    
    
    private boolean UpdateOnly = false;

    public Form_Dictionary_From_Words(){
        List_Word = new ArrayList<Word>();
    }
    
    
    @PostConstruct
    public void Init(){
    }
    
    public Dictionary getDictionary(){
        return dictionary;
    }

    public void setDictionary(Dictionary dict){
        this.dictionary = dict;
    }   
    
    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }   
   
    public List<Word> getList_Word(){
        return List_Word;
    }
    
    
    public boolean getUpdateOnly(){
        return UpdateOnly;
    }

    public void setUpdateOnly(boolean UpdateOnly){
        this.UpdateOnly = UpdateOnly;
    }
    
    
    public void Clear_Text(){  
        text = "";
    }        
    
    
    private void AddMessage(String clientId, FacesMessage.Severity severity, String summary, String detail){
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(severity, summary, detail));
    }
    
    public void AddText_To_List_Word(){
        
        if (text.isEmpty()){
            return;
        }
        
        String[] array_strings = text.split("\n");
        
        
        List_Word.clear();
        
        for(String str : array_strings){
        	
        	if (str.isEmpty()){
        		continue;
        	}
        	
        	String[] array_fields = str.split(";");
        	
        	String wordSpelling = "";
        	String wordTranslation = "";
        	String wordDescription = "";
        	
        	if (array_fields.length > 0){
        		wordSpelling = array_fields[0].trim();
        		wordSpelling = wordSpelling.toLowerCase(); 
        	}      	
        	
        	
        	if (array_fields.length > 1){
        		wordTranslation = array_fields[1].trim(); 
        	}

        	if (array_fields.length > 2){
        		wordDescription = array_fields[2].trim(); 
        	}
        	
        	if (wordSpelling.isEmpty()){
        		continue;
        	}
        	
             Word w = new Word();
             w.setWordSpelling(wordSpelling);
             
         	//if (!wordTranslation.isEmpty()){
         	//	w.setWordTranslation(wordTranslation);	
        	//}
             
         	//if (!wordDescription.isEmpty()){
         	//	w.setWordDescription(wordDescription);
        	//}
              
         	w.setWordTranslation(wordTranslation);
         	w.setWordDescription(wordDescription);
            w.setWordDeleted(Boolean.FALSE);
                
            List_Word.add(w);
        	
        }
        
        
        /*
        String[] array_words = text.split(";");
        
        
        
        Map<String,Integer> unique_words = new HashMap<String,Integer>();
        
        for (Word w: List_Word){
            unique_words.put(w.getWordSpelling(), w.getWordCount());
        }
        
        
        String s = new String("Str87uyuy232");
        Matcher matcher = Pattern.compile("\\d+").matcher(s);
        matcher.find();
        int i = Integer.valueOf(matcher.group());  
        
        
        Pattern p = Pattern.compile("([0-9])");
        

        
        
        for(String str : array_words){
            
            if (str.length() == 1){
                continue;
            }
            
            //if (str.matches("\\[0-9]")){
            //    continue;                
            //}
            
            Matcher m = p.matcher(str);

            if(m.find()){
                continue;                
            }
            
            str = str.toLowerCase();

            if(unique_words.containsKey(str)) {
                unique_words.put(str, unique_words.get(str) + 1);
            }
            else {
                unique_words.put(str, 1);
            }
        }
        
        int total_count = 0;
        for (int value : unique_words.values()) {
            total_count = total_count + value;
        }        

        List_Word.clear();
        
        for (Map.Entry<String,Integer> entry : unique_words.entrySet()) {
            Word w = new Word();
            w.setWordSpelling(entry.getKey());
            w.setWordCount(entry.getValue());
            w.setWordTotalCount(total_count);
            w.setWordDeleted(Boolean.FALSE);
            w.setWordHasSound(Boolean.FALSE);
            
            List_Word.add(w);

        }
        */
                
    }   
    
    public void MarkDeleted(Word w){        
        w.setWordDeleted(Boolean.TRUE);
    }        
    
    public void UnMarkDeleted(Word w){
        w.setWordDeleted(Boolean.FALSE);
    }        
    
    
    
    public void Clear_List_Word(){  
        List_Word.clear(); 
    }        

    public void Save_List_Word(){  
        
        if(dictionary==null){
            return;
        }
        
        for (Word w: List_Word){
            if (!w.getWordDeleted()){
                List<Word> list = DAO_Word.findByDictionaryAndSpelling(dictionary, w.getWordSpelling());
                if (!list.isEmpty()){
                    //AddMessage(null, FacesMessage.SEVERITY_ERROR, "Слово \"" + w.getWordSpelling() + "\" уже есть в словаре", "");
                    //return;
                }
            }
        }
        
        for (Word w: List_Word){
            if (!w.getWordDeleted()){
                
                List<Word> list = DAO_Word.findByDictionaryAndSpelling(dictionary, w.getWordSpelling());
                if (!list.isEmpty()){
                    Word w1 = list.get(0);
                    
                    String wordTranslation = w.getWordTranslation();
                    String wordDescription = w.getWordDescription();
                    
                 	if (!wordTranslation.isEmpty()){
                 		w1.setWordTranslation(wordTranslation);	
                	}
                     
                 	if (!wordDescription.isEmpty()){
                 		w1.setWordDescription(wordDescription);
                	}
                    
                    //w1.setWordTranslation(w.getWordTranslation()); 
                    //w1.setWordDescription(w.getWordDescription());
                    
                    
                    DAO_Word.Update(w1);
                }
                else{
                	if (!UpdateOnly){
                		w.setDictID(dictionary.getDictID());    
                		w.setWordDeleted(Boolean.FALSE);
                		w.GenerateAndSetWordRandomNumber();
                		w.setWordCount(0);
                		w.setWordTotalCount(0);                    
                    
                		Integer id = DAO_Word.SaveNew(w);
                		w.setWordID(id);
                	}
                }                                                                              
            }
        }
        
        AddMessage(null, FacesMessage.SEVERITY_INFO, "Слова помещены в словарь", "");
    }        
    
}
