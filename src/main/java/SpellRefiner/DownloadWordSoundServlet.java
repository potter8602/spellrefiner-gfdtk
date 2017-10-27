package SpellRefiner;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
//import java.io.File;
//import java.io.FileInputStream;
import java.io.IOException;
//import java.util.List;
import javax.ejb.EJB;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.PersistenceUnit;
//import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "downloadWordSoundServlet",
        urlPatterns = "/word_sounds"
)
public class DownloadWordSoundServlet extends HttpServlet {
     private static final int DEFAULT_BUFFER_SIZE = 10240; 
     
    @EJB
    private DAO_Interface_Word DAO_Word;        
     
    
//    @PersistenceUnit(unitName = "SpellRefiner")
//    private EntityManagerFactory emf;    
     

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        
        String param_wordID = request.getParameter("wordID");
        
        //System.out.println("--------------------param_wordID " + param_wordID);
        
        if (param_wordID == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); 
            return;
        }
        
        if (param_wordID == "") {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); 
            return;
        }
        
        Integer wordID = 0;
        
        try{
            wordID = Integer.valueOf(param_wordID);
        }
        catch(Exception e){
            response.sendError(HttpServletResponse.SC_NOT_FOUND); 
            return;
        }
        
        
//        EntityManager em = emf.createEntityManager();
//        
//        Query query = em.createNamedQuery("Word.GetWordSoundByWordID");
//        query.setParameter("wordID", wordID);
//        List<byte[]> list = query.getResultList();
//
//        if (list.isEmpty()){
//            response.sendError(HttpServletResponse.SC_NOT_FOUND); 
//            return;
//        };
//        
//        byte[] wordSound = list.get(0);
        
        
        Word word = DAO_Word.GetWordByID(wordID);
        
//        word = new Word();
//        if(false){
        
        //System.out.println("--------------------word " + word);
        if (word == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); 
            return;
        }
        
        //System.out.println("--------------------param_wordID " + param_wordID +  "  word  " + word + "  WordHasSound " + word.getWordHasSound());
        if (!word.getWordHasSound()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); 
            return;            
        }
        
        byte[] wordSound = DAO_Word.GetWordSoundByWordID(wordID);
        
        //};
        
        if (wordSound == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); 
            return;
        }
        
        //System.out.println("--------------------param_wordID " + param_wordID +  "  word  " + word + "  wordSound " + wordSound);
        ByteArrayInputStream bis = new ByteArrayInputStream(wordSound);
        
        
        
        //String contentType = "audio/x-wav";
        String contentType = "audio/mpeg";
        
        
        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(wordSound.length));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + word.getWordID() + "\"");
        
        
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            input = new BufferedInputStream(bis, DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } finally {
            close(output);
            close(input);
        }
    }
     

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
            }
        }
    }     
    
}
