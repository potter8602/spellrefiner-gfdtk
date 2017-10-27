package SpellRefiner;

import java.io.Serializable;
import javax.inject.Named;
import javax.ejb.EJB;
import java.util.List;
import java.util.ArrayList;
//import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import java.util.Map;
//import javax.faces.application.FacesMessage;
//import javax.faces.context.FacesContext;


@Named(value = "form_my_checks")
@ViewScoped
public class Form_My_Checks implements Serializable {
    
    @EJB
    private DAO_Interface_Check DAO_Check;  
    
    private List<Check> List_Check;
    
    @Inject
    private CurrentUser currentUser;      
    
    private Boolean onlyDeleted;
    private Boolean onlyUnfinished;
    
    
    public Form_My_Checks(){
       List_Check = new ArrayList<Check>();
       onlyDeleted = false;
       onlyUnfinished = true;
    }
    
    @PostConstruct
    public void Init(){        
        UpdateList();
    }
    
    public List<Check> getList_Check(){
        return List_Check;
    }
    
    public Boolean getOnlyDeleted(){
        return onlyDeleted;
    }
    
    public void setOnlyDeleted(Boolean onlyDeleted){
        this.onlyDeleted = onlyDeleted;
    }

    public Boolean getOnlyUnfinished(){
        return onlyUnfinished;
    }
    
    public void setOnlyUnfinished(Boolean onlyUnfinished){
        this.onlyUnfinished = onlyUnfinished;
    }
    
    
    
    public void UpdateList_Info(List<Check> list){
        
        for (Check check: list) {
            Map<String, Object> Info = DAO_Check.GetCheckInfo(check);
            check.setNumberOfCheckedWords((Integer)Info.get("NumberOfCheckedWords"));
        } 
                
    }
    
    public void UpdateList(){
        List_Check = DAO_Check.MyChecks(currentUser.getUser(), onlyDeleted, onlyUnfinished);
        UpdateList_Info(List_Check);
                
    }
    
    public void Delete(Check check){
        DAO_Check.Delete(check);
        List_Check.remove(check);
    }     
    
    public void Update(Check check){
    	
        if (check.getCheckName().isEmpty()){
        	AddMessage("form_my_checks", FacesMessage.SEVERITY_ERROR, "Название: Введите значение поля", "");
        	return;
        }
    	
        DAO_Check.Update(check);
    }    
    
    public void MarkDeleted(Check check){
        check.setCheckDeleted(Boolean.TRUE);
        DAO_Check.Update(check);
        UpdateList();
    }        
    
    public void UnMarkDeleted(Check check){
        check.setCheckDeleted(Boolean.FALSE);
        DAO_Check.Update(check);
        UpdateList();
    }        
    
    private void AddMessage(String clientId, FacesMessage.Severity severity, String summary, String detail){
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(severity, summary, detail));
    }
    
    
    
}
