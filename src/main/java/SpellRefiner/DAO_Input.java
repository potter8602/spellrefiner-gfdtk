package SpellRefiner;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import javax.persistence.Query;
//import org.hibernate.type.LongType;
//import java.sql.Types;
import javax.validation.constraints.Size;




@Stateless
public class DAO_Input implements DAO_Interface_Input{    
    @PersistenceContext(unitName = "SpellRefiner")
    private EntityManager em;       
    
    @EJB
    private DAO_Interface_Word DAO_Word;        

    
    @Override
    public Integer SaveNew(Input entity){    
       em.persist(entity);    
       em.flush();
       return entity.getInputID();
       
    }            
    
    
    @Override
    public void Update(Input entity){    
       em.merge(entity);        
    }            

    @Override
    public void Delete(Input entity){    
       Input entity1 = em.merge(entity);        
       em.remove(entity1);        
    }            
    
    /*
    @Override
    public Input Get(Integer id){    
        Input u = em.find(Input.class, id);
        return u;
    } 
    */

    @Override
    public List<Input> findAll(){    
        Query query = em.createNamedQuery("Input.findAll");
        List<Input> list = query.getResultList();
        
        return list;
    }   
    
    
    @Override
    public List<Input> findByInputID(Integer inputID){    
        Query query = em.createNamedQuery("Input.findByInputID");
        query.setParameter("inputID", inputID);
        List<Input> list = query.getResultList();
        
        return list;
    }   
    
    /*
    @Override
    public List<Word> getNextInputWordFromDictionary(Check check){    
        Query query = em.createNamedQuery("Input.getNextInputWordFromDictionary");
        query.setParameter("check", check);
        query.setParameter("dict", check.getCheckDict());
        query.setMaxResults(1);
        List<Word> list = query.getResultList();
        
        return list;
    } 
    */

    //@Override
    /*
    public List<Word> getNextInputWordFromLastInput_Old(Check check, Integer passNumber){    
        Query query = em.createNamedQuery("Input.getNextInputWordFromLastInput");
        query.setParameter("check", check);
        query.setParameter("passNumber", passNumber);
        query.setParameter("previousPassNumber", passNumber - 1);
        
        query.setMaxResults(1);
        List<Word> list = query.getResultList();
        
        return list;
    } 
    */
    
    @Override
    public List<Word> getInputWordsFromDictionary(Dictionary dict){
        Query query = em.createNamedQuery("Input.getInputWordsFromDictionary");
        query.setParameter("dict", dict);
        
        List<Word> list = query.getResultList();
        
        return list;
    }   
    
    @Override
    public List<Word> getInputWordsByPassNumber(Check check, Integer passNumber){
        Query query = em.createNamedQuery("Input.getInputWordsByPassNumber");
        query.setParameter("check", check);
        query.setParameter("passNumber", passNumber);
        
        List<Word> list = query.getResultList();
        
        return list;
    }   

    @Override
    public Integer NumberOfInputWordsByPassNumber(Check check, Integer passNumber){
        Query query = em.createNamedQuery("Input.NumberOfInputWordsByPassNumber");
        query.setParameter("check", check);
        query.setParameter("passNumber", passNumber);
        
        int count = (int)(long)query.getSingleResult();
        
        return count;
    }   
    
    @Override
    public Integer NumberOfInputWordsFromLastInput(Check check, Integer previousPassNumber){
        Query query = em.createNamedQuery("Input.NumberOfInputWordsFromLastInput");
        query.setParameter("check", check);
        query.setParameter("previousPassNumber", previousPassNumber);
        
        int count = (int)(long)query.getSingleResult();
        
        return count;
    }   

    @Override
    public Integer NumberOfInputWordsFromDictionary(Dictionary dict){
        Query query = em.createNamedQuery("Input.NumberOfInputWordsFromDictionary");
        query.setParameter("dict", dict);
        
        int count = (int)(long)query.getSingleResult();
        
        return count;
    }   
    
