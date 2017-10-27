package SpellRefiner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.Stateless;
import java.util.List;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.EJB;


@Stateless
public class DAO_Word implements DAO_Interface_Word{    
    @PersistenceContext(unitName = "SpellRefiner")
    private EntityManager em;        
    
    
    @Override
    public Integer SaveNew(Word entity){    
       em.persist(entity);    
       em.flush();
       //Integer id = entity.getWordID();
       //System.out.println("DAO_Word --------------------id = " + id);
       
       return entity.getWordID();
    }            
    
    
    @Override
    public void Update(Word entity){    
       em.merge(entity);        
    }            

    @Override
    public void Delete(Word entity){    
       Word entity1 = em.merge(entity);        
       em.remove(entity1);        
    }            
    
    /*
    @Override
    public Word Get(Integer id){    
        Word u = em.find(Word.class, id);
        return u;
    } 
    */

    @Override
    public List<Word> findAll(){    
        Query query = em.createNamedQuery("Word.findAll");
        List<Word> list = query.getResultList();
        
        return list;
    }   
    
    
    /*
    @Override
    public List<Word> findByWordID(Long wordID){    
        Query query = em.createNamedQuery("Word.findByWordID");
        query.setParameter("wordID", wordID);
        List<Word> list = query.getResultList();
        
        return list;
    }   
    */
    
    @Override
    public List<Word> DictionaryWords(Dictionary dict){    
        Query query = em.createNamedQuery("Word.DictionaryWords");
        query.setParameter("dict", dict);
        List<Word> list = query.getResultList();
        
        return list;
    }   
    
    
    @Override
    public List<Word> findByDictionaryAndSpelling(Dictionary dict, String wordSpelling){    
        Query query = em.createNamedQuery("Word.findByDictionaryAndSpelling");
        query.setParameter("dict", dict);
        query.setParameter("wordSpelling", wordSpelling);
        
        List<Word> list = query.getResultList();
        
        return list;
    }   
    

    
    @Override
    public List<Word> findByWordID(Integer wordID){    
        Query query = em.createNamedQuery("Word.findByWordID");
        query.setParameter("wordID", wordID);
        List<Word> list = query.getResultList();
        
        return list;
        
    }   
    
    
    @Override
    public Word GetWordByID(Integer wordID){  
        List<Word> list = findByWordID(wordID);
        //Query query = em.createNamedQuery("Word.findByWordID");
        //query.setParameter("wordID", wordID);
        //List<Word> list = query.getResultList();
        if (list.isEmpty()){
            return null;  
        };
        
        return list.get(0);
        
    }   
    
    
    @Override
    public byte[] GetWordSoundByWordID(Integer wordID){    
        Query query = em.createNamedQuery("Word.GetWordSoundByWordID");
        query.setParameter("wordID", wordID);
        List<byte[]> list = query.getResultList();

        if (list.isEmpty()){
            return null;  
        };
        
        return list.get(0);
        
    }   
    
    
    @Override
    public void SetWordSoundByWordID(Integer wordID, byte[] wordSound, Boolean wordHasSound){    
        Date date = new Date();
        
        //Query query = em.createNamedQuery("Word.UpdateWordSoundByWordID");
        //query.setParameter("wordID", wordID);
        //query.setParameter("wordSound", wordSound);
        //query.setParameter("wordHasSound", wordHasSound);
        //query.setParameter("wordEditionDateTime", date);
        
        //query.executeUpdate();
        
      //String str_query = "UPDATE words SET Word_Sound = :wordSound, Word_HasSound=:wordHasSound, Word_Edition_DateTime=:wordEditionDateTime WHERE Word_ID = :wordID";
      //Query query1 = em.createNativeQuery(str_query);
        
        
        String str_query = "UPDATE Word w SET w.wordSound = :wordSound, w.wordHasSound=:wordHasSound, w.wordEditionDateTime=:wordEditionDateTime WHERE w.wordID = :wordID";        
        
        
        Query query = em.createQuery(str_query);
        query.setParameter("wordID", wordID);
        query.setParameter("wordSound", wordSound);
        query.setParameter("wordHasSound", wordHasSound);
        query.setParameter("wordEditionDateTime", date);
        query.executeUpdate();        
        
    }   


