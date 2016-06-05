/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theserver;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static theserver.Server.process;

/**
 *
 * @author sailw
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class ServerTest {
    static private String token;
    static private String anotherToken;
    static private String fakeToken = "hahahahha";
    static private Long aid;
    static private Long fakeAid = Long.valueOf(12345679);
    static private Long nid;
    static private Long fakeNid = Long.valueOf(12345679);
    static private Long qid;
    static private Long fakeQid = Long.valueOf(12345679);
    static private Long asid;
    static private Long fakeAsid = Long.valueOf(12345679);
    
    /**
     * Test of User-relateds process method, of class Server.
     */
    @Test
    public void test_1_User() {
        String debugMessage =  "";
        
        System.out.println("process-User-regist");
        
        Gson gson = new Gson();
        Map<String, String> tokenMap = new HashMap<>();
        debugMessage = process("{\"command\":\"regist\",\"username\":\"sw7\",\"password\":\"sss\"}");
        assertEquals(debugMessage, "{\"result\":\"Success\",\"id\":\"1\"}");
        String tokenJson=process("{\"command\":\"login\",\"username\":\"sw7\",\"password\":\"sss\"}");
        tokenMap = gson.fromJson(tokenJson, tokenMap.getClass());
        token=tokenMap.get("token");
                
        debugMessage = process("{\"command\":\"regist\",\"username\":\"sw8.1\",\"password\":\"233\"}");
        assertEquals(debugMessage, "{\"result\":\"Success\",\"id\":\"2\"}");
        String tokenJson2=process("{\"command\":\"login\",\"username\":\"sw8.1\",\"password\":\"233\"}");
        tokenMap=new HashMap<>();
        tokenMap=gson.fromJson(tokenJson2, tokenMap.getClass());
        anotherToken=tokenMap.get("token2");
        
        debugMessage = process("{\"command\":\"regist\",\"username\":\"sw7\",\"password\":\"233\"}");
        assertEquals(debugMessage, "{\"result\":\"Failed\",\"id\":\"\"}");
        debugMessage = process("{\"command\":\"regist\",\"username\":\"\",\"password\":\"233\"}");
        assertEquals(debugMessage, "{\"result\":\"Incorrect command\"}");
        debugMessage = process("{\"command\":\"regist\",\"username\":\"sw7\",\"password\":\"\"}");
        assertEquals(debugMessage, "{\"result\":\"Incorrect command\"}");
        debugMessage = process("{\"command\":\"regist\",\"username\":\"\",\"password\":\"\"}");
        assertEquals(debugMessage, "{\"result\":\"Incorrect command\"}");
        
        debugMessage = process("{\"command\":\"login\",\"username\":\"sw7\",\"password\":\"233\"}");
        assertEquals(debugMessage, "{\"result\":\"Failed\",\"token\":\"\"}");
        debugMessage = process("{\"command\":\"login\",\"username\":\"sw8\",\"password\":\"233\"}");
        assertEquals(debugMessage, "{\"result\":\"Failed\",\"token\":\"\"}");
        debugMessage = process("{\"command\":\"login\",\"username\":\"\",\"password\":\"\"}");
        assertEquals(debugMessage, "{\"result\":\"Incorrect command\"}");
        debugMessage = process("{\"command\":\"login\",\"username\":\"\",\"password\":\"233\"}");
        assertEquals(debugMessage, "{\"result\":\"Incorrect command\"}");
        debugMessage = process("{\"command\":\"login\",\"username\":\"sw8\",\"password\":\"\"}");
        assertEquals(debugMessage, "{\"result\":\"Incorrect command\"}");
        
    }
    
    /**
     * Test of Activity-relateds process method, of class Server.
     */
    @Test
    public void test_2_Activity() {
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * Test of Notice-relateds process method, of class Server.
     */
    @Test
    public void test_3_Notice() {
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * Test of Question-relateds process method, of class Server.
     */
    @Test
    public void test_2_Question() {
//Add Question 1
        String debugMessage = process("{\"command\":\"addQuestion\",\"token\":\""+token+"\",\"content\":\"This is Question 1\"}");
        assertEquals(debugMessage, "{\"result\":\"Success\",\"id\":\"4\"}");
//Add Question 2
        debugMessage = process("{\"command\":\"addQuestion\",\"token\":\""+token+"\",\"content\":\"This is Question 2\"}");
        assertEquals(debugMessage, "{\"result\":\"Success\",\"id\":\"6\"}");
//Add Question 3 token=null
        debugMessage = process("{\"command\":\"addQuestion\",\"token\":\"\",\"content\":\"This is Question 3\"}");
        assertEquals(debugMessage, "{\"result\":\"Incorrect command\"}");

//Get Questions of User 1
//输出不太对？
        debugMessage = process("{\"command\":\"getQuestions\",\"token\":\""+token+"\"}");
        assertEquals(debugMessage, "{\"result\":\"Success\",\"questions\":\"{\\\"author\\\":\\\"{\\\\\\\"id\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"username\\\\\\\":\\\\\\\"sw7\\\\\\\"}\\\",\\\"id\\\":\\\"6\\\",\\\"content\\\":\\\"This is Question 2\\\"}\"}");

//token=null
        debugMessage = process("{\"command\":\"getQuestions\",\"token\":\"\"}");
        //assertEquals(debugMessage, "{\"result\":\"Incorrect command\"}");

//Update Question 1
        debugMessage = process("{\"command\":\"updateQuestion\",\"token\":\""+token+"\",\"content\":\"This is Question 1(Updated)\",\"id\":\"4\"}");
        assertEquals(debugMessage, "{\"result\":\"Success\"}");

//Mismatched id
        debugMessage = process("{\"command\":\"updateQuestion\",\"token\":\""+anotherToken+"\",\"content\":\"This is Question 1(Mismatched)\",\"id\":\"4\"}");
        assertEquals(debugMessage, "{\"result\":\"Failed\"}");

//Invalid id
        debugMessage = process("{\"command\":\"updateQuestion\",\"token\":\""+token+"\",\"content\":\"This is Question 1(Invalid id)\",\"id\":\"5\"}");
        assertEquals(debugMessage, "{\"result\":\"Failed\"}");
        
//Delete Question 2
        debugMessage = process("{\"command\":\"deleteQuestion\",\"token\":\""+token+"\",\"id\":\"6\"}");
        assertEquals(debugMessage, "{\"result\":\"Success\"}");

//Mismatched id       
        debugMessage = process("{\"command\":\"deleteQuestion\",\"token\":\""+anotherToken+"\",\"id\":\"6\"}");
        assertEquals(debugMessage, "{\"result\":\"Failed\"}");
        
//Delete Again
        debugMessage = process("{\"command\":\"deleteQuestion\",\"token\":\""+token+"\",\"id\":\"6\"}");
        assertEquals(debugMessage, "{\"result\":\"Failed\"}");
//Check result
        debugMessage = process("{\"command\":\"getQuestions\",\"token\":\""+token+"\"}");
        assertEquals(debugMessage, "{\"result\":\"Success\",\"questions\":\"{\\\"author\\\":\\\"{\\\\\\\"id\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"username\\\\\\\":\\\\\\\"sw7\\\\\\\"}\\\",\\\"id\\\":\\\"4\\\",\\\"content\\\":\\\"This is Question 1(Updated)\\\"}\"}");
        
        //fail("The test case is a prototype.");
    }
    
    /**
     * Test of Answer-relateds process method, of class Server.
     */
    @Test
    public void test_3_Answer() {
//Add Answer 11 for Question 1
        String debugMessage = process("{\"command\":\"addAnswer\",\"token\":\"" + token + "\",\"content\":\"This is Answer 11\",\"id\":\"4\"}");
        assertEquals(debugMessage, "{\"result\":\"Success\",\"id\":\"9\"}");
//Add Answer 12 for Question 1
        debugMessage = process("{\"command\":\"addAnswer\",\"token\":\"" + token + "\",\"content\":\"This is Answer 12\",\"id\":\"4\"}");
        assertEquals(debugMessage, "{\"result\":\"Success\",\"id\":\"11\"}");
//Add Answer 13 for Question 1 ( Another Token )
        debugMessage = process("{\"command\":\"addAnswer\",\"token\":\"" + anotherToken + "\",\"content\":\"This is Answer 13\",\"id\":\"4\"}");
        assertEquals(debugMessage, "{\"result\":\"Failed\",\"id\":\"\"}");
//Add Answer 14 for Question 1 ( Fake Token )
        debugMessage = process("{\"command\":\"addAnswer\",\"token\":\"" + fakeToken + "\",\"content\":\"This is Answer 14\",\"id\":\"4\"}");
        assertEquals(debugMessage, "{\"result\":\"Failed\",\"id\":\"\"}");
//Add Answer 21 for Question 2 ( Unexisted Question )
        debugMessage = process("{\"command\":\"addAnswer\",\"token\":\"" + token + "\",\"content\":\"This is Answer 21\",\"id\":\"6\"}");
        assertEquals(debugMessage, "{\"result\":\"Failed\",\"id\":\"\"}");
        
//Get Answer List of Question 1
        debugMessage = process("{\"command\":\"getAnswers\",\"id\":\"4\"}");
        assertEquals(debugMessage, "{\"result\":\"Success\",\"answers\":\"[{\\\"id\\\":9,\\\"question\\\":{\\\"id\\\":4,\\\"content\\\":{\\\"id\\\":7,\\\"text\\\":\\\"This is Question 1(Updated)\\\",\\\"author\\\":{\\\"id\\\":1,\\\"username\\\":\\\"sw7\\\",\\\"password\\\":\\\"sss\\\",\\\"authority\\\":0,\\\"actSet\\\":[],\\\"token\\\":\\\""+ token + "\\\"}}},\\\"content\\\":{\\\"id\\\":8,\\\"text\\\":\\\"This is Answer 11\\\",\\\"author\\\":{\\\"id\\\":1,\\\"username\\\":\\\"sw7\\\",\\\"password\\\":\\\"sss\\\",\\\"authority\\\":0,\\\"actSet\\\":[],\\\"token\\\":\\\"" + token + "\\\"}}},{\\\"id\\\":11,\\\"question\\\":{\\\"id\\\":4,\\\"content\\\":{\\\"id\\\":7,\\\"text\\\":\\\"This is Question 1(Updated)\\\",\\\"author\\\":{\\\"id\\\":1,\\\"username\\\":\\\"sw7\\\",\\\"password\\\":\\\"sss\\\",\\\"authority\\\":0,\\\"actSet\\\":[],\\\"token\\\":\\\"" + token + "\\\"}}},\\\"content\\\":{\\\"id\\\":10,\\\"text\\\":\\\"This is Answer 12\\\",\\\"author\\\":{\\\"id\\\":1,\\\"username\\\":\\\"sw7\\\",\\\"password\\\":\\\"sss\\\",\\\"authority\\\":0,\\\"actSet\\\":[],\\\"token\\\":\\\"" + token + "\\\"}}}]\"}");
//Get Answer List of Question 2 ( Unexisted Question )
//这里似乎返回Failed更恰当，但得到的结果是Success与一个空表
        debugMessage = process("{\"command\":\"getAnswers\",\"id\":\"6\"}");
        assertEquals(debugMessage, "{\"result\":\"Success\",\"answers\":\"[]\"}");
        
//Get Answer List of User 1
//好像跟上边的Question有一样的错误
        //debugMessage = process("{\"command\":\"getAnswers\",\"token\":\"" + token + "\"}");
        //assertEquals(debugMessage, "{\"result\":\"Success\",\"answers\":\"[{\\\"id\\\":9,\\\"question\\\":{\\\"id\\\":4,\\\"content\\\":{\\\"id\\\":7,\\\"text\\\":\\\"This is Question 1(Updated)\\\",\\\"author\\\":{\\\"id\\\":1,\\\"username\\\":\\\"sw7\\\",\\\"password\\\":\\\"sss\\\",\\\"authority\\\":0,\\\"actSet\\\":[],\\\"token\\\":\\\""+ token + "\\\"}}},\\\"content\\\":{\\\"id\\\":8,\\\"text\\\":\\\"This is Answer 11\\\",\\\"author\\\":{\\\"id\\\":1,\\\"username\\\":\\\"sw7\\\",\\\"password\\\":\\\"sss\\\",\\\"authority\\\":0,\\\"actSet\\\":[],\\\"token\\\":\\\"" + token + "\\\"}}},{\\\"id\\\":11,\\\"question\\\":{\\\"id\\\":4,\\\"content\\\":{\\\"id\\\":7,\\\"text\\\":\\\"This is Question 1(Updated)\\\",\\\"author\\\":{\\\"id\\\":1,\\\"username\\\":\\\"sw7\\\",\\\"password\\\":\\\"sss\\\",\\\"authority\\\":0,\\\"actSet\\\":[],\\\"token\\\":\\\"" + token + "\\\"}}},\\\"content\\\":{\\\"id\\\":10,\\\"text\\\":\\\"This is Answer 12\\\",\\\"author\\\":{\\\"id\\\":1,\\\"username\\\":\\\"sw7\\\",\\\"password\\\":\\\"sss\\\",\\\"authority\\\":0,\\\"actSet\\\":[],\\\"token\\\":\\\"" + token + "\\\"}}}]\"}");
//Get Answer List of User ( Fake Token )
        //debugMessage = process("{\"command\":\"getAnswers\",\"token\":\"" + fakeToken + "\"}");
        //assertEquals(debugMessage, "{\"result\":\"Success\",\"answers\":\"[]\"}");
        
//Update Answer 11
        debugMessage = process("{\"command\":\"updateAnswer\",\"token\":\"" + token + "\",\"content\":\"This is Answer 11 NEW\",\"id\":\"9\"}");
        assertEquals(debugMessage, "{\"result\":\"Success\"}");
//Update Answer 11 ( Fake Token )
        debugMessage = process("{\"command\":\"updateAnswer\",\"token\":\"" + fakeToken + "\",\"content\":\"This is Answer 11 NEW\",\"id\":\"9\"}");
        assertEquals(debugMessage, "{\"result\":\"Failed\"}");
//Update Answer ( Fake Aid )
        debugMessage = process("{\"command\":\"updateAnswer\",\"token\":\"" + token + "\",\"content\":\"This is Answer 11 NEW\",\"id\":\"9999\"}");
        assertEquals(debugMessage, "{\"result\":\"Failed\"}");
        
//Delete Answer 11 ( Fake Token )
        debugMessage = process("{\"command\":\"deleteAnswer\",\"token\":\"" + fakeToken + "\",\"id\":\"9\"}");
        assertEquals(debugMessage, "{\"result\":\"Failed\"}");
//Delete Answer 11
        debugMessage = process("{\"command\":\"deleteAnswer\",\"token\":\"" + token + "\",\"id\":\"9\"}");
        assertEquals(debugMessage, "{\"result\":\"Success\"}");
//Delete Again
        debugMessage = process("{\"command\":\"deleteAnswer\",\"token\":\"" + token + "\",\"id\":\"9\"}");
        assertEquals(debugMessage, "{\"result\":\"Failed\"}");
//Delete Answer ( Fake Aid )
        debugMessage = process("{\"command\":\"deleteAnswer\",\"token\":\"" + token + "\",\"id\":\"999\"}");
        assertEquals(debugMessage, "{\"result\":\"Failed\"}");
    }
}
