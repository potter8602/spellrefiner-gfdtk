/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package SpellRefiner;

/**
 *
 * @author Alex
 */
public class Info {
    
}

/*

package net.codejava.jdbc;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
 
public class JdbcInsertFileOne {
 
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/contactdb";
        String user = "root";
        String password = "secret";
 
        String filePath = "D:/Photos/Tom.png";
 
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
 
            String sql = "INSERT INTO person (first_name, last_name, photo) values (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, "Tom");
            statement.setString(2, "Eagar");
            InputStream inputStream = new FileInputStream(new File(filePath));
 
            statement.setBlob(3, inputStream);
 
            int row = statement.executeUpdate();
            if (row > 0) {
                System.out.println("A contact was inserted with photo image.");
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}


*/

//System.out.println("--------------------setFile " + file);

//@WebServlet("/images/*")


/*
//web.xml


  <servlet>
        <servlet-name>fileDownloadServlet</servlet-name>
        <servlet-class>SpellRefiner.FileDownloadServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>fileDownloadServlet</servlet-name>
        <url-pattern>/file/*</url-pattern>
    </servlet-mapping>  

*/
    /*
    @Override
    public List<Word> Test1(){    
        
        
        
        String str_query = "";
        str_query = "SELECT * FROM (SELECT * FROM (SELECT word_id, dict_id, word_spelling FROM Words ORDER BY word_id ASC LIMIT 10) AS Table1 ORDER BY word_id DESC LIMIT 3) AS Table2 ORDER BY word_id ASC";  
        System.out.println("--------------------str_query = " + str_query);
        
        Query query = em.createNativeQuery(str_query, Word.class);
        //em.createNativeQuery(str_query, null)
        //query.setParameter("LastRecord", LastRecord);
        //query.setParameter("NumberOfRecords", (LastRecord - FirstRecord + 1));
        List<Word> list = query.getResultList();
        
        return list;
    }   
    

    @Override
    public List<Word> Test2(){    
        
        List<Word> list = new ArrayList<Word>();
        
        
        String str_query = "";
        str_query = "SELECT Words.word_id, Words.word_spelling, Words.dict_id, Dictionaries.dict_name FROM Words INNER JOIN Dictionaries ON Dictionaries.dict_id = Words.dict_id";  
        System.out.println("--------------------str_query = " + str_query);
        
        Query query = em.createNativeQuery(str_query);
        //em.createNativeQuery(str_query, null)
        //query.setParameter("LastRecord", LastRecord);
        //query.setParameter("NumberOfRecords", (LastRecord - FirstRecord + 1));
        List<Object[]> list1 = query.getResultList();
        
        for (int i = 0; i < list1.size(); i++) {
            Word w = new Word();
            Dictionary dict = new Dictionary();
            
            w.setWordID((Integer)list1.get(i)[0]);
            w.setWordSpelling((String)list1.get(i)[1]);
            w.setDictID((Integer)list1.get(i)[2]);
            
            dict.setDictID((Integer)list1.get(i)[2]);
            dict.setDictName((String)list1.get(i)[3]);
            
            w.setWordDict(dict);
            list.add(w);
        }
        
        
        return list;
    }   
    
    @Override
    public List<Word> Test3(Dictionary dict){    
        return DictionaryWordsWithParamsInInterval(dict, "word_id", true, false, true, true, 1, 10);
        
    }
    
    */
