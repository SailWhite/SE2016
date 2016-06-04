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
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * Test of Answer-relateds process method, of class Server.
     */
    @Test
    public void test_3_Answer() {
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
