package SpellRefiner;

import java.io.Serializable;
import javax.inject.Named;
import javax.ejb.EJB;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named(value = "form_shared_dictionaries")
@ViewScoped
public class Form_Shared_Dictionaries implements Serializable {
    private static final String page_success = null;
    private static final String page_error = "error";
    
    @EJB
    private DAO_Interface_Dictionary DAO_Dictionary;        
    
    //private Dictionary dict = new Dictionary();   
    private List<Dictionary> List_Dictionary;
    
    
    public Form_Shared_Dictionaries(){
        List_Dictionary = new ArrayList<Dictionary>();
    }
    
    @PostConstruct
    public void Init(){
        UpdateList();
    }
    
    /*
    public void Update(){
        DAO_Dictionary.Update(dict);
    }    
    
    public void Delete(){
        DAO_Dictionary.Delete(dict);
        List_Dictionary.remove(dict);
    }        
    
    public Dictionary getDictionary(){
        return dict;
    }

    public void setDictionary(Dictionary dict){
        this.dict = dict;
    }
    */
    
    public List<Dictionary> getList_Dictionary(){
        return List_Dictionary;
    }
    
    public void UpdateList(){
        List_Dictionary = DAO_Dictionary.Shared();
        UpdateList_Info(List_Dictionary);
        //Update_DictinariesInfo(List_Dictionary);
        //UpdateList_DictinariesInfo();
    }
    
    /*
    public void Update_DictinariesInfo(List<Dictionary> List_Dictionary){
        for (Dictionary dict: List_Dictionary) {
            Map<String, Object> Info = DAO_Dictionary.GetDictionaryInfo(dict);
            System.out.println("--------------------dict.getDictID() = " + dict.getDictID());
            System.out.println("--------------------Info.get(NumberOfWords) = " + Info.get("NumberOfWords"));
            
            dict.setNumberOfWords((Integer)Info.get("NumberOfWords"));
            dict.setNumberOfWordsWithSound((Integer)Info.get("NumberOfWordsWithSound"));
	}    
    }
    */
    
    /*
    public void UpdateList_DictinariesInfo(){
        for (Dictionary d: List_Dictionary) {
            Map<String, Object> Info = DAO_Dictionary.GetDictionaryInfo(d);
            System.out.println("--------------------dict.getDictID() = " + d.getDictID());
            System.out.println("--------------------Info.get(NumberOfWords) = " + Info.get("NumberOfWords"));
            
            d.setNumberOfWords((Integer)Info.get("NumberOfWords"));
            d.setNumberOfWordsWithSound((Integer)Info.get("NumberOfWordsWithSound"));
	}    
    }
    */
    
    public void UpdateList_Info(List<Dictionary> list){
        for (Dictionary dict: list) {
            Map<String, Object> Info = DAO_Dictionary.GetDictionaryInfo(dict);
            dict.setNumberOfWords((Integer)Info.get("NumberOfWords"));
            dict.setNumberOfWordsWithSound((Integer)Info.get("NumberOfWordsWithSound"));
	}    
    }
    
    
}
