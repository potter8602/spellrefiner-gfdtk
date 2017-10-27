package SpellRefiner;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named(value = "form_check")
@ViewScoped
public class Form_Check implements Serializable {
    
    @Inject
    private CurrentUser currentUser;      

    @EJB
    private DAO_Interface_Check DAO_Check;  
    
    @EJB
    private DAO_Interface_Dictionary DAO_Dictionary;        
    
    
    private Check check = null;   
    
    private List<Dictionary> List_Dictionary;
    
    private Dictionary selectedDictionary;   
    
    
    public Form_Check(){
    	//System.out.println("Form_Check.Form_Check");
    	//System.out.println("check==null " + (check==null));
    }
    
    
    @PostConstruct
    public void Init(){
    	//System.out.println("Form_Check.Init");
        UpdateList();        
        
    }
    
    
    public void NewCheck(){
    	//System.out.println("Form_Check.NewCheck");
        check = new Check();
        selectedDictionary = null;
        
        //System.out.println("check " + check);
        //System.out.println("check==null " + (check==null));
    }    
    
    
    public void Save(){
    	System.out.println("Form_Check.Save");
    	//System.out.println("check " + check);
    	//System.out.println("check==null " + (check==null));
    	//System.out.println("check.getCheckName() " + (check.getCheckName()));
    	//System.out.println("check.getCheckName()==null " + (check.getCheckName()==null));
    	
    	
        if (check.getCheckID() == null){
            check.setCheckDeleted(Boolean.FALSE);            
            
            Date date = new Date();
            check.setCheckDateTime(date);
            check.setUserID(currentUser.getUser().getUserID());
            check.setCheckDict(selectedDictionary);
            check.setDictID(selectedDictionary.getDictID());
            
            check.setCheckPass(0);
            
            check.setCheckFinished(Boolean.FALSE);
        
            Integer id = DAO_Check.SaveNew(check);
            check.setCheckID(id);
            
            //check = DAO_Check.Add(check);
            
            //check = DAO_Check.Get(check.getCheckID());
            
        	//System.out.println("2 check " + check);
        	//System.out.println("2 check == null " + (check == null));
        	//System.out.println("2 check.getCheckID() " + check.getCheckID());
        	//System.out.println("2 check.getCheckDict() == null " + (check.getCheckDict() == null));
        	//System.out.println("2 check.getDictID() " + check.getDictID());
            
        }
        else{
            DAO_Check.Update(check);
        }
        
        
    }    
    
    public String CheckError(){
    	//System.out.println("Form_Check.CheckError");
    	
        if (check.getCheckName()==null || check.getCheckName().isEmpty()){
        	return "Введите название проверки";
        }

        if (check.getCheckID() == null){
        	if (selectedDictionary == null){
        		return "Не выбран словарь";
        	}
        }
    	
    	return null;
    }
    
    public void ActionSave(String form_id){
    	//System.out.println("Form_Check.ActionSave");
    	//System.out.println("check " + check);
    	//System.out.println("check==null " + (check==null));
    	//System.out.println("check.getCheckName() " + (check.getCheckName()));
    	//System.out.println("check.getCheckName()==null " + (check.getCheckName()==null));
    	//System.out.println("selectedDictionary " + selectedDictionary);
    	
        /*    
        List<Check> list = DAO_Check.findByDictNameAndUser(check.getDictName(), currentUser.getUser());
        
        for (Check ch: list) {
            if (!ch.getDictID().equals(check.getDictID())){
                AddMessage(null, FacesMessage.SEVERITY_ERROR, "Словарь с таким названием уже есть", "");
                return;
            }
		}            
        
        */
    	
        
    	String error = CheckError();
    	if (error!=null){
            AddMessage(form_id, FacesMessage.SEVERITY_ERROR, error, "");
            return;             
    	};
        
        
        Save();
        
        
        
        selectedDictionary = null;
        
    }    
    
    /*
    public void CreateAndSave(){
    	System.out.println("CreateNameAndSave");
    	check = new Check();
    	
    	System.out.println("Form_Check.CreateAndSave");
    	String checkName = "Проверка";
    	
    	if (selectedDictionary != null){
    		checkName = checkName + ", " + selectedDictionary.getDictName(); 
    	}
    	
    	check.setCheckName(checkName);
    	
    	
    	Save();
    	
    }
    */
    
    
    private void AddMessage(String clientId, FacesMessage.Severity severity, String summary, String detail){
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(severity, summary, detail));
    }
    
    public void Update(){
        /*
        List<Check> list = DAO_Check.findByDictNameAndUser(check.getDictName(), currentUser.getUser());
        
        for (Check ch: list) {
            if (!ch.getDictID().equals(check.getDictID())){
                AddMessage(null, FacesMessage.SEVERITY_ERROR, "Словарь с таким названием уже есть", "");
                return;
            }
	}            
        */
        
        DAO_Check.Update(check);
    }        
  
    
    public Check getCheck(){
    	//System.out.println("Form_Check.getCheck");
    	//System.out.println("check " + check);
    	//System.out.println("check==null " + (check==null));
    	
        return check;
    }

    public void setCheck(Check check){
    	
    	//System.out.println("Form_Check.setCheck");
    	//System.out.println("check " + check);
    	//System.out.println("check==null " + (check==null));
    	
        this.check = check;
    }   
    
    public List<Dictionary> getList_Dictionary(){
        return List_Dictionary;
    }    

    
    public Dictionary getSelectedDictionary(){
        return selectedDictionary;
    }

    public void setSelectedDictionary(Dictionary selectedDictionary){
        this.selectedDictionary = selectedDictionary;
    }   
    
    public void UpdateList_Info(List<Dictionary> list){
        for (Dictionary dict: list) {
            Map<String, Object> Info = DAO_Dictionary.GetDictionaryInfo(dict);
            dict.setNumberOfWords((Integer)Info.get("NumberOfWords"));
            dict.setNumberOfWordsWithSound((Integer)Info.get("NumberOfWordsWithSound"));
	}    
    }
    
    public void UpdateList(){          
        
        List_Dictionary = DAO_Dictionary.Accessible(currentUser.getUser());
        UpdateList_Info(List_Dictionary);
    }
    
    
    
    
}
