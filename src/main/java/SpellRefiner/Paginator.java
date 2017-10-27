package SpellRefiner;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;


public class Paginator implements Serializable {

    public List<PaginatorItem> list;
    public Integer CurrentPage = 0;
    public Integer NumberOfPages = 0;
    public Integer RecordsOnPage = 0;
    
    public String FirstPageName = "В начало";
    public String LastPageName = "В конец";
    public String PreviousPageName = "<";
    public String NextPageName = ">";
    public String PageDelimeterName = "...";
    
    public Boolean FirstPageEnabled = true;
    public Boolean LastPageEnabled = true;
    public Boolean PreviousPageEnabled = true;
    public Boolean NextPageEnabled = true;

    public Boolean FirstPageVisible = true;
    public Boolean LastPageVisible = true;
    public Boolean PreviousPageVisible = true;
    public Boolean NextPageVisible = true;
    
    public Paginator(){
        list = new ArrayList<PaginatorItem>();
        
    }
    
    public List<PaginatorItem> getList(){
        return list;
    }    
    
    public String getFirstPageName(){
        return FirstPageName;
    }              
    
    public String getLastPageName(){
        return LastPageName;
    }              

    public String getPreviousPageName(){
        return PreviousPageName;
    }              

    public String getNextPageName(){
        return NextPageName;
    }       
    
    
    public Boolean getFirstPageEnabled(){
        return FirstPageEnabled;
    }              
    
    public Boolean getLastPageEnabled(){
        return LastPageEnabled;
    }              

    public Boolean getPreviousPageEnabled(){
        return PreviousPageEnabled;
    }              

    public Boolean getNextPageEnabled(){
        return NextPageEnabled;
    }       
    

    public Boolean getFirstPageVisible(){
        return FirstPageVisible;
    }              
    
    public Boolean getLastPageVisible(){
        return LastPageVisible;
    }              

    public Boolean getPreviousPageVisible(){
        return PreviousPageVisible;
    }              

    public Boolean getNextPageVisible(){
        return NextPageVisible;
    }       

   public Integer getCurrentPage(){
        return CurrentPage;
    }              
    
   public void setCurrentPage(Integer currentPage){
        this.CurrentPage = currentPage;
    }              

   public Integer getNumberOfPages(){
        return NumberOfPages;
    }              
    
   public void setNumberOfPages(Integer numberOfPages){
        this.NumberOfPages = numberOfPages;
    }              
    
    
    public void SetNumberOfPagesByRecordsCount(int RecordsCount){    
        NumberOfPages = 0;
        
        if (RecordsOnPage == 0){ 
            return;
        };
        
        if (RecordsCount == 0){ 
            return;
        };
        
        NumberOfPages = (int) RecordsCount / RecordsOnPage;
        if (NumberOfPages * RecordsOnPage < RecordsCount){
            NumberOfPages = NumberOfPages + 1;
        }
        
        //if (CurrentPage > NumberOfPages){ 
        //    CurrentPage = NumberOfPages;
        //};        
        
    }
    
    private void AddPage(int pageNumber){    
        PaginatorItem item = new PaginatorItem(this);
        item.PageNumber = pageNumber;
        item.Enabled = true;
        item.Name = item.PageNumber.toString();
        list.add(item);
    }

    private void AddPageDelimeter(){    
        PaginatorItem item = new PaginatorItem(this);
        item.PageNumber = 0;
        item.PageDelimeter = true;
        item.Enabled = false;
        item.Name = PageDelimeterName;
        list.add(item);
    }
    
    public Integer getFirstRecord(){
        if (CurrentPage == 0){
            return 0;
        }
        
        return (CurrentPage - 1) * RecordsOnPage;
    }              
    
    
    /*
   public PaginatorItem getCurrentItem(){
        return CurrentItem;
    }              
    
   public void setCurrentItem(PaginatorItem item){
        this.CurrentItem = item;
    }              
   
    private void SetListAttributes(){   
        for (PaginatorItem item: list) {
            if (item.FirstPage){
                item.Name = FirstPageName;
            }else if (item.LastPage){
                item.Name = LastPageName;
            }
            else if (item.PageDelimeter){
                item.Name = PageDelimeterName;                
            }    
            else if (item.NextPage){             
                item.Name = NextPageName;                
            }    
            else if (item.PreviousPage){
                item.Name = PreviousPageName;                
            }
            else{
                item.Name = item.PageNumber.toString();
            }
	}    
        
        for (PaginatorItem item: list) {
            item.Enabled = false;
            if (item.FirstPage){
                item.Enabled = NumberOfPages > 1;
            }else if (item.LastPage){
                item.Enabled = NumberOfPages > 1;
            }
            else if (item.PageDelimeter){
                item.Enabled = false;
            }    
            else if (item.NextPage){             
                item.Enabled = CurrentPage < NumberOfPages;
            }    
            else if (item.PreviousPage){
                item.Enabled = CurrentPage > 1;
            }
            else{
                item.Enabled = true;
            }
	}    
        
        
    }
    
    
    public void Update(int RecordsCount){    
        PaginatorItem item;
        
        list.clear();     
        
        
        SetNumberOfPages(RecordsCount);        
        
        if (NumberOfPages > 1){
            item = new PaginatorItem(0);
            item.FirstPage = true;
            list.add(item);
        }    
        
        if (NumberOfPages > 1){
            item = new PaginatorItem(0);
            item.PreviousPage = true;
            list.add(item);
        }    
        
        if (NumberOfPages <= 5){
            for (int i = 1; i < NumberOfPages; i++) {
                item = new PaginatorItem(i);
                list.add(item);
            }
        }
        
        if (NumberOfPages > 10){
            for (int i = 1; i < 3; i++) {
                item = new PaginatorItem(i);
                list.add(item);
            }

            item = new PaginatorItem(0);
            item.PageDelimeter = true;
            list.add(item);
            
            item = new PaginatorItem(10);
            
            list.add(item);
        }
        
        if (NumberOfPages > 1){
            item = new PaginatorItem(0);
            item.NextPage = true;
            list.add(item);
        }    
        
        
        if (NumberOfPages > 1){
            item = new PaginatorItem(0);
            item.LastPage = true;
            list.add(item);
        }   
        
        SetListAttributes();
        
        
    }
    
    */
    
    
    /*
    private void SetListAttributes(){   
        for (PaginatorItem item: list) {
            if (item.PageDelimeter){
                item.Name = PageDelimeterName;
            }    
            else{
                item.Name = item.PageNumber.toString();
            }
	}    
        
        for (PaginatorItem item: list) {
            item.Enabled = false;
            if (item.PageDelimeter){
                item.Enabled = false;
            }    
            else{
                item.Enabled = true;
            }
	}    
        
        
    }
    */
    
