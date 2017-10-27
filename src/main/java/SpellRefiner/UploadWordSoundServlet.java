package SpellRefiner;

//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
//import java.io.Closeable;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
//import java.io.FileInputStream;
import java.io.IOException;
//import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.PersistenceUnit;
//import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.MultipartConfig;
import java.io.InputStream;
import javax.servlet.http.Part;
import java.util.Enumeration;
import javax.servlet.ServletInputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Arrays;


@WebServlet(
        name = "uploadWordSoundServlet",
        urlPatterns = "/upload_word_sounds"
)

@MultipartConfig(maxFileSize = 1000000)
public class UploadWordSoundServlet extends HttpServlet {
     private static final int DEFAULT_BUFFER_SIZE = 10240; 
     
    @EJB
    private DAO_Interface_Word DAO_Word;       
    
    @Inject
    private CurrentUser currentUser;      
    
     
    
//    @PersistenceUnit(unitName = "SpellRefiner")
//    private EntityManagerFactory emf;    
     

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
	  
	  //doPost_Test(request, response);
	  //if (true) return;

	  
	  doPost_WildFly(request, response);
	  if (true) return;
	  
	  
	  
	  
	  
	  
        
	  //System.out.println("UploadWordSoundServlet--------------------doPost 8");
        
        
        String param_wordID = request.getParameter("wordID");
        
        //System.out.println("UploadWordSoundServlet--------------------param_wordID " + param_wordID);
        