    @Override
    public List<Word> getNextInputWordFromLastInput(Check check, Integer passNumber){    
        String query_str = "SELECT ";
        //query_str = query_str + "w.Word_ID AS WordID, ";
        //query_str = query_str + "w.Dict_ID AS DictID ";
        query_str = query_str + "i.Word_ID AS WordID, ";
        query_str = query_str + "i.Dict_ID AS DictID ";
        
        /*
        query_str = query_str + "w.Word_Spelling AS WordSpelling, ";
        query_str = query_str + "w.Word_Translation AS WordTranslation, ";
        query_str = query_str + "w.Word_Description AS WordDescription, ";
        query_str = query_str + "w.Word_Random_Number AS WordRandomNumber, ";
        query_str = query_str + "w.Word_Deleted AS WordDeleted, ";
        query_str = query_str + "w.Word_HasSound AS WordHasSound ";
        */
        query_str = query_str + "FROM inputs AS i ";
        //query_str = query_str + "INNER JOIN words AS w ON w.Word_ID = i.Word_ID ";
        query_str = query_str + "WHERE i.Check_ID = :checkID AND i.Pass_Number = :previousPassNumber  AND (i.error = TRUE OR i.uncertain = TRUE) AND i.skipped = FALSE AND NOT i.Word_ID IN ";
        query_str = query_str + "(SELECT i.Word_ID FROM inputs i WHERE i.Check_ID = :checkID AND i.Pass_Number = :passNumber)  ";
        query_str = query_str + "ORDER BY i.Input_ID ";
        query_str = query_str + "LIMIT 1";
        //System.out.println("getNextInputWordFromLastInput --------------------str_query = " + query_str);
        
        //System.out.println("getNextInputWordFromLastInput --------------------passNumber = " + passNumber);
        
        Query query = em.createNativeQuery(query_str);
        query.setParameter("checkID", check.getCheckID());
        query.setParameter("passNumber", passNumber);
        query.setParameter("previousPassNumber", passNumber - 1);
        
        List<Object[]> list1 = query.getResultList();        
        
        List<Word> list = new ArrayList<Word>();
        
        
        for (int i = 0; i < list1.size(); i++) {
        	
        	/*
            Word w = new Word();
            Dictionary dict = new Dictionary();
            
            w.setWordID((Integer)list1.get(i)[0]);
            w.setDictID((Integer)list1.get(i)[1]);
            w.setWordSpelling((String)list1.get(i)[2]);                        
            w.setWordTranslation((String)list1.get(i)[3]);                        
            w.setWordDescription((String)list1.get(i)[4]);                        
            w.setWordRandomNumber((Integer)list1.get(i)[5]);                        
            w.setWordDeleted((Integer)list1.get(i)[6]==1);                        
            w.setWordHasSound((Integer)list1.get(i)[7]==1);                        
            
            dict.setDictID((Integer)list1.get(i)[1]);
            
            w.setWordDict(dict);
            list.add(w);
            */
            
            Integer w2_id = (Integer)list1.get(i)[0];
            List<Word> list2 = DAO_Word.findByWordID(w2_id);
            
            for (Word w2:list2){
            	list.add(w2);
            }
            
        }        
        
        
        /*
        Query query = em.createNativeQuery(str_query);
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
        */        
        
        return list;
    }   

    
    @Override
    public List<Word> getNextInputWordFromDictionary(Check check, String wordsOrder, boolean ascendingOrder, boolean WithSound, boolean WithoutSound, boolean ExceptWordsInFinishedChecks, boolean ExceptWordsInFinishedChecksSameDictionary){
    	
    	//System.out.println("--------------------getNextInputWordFromDictionary");
        
        String OrderField = "";

        if (wordsOrder.equals("wordSpelling")){
            OrderField = "Word_Spelling";
        };
        
        if (wordsOrder.equals("wordRandomNumber")){
            OrderField = "Word_Random_Number";
        };
        
        if (wordsOrder.equals("wordID")){
            OrderField = "Word_ID";
        };
        
        //String str_order = "" + OrderField + " " + ((ascendingOrder) ? "DESC": "ASC");
        String str_order = "" + OrderField + " " + ((ascendingOrder) ? "ASC": "DESC");
        
        String str_filter = "";
        str_filter = str_filter + (WithSound && !WithoutSound ? " AND w.Word_HasSound = TRUE " : "");
        str_filter = str_filter + (!WithSound && WithoutSound ? " AND w.Word_HasSound = FALSE " : "");
        str_filter = str_filter + (!WithSound && !WithoutSound ? " AND FALSE = TRUE " : "");
        
        str_filter = str_filter + (ExceptWordsInFinishedChecksSameDictionary ? " AND NOT w.Word_ID IN (SELECT DISTINCT i.Word_ID FROM inputs AS i INNER JOIN checks AS c ON c.Check_ID = i.Check_ID  WHERE i.Dict_ID = :dictID AND c.Check_Finished = TRUE AND c.Check_Deleted = FALSE AND c.User_ID = :userID) " : "");
        str_filter = str_filter + (ExceptWordsInFinishedChecks ? " AND NOT w.Word_Spelling IN (SELECT DISTINCT i.Word_Spelling FROM inputs AS i INNER JOIN checks AS c ON c.Check_ID = i.Check_ID  WHERE c.Check_Finished = TRUE AND c.Check_Deleted = FALSE AND c.User_ID = :userID) " : "");
        
        String query_str = "SELECT ";
        query_str = query_str + "w.Word_ID AS WordID, ";
        query_str = query_str + "w.Dict_ID AS DictID ";
        /*
        query_str = query_str + "w.Word_Spelling AS WordSpelling, ";
        query_str = query_str + "w.Word_Translation AS WordTranslation, ";
        query_str = query_str + "w.Word_Description AS WordDescription, ";
        query_str = query_str + "w.Word_Random_Number AS WordRandomNumber, ";
        query_str = query_str + "w.Word_Deleted AS WordDeleted, ";
        query_str = query_str + "w.Word_HasSound AS WordHasSound ";
        */
        
        query_str = query_str + "FROM words AS w ";
        query_str = query_str + "WHERE w.Dict_ID = :dictID  AND w.Word_Deleted = FALSE AND NOT w.Word_ID IN ";
        query_str = query_str + "(SELECT i.Word_ID FROM inputs AS i WHERE i.Check_ID = :checkID AND i.Pass_Number = 1) ";
        
        query_str = query_str + str_filter;
        query_str = query_str + "ORDER BY w." + str_order + " ";
        query_str = query_str + "LIMIT 1 ";
        
        //System.out.println("--------------------str_query = " + query_str);
        
        Query query = em.createNativeQuery(query_str);
        query.setParameter("checkID", check.getCheckID());
        query.setParameter("dictID", check.getDictID());
        
        if (ExceptWordsInFinishedChecksSameDictionary || ExceptWordsInFinishedChecks){
        	query.setParameter("userID", check.getUserID());
        };	
        
        List<Object[]> list1 = query.getResultList();        
        
        List<Word> list = new ArrayList<Word>();
        
        
        for (int i = 0; i < list1.size(); i++) {
        	/*
            Word w = new Word();
            Dictionary dict = new Dictionary();
            
            w.setWordID((Integer)list1.get(i)[0]);
            w.setDictID((Integer)list1.get(i)[1]);
            w.setWordSpelling((String)list1.get(i)[2]);                        
            w.setWordTranslation((String)list1.get(i)[3]);                        
            w.setWordDescription((String)list1.get(i)[4]);                        
            w.setWordRandomNumber((Integer)list1.get(i)[5]);                        
            w.setWordDeleted((Integer)list1.get(i)[6]==1);                        
            w.setWordHasSound((Integer)list1.get(i)[7]==1);                        
            
            dict.setDictID((Integer)list1.get(i)[1]);
            
            w.setWordDict(dict);
            
            
            list.add(w);
            
            */
            
            Integer w2_id = (Integer)list1.get(i)[0];
            List<Word> list2 = DAO_Word.findByWordID(w2_id);
            
            for (Word w2:list2){
            	list.add(w2);
            }
            
        }    
        
        //System.out.println("--------------------list.size()" + list.size());
        
        return list;
    }   

    
    @Override
    public Integer NumberOfInputWordsFromDictionaryWithParams(Dictionary dict, boolean WithSound, boolean WithoutSound, boolean ExceptWordsInFinishedChecks, boolean ExceptWordsInFinishedChecksSameDictionary, User user){
    	
    	//System.out.println("--------------------NumberOfInputWordsFromDictionaryWithParams");
    	
        String str_filter = "";
        str_filter = str_filter + (WithSound && !WithoutSound ? " AND w.Word_HasSound = TRUE " : "");
        str_filter = str_filter + (!WithSound && WithoutSound ? " AND w.Word_HasSound = FALSE " : "");
        str_filter = str_filter + (!WithSound && !WithoutSound ? " AND FALSE = TRUE " : "");
        
        str_filter = str_filter + (ExceptWordsInFinishedChecksSameDictionary ? " AND NOT w.Word_ID IN (SELECT DISTINCT i.Word_ID FROM inputs AS i INNER JOIN checks AS c ON c.Check_ID = i.Check_ID  WHERE i.Dict_ID = :dictID AND c.Check_Finished = TRUE AND c.Check_Deleted = FALSE AND c.User_ID = :userID) " : "");
        str_filter = str_filter + (ExceptWordsInFinishedChecks ? " AND NOT w.Word_Spelling IN (SELECT DISTINCT i.Word_Spelling FROM inputs AS i INNER JOIN checks AS c ON c.Check_ID = i.Check_ID  WHERE c.Check_Finished = TRUE AND c.Check_Deleted = FALSE AND c.User_ID = :userID) " : "");
        
        
        String query_str = "SELECT COUNT(*) as count ";
        query_str = query_str + "FROM words AS w ";
        query_str = query_str + "WHERE w.Dict_ID = :dictID  AND w.Word_Deleted = FALSE ";
        query_str = query_str + str_filter;
        
        //System.out.println("--------------------str_query = " + query_str);
        
        
        Query query = em.createNativeQuery(query_str);
        query.setParameter("dictID", dict.getDictID());
        
        if (ExceptWordsInFinishedChecksSameDictionary || ExceptWordsInFinishedChecks){
        	query.setParameter("userID", user.getUserID());
        };	
        
        
        //int count = (int)(long)query.getSingleResult();
        int count = (int)((Number)query.getSingleResult()).longValue();

        
        //System.out.println("--------------------count = " + count);
    	
    	return count;
    }   
    