    private String getQueryString_Tail_DictionaryWordsWithParams(Dictionary dict, String wordsOrder, boolean ascendingOrder, boolean DeletedOnly, boolean WithSound, boolean WithoutSound, boolean WithErrorsOnly, boolean WithNotProcessedErrorsOnly, String WordSpellingFilter){    
        String str_order = "order by w." + wordsOrder + " " + ((ascendingOrder) ? "asc": "desc");
        String str_filter = "";
        str_filter = str_filter + (DeletedOnly ? " and w.wordDeleted = true" : "");
        str_filter = str_filter + (WithSound && !WithoutSound ? " and w.wordHasSound = true" : "");
        str_filter = str_filter + (!WithSound && WithoutSound ? " and w.wordHasSound = false" : "");
        str_filter = str_filter + (!WithSound && !WithoutSound ? " and false = true" : "");
        str_filter = str_filter + (WithErrorsOnly ? " and w.wordID in (SELECT de.wordID FROM DictionaryError de)" : "");
        str_filter = str_filter + (WithNotProcessedErrorsOnly ? " and w.wordID in (SELECT de.wordID FROM DictionaryError de WHERE de.dictErrorProcessed = false)" : "");
        //str_filter = str_filter + (!WordSpellingFilter.isEmpty() ? " and w.wordSpelling like :wordSpelling" : "");
        str_filter = str_filter + " and (:filter_wordSpelling = false or w.wordSpelling like :wordSpelling)";
        
        
        
        
        String str_query = "FROM Word w WHERE w.wordDict = :dict"  
                + " " + str_filter
                + " " + str_order;
        
        return str_query;
    }   
    
    
    private String getQueryString_DictionaryWordsWithParams(Dictionary dict, String wordsOrder, boolean ascendingOrder, boolean DeletedOnly, boolean WithSound, boolean WithoutSound, boolean WithErrorsOnly, boolean WithNotProcessedErrorsOnly, String WordSpellingFilter){    
        String str_query = "SELECT w " + getQueryString_Tail_DictionaryWordsWithParams(dict, wordsOrder, ascendingOrder, DeletedOnly, WithSound, WithoutSound, WithErrorsOnly, WithNotProcessedErrorsOnly, WordSpellingFilter);                
        
        return str_query;
    }   

    private String getQueryString_DictionaryWordsWithParams_Count(Dictionary dict, String wordsOrder, boolean ascendingOrder, boolean DeletedOnly, boolean WithSound, boolean WithoutSound, boolean WithErrorsOnly, boolean WithNotProcessedErrorsOnly){    
        String str_query = "SELECT COUNT(w) " + getQueryString_Tail_DictionaryWordsWithParams(dict, wordsOrder, ascendingOrder, DeletedOnly, WithSound, WithoutSound, WithErrorsOnly, WithNotProcessedErrorsOnly, "");                
        
        return str_query;
    }   
    
    
    @Override
    public List<Word> DictionaryWordsWithParams(Dictionary dict, String wordsOrder, boolean ascendingOrder, boolean DeletedOnly, boolean WithSound, boolean WithoutSound, boolean WithErrorsOnly, boolean WithNotProcessedErrorsOnly, String WordSpellingFilter){    
        
        String str_query = getQueryString_DictionaryWordsWithParams(dict, wordsOrder, ascendingOrder, DeletedOnly, WithSound, WithoutSound, WithErrorsOnly, WithNotProcessedErrorsOnly, WordSpellingFilter);
        
        //System.out.println("--------------------str_query = " + str_query);
        
        Query query = em.createQuery(str_query);
        query.setParameter("dict", dict);
        query.setParameter("filter_wordSpelling", !WordSpellingFilter.isEmpty());
        query.setParameter("wordSpelling", "" + WordSpellingFilter + "%");
        
        //query.setParameter("wordDeleted", wordDeleted);
        List<Word> list = query.getResultList();
        
        return list;
    }   

