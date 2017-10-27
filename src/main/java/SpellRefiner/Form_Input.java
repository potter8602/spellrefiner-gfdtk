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

@Named(value = "form_input")
@ViewScoped
public class Form_Input implements Serializable  {
        
    @Inject
    private CurrentUser currentUser;          
    
    @EJB
    private DAO_Interface_Check DAO_Check;  
    
    @EJB
    private DAO_Interface_Input DAO_Input;  
    
    private Check check = null;   
    //private Input input = new Input();
    private Input input = null;
    
    private Word word = null;   
    
    //filters
    private boolean WithSound = true;
    private boolean WithoutSound = true;
    
    private boolean ExceptWordsInFinishedChecks = false;
    private boolean ExceptWordsInFinishedChecksSameDictionary = true;
    
    //sort
    private boolean AscendingOrder = true;    
    private String WordsOrder; //wordID, wordSpelling, wordRandomNumber
    
    private boolean Training = false;
    
    
    //private boolean CurrentPassClosed;
    //private boolean CurrentPassAllWordDone;
    
    private Integer NumberOfWordsForInput = 0;
    private Integer NumberOfInputWordsInCurrentPass = 0;   

    private Integer NumberOfPassedWordInCurrentPass = 0;
    
    private boolean NoNextInput = true;
    private boolean NoPreviousInput = true;
    
    public Form_Input() {
        WordsOrder = "wordRandomNumber";
        //NumberOfWordsForInput = 0;
        NewInput();
    }
    
    @PostConstruct
    public void Init(){
    	//Update_CurrentPassAllWordDone();
    	
    	Update_NumberOfWordsForInput();
    	Update_NumberOfInputWordsInCurrentPass();
    	Update_NoNextInput();
    	Update_NoPreviousInput();
    	Update_NumberOfPassedWordInCurrentPass();
    	
    	UpdateForm();
        
    }
    
    public Check getCheck(){
        return check;
    }

    public void setCheck(Check check){
        this.check = check;
        
        Update_NumberOfWordsForInput();
        Update_NumberOfInputWordsInCurrentPass();
        Update_NumberOfPassedWordInCurrentPass();
    }   
    
    public Word getWord(){
        return word;
    }
    
    public void setWord(Word word){
        this.word = word;
    }   
    
    
    public Input getInput(){
        return input;
    }
    
    //sort
    public boolean getAscendingOrder(){
        return AscendingOrder;
    }

    public void setAscendingOrder(boolean order){
        this.AscendingOrder = order;
        GetNextWord();
        UpdateForm();
    }
    
    public boolean getDescendingOrder(){
        return !AscendingOrder;
    }

    public void setDescendingOrder(boolean order){
        this.AscendingOrder = !order;
        GetNextWord();
        UpdateForm();
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
        GetNextWord();
        UpdateForm();
    } 
    
    public boolean getWithSound(){        
        return WithSound;
    }

    //filters
    public void setWithSound(boolean withSound){
        this.WithSound = withSound;
        GetNextWord();
        Update_NumberOfWordsForInput();
        UpdateForm();
    }

    public boolean getWithoutSound(){        
        return WithoutSound;
    }

    public void setWithoutSound(boolean withoutSound){
        this.WithoutSound = withoutSound;
        GetNextWord();
        Update_NumberOfWordsForInput();
        UpdateForm();
    }
    
    
    
    public boolean getExceptWordsInFinishedChecks(){        
        return ExceptWordsInFinishedChecks;
    }

    public void setExceptWordsInFinishedChecks(boolean ExceptWordsInFinishedChecks){
        this.ExceptWordsInFinishedChecks = ExceptWordsInFinishedChecks;
        GetNextWord();
        Update_NumberOfWordsForInput();
        UpdateForm();
    }
    
    
    public boolean getExceptWordsInFinishedChecksSameDictionary(){        
        return ExceptWordsInFinishedChecksSameDictionary;
    }

    public void setExceptWordsInFinishedChecksSameDictionary(boolean ExceptWordsInFinishedChecksSameDictionary){
        this.ExceptWordsInFinishedChecksSameDictionary = ExceptWordsInFinishedChecksSameDictionary;
        GetNextWord();
        Update_NumberOfWordsForInput();
        UpdateForm();
    }

    public boolean getTraining(){        
        return Training;
    }

    public void setTraining(boolean Training){
        this.Training = Training;
        if (Training){
        	if (input!=null){
        		input.setUncertain(true);
        	}	
        }
    }
    
