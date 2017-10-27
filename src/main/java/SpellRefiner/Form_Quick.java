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

@Named(value = "form_quick")
@ViewScoped
public class Form_Quick implements Serializable  {
        
    @Inject
    private CurrentUser currentUser;    
    
    @Inject
    private Form_Login form_login;

    @Inject
    private Form_Registration form_registration;
    
    @Inject
    private Form_Check form_check;
    
    @Inject
    private Form_My_Checks form_my_checks;
    
    
    @Inject
    private Form_Input form_input;
    
    @Inject
    private AccessControl access_control;
    
    
    @EJB
    private DAO_Interface_Check DAO_Check;
    
    @EJB
    private DAO_Interface_Input DAO_Input; 
    
    @EJB
    private DAO_Interface_Dictionary DAO_Dictionary;
    
    
    //private Check check = null;   
    //private Input input = null;
    //private Word word = null;
    
    private boolean my_checks_exists = false;    
    
    
    //none, registration, new_check, old_check, input, end
    private String state = null; 
    
    
    public Form_Quick() {
    	state = "none";
    	//System.out.println("Form_Quick.Form_Quick");
    }   
    
    
    @PostConstruct
    public void Init(){
    	//System.out.println("Form_Quick.Init");
    	
    	Update_MyChecksExists();
    }
    
    public String getState(){
    	//System.out.println("Form_Quick.getState " + state);
        return state;
    }

    public void setState(String state){
    	//System.out.println("Form_Quick.setState " + state);
        this.state = state;
    }
    
    public boolean getMyChecksExtists(){
    	//System.out.println("Form_Quick.getState " + state);
        return my_checks_exists;
    }
    
    public void Update_MyChecksExists(){
    	List<Check> List_Check = DAO_Check.MyChecks(currentUser.getUser(), false, true);
    	my_checks_exists = !List_Check.isEmpty();
    	//System.out.println("Form_Quick.Update_MyChecksExists " + my_checks_exists);
    }
    
    
    
    private void AddMessage(String clientId, FacesMessage.Severity severity, String summary, String detail){
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(severity, summary, detail));
    }    
    
    
    public String ActionLogin(String form_id){

    	form_login.ActionLogin(form_id);
        
    	Update_MyChecksExists();
    	
    	state="none";
    	
        return null;                
    	
    	
    }   
    
    public String ActionRegistration(String form_id){

    	String error = form_registration.CheckError();
    	
    	if (error!=null){
    		AddMessage(form_id, FacesMessage.SEVERITY_ERROR, error, "");
    		return null;
    	}
    	
    	if (!form_registration.DoRegistration()){
    		AddMessage(form_id, FacesMessage.SEVERITY_ERROR, "Системная ошибка", "");
    		return null;
    	}

    	form_registration.setClosed(true);        
        
        state="none";
        
        Update_MyChecksExists();
        
        return null;        
    	
    }   
    

    public String ActionNewCheck(){

    	form_check.NewCheck();
    	
    	state="new_check";
        return null;              
    }   

    public String ActionOldCheck(){
    	form_my_checks.setOnlyUnfinished(true);
    	state="old_check";
        return null;              
    }
    
    public void SetNewPass(Check check){
    	
        check.setCheckPass(check.getCheckPass() + 1);
        DAO_Check.Update(check);
    }   

    private void Set_Form_Input(Check check){
    	form_input.setCheck(check);
    	form_input.setExceptWordsInFinishedChecks(true);
    	form_input.setExceptWordsInFinishedChecksSameDictionary(false);
    	form_input.GotoNextWord();
    }   
    
    
    public String ActionStartNewCheck(String form_id){
    	
    	System.out.println("ActionStartNewCheck ");
    	   	    	

    	//List<Check> List_Check = DAO_Check.MyChecks(currentUser.getUser(), false, false);    	
    	//long new_check_number = List_Check.size() + 1;
    	
    	int count_my_checks = DAO_Check.NumberOfMyChecks(currentUser.getUser());
    	long new_check_number = count_my_checks + 1;
    	
    	String checkName = "Проверка " + new_check_number;
    	
    	Check check = form_check.getCheck();
    	check.setCheckName(checkName);
    	
    	String error = form_check.CheckError();
    	if (error!=null){
            AddMessage(form_id, FacesMessage.SEVERITY_ERROR, error, "");
            return null;             
    	};
        
    	Integer count_words = DAO_Dictionary.NumberOfWords(form_check.getSelectedDictionary());
    	if (count_words == 0){
    		AddMessage(form_id, FacesMessage.SEVERITY_ERROR, "Нет слов в этом словаре.", "");
   		 	return null;
    	};
    	
    	Integer count_input_words = DAO_Input.NumberOfInputWordsFromDictionaryWithParams(form_check.getSelectedDictionary(), true, true, true, false, currentUser.getUser());
    	if (count_input_words == 0){
    		 AddMessage(form_id, FacesMessage.SEVERITY_ERROR, "Все слова из этого словаря пройдены.", "");
    		 return null;
    	};
    	
    	form_check.Save();

    	
    	SetNewPass(check);
        
    	//System.out.println("1 check " + check);
    	//System.out.println("1 check == null " + (check == null));
    	//System.out.println("1 check.getCheckID() " + check.getCheckID());
    	//System.out.println("1 check.getCheckDict() == null " + (check.getCheckDict() == null));
    	//System.out.println("1 check.getDictID() " + check.getDictID());
    	
    	Set_Form_Input(check);
    	
    	Set_Access_Control_Check(check);
    	
    	state="input";
        return null;              
    }
    
    
    private void Set_Access_Control_Check(Check check){
    	access_control.setCheck(check);
    	access_control.Update_Access_Check();
    	
    }
    
    public String ActionStartNewPass(Check check){
    	
    	SetNewPass(check);
        
    	Set_Form_Input(check);
    	
    	Set_Access_Control_Check(check);
    	
    	
        state="input";

        return null;              
    }   
    
    public String ActionContinueCheck(Check check){

    	Set_Form_Input(check);
    	Set_Access_Control_Check(check);
    	
    	state="input";
        return null;              
    }   
    
}