    @Override
    public List<Input> getPreviousInput(Check check, Integer passNumber, Integer inputID){
    	
    	
        String query_str = "SELECT ";
        query_str = query_str + "i.Input_ID AS inputID, ";
        query_str = query_str + "i.Check_ID AS checkID ";        
        query_str = query_str + "FROM inputs AS i ";
        query_str = query_str + "WHERE i.Check_ID = :checkID  AND i.Pass_Number = :passNumber AND (:inputID IS NULL OR i.Input_ID < :inputID) ";
        query_str = query_str + "ORDER BY i.Input_ID DESC ";
        query_str = query_str + "LIMIT 1 ";
        
        //System.out.println("getPreviousInput --------------------str_query = " + query_str);
        
        Query query = em.createNativeQuery(query_str);
        query.setParameter("checkID", check.getCheckID());
        query.setParameter("passNumber", passNumber);
        query.setParameter("inputID", inputID);
        
        List<Object[]> list1 = query.getResultList();        
        
        
        Integer Previous_Input_ID = null;
        
        if (!list1.isEmpty()){
        	Previous_Input_ID = (Integer)list1.get(0)[0];
        };

        List<Input> list = findByInputID(Previous_Input_ID);        
        
        return list;
    }   

    @Override
    public List<Input> getNextInput(Check check, Integer passNumber, Integer inputID){
    	
    	
        String query_str = "SELECT ";
        query_str = query_str + "i.Input_ID AS inputID, ";
        query_str = query_str + "i.Check_ID AS checkID ";        
        query_str = query_str + "FROM inputs AS i ";
        query_str = query_str + "WHERE i.Check_ID = :checkID  AND i.Pass_Number = :passNumber AND (:inputID IS NULL OR i.Input_ID > :inputID) ";
        query_str = query_str + "ORDER BY i.Input_ID ";
        query_str = query_str + "LIMIT 1 ";
        
        //System.out.println("getNextInput --------------------str_query = " + query_str);
        
        Query query = em.createNativeQuery(query_str);
        query.setParameter("checkID", check.getCheckID());
        query.setParameter("passNumber", passNumber);
        query.setParameter("inputID", inputID);
        
        List<Object[]> list1 = query.getResultList();        
        
        
        Integer Next_Input_ID = null;
        
        if (!list1.isEmpty()){
        	Next_Input_ID = (Integer)list1.get(0)[0];
        };

        List<Input> list = findByInputID(Next_Input_ID);        
        
        return list;
    }   
    

    
    @Override
    public Integer NumberOfPassedWordsByPassNumber(Check check, Integer passNumber){
        Query query = em.createNamedQuery("Input.NumberOfPassedWordsByPassNumber");
        query.setParameter("check", check);
        query.setParameter("passNumber", passNumber);
        
        int count = (int)(long)query.getSingleResult();
        
        return count;
    }   
    
}