    public Integer getNumberOfWordsForInput(){        
        return NumberOfWordsForInput;
    }
    
    
    public Integer getNumberOfInputWordsInCurrentPass(){        
        return NumberOfInputWordsInCurrentPass;
    }
    
    
    public boolean getNoNextInput(){        
        return NoNextInput;
    }

    public boolean getNoPreviousInput(){        
        return NoPreviousInput;
    }
    
    
    //public boolean getCurrentPassClosed(){
    //    return CurrentPassClosed;
    //}
    
    //public void setCurrentPassClosedSound(boolean currentPassClosed){
    //    this.CurrentPassClosed = currentPassClosed;
    //}
    
    //public boolean getCurrentPassAllWordDone(){
    //    return CurrentPassAllWordDone;
    //}
    
    //public void Update_CurrentPassAllWordDone(){
    //	CurrentPassAllWordDone = (GetNumberOfWordsForInput()!=GetNumberOfInputWordsInCurrentPass());
    //}
    
    public void Update_NoNextInput(){
    	
    	NoNextInput = false;
    	
        if (check !=null){
        	if (input.getInputID() == null) {
        		NoNextInput = true;
        		
        	}
        	else{
            	List<Input> list = DAO_Input.getNextInput(check, check.getCheckPass(), input.getInputID());
            	
                if (list.isEmpty()){
                	NoNextInput = true;
                }        		
        	}
        };
    	
    	
    }
    
    public void Update_NoPreviousInput(){
    	
    	NoPreviousInput = false;
        if (check !=null){
        	List<Input> list = DAO_Input.getPreviousInput(check, check.getCheckPass(), input.getInputID());
        	
            if (list.isEmpty()){
            	NoPreviousInput = true;
            }
        };
    	
    }
        
    
    public void Update_NumberOfWordsForInput(){
    	NumberOfWordsForInput = GetNumberOfWordsForInput();
    	//System.out.println("Update_NumberOfWordsForInput--------------------NumberOfWordsForInput = " + NumberOfWordsForInput);
    }
    