    @Override
    public List<Word> DictionaryWordsWithParamsInInterval(Dictionary dict, String wordsOrder, boolean ascendingOrder, boolean DeletedOnly, boolean WithSound, boolean WithoutSound, boolean WithErrorsOnly, boolean WithNotProcessedErrorsOnly, int FirstRecord, int NumberOfRecords){    
        
        String str_query = getQueryString_DictionaryWordsWithParams(dict, wordsOrder, ascendingOrder, DeletedOnly, WithSound, WithoutSound, WithErrorsOnly, WithNotProcessedErrorsOnly, "");
        //System.out.println("--------------------str_query = " + str_query);
        
        Query query = em.createQuery(str_query);                
        query.setFirstResult(FirstRecord);
        query.setMaxResults(NumberOfRecords);
        

        query.setParameter("dict", dict);
        List<Word> list = query.getResultList();
        
        return list;
    }   
    
    @Override
    public Integer DictionaryWordsWithParams_Count(Dictionary dict, String wordsOrder, boolean ascendingOrder, boolean DeletedOnly, boolean WithSound, boolean WithoutSound, boolean WithErrorsOnly, boolean WithNotProcessedErrorsOnly){    
        
        String str_query = getQueryString_DictionaryWordsWithParams_Count(dict, wordsOrder, ascendingOrder, DeletedOnly, WithSound, WithoutSound, WithErrorsOnly, WithNotProcessedErrorsOnly);
        
        //System.out.println("--------------------str_query = " + str_query);
        
        Query query = em.createQuery(str_query);
        query.setParameter("dict", dict);
        int count = (int)(long)query.getSingleResult();
        
        return count;
    }   
    
    

    //@Override
    public List<Word> DictionaryWordsWithParamsInInterval1(Dictionary dict, String wordsOrder, boolean ascendingOrder, boolean DeletedOnly, boolean WithSound, boolean WithoutSound, int FirstRecord, int NumberOfRecords){    
        
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
        
        String str_order = "" + OrderField + " " + ((ascendingOrder) ? "DESC": "ASC");
        String str_order_opposite = "" + OrderField + " " + ((ascendingOrder) ? "ASC": "DESC");        
        
        String str_filter = "";
        str_filter = str_filter + (DeletedOnly ? " and Words.Word_Deleted = true" : "");
        str_filter = str_filter + (WithSound && !WithoutSound ? " and Words.Word_HasSound = true" : "");
        str_filter = str_filter + (!WithSound && WithoutSound ? " and Words.Word_HasSound = false" : "");
        str_filter = str_filter + (!WithSound && !WithoutSound ? " and false = true" : "");
        
        
        
        String str_query = 
                "SELECT Table2.* FROM ("
                + " " + "SELECT Table1.* FROM ("
                + " " + "SELECT Word_ID, Dict_ID, Word_Spelling, Word_Translation, Word_Description, Word_Random_Number, Word_Deleted, Word_HasSound FROM Words WHERE Words.Dict_ID=?dict_id"  
                + " " + str_filter
                + " " + "ORDER BY"
                + " " + str_order
                + " " + "LIMIT ?LastRecord "
                //+ " " + "LIMIT 8 "
                + " " + ") AS Table1"        
                + " " + "ORDER BY"
                + " " + str_order_opposite
                + " " + "LIMIT ?NumberOfRecords" 
                //+ " " + "LIMIT 3" 
                + " " + ") AS Table2"        
                + " " + "ORDER BY"
                + " " + str_order
                ;
        
        //str_query = "SELECT w FROM Word w WHERE w.wordDict = :dict LIMIT 10";  
        //System.out.println("--------------------str_query = " + str_query);
        
        Query query = em.createNativeQuery(str_query, Word.class);
        //query.setParameter(1, dict.getDictID());
        query.setParameter("dict_id", dict.getDictID());
        query.setParameter("LastRecord", FirstRecord + NumberOfRecords);
        query.setParameter("NumberOfRecords", NumberOfRecords);
        List<Word> list = query.getResultList();
        
        return list;
    }   
    
    
    @Override
    public Word GetLastWordWithoutSound(Dictionary dict){    
        Query query = em.createNamedQuery("Word.findWordsWithoutSound");
        query.setParameter("dict", dict);
        query.setMaxResults(1);
        
        List<Word> list = query.getResultList();
        
        if (list.isEmpty()){
            return null;  
        };
        
        return list.get(0);
    }   
    