        if (param_wordID == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); 
            return;
        }
        
        if (param_wordID == "") {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); 
            return;
        }
        
        Integer wordID;
        
        try{
            wordID = Integer.valueOf(param_wordID);
        }
        catch(Exception e){
            response.sendError(HttpServletResponse.SC_NOT_FOUND); 
            return;            
        }
            
        
        
        
        String param_wordHasSound = request.getParameter("wordHasSound");
        
        //System.out.println("UploadWordSoundServlet--------------------param_wordHasSound " + param_wordHasSound);
        
        if (param_wordHasSound == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); 
            return;
        }
        
        if (param_wordHasSound == "") {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); 
            return;
        }
        
        Boolean wordHasSound = Boolean.FALSE;
        try{
            wordHasSound = Boolean.valueOf(param_wordHasSound);
        }
        catch(Exception e){
            response.sendError(HttpServletResponse.SC_NOT_FOUND); 
            return;            
        }
        
        
        //System.out.println("UploadWordSoundServlet--------------------wordHasSound " + wordHasSound);
        
        
        
        Word word = DAO_Word.GetWordByID(wordID);
        
        
        //System.out.println("UploadWordSoundServlet--------------------word " + word);
        if (word == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); 
            return;
        }
        
        //System.out.println("UploadWordSoundServlet--------------------2");
        
        byte[] wordSound;
        
        
        if (wordHasSound){
        	
        	//System.out.println("UploadWordSoundServlet--------------------3");
        
            InputStream inputStream = null; // input stream of the upload file
         
            Part filePart = request.getPart("wordSound");
        
            //System.out.println("UploadWordSoundServlet--------------------filePart " + filePart);
        
            if (filePart == null) {             
                response.sendError(HttpServletResponse.SC_NOT_FOUND); 
                return;
            }
            
            //System.out.println("UploadWordSoundServlet--------------------4");
            
            inputStream = filePart.getInputStream();
            
            
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();

            wordSound = buffer.toByteArray();
            
            //test code?
            //FileOutputStream fos = new FileOutputStream("_sr_wordSound1.ogg");
            //fos.write(wordSound);
            //fos.close();            
            
            DAO_Word.SetWordSoundByWordID(wordID, wordSound, wordHasSound);            
            
        }   
        else{
            //System.out.println("UploadWordSoundServlet-------------------- no sound");
            DAO_Word.SetWordSoundByWordID(wordID, null, wordHasSound);
        }
        
            
        
        response.setContentType("text/html");
        
    
    }

  
  
  
  
  
  
  protected void doPost_Test(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException
	    {
		  
		  
		  //System.out.println("UploadWordSoundServlet--------------------doPost_Test 8");
	        
		  
	        Enumeration<String> header_names = request.getHeaderNames();
	        
	        
	        //System.out.println("UploadWordSoundServlet--------------------Headers");
	        
	        
	        while(header_names.hasMoreElements()){
	            String header_name = (String) header_names.nextElement();
	            //System.out.println("        header_name = " + header_name);
	            
	            Enumeration<String> headers = request.getHeaders(header_name);
	            while(headers.hasMoreElements()){
	                
	                String header = (String) headers.nextElement();
	                //System.out.println("            header = " + header);
	            }
	        } 
	        
	        //if (true) return;
	        
	        

	        /*
	        System.out.println("UploadWordSoundServlet--------------------getInputStream");
	        ServletInputStream ris = request.getInputStream();
	        
	        OutputStream os = new FileOutputStream("/_spell_refiner_request");
	        byte[] buffer1 = new byte[4096];
	        int bytesRead;
	        while ((bytesRead = ris.read(buffer1)) != -1) {
	            os.write(buffer1, 0, bytesRead);
	        }
	        os.close();        
	        
	        System.out.println("UploadWordSoundServlet--------------------write file spell_refiner_request");
	        
	        */
	        
	        //System.out.println("UploadWordSoundServlet--------------------getPart wordSound 1");
	        Part filePart1 = request.getPart("wordSound");
	        InputStream inputStream2 = null; 
	        inputStream2 = filePart1.getInputStream();
	        File uploads2 = new File("/");
	        File file2 = new File(uploads2, "_sr_wordsound");
	        Files.copy(inputStream2, file2.toPath());        
	        
	        //System.out.println("UploadWordSoundServlet--------------------getParts");
	         Collection<Part> fileParts = request.getParts();
	         for (Part filePart: fileParts){                         
	            //System.out.println("UploadWordSoundServlet--------------------filePart.getName() " + filePart.getName());
	            
	            InputStream inputStream1 = null; // input stream of the upload file
	            inputStream1 = filePart.getInputStream();
	            File uploads1 = new File("/");
	            File file1 = new File(uploads1, "_sr_filePart " + filePart.getName());
	            Files.copy(inputStream1, file1.toPath());
	            
	         }
	        
	        
	        String param_wordID = request.getParameter("wordID");
	        
	        //System.out.println("UploadWordSoundServlet--------------------param_wordID " + param_wordID);
	        
	        
	        Integer wordID;
	        
	        try{
	            wordID = Integer.valueOf(param_wordID);
	        }
	        catch(Exception e){
	            response.sendError(HttpServletResponse.SC_NOT_FOUND); 
	            return;            
	        }            
	        
	        
	        
	        String param_wordHasSound = request.getParameter("wordHasSound");
	        
	        //System.out.println("UploadWordSoundServlet--------------------param_wordHasSound " + param_wordHasSound);
	        
	        
	        Boolean wordHasSound = Boolean.FALSE;
	        try{
	            wordHasSound = Boolean.valueOf(param_wordHasSound);
	        }
	        catch(Exception e){
	            response.sendError(HttpServletResponse.SC_NOT_FOUND); 
	            return;            
	        }
	        
	        
	        //System.out.println("UploadWordSoundServlet--------------------wordHasSound " + wordHasSound);
	        
	        
	        
	        Word word = DAO_Word.GetWordByID(wordID);
	        
	        
	        //System.out.println("UploadWordSoundServlet--------------------word " + word);
	        if (word == null) {
	            response.sendError(HttpServletResponse.SC_NOT_FOUND); 
	            return;
	        }
	        
	        //System.out.println("UploadWordSoundServlet--------------------2");
	        
	        
	        byte[] wordSound;
	        
	        
	        if (wordHasSound){
	        	
	        	//System.out.println("UploadWordSoundServlet--------------------3");
	        
	            InputStream inputStream = null; // input stream of the upload file
	         
	            Part filePart = request.getPart("wordSound");
	        
	            //System.out.println("UploadWordSoundServlet--------------------filePart " + filePart);
	        
	            if (filePart == null) {             
	                response.sendError(HttpServletResponse.SC_NOT_FOUND); 
	                return;
	            }
	            
	            //System.out.println("UploadWordSoundServlet--------------------4");
	            
	            inputStream = filePart.getInputStream();
	            
	            
	            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

	            int nRead;
	            byte[] data = new byte[16384];

	            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
	                buffer.write(data, 0, nRead);
	            }

	            buffer.flush();

	            wordSound = buffer.toByteArray();
	            
	            FileOutputStream fos = new FileOutputStream("_sr_wordSound1.ogg");
	            fos.write(wordSound);
	            fos.close();            
	            
	            DAO_Word.SetWordSoundByWordID(wordID, wordSound, wordHasSound);            
	            
	        }   
	        else{
	            //System.out.println("UploadWordSoundServlet-------------------- no sound");
	            DAO_Word.SetWordSoundByWordID(wordID, null, wordHasSound);
	        }
	        
	            
	        
	        response.setContentType("text/html");
	        
	        //System.out.println("UploadWordSoundServlet--------------------end ");
	        
	        //InputStream is = ...
	        //ByteArrayOutputStream buffer = new ByteArrayOutputStream();

	        //int nRead;
	        //byte[] data = new byte[16384];

	        //while ((nRead = is.read(data, 0, data.length)) != -1) {
	        //    buffer.write(data, 0, nRead);
	        //}

	        //buffer.flush();

	        //return buffer.toByteArray();        
	        
	    
	    }

  
  
  int indexOf_ByteArray(byte[] pattern, byte[] target){
	  
	  if (target.length==0){
		  return -1;
	  }
		  
	  if (pattern.length==0){
		  return -1;
	  }
	  
	  int i = 0;
	  while (i < target.length){		  
		  
		  boolean found = true;
		  int j = 0;
		  while (j < pattern.length){
			  if (i + j >= target.length){
				  found = false;
				  break;
			  }
				  
			  
			  if (target[i + j]!=pattern[j]){
				  found = false;
				  break;
			  }
			  
			  j++;
		  }		
		  
		  if (found){
			  return i;
		  }  
		  
		  i++;
	  }
	  
	  
	  return -1;
  }
  
  
  public String toHexString(byte[] bytes) {
      StringBuilder hexString = new StringBuilder();

      for (int i = 0; i < bytes.length; i++) {
          String hex = Integer.toHexString(0xFF & bytes[i]);
          if (hex.length() == 1) {
              hexString.append('0');
          }
          hexString.append(hex);
          hexString.append(" ");
      }

      return hexString.toString();
  } 
     

  public byte[]  getPartOfByteArray(byte[] array, int first, int length){
	  
	//System.out.println("first=" + first + " length  " + length);  
	byte[] array_new = new byte[length];
	
	for(int i = 0; i < length; i++){
		//System.out.println("i=" + i + " i + first  " + (i + first));
		array_new[i] = array[i + first];
	}
	  
	  
	return array_new;
  }
  
  
  byte[] getPart_WilfFly(byte [] request_byte_array, String name, String boundary) throws IOException
  {
	  
	  //byte[] part;
	  
	  
	  
	  //System.out.println("indexOf_ByteArray -1 = " + indexOf_ByteArray(new byte[]{}, new byte[]{}));
	  //System.out.println("indexOf_ByteArray -1 = " + indexOf_ByteArray(new byte[]{1}, new byte[]{}));
	  //System.out.println("indexOf_ByteArray -1 = " + indexOf_ByteArray(new byte[]{}, new byte[]{1}));
	  //System.out.println("indexOf_ByteArray 0 = " + indexOf_ByteArray(new byte[]{1}, new byte[]{1}));
	  //System.out.println("indexOf_ByteArray 0 = " + indexOf_ByteArray(new byte[]{1}, new byte[]{1,2,3}));
	  //System.out.println("indexOf_ByteArray 1 = " + indexOf_ByteArray(new byte[]{2}, new byte[]{1,2,3}));
	  //System.out.println("indexOf_ByteArray 2 = " + indexOf_ByteArray(new byte[]{3}, new byte[]{1,2,3}));
	  //System.out.println("indexOf_ByteArray 1 = " + indexOf_ByteArray(new byte[]{2,3}, new byte[]{1,2,3,4}));
	  //System.out.println("indexOf_ByteArray 2 = " + indexOf_ByteArray(new byte[]{3,4}, new byte[]{1,2,3,4}));
	  //System.out.println("indexOf_ByteArray 0 = " + indexOf_ByteArray(new byte[]{1,2}, new byte[]{1,2,3,4}));
	  //System.out.println("indexOf_ByteArray 1 = " + indexOf_ByteArray(new byte[]{2,3}, new byte[]{1,2,3,4}));
	  //System.out.println("indexOf_ByteArray 0 = " + indexOf_ByteArray(new byte[]{1,2,3,4}, new byte[]{1,2,3,4}));
	  //System.out.println("indexOf_ByteArray -1 = " + indexOf_ByteArray(new byte[]{1,2,3,4,5}, new byte[]{1,2,3,4}));
	  //System.out.println("indexOf_ByteArray -1 = " + indexOf_ByteArray(new byte[]{3,4,5}, new byte[]{1,2,3,4}));
	  //System.out.println("indexOf_ByteArray -1 = " + indexOf_ByteArray(new byte[]{5}, new byte[]{1,2,3,4}));
	  
	  
	  //byte[] array = new byte[]{(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05};
	  //System.out.println("toHexString=" + toHexString(array));
	  
	  //System.out.println("Test getPartOfByteArray");
	  //System.out.println("getPartOfByteArray=" + toHexString(getPartOfByteArray(array, 0, 0))); 
	  //System.out.println("getPartOfByteArray=" + toHexString(getPartOfByteArray(array, 0, 1)));
	  //System.out.println("getPartOfByteArray=" + toHexString(getPartOfByteArray(array, 1, 1)));
	  //System.out.println("getPartOfByteArray=" + toHexString(getPartOfByteArray(array, 2, 2)));
	  //System.out.println("getPartOfByteArray=" + toHexString(getPartOfByteArray(new byte[]{}, 0, 0)));
	  //System.out.println("getPartOfByteArray=" + toHexString(getPartOfByteArray(new byte[]{(byte)0x01}, 0, 1)));
	  
	  //byte[] array_new = Arrays.copyOfRange(array, 2, 4);
	  
	  //System.out.println("array_new = " + toHexString(array_new));
	  
	  //byte[] b = string.getBytes(Charset.forName("UTF-8"));
	  //byte[] b = string.getBytes(StandardCharsets.UTF_8); // Java 7+ only
	  
	  //0x0d0a - line break
	  //0x22 - "
	  //0x2d2d - double dash
	  //ox3b - ;
	  
	  
	  
	  
	  //--BoundaryRandomString_SpellRefiner_Recorder
	  //Content-Disposition: form-data; name="wordSound";
	  //Content-Type: application/octet-stream
	  
	  String content_disposition = "Content-Disposition: form-data; name=";
	  String content_type = "Content-Type: application/octet-stream"; //Content-Type: audio/ogg	  
	  
	  
	  byte[] dash = new byte[]{(byte)0x2d};
	  byte[] line_break = new byte[]{(byte)0x0d, (byte)0x0a};
	  byte[] quotation_mark = new byte[]{(byte)0x22};
	  byte[] semicolon = new byte[]{(byte)0x3b};
	  
	  
	  byte[] boundary_bytes = boundary.getBytes(StandardCharsets.UTF_8);
	  byte[] name_bytes = name.getBytes(StandardCharsets.UTF_8);
	  //byte[] content1_bytes = content1.getBytes(StandardCharsets.UTF_8);
	  //byte[] content2_bytes = content2.getBytes(StandardCharsets.UTF_8);
	  byte[] content_disposition_bytes = content_disposition.getBytes(StandardCharsets.UTF_8);
	  byte[] content_type_bytes = content_type.getBytes(StandardCharsets.UTF_8);
	  
	  
	  
	  ByteArrayOutputStream multipart_header_stream = new ByteArrayOutputStream( );
	  multipart_header_stream.write(dash);
	  multipart_header_stream.write(dash);
	  multipart_header_stream.write(boundary_bytes);
	  multipart_header_stream.write(line_break);
	  multipart_header_stream.write(content_disposition_bytes);
	  multipart_header_stream.write(quotation_mark);
	  multipart_header_stream.write(name_bytes);
	  multipart_header_stream.write(quotation_mark);
	  multipart_header_stream.write(semicolon);
	  multipart_header_stream.write(line_break);
	  multipart_header_stream.write(content_type_bytes);
	  multipart_header_stream.write(line_break);
	  multipart_header_stream.write(line_break);
	  

	  byte[] multipart_header = multipart_header_stream.toByteArray( );
	  
	  String multipart_header_str = toHexString(multipart_header);	  
	  //System.out.println("multipart_header_str    = " + multipart_header_str);
	  
	  String request_byte_array_str = toHexString(request_byte_array);
	  //System.out.println("request_byte_array_str = " + request_byte_array_str);
	  
	  
	  String multipart_header1 = multipart_header_stream.toString();
	  
	  //System.out.println("multipart_header1 = " + multipart_header1);
	  
	  int index = indexOf_ByteArray(multipart_header, request_byte_array);
	  
	  //System.out.println("index = " + index);
	  
	  if (index < 0){
		  return new byte[]{};
	  }
	  
	  byte[] request_byte_array1 = Arrays.copyOfRange(request_byte_array, index + multipart_header.length, request_byte_array.length);
	  
	  //System.out.println("request_byte_array1 = " + toHexString(request_byte_array1));
	  
	  ByteArrayOutputStream boundary_stream1 = new ByteArrayOutputStream( );
	  boundary_stream1.write(line_break);
	  boundary_stream1.write(dash);
	  boundary_stream1.write(dash);
	  boundary_stream1.write(boundary_bytes);
	  //boundary_stream1.write(line_break);
	  byte[] boundary_bytes1 = boundary_stream1.toByteArray( );
	  
	  String boundary_bytes_str1 = toHexString(boundary_bytes1);
	  //System.out.println("boundary_bytes_str1 = " + boundary_bytes_str1);
	  
	  
	  
	  index = indexOf_ByteArray(boundary_bytes1, request_byte_array1);
	  
	  //System.out.println("index = " + index);
	  
	  if (index < 0){
		  return new byte[]{};
	  }	  
	  
	  byte[] Part = Arrays.copyOfRange(request_byte_array1, 0, index);
	  
	  //System.out.println("Part = " + toHexString(Part));
	  
	  return Part;	  
  }
  
  /*
  public static String toHex(byte[] bytes) {
	    BigInteger bi = new BigInteger(1, bytes);
	    return String.format("%0" + (bytes.length << 1) + "X", bi);
	}
  */
  
  
  String get_boundary(HttpServletRequest request){
      Enumeration<String> header_names = request.getHeaderNames();
      
      
      //System.out.println("UploadWordSoundServlet--------------------Headers");
      
      String boundary = null;
      String header_boundary = "multipart/form-data; boundary=";
      String header_length = "Content-length";
      String header_type = "Content-Type";       
      Integer content_lengh = 0;
      
      
      header_boundary = header_boundary.toLowerCase();
      header_length = header_length.toLowerCase();
      header_type = header_type.toLowerCase();
      
      //header_type.toLowerCase()
      
      while(header_names.hasMoreElements()){
          String header_name = (String) header_names.nextElement();
          header_name = header_name.toLowerCase();
          
          //System.out.println("        header_name = " + header_name);
          
          
          Enumeration<String> headers = request.getHeaders(header_name);
          while(headers.hasMoreElements()){             
        	  
              String header = (String) headers.nextElement();              
              String header_lower = header.toLowerCase();
              
              //System.out.println("            header = " + header);
              
              if (header_name.equals(header_type)){
            	  //System.out.println("            header_name.equals ");
            	if (header_lower.contains(header_boundary)){
            		//System.out.println("            header.contains ");
            		
            		boundary = header.substring(header_boundary.length());
            	}
              }
              
              if (header_name.equals(header_length)){            	  
            	  content_lengh = Integer.valueOf(header);
            	  
              }
              
              
              
              
          }
      } 
      
      //System.out.println("UploadWordSoundServlet--------------------boundary = " + boundary);
      //System.out.println("UploadWordSoundServlet--------------------content_lengh = " + content_lengh);
	  
      return boundary;      
  }

  byte[] get_request_input_bytes(HttpServletRequest request) throws ServletException, IOException
  {
      byte[] request_input;
           
      //System.out.println("get_request_input_bytes-------------------- ");
      
      ServletInputStream ris = request.getInputStream();
      
      
     
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();

      int nRead;
      byte[] data = new byte[16384];

      //while ((nRead = ris.read(data, 0, data.length)) != -1) {
      while ((nRead = ris.read(data, 0, data.length)) > 0) {
    	  //System.out.println("get_request_input_bytes-------------------- nRead = " + nRead);
    	  
          buffer.write(data, 0, nRead);
      }

      buffer.flush();

      request_input = buffer.toByteArray();        
	  
	  return request_input;
  }
  
  
  
  //method getPart() do not work on WildFly for http request from Flash - result file is corrupted
  void doPost_WildFly(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
	  //System.out.println("UploadWordSoundServlet--------------------doPost_WildFly v30");

      byte[] request_input = get_request_input_bytes(request);
      //System.out.println("UploadWordSoundServlet--------------------request_input.length = " + request_input.length);
	  
	  //System.out.println("UploadWordSoundServlet--------------------request.getContentLength() = " + request.getContentLength());
	  
	  
      String boundary = get_boundary(request);
      
      //String boundary = "BoundaryRandomString_SpellRefiner_Recorder";
	  
      if (boundary == null) {             
          response.sendError(HttpServletResponse.SC_NOT_FOUND); 
          return;
      }
      

      String request_input_hex = toHexString(request_input);
      //System.out.println("request_input_hex =" + request_input_hex);
      
      //String request_input_str1 = request_input.toString();
      //System.out.println("UploadWordSoundServlet--------------------request_input_str1");
      //System.out.println(request_input_str1);
      
      
	  
      
      
      /*
      byte[] request_input;     
      
      
      ServletInputStream ris = request.getInputStream();
     
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();

      int nRead;
      byte[] data = new byte[16384];

      while ((nRead = ris.read(data, 0, data.length)) != -1) {
          buffer.write(data, 0, nRead);
      }

      buffer.flush();

      request_input = buffer.toByteArray();    
      */    
      
      byte[] wordSound = getPart_WilfFly(request_input, "wordSound", boundary);
      
      //test code - delete
      //FileOutputStream fos = new FileOutputStream("_sr_wordSound1.ogg");
      //fos.write(wordSound);
      //fos.close();            
      
      byte[] param_wordID_bytes = getPart_WilfFly(request_input, "wordID", boundary);
      
      
      String param_wordID = new String(param_wordID_bytes);
      //System.out.println("UploadWordSoundServlet--------------------param_wordID " + param_wordID);
      
      
      byte[] param_wordHasSound_bytes = getPart_WilfFly(request_input, "wordHasSound", boundary);
      String param_wordHasSound = new String(param_wordHasSound_bytes);
      
      String param_wordHasSound_hex = toHexString(param_wordHasSound_bytes);
      //System.out.println("UploadWordSoundServlet--------------------param_wordHasSound_hex " + param_wordHasSound_hex);
      //System.out.println("UploadWordSoundServlet--------------------param_wordHasSound " + param_wordHasSound);
	  
	  
      //String param_wordID = request.getParameter("wordID");
      
      
      if (param_wordID.isEmpty()) {
          response.sendError(HttpServletResponse.SC_NOT_FOUND); 
          return;
      }
      
      /*
      if (param_wordID == null) {
          response.sendError(HttpServletResponse.SC_NOT_FOUND); 
          return;
      }
      
      if (param_wordID == "") {
          response.sendError(HttpServletResponse.SC_NOT_FOUND); 
          return;
      }
      */
      
      Integer wordID;
      
      try{
          wordID = Integer.valueOf(param_wordID);
      }
      catch(Exception e){
          response.sendError(HttpServletResponse.SC_NOT_FOUND); 
          return;            
      }
          
      
      Integer userID = currentUser.getUser().getUserID(); 
  	  boolean access = DAO_Word.CheckPermissionForWord(wordID, userID);
  	  
      //System.out.println("UploadWordSoundServlet--------------------userID " + userID);
      //System.out.println("UploadWordSoundServlet--------------------wordID " + wordID);
      //System.out.println("UploadWordSoundServlet--------------------access " + access);
  	  
  	  if (!access){
  		response.sendError(HttpServletResponse.SC_FORBIDDEN);
  		return;
  	  }
      
      //System.out.println("UploadWordSoundServlet-------------------- allowed ");
      
      
      
      //String param_wordHasSound = request.getParameter("wordHasSound");
      
      //System.out.println("UploadWordSoundServlet--------------------param_wordHasSound " + param_wordHasSound);

      if (param_wordHasSound.isEmpty()) {
          response.sendError(HttpServletResponse.SC_NOT_FOUND); 
          return;
      }

      /*
      if (param_wordHasSound == null) {
          response.sendError(HttpServletResponse.SC_NOT_FOUND); 
          return;
      }
      
      if (param_wordHasSound == "") {
          response.sendError(HttpServletResponse.SC_NOT_FOUND); 
          return;
      }
      */
      
      Boolean wordHasSound = Boolean.FALSE;
      try{
          wordHasSound = Boolean.valueOf(param_wordHasSound);
      }
      catch(Exception e){
          response.sendError(HttpServletResponse.SC_NOT_FOUND); 
          return;            
      }
      
      
      //System.out.println("UploadWordSoundServlet--------------------wordHasSound " + wordHasSound);
      
      
      
      
      Word word = DAO_Word.GetWordByID(wordID);
      
      
      //System.out.println("UploadWordSoundServlet--------------------word " + word);
      if (word == null) {
          response.sendError(HttpServletResponse.SC_NOT_FOUND); 
          return;
      }
      
      //System.out.println("UploadWordSoundServlet--------------------2");
      
      
      
      if (wordHasSound){
      	
          

          
      
          if (wordSound.length==0) {             
              response.sendError(HttpServletResponse.SC_NOT_FOUND); 
              return;
          }
          
          
          
          DAO_Word.SetWordSoundByWordID(wordID, wordSound, wordHasSound);            
          
      }   
      else{
          //System.out.println("UploadWordSoundServlet-------------------- no sound");
          DAO_Word.SetWordSoundByWordID(wordID, null, wordHasSound);
      }
      
          
      
      response.setContentType("text/html");
	  
	  
	  
	  
	  
	  
	  
	  /*
      Enumeration<String> header_names = request.getHeaderNames();
      
      
      System.out.println("UploadWordSoundServlet--------------------Headers");
      
      String boundary = null;
      String header_boundary = "multipart/form-data; boundary=";
      String header_length = "Content-length";
      String header_type = "Content-Type";
      Integer content_lengh = 0;
      
      while(header_names.hasMoreElements()){
          String header_name = (String) header_names.nextElement();
          System.out.println("        header_name = " + header_name);
          
          
          Enumeration<String> headers = request.getHeaders(header_name);
          while(headers.hasMoreElements()){             
        	  
              String header = (String) headers.nextElement();
              
              System.out.println("            header = " + header);
              
              if (header_name.equals(header_type)){
            	  System.out.println("            header_name.equals ");
            	if (header.contains(header_boundary)){
            		System.out.println("            header.contains ");
            		
            		boundary = header.substring(header_boundary.length());
            	}
              }
              
              if (header_name.equals(header_length)){            	  
            	  content_lengh = Integer.valueOf(header);
            	  
              }
              
              
              
              
          }
      } 
      
      System.out.println("UploadWordSoundServlet--------------------boundary = " + boundary);
      System.out.println("UploadWordSoundServlet--------------------content_lengh = " + content_lengh);
      
      */
	  
      
      
      
  }
  
}