    public void Update_NumberOfInputWordsInCurrentPass(){
    	NumberOfInputWordsInCurrentPass = GetNumberOfInputWordsInCurrentPass();
    	//System.out.println("Update_NumberOfInputWordsInCurrentPass--------------------NumberOfInputWordsInCurrentPass = " + NumberOfInputWordsInCurrentPass);
    }
    
    
    private void AddMessage(String clientId, FacesMessage.Severity severity, String summary, String detail){
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(severity, summary, detail));
    }
    
    
    public void GetNextWord(){
    	
    	//System.out.println("--------------------GetNextWord 1 check = " + check);
    	
        word = null;
        if (check !=null){
        	//System.out.println("--------------------GetNextWord check.getCheckPass() = " + check.getCheckPass());
        	
            if (check.getCheckPass() == 1){
                List<Word> list = DAO_Input.getNextInputWordFromDictionary(check, WordsOrder, AscendingOrder, WithSound, WithoutSound, ExceptWordsInFinishedChecks, ExceptWordsInFinishedChecksSameDictionary);
                if (!list.isEmpty()){
                    word = list.get(0);
                }
            };
            
            if (check.getCheckPass() > 1){
                List<Word> list = DAO_Input.getNextInputWordFromLastInput(check, check.getCheckPass());
                
                //System.out.println("--------------------GetNextWord list.isEmpty() = " + list.isEmpty());
                
                if (!list.isEmpty()){
                    word = list.get(0);
                }
                
            }
        };        
        
        //System.out.println("--------------------GetNextWord 2 word = " + word);
        
    }

    
    public void UpdateForm(){
        //GetNextWord();
        //Update_NumberOfWordsForInput();
    }
    
    public void NewInput(){
        input = new Input();
        
        if (Training){
        	if (check!=null){
        		if (check.getCheckPass()==1){
        			input.setUncertain(true);
        		}
        	}        		
        }
        
    }      
    
    
    public void StartNewPass(){
        
        if (check == null){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Не выбрана проверка", "");
            return;
        }                
        
        if (GetNumberOfDictionaryWords() == 0){
            AddMessage("form_input_check", FacesMessage.SEVERITY_ERROR, "Нет слов в словаре", "");
            return;
        }                
        
        setTraining(false);
        
        check.setCheckPass(check.getCheckPass() + 1);
        DAO_Check.Update(check);
        
        GetNextWord();
        //input = new Input();
        NewInput();
        
        Update_NumberOfWordsForInput();    
        Update_NumberOfInputWordsInCurrentPass();
        
    	Update_NoNextInput();
    	Update_NoPreviousInput();        
    	Update_NumberOfPassedWordInCurrentPass();
        
        UpdateForm();
        //Update_CurrentPassAllWordDone();
        
    }    
    
    public void GotoNextWord(){
    	//System.out.println("--------------------GotoNextWord");
    	
        
        if (check == null){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Не выбрана проверка", "");
            return;
        }                
        
        GetNextWord();
        //input = new Input();
        NewInput();
        
        NoNextInput = true;
        NoPreviousInput = false;
        
        
        UpdateForm();
        
    }    
    
    
    public void FinishCheck(){
        
        if (check == null){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Не выбрана проверка", "");
            return;
        }               
        
        
        //check.setCheckPass(-1);
        check.setCheckFinished(true);
        DAO_Check.Update(check);
        
        Update_NumberOfWordsForInput();
        Update_NumberOfInputWordsInCurrentPass();
        
    	Update_NoNextInput();
    	Update_NoPreviousInput();
    	Update_NumberOfPassedWordInCurrentPass();
        
        UpdateForm();
        
    }    
    
    
    public Integer SaveNew(){
        
            input.setUserID(currentUser.getUser().getUserID());
            input.setCheckID(check.getCheckID());
            input.setDictID(check.getDictID());
            input.setPassNumber(check.getCheckPass());
            input.setWordID(word.getWordID());
            
            input.setSkipped(Boolean.FALSE);
            input.setWordSpelling(word.getWordSpelling());
            
            input.setError(!input.getWordSpelling().equals(input.getWordInput()));
        
            Integer id = DAO_Input.SaveNew(input);
            input.setInputID(id);
            
            return id;
        
    }   
    
    
    public void Save(){
        
        if (word == null){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Не выбрано слово", "");
            return;
        }                
        
        if (check == null){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Не выбрана проверка", "");
            return;
        }                

        if (input.getWordInput().isEmpty()){
            AddMessage("form_input", FacesMessage.SEVERITY_ERROR, "Введите написание слова", "");
            return;
        }                
        
        //System.out.println("--------------------selectedDictionary = " + selectedDictionary);
        
        if (input.getInputID() == null){
            SaveNew();
            
            /*
            input.setUserID(currentUser.getUser().getUserID());
            input.setCheckID(check.getCheckID());
            input.setDictID(check.getDictID());
            input.setPassNumber(check.getCheckPass());
            input.setWordID(word.getWordID());
            
            input.setSkipped(Boolean.FALSE);
            input.setWordSpelling(word.getWordSpelling());
            
            input.setError(!input.getWordSpelling().equals(input.getWordInput()));
        
            Integer id = DAO_Input.SaveNew(input);
            input.setInputID(id);
            */        
        }
        else{
            input.setError(!input.getWordSpelling().equals(input.getWordInput()));
            DAO_Input.Update(input);
        }
        
        Update_NumberOfInputWordsInCurrentPass();
        Update_NumberOfPassedWordInCurrentPass();
        
        //Update_CurrentPassAllWordDone();
        UpdateForm();
        
    }   
    
    public void SkipWord(){
        
        
        if (check == null){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Не выбрана проверка", "");
            return;
        }                
        
        if (word == null){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Не выбрано слово", "");
            return;
        }                
        
        
        if (input.getInputID() == null){
            SaveNew();
            input.setSkipped(Boolean.TRUE);
            DAO_Input.Update(input);
            
        }
        else{
            
            input.setSkipped(Boolean.TRUE);
            input.setError(!input.getWordSpelling().equals(input.getWordInput()));
            DAO_Input.Update(input);
        }
        
        //GotoNextWord();
        
        //Update_CurrentPassAllWordDone();
        
        GetNextWord();
        //input = new Input();
        NewInput();
        
        Update_NumberOfInputWordsInCurrentPass();
        Update_NumberOfPassedWordInCurrentPass();
        
        NoNextInput = true;
        NoPreviousInput = false;
        
        
        UpdateForm();
        
    }    
    
    
    public Integer GetNumberOfDictionaryWords(){
        if (check != null){
            //List<Word> list = DAO_Input.getInputWordsFromDictionary(check.getCheckDict());            
            //return list.size();
            Integer count = DAO_Input.NumberOfInputWordsFromDictionary(check.getCheckDict());
            return count;
            
        }
        
        return 0;
    }

    public Integer GetNumberOfInputWordsInCurrentPass(){
    	//System.out.println("--------------------GetNumberOfInputWordsInCurrentPass 1 ");
        if (check != null){
            
            Integer count = DAO_Input.NumberOfInputWordsByPassNumber(check, check.getCheckPass());
            //System.out.println("--------------------GetNumberOfInputWordsInCurrentPass 2 ");
            return count;
        }
        
        return 0;
    }
    
    public Integer GetNumberOfWordsForInput(){
    	//System.out.println("--------------------GetNumberOfWordsForInput 1 ");
        if (check != null){
            
            if (check.getCheckPass() == 1){
                //Integer count = DAO_Input.NumberOfInputWordsFromDictionary(check.getCheckDict());
            	Integer count = DAO_Input.NumberOfInputWordsFromDictionaryWithParams(check.getCheckDict(), WithSound, WithoutSound, ExceptWordsInFinishedChecks, ExceptWordsInFinishedChecksSameDictionary, currentUser.getUser());
            	//System.out.println("--------------------GetNumberOfWordsForInput 2 ");
                return count;
            }    
            
            if (check.getCheckPass() > 1){
                Integer count = DAO_Input.NumberOfInputWordsFromLastInput(check, check.getCheckPass() - 1);
                return count;
            }    
            
            
        }
        
        return 0;
    }

    public void GetPreviousInput(){
    	
    	//System.out.println("--------------------GetPreviousInput check = " + check);
    	
        if (check !=null){
        	List<Input> list = DAO_Input.getPreviousInput(check, check.getCheckPass(), input.getInputID());
        	
            if (!list.isEmpty()){
                input = list.get(0);
                word = input.getInputWord();
            }
        };
        
        //System.out.println("--------------------GetPreviousInput input = " + input);
        
    }
    
    public void GotoPreviousInput(){
    	//System.out.println("--------------------GotoPreviousInput");
    	
        
        if (check == null){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Не выбрана проверка", "");
            return;
        }                
        
        GetPreviousInput();
        
    	Update_NoNextInput();
    	Update_NoPreviousInput();
        
        UpdateForm();
        
    }    
    
    public void GetNextInput(){
    	
    	//System.out.println("--------------------GetPreviousInput check = " + check);
    	
        if (check !=null){
        	List<Input> list = DAO_Input.getNextInput(check, check.getCheckPass(), input.getInputID());
        	
            if (!list.isEmpty()){
                input = list.get(0);
                word = input.getInputWord();
            }
        };
        
        //System.out.println("--------------------GetNextInput input = " + input);
        
    }
    
    
    public void GotoNextInput(){
    	//System.out.println("--------------------GotoNextInput");
    	
        
        if (check == null){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Не выбрана проверка", "");
            return;
        }                
        
        GetNextInput();
        
    	Update_NoNextInput();
    	Update_NoPreviousInput();
        
        UpdateForm();
        
    }    
    

    public void UnSkipWord(){
        
        
        if (check == null){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Не выбрана проверка", "");
            return;
        }                
        
        if (word == null){
            AddMessage(null, FacesMessage.SEVERITY_ERROR, "Не выбрано слово", "");
            return;
        }                
        
        
        if (input.getInputID() == null){
        }
        else{
            
            input.setSkipped(Boolean.FALSE);
            input.setError(!input.getWordSpelling().equals(input.getWordInput()));
            DAO_Input.Update(input);
        }        
        
        
        //GotoNextWord();
        
        //Update_CurrentPassAllWordDone();
        
        //GetNextWord();
        //input = new Input();
        
        Update_NumberOfInputWordsInCurrentPass();
        Update_NumberOfPassedWordInCurrentPass();
        
        UpdateForm();
        
    }    

    public void SetUncertain(){
    		input.setUncertain(true);
    }   
    

    public Integer GetNumberOfPassedWordInCurrentPass(){
    	
    	//System.out.println("--------------------GetNumberOfPassedWordInCurrentPass 1 ");
        if (check != null){
        	Integer count = DAO_Input.NumberOfPassedWordsByPassNumber(check, check.getCheckPass());
            //System.out.println("--------------------GetNumberOfPassedWordInCurrentPass 2   count = " + count);
            return count;
        }
        
        return 0;
    }

    public void Update_NumberOfPassedWordInCurrentPass(){   	
    	
    	NumberOfPassedWordInCurrentPass = GetNumberOfPassedWordInCurrentPass();
    	//System.out.println("Update_NumberOfPassedWordInCurrentPass--------------------NumberOfPassedWordInCurrentPass = " + NumberOfPassedWordInCurrentPass);
    }
    
    public Integer getNumberOfRemainingWordsInCurrentPass(){
    	return NumberOfWordsForInput - NumberOfPassedWordInCurrentPass;
    }
    
    public Integer getNumberOfPassedWordInCurrentPass(){        
        return NumberOfPassedWordInCurrentPass;
    }
    
    
}