    @Override
    public List<Word> findWordsByDictionaryByEditionDateTime(Dictionary dict){    
        //System.out.println("--------------------findWordsByDictionaryByEditionDateTime dict = " + dict);
        
        Query query = em.createNamedQuery("Word.findWordsByDictionaryByEditionDateTime");
        query.setParameter("dict", dict);
        query.setMaxResults(10);
        
        List<Word> list = query.getResultList();
        
        return list;
    }   
    
    
    @Override
    public Word GetLastWordWithoutSoundWithParams(Dictionary dict, String wordsOrder, boolean ascendingOrder){
    	//System.out.println("--------------------GetLastWordWithoutSoundWithParams");
    	//System.out.println("dict=" + dict);
    	
    	if (dict==null){
    		return null;
    	};
    	
    	//System.out.println("dict.getDictID()=" + dict.getDictID());
    	
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
        
        String str_order = "" + OrderField + " " + ((ascendingOrder) ? "ASC": "DESC");
        str_order = "Word_Postponed, " + str_order + ", Word_ID";
        
        String str_query ="SELECT Word_ID, Word_Spelling FROM words AS w WHERE w.Dict_ID = :dict_id and w.Word_HasSound = false and w.Word_Deleted = false ORDER BY " + str_order + " LIMIT 1";
        	
        //System.out.println("--------------------str_query = " + str_query);
        
        Query query = em.createNativeQuery(str_query);
        query.setParameter("dict_id", dict.getDictID());
        List<Object[]> list1 = query.getResultList();
        
        if (list1.isEmpty()){
            return null;  
        };
        
        Integer last_word_id = (Integer)list1.get(0)[0];
        
        List<Word> list = findByWordID(last_word_id);
        
        if (list.isEmpty()){
            return null;  
        };
        
        return list.get(0);
    }   

    
    
    
    @Override
    public boolean CheckPermissionForWord(Integer word_id, Integer user_id){
    	//System.out.println("--------------------CheckPermissionForWord");
    	//System.out.println("word_id=" + word_id);
    	//System.out.println("user_id=" + user_id);
    	
        String str_query ="SELECT w.Word_ID, w.Dict_ID, d.User_ID FROM words AS w INNER JOIN dictionaries as d ON w.Dict_ID = d.Dict_ID WHERE w.Word_ID = :word_id AND d.User_ID = :user_id";
    	
        //System.out.println("--------------------str_query 1 = " + str_query);
        
        Query query = em.createNativeQuery(str_query);
        query.setParameter("word_id", word_id);
        query.setParameter("user_id", user_id);
        List<Object[]> list1 = query.getResultList();
        
        //System.out.println("--------------------list1.size = " + list1.size());
        
        if (!list1.isEmpty()){
            return true;  
        };
        
        str_query ="SELECT w.Word_ID, w.Dict_ID, dp.User_ID FROM words AS w INNER JOIN dictionarypermissions AS dp ON w.Dict_ID = dp.Dict_ID WHERE dp.DictPerm_WriteAccess=1 AND w.Word_ID = :word_id AND dp.User_ID = :user_id";
    	
        //System.out.println("--------------------str_query 2 = " + str_query);
        
        query = em.createNativeQuery(str_query);
        query.setParameter("word_id", word_id);
        query.setParameter("user_id", user_id);
        List<Object[]> list2 = query.getResultList();
        
        //System.out.println("--------------------list2.size = " + list2.size());
        
        if (!list2.isEmpty()){
            return true;  
        };
        
    	
        return false;
    }   
    

}
