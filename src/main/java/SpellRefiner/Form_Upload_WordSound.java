package SpellRefiner;

//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//import java.util.Scanner;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.ejb.EJB;
//import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
//import javax.inject.Inject;
import javax.servlet.http.Part;
//import sun.misc.IOUtils;
//import org.apache.commons.io;



@Named(value = "form_upload_wordSound")
@ViewScoped
//@RequestScoped
public class Form_Upload_WordSound implements Serializable {
    private static final String page_success = null;//"success";
    private static final String page_error = "error";
    Integer MaxLength_WordSound = 30000000;
    
    @EJB
    private DAO_Interface_Word DAO_Word;        
    
    private Word word = new Word();   
    
    
    private Part file;
    
    
    
    @PostConstruct
    public void Init(){
    }
    
    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        //System.out.println("--------------------setFile " + file);
        this.file = file;
    }
    
    

    public void ValidateFile(FacesContext ctx, UIComponent comp, Object value) {
        //System.out.println("--------------------ValidateFile " + value);
        if (value == null){ 
            return;
        };
        
        List<FacesMessage> msgs = new ArrayList<FacesMessage>();
        
        Part file1 = (Part)value;
        if (file1.getSize() > MaxLength_WordSound) {
            msgs.add(new FacesMessage("file too big"));
            throw new ValidatorException(msgs);
        }
    }    
    
    public Word getWord(){
        return word;
    }

    public void setWord(Word word){
        //System.out.println("--------------------setWord " + word);
        this.word = word;
    }    


    
    
    public byte[] FromInputStreamToByteArray(InputStream input){
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[10000];
        
        try {
            for (int length = 0; (length = input.read(buffer)) > 0;) 
            output.write(buffer, 0, length);
            return output.toByteArray();    
        }        
        catch(Exception e){
            return null;               
        }                 
                
    }
    
    
            
    
    
    public String Upload(){
        try {
//            System.out.println("--------------------word " + word);
//            System.out.println("--------------------wordID " + word.getWordID());            
//            
//            System.out.println("--------------------file " + file);
            //byte[] bytes = IOUtils.readFully(file.getInputStream(), -1, true);            
            
            byte[] bytes = FromInputStreamToByteArray(file.getInputStream());
            word.setWordSound(bytes);
            word.setWordHasSound(Boolean.TRUE);
            DAO_Word.Update(word);
        }
        catch(Exception e){
            //System.out.println("--------------------Exception " + e);
            return page_error;               
        }                 
        
        return page_success;                
    }    
    
    
}


/*
public void validateFile(FacesContext ctx,
                         UIComponent comp,
                         Object value) {
  List<FacesMessage> msgs = new ArrayList<FacesMessage>();
  Part file = (Part)value;
  if (file.getSize() > 1024) {
    msgs.add(new FacesMessage("file too big"));
  }
  if (!"text/plain".equals(file.getContentType())) {
    msgs.add(new FacesMessage("not a text file"));
  }
  if (!msgs.isEmpty()) {
    throw new ValidatorException(msgs);
  }
}
*/
/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