    private void SetAttributes(){   
        
        FirstPageEnabled = NumberOfPages > 1;
        LastPageEnabled = NumberOfPages > 1;
        PreviousPageEnabled = CurrentPage >1;
        NextPageEnabled = CurrentPage < NumberOfPages;

        //FirstPageVisible = true;
        //LastPageVisible = true;
        //PreviousPageVisible = true;
        //NextPageVisible = true;
        
        
    }
    
    private void UpdateList(){   
        list.clear();   

        if (CurrentPage > NumberOfPages){
            CurrentPage = NumberOfPages;
        }            
        
        if (NumberOfPages == 0){
            CurrentPage = 0;
            return;
        }
        
        if (NumberOfPages != 0){
            if (CurrentPage == 0){
                CurrentPage = 1;
            }            
        }

        
        List<Integer> list_number = new ArrayList<Integer>();
        
        if (NumberOfPages > 0){
            list_number.add(1);
        };    
        
        
        if (NumberOfPages <= 5){
            for (int i = 2; i < NumberOfPages; i++) {
                list_number.add(i);
            }
        }
        
        if (NumberOfPages > 5){
            for (int i = 2; i <= 3; i++) {
                list_number.add(i);
            }
            
            if (NumberOfPages > 10){
                for (int i = 10; i < 30 && i < NumberOfPages; i = i + 10) {
                    list_number.add(i);
                }
            }    
            
        }
        
        if (NumberOfPages > 1){
            list_number.add(NumberOfPages);
        };  

        if (CurrentPage != 0){
            if (!list_number.contains(CurrentPage)){
                list_number.add(CurrentPage);
            }            
        }
        
        //list_number.add(CurrentPage);

        list_number.sort(null);

        
        
        /*
        System.out.println("--------------------Paginator.UpdateList()");        
        for (Integer i: list_number) {
            System.out.println("--------------------AddPage(i) " + i);        
            AddPage(i);
	} 
        */
        
        for (int i = 0; i < list_number.size(); i++) {
            AddPage(list_number.get(i));   
            
            if (i < list_number.size() - 1){
                if ((list_number.get(i) + 1) != list_number.get(i + 1)){
                    AddPageDelimeter();                                            
                }                
            }    
        }

        
        /*
        
        list.clear();   
        
        if (NumberOfPages == 0){
            CurrentPage = 0;
            return;
        }
        
        if (NumberOfPages > 0){
            AddPage(1);
        };    
        
        
        if (NumberOfPages <= 5){
            for (int i = 2; i < NumberOfPages; i++) {
                AddPage(i);
            }
        }
        
        if (NumberOfPages > 5){
            for (int i = 2; i <= 3; i++) {
                AddPage(i);
            }
            
            if (NumberOfPages > 10){
                AddPageDelimeter();                        
            
                for (int i = 10; i < NumberOfPages; i = i + 10) {
                    AddPage(i);                
                }
            }    
            
        }
        
        
        if (NumberOfPages > 10){
            AddPageDelimeter();                        
        }
        
        if (NumberOfPages > 1){
            AddPage(NumberOfPages);
        };  
        
        
        for (PaginatorItem item: list) {
            if (!item.PageDelimeter){
                list_number.add(item.PageNumber);
            }    
	}               
        
        if (CurrentPage != 0){
            if (!list_number.contains(CurrentPage)){
                
            }            
        }
                
        */
        
        
    }
    
    
    
    public void Update(){    
        //SetNumberOfPagesByRecordsCount(RecordsCount);                
        UpdateList();
        SetAttributes();
        
    }
    
    
    public void GotoPage(Integer pageNumber){
        CurrentPage = pageNumber;
        Update();
    }

    public void GotoFirstPage(){
        CurrentPage = 0;
        if  (NumberOfPages > 1){
            CurrentPage = 1;
        }   
        Update();
    }
    
    public void GotoLastPage(){
        CurrentPage = NumberOfPages;
        Update();
    }
    
    public void GotoNextPage(){
        if  (CurrentPage < NumberOfPages){
            CurrentPage = CurrentPage + 1;
        }    
        Update();
    }
    
    public void GotoPreviousPage(){
        if  (CurrentPage > 1){
            CurrentPage = CurrentPage - 1;
        }    
        Update();
    }
    
    
}
