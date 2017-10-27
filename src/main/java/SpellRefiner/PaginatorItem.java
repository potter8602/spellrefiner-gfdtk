package SpellRefiner;
import java.io.Serializable;

public class PaginatorItem implements Serializable {
    public Integer PageNumber = 0;
    public Boolean PageDelimeter = false;
    public Boolean Enabled = false;    
    //public Boolean FirstPage = false;    
    //public Boolean LastPage = false;    
    //public Boolean CurrentPage = false;    
    //public Boolean PreviousPage = false;  
    //public Boolean NextPage = false;  
    //public Boolean Visible = false;  
    public Paginator paginator;
    
    public String Name;
    
    public PaginatorItem(Paginator paginator){
        this.paginator = paginator;
    }
    
    public String getName(){
        return Name;
    }    
    
    public Integer getPageNumber(){
        return PageNumber;
    }    

    public Boolean getPageDelimeter(){
        return PageDelimeter;
    }    

    /*
    public Boolean getLastPage(){
        return LastPage;
    }    

    */
    public Boolean getEnabled(){
        return Enabled;
    } 
    
    public Boolean getIsCurrentPage(){
        if (PageDelimeter){
            return false;
        }
        
        if (paginator.CurrentPage == PageNumber){
            return true;
        }
        
        return false;
    } 

    public String getStyleClass(){
        if (getIsCurrentPage()){
            return "paginator-item-current-page";
        }
        
        if (PageDelimeter){
            return "paginator-item-page-delimeter";
        }
        
        return "paginator-item";
    } 
    
}
