package SpellRefiner;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.Part;

@Named(value = "form_upload_text_file")
@ViewScoped
public class Form_Upload_Text_File implements Serializable {
    private static final String page_success = null;//"success";
    private static final String page_error = "error";
    
    private Part file;
    private String fileContent;    
    
    @PostConstruct
    public void Init(){
    }
    
    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
    
    public String Upload() {
        try {
            fileContent = new Scanner(file.getInputStream()).useDelimiter("\\A").next();
        } catch (IOException e) {
            return page_error;    
        }
        
        return page_success; 
    }
    
    public String getFileContent(){
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public void ValidateFile(FacesContext ctx, UIComponent comp, Object value) {
        
        List<FacesMessage> msgs = new ArrayList<FacesMessage>();
        
        Part file1 = (Part)value;
        if (file1.getSize() > 1000) {
            msgs.add(new FacesMessage("file too big"));
        }
        
        if (!"text/plain".equals(file1.getContentType())) {
            msgs.add(new FacesMessage("not a text file"));
        }
        
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
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