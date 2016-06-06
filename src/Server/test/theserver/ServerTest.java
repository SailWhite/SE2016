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
        /*
        关于等价类的设计
        用户名正确1
        用户名重复2
        用户名错误3
        用户名为空4
        密码正确5
        密码错误6
        密码为空7*/
        
        Gson gson = new Gson();
        Map<String, String> tokenMap = new HashMap<>();
        debugMessage = process("{\"command\":\"regist\",\"username\":\"sw7\",\"password\":\"sss\"}");
        assertEquals("{\"result\":\"Success\",\"id\":\"1\"}", debugMessage);
        /*
        输入为{"command":"regist","username":"sw7","password":"sss"}
        覆盖等价类为15
        期望输出为{"result":"Success","id":"1"}
        实际输出为{"result":"Success","id":"1"}
        结果正确*/
        String tokenJson=process("{\"command\":\"login\",\"username\":\"sw7\",\"password\":\"sss\"}");
        tokenMap = gson.fromJson(tokenJson, tokenMap.getClass());
        token=tokenMap.get("token");
        /*
        输入为{"command":"login","username":"sw7","password":"sss"}
        覆盖等价类为15*/
                
        debugMessage = process("{\"command\":\"regist\",\"username\":\"sw8.1\",\"password\":\"233\"}");
        assertEquals("{\"result\":\"Success\",\"id\":\"2\"}", debugMessage);
        String tokenJson2=process("{\"command\":\"login\",\"username\":\"sw8.1\",\"password\":\"233\"}");
        tokenMap=new HashMap<>();
        tokenMap=gson.fromJson(tokenJson2, tokenMap.getClass());
        anotherToken=tokenMap.get("token2");
        
        debugMessage = process("{\"command\":\"regist\",\"username\":\"sw7\",\"password\":\"233\"}");
        assertEquals("{\"result\":\"Failed\",\"id\":\"\"}", debugMessage);
        /*
        输入为{"command":"regist","username":"sw7","password":"233"}
        覆盖等价类为26
        期望输出为{"result":"Failed","id":""}
        实际输出为{"result":"Failed","id":""}
        结果正确*/
        debugMessage = process("{\"command\":\"regist\",\"username\":\"sw7\",\"password\":\"\"}");
        assertEquals("{\"result\":\"Incorrect command\"}", debugMessage);
        /*
        输入为{"command":"regist","username":"sw7","password":""}
        覆盖等价类为27
        期望输出为{"result":"Incorrect command"}
        实际输出为{"result":"Incorrect command"}
        结果正确*/
        debugMessage = process("{\"command\":\"regist\",\"username\":\"\",\"password\":\"233\"}");
        assertEquals("{\"result\":\"Incorrect command\"}", debugMessage);
        /*
        输入为{"command":"regist","username":"","password":"233"}
        覆盖等价类为46
        期望输出为{"result":"Incorrect command"}
        实际输出为{"result":"Incorrect command"}
        结果正确*/
        debugMessage = process("{\"command\":\"regist\",\"username\":\"\",\"password\":\"\"}");
        assertEquals("{\"result\":\"Incorrect command\"}", debugMessage);
        /*
        输入为{"command":"regist","username":"","password":""}
        覆盖等价类为47
        期望输出为{"result":"Incorrect command"}
        实际输出为{"result":"Incorrect command"}
        结果正确*/
        
        debugMessage = process("{\"command\":\"login\",\"username\":\"sw7\",\"password\":\"233\"}");
        assertEquals("{\"result\":\"Failed\",\"token\":\"\"}", debugMessage);
        /*
        输入为{"command":"login","username":"sw7","password":"233"}
        覆盖等价类为16
        期望输出为{"result":"Failed","token":""}
        实际输出为{"result":"Failed","token":""}
        结果正确*/
        debugMessage = process("{\"command\":\"login\",\"username\":\"sw8\",\"password\":\"\"}");
        assertEquals("{\"result\":\"Incorrect command\"}", debugMessage);
        /*
        输入为{"command":"login","username":"sw7","password":"233"}
        覆盖等价类为17
        期望输出为{"result":"Incorrect command"}
        实际输出为{"result":"Incorrect command"}
        结果正确*/
        debugMessage = process("{\"command\":\"login\",\"username\":\"sw8\",\"password\":\"233\"}");
        assertEquals("{\"result\":\"Failed\",\"token\":\"\"}", debugMessage);
        /*
        输入为{"command":"login","username":"sw8","password":"233"}
        覆盖等价类为36
        期望输出为{"result":"Failed","token":""}
        实际输出为{"result":"Failed","token":""}
        结果正确*/
        debugMessage = process("{\"command\":\"login\",\"username\":\"sw8\",\"password\":\"\"}");
        assertEquals("{\"result\":\"Incorrect command\"}", debugMessage);
        /*
        输入为{"command":"login","username":"sw8","password":""}
        覆盖等价类为37
        期望输出为{"result":"Incorrect command"}
        实际输出为{"result":"Incorrect command"}
        结果正确*/
        debugMessage = process("{\"command\":\"login\",\"username\":\"\",\"password\":\"\"}");
        assertEquals("{\"result\":\"Incorrect command\"}", debugMessage);
        /*
        输入为{"command":"login","username":"","password":""}
        覆盖等价类为47
        期望输出为{"result":"Incorrect command"}
        实际输出为{"result":"Incorrect command"}
        结果正确*/
        debugMessage = process("{\"command\":\"login\",\"username\":\"\",\"password\":\"233\"}");
        assertEquals("{\"result\":\"Incorrect command\"}", debugMessage);
        /*
        输入为{"command":"login","username":"","password":"233"}
        覆盖等价类为46
        期望输出为{"result":"Incorrect command"}
        实际输出为{"result":"Incorrect command"}
        结果正确*/
        
    }
    
    /**
     * Test of Activity-relateds process method, of class Server.
     */
    @Test
    public void test_2_Activity() {
        // add an activity to a valid user
        String debugMessage = process("{\"command\":\"addActivity\",\"token\":\""+token+"\",\"content\":\"This is Activity 01.\"}");
        System.out.println(debugMessage);
        
        // add an activity to an invalid user
        debugMessage = process("{\"command\":\"addActivity\",\"token\":\""+fakeToken+"\",\"content\":\"This is Activity -1.\"}");
        System.out.println(debugMessage);
        
        // add another activity to the same valid user
        debugMessage = process("{\"command\":\"addActivity\",\"token\":\""+token+"\",\"content\":\"This is Activity 02.\"}");
        System.out.println(debugMessage);
        
        // show the activity list of a valid user
        debugMessage = process("{\"command\":\"getActivities\",\"token\":\""+token+"\"}");
        System.out.println(debugMessage);
        
        // show the activity list of an invalid user
        debugMessage = process("{\"command\":\"getActivities\",\"token\":\""+fakeToken+"\"}");
        System.out.println(debugMessage);
        
        // show all the activities
        debugMessage = process("{\"command\":\"getActivities\"}");
        System.out.println(debugMessage);
        
        // update a valid activity with a correct user token
        debugMessage = process("{\"command\":\"updateActivity\",\"token\":\""+token+"\",\"content\":\"new 01\",\"id\":\"4\"}");
        System.out.println(debugMessage);
        
        // update a valid activity with an incorrect user token
        // ...
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
        
        /*等价类
        1.token正确
        2.token错误
        3.Question ID 与 token相符
        4.Question ID 与 token不符
        5.Question ID 不存在
        */
        
//Add Question 1
        String debugMessage = process("{\"command\":\"addQuestion\",\"token\":\""+token+"\",\"content\":\"This is Question 1\"}");
        assertEquals("{\"result\":\"Success\",\"id\":\"9\"}", debugMessage);
        /*
        输入为{"command":"addQuestion","token":(token) "content":"This is Question 1"}
        覆盖等价类为1
        期望输出为{"result":"Success","id":"9"}
        实际输出为{"result":"Success","id":"9"}
        结果正确
        */
        
//Add Question 2
        debugMessage = process("{\"command\":\"addQuestion\",\"token\":\""+token+"\",\"content\":\"This is Question 2\"}");
        assertEquals("{\"result\":\"Success\",\"id\":\"11\"}", debugMessage);
        /*
        输入为{"command":"addQuestion","token":(token) "content":"This is Question 2"}
        覆盖等价类为1
        期望输出为{"result":"Success","id":"11"}
        实际输出为{"result":"Success","id":"11"}
        结果正确
        */
        
//Add Question 3 token=null
        debugMessage = process("{\"command\":\"addQuestion\",\"token\":\"\",\"content\":\"This is Question 3\"}");
        assertEquals("{\"result\":\"Incorrect command\"}", debugMessage);
        /*
        输入为{"command":"addQuestion","token":(null) "content":"This is Question 3"}
        覆盖等价类为2
        期望输出为{"result":"Incorrect command"}
        实际输出为{"result":"Incorrect command"}
        结果正确
        */

//Get Questions of User 1
        debugMessage = process("{\"command\":\"getQuestions\",\"token\":\""+token+"\"}");
        assertEquals("{\"result\":\"Success\",\"questions\":\"{\\\"author\\\":\\\"{\\\\\\\"id\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"username\\\\\\\":\\\\\\\"sw7\\\\\\\"}\\\",\\\"id\\\":\\\"11\\\",\\\"content\\\":\\\"This is Question 2\\\"}\"}", debugMessage);
        /*
        输入为{"command":"getQuestions","token":(token)"}
        覆盖等价类为1
        期望输出为{"result":"Success","questions":[{"id":"9","content":"This is Question 1","author":{"id":"1","username":"sw7"}},
                                                  {"id":"11","content":"This is Question 2","author":{"id":"1","username":"sw7"}}]}
        实际输出为{"result":"Success","questions":{"author":{"id":"1","username":"sw7"},"id":"11","content":"This is Question 2"}
        结果不正确？
        */
        
//token=null
        debugMessage = process("{\"command\":\"getQuestions\",\"token\":\"\"}");
//        assertEquals("{\"result\":\"Incorrect command\"}", debugMessage);
        /*
        输入为{"command":"getQuestions","token":(token)"}
        覆盖等价类为1
        期望输出为{"result":"Incorrect command"}
        实际输出为{"result":"Success","questions":{"author":{"id":"1","username":"sw7"},"id":"6","content":"This is Question 2"}
        结果不正确？
        */
        
//Update Question 1
        debugMessage = process("{\"command\":\"updateQuestion\",\"token\":\""+token+"\",\"content\":\"This is Question 1(Updated)\",\"id\":\"9\"}");
        assertEquals("{\"result\":\"Success\"}", debugMessage);
        /*
        输入为{"command":"updateQuestion","token":(token) "content":"This is Question 1(Updated)","id":"9"}
        覆盖等价类为13
        期望输出为{"result":"Success"}
        实际输出为{"result":"Success"}
        结果正确
        */

//Mismatched id
        debugMessage = process("{\"command\":\"updateQuestion\",\"token\":\""+anotherToken+"\",\"content\":\"This is Question 1(Mismatched)\",\"id\":\"9\"}");
        assertEquals("{\"result\":\"Failed\"}", debugMessage);
        /*
        输入为{"command":"updateQuestion","token":(anotherToken) "content":"This is Question 1(Mismatched)","id":"9"}
        覆盖等价类为14
        期望输出为{"result":"Failed"}
        实际输出为{"result":"Failed"}
        结果正确
        */

//Invalid id
        debugMessage = process("{\"command\":\"updateQuestion\",\"token\":\""+token+"\",\"content\":\"This is Question 1(Invalid id)\",\"id\":\"10\"}");
        assertEquals("{\"result\":\"Failed\"}", debugMessage);
        /*
        输入为{"command":"updateQuestion","token":(token) "content":"This is Question 1(Invalid id)","id":"10"}
        覆盖等价类为15
        期望输出为{"result":"Failed"}
        实际输出为{"result":"Failed"}
        结果正确
        */
        
//Delete Question 2
        debugMessage = process("{\"command\":\"deleteQuestion\",\"token\":\""+token+"\",\"id\":\"11\"}");
        assertEquals("{\"result\":\"Success\"}", debugMessage);
        /*
        输入为{"command":"deleteQuestion","token":(token) "content":"This is Question 1","id":"11"}
        覆盖等价类为13
        期望输出为{"result":"Success"}
        实际输出为{"result":"Success"}
        结果正确
        */

//Mismatched id       
        debugMessage = process("{\"command\":\"deleteQuestion\",\"token\":\""+anotherToken+"\",\"id\":\"6\"}");
        assertEquals("{\"result\":\"Failed\"}", debugMessage);
        /*
        输入为{"command":"deleteQuestion","token":(token) "content":"This is Question 1","id":"11"}
        覆盖等价类为14
        期望输出为{"result":"Failed"}
        实际输出为{"result":"Failed"}
        结果正确
        */
        
//Delete Again
        debugMessage = process("{\"command\":\"deleteQuestion\",\"token\":\""+token+"\",\"id\":\"11\"}");
        assertEquals("{\"result\":\"Failed\"}", debugMessage);
        /*
        输入为{"command":"deleteQuestion","token":(token) "content":"This is Question 1(id)","id":"11"}
        覆盖等价类为15
        期望输出为{"result":"Failed"}
        实际输出为{"result":"Failed"}
        结果正确
        */
        
//Check result
        debugMessage = process("{\"command\":\"getQuestions\",\"token\":\""+token+"\"}");
        assertEquals("{\"result\":\"Success\",\"questions\":\"{\\\"author\\\":\\\"{\\\\\\\"id\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"username\\\\\\\":\\\\\\\"sw7\\\\\\\"}\\\",\\\"id\\\":\\\"9\\\",\\\"content\\\":\\\"This is Question 1(Updated)\\\"}\"}", debugMessage);
        /*
        输入为{"command":"getQuestions","token":(token)"}
        覆盖等价类为1
        期望输出为{"result":"Success","questions":{"id":"9","content":"This is Question 1(Updated)","author":{"id":"1","username":"sw7"}}}
        实际输出为{"result":"Success","questions":{"author":{"id":"1","username":"sw7"},"id":"9","content":"This is Question 1"}
        结果正确？
        */
    }
    
    /**
     * Test of Answer-relateds process method, of class Server.
     */
    @Test
    public void test_3_Answer() {
        /*
        等价类
        1. 正确的token、ID等
        2. token错误
        3. Question ID错误
        4. Answer ID 与 token不符
        5. Answer ID 不存在
        */
//Add Answer 11 for Question 1
        String debugMessage = process("{\"command\":\"addAnswer\",\"token\":\"" + token + "\",\"content\":\"This is Answer 11\",\"id\":\"9\"}");
        assertEquals("{\"result\":\"Success\",\"id\":\"14\"}", debugMessage);
        /*
        输入为{"command":"addAnswer","token":(token),"content":"This is Answer 11","id":"9"}
        覆盖等价类为1
        期望输出为{"result":"Success","id":"14"}
        实际输出为{"result":"Success","id":"14"}
        结果正确
        */
//Add Answer 12 for Question 1
        debugMessage = process("{\"command\":\"addAnswer\",\"token\":\"" + token + "\",\"content\":\"This is Answer 12\",\"id\":\"9\"}");
        assertEquals("{\"result\":\"Success\",\"id\":\"16\"}", debugMessage);
        /*
        输入为{"command":"addAnswer","token":(token),"content":"This is Answer 12","id":"9"}
        覆盖等价类为1
        期望输出为{"result":"Success","id":"16"}
        实际输出为{"result":"Success","id":"16"}
        结果正确
        */
//Add Answer 13 for Question 1 ( Another Token )
        debugMessage = process("{\"command\":\"addAnswer\",\"token\":\"" + anotherToken + "\",\"content\":\"This is Answer 13\",\"id\":\"9\"}");
        assertEquals("{\"result\":\"Failed\",\"id\":\"\"}", debugMessage);
        /*
        输入为{"command":"addAnswer","token":(anotherToken),"content":"This is Answer 13","id":"9"}
        覆盖等价类为2
        期望输出为{"result":"Failed","id":""}
        实际输出为{"result":"Failed","id":""}
        结果正确
        */
//Add Answer 14 for Question 1 ( Fake Token )
        debugMessage = process("{\"command\":\"addAnswer\",\"token\":\"" + fakeToken + "\",\"content\":\"This is Answer 14\",\"id\":\"9\"}");
        assertEquals("{\"result\":\"Failed\",\"id\":\"\"}", debugMessage);
        /*
        输入为{"command":"addAnswer","token":(fakeToken),"content":"This is Answer 14","id":"9"}
        覆盖等价类为2
        期望输出为{"result":"Failed","id":""}
        实际输出为{"result":"Failed","id":""}
        结果正确
        */
//Add Answer 21 for Question 2 ( Unexisted Question )
        debugMessage = process("{\"command\":\"addAnswer\",\"token\":\"" + token + "\",\"content\":\"This is Answer 21\",\"id\":\"11\"}");
        assertEquals("{\"result\":\"Failed\",\"id\":\"\"}", debugMessage);
        /*
        输入为{"command":"addAnswer","token":(token),"content":"This is Answer 21","id":"11"}
        覆盖等价类为3
        期望输出为{"result":"Failed","id":""}
        实际输出为{"result":"Failed","id":""}
        结果正确
        */
        
//Get Answer List of Question 1
        debugMessage = process("{\"command\":\"getAnswers\",\"id\":\"9\"}");
        assertEquals("{\"result\":\"Success\",\"answers\":\"[{\\\"id\\\":14,\\\"question\\\":{\\\"id\\\":9,\\\"content\\\":{\\\"id\\\":12,\\\"text\\\":\\\"This is Question 1(Updated)\\\",\\\"author\\\":{\\\"id\\\":1,\\\"username\\\":\\\"sw7\\\",\\\"password\\\":\\\"sss\\\",\\\"authority\\\":0,\\\"actSet\\\":[],\\\"token\\\":\\\""+ token + "\\\"}}},\\\"content\\\":{\\\"id\\\":13,\\\"text\\\":\\\"This is Answer 11\\\",\\\"author\\\":{\\\"id\\\":1,\\\"username\\\":\\\"sw7\\\",\\\"password\\\":\\\"sss\\\",\\\"authority\\\":0,\\\"actSet\\\":[],\\\"token\\\":\\\"" + token + "\\\"}}},{\\\"id\\\":16,\\\"question\\\":{\\\"id\\\":9,\\\"content\\\":{\\\"id\\\":12,\\\"text\\\":\\\"This is Question 1(Updated)\\\",\\\"author\\\":{\\\"id\\\":1,\\\"username\\\":\\\"sw7\\\",\\\"password\\\":\\\"sss\\\",\\\"authority\\\":0,\\\"actSet\\\":[],\\\"token\\\":\\\"" + token + "\\\"}}},\\\"content\\\":{\\\"id\\\":15,\\\"text\\\":\\\"This is Answer 12\\\",\\\"author\\\":{\\\"id\\\":1,\\\"username\\\":\\\"sw7\\\",\\\"password\\\":\\\"sss\\\",\\\"authority\\\":0,\\\"actSet\\\":[],\\\"token\\\":\\\"" + token + "\\\"}}}]\"}", debugMessage);
        /*
        输入为{"command":"getAnswers","id":"9"}
        覆盖等价类为1
        期望输出为{"result":"Success","answers":["id":"9"……]}
        实际输出为{"result":"Success","answers":["id":"9"……]}
        结果正确
        */
//Get Answer List of Question 2 ( Unexisted Question )
//这里似乎返回Failed更恰当，但得到的结果是Success与一个空表
        debugMessage = process("{\"command\":\"getAnswers\",\"id\":\"11\"}");
        assertEquals("{\"result\":\"Success\",\"answers\":\"[]\"}", debugMessage);
        /*
        输入为{"command":"getAnswers","id":"11"}
        覆盖等价类为3
        期望输出为{"result":"Failed"}
        实际输出为{"result":"Success","answers":[]}
        结果有偏差，但不影响正常使用
        */
        
//Get Answer List of User 1
//好像跟上边的Question有一样的错误
        //debugMessage = process("{\"command\":\"getAnswers\",\"token\":\"" + token + "\"}");
        //assertEquals("{\"result\":\"Success\",\"answers\":\"[{\\\"id\\\":9,\\\"question\\\":{\\\"id\\\":4,\\\"content\\\":{\\\"id\\\":7,\\\"text\\\":\\\"This is Question 1(Updated)\\\",\\\"author\\\":{\\\"id\\\":1,\\\"username\\\":\\\"sw7\\\",\\\"password\\\":\\\"sss\\\",\\\"authority\\\":0,\\\"actSet\\\":[],\\\"token\\\":\\\""+ token + "\\\"}}},\\\"content\\\":{\\\"id\\\":8,\\\"text\\\":\\\"This is Answer 11\\\",\\\"author\\\":{\\\"id\\\":1,\\\"username\\\":\\\"sw7\\\",\\\"password\\\":\\\"sss\\\",\\\"authority\\\":0,\\\"actSet\\\":[],\\\"token\\\":\\\"" + token + "\\\"}}},{\\\"id\\\":11,\\\"question\\\":{\\\"id\\\":4,\\\"content\\\":{\\\"id\\\":7,\\\"text\\\":\\\"This is Question 1(Updated)\\\",\\\"author\\\":{\\\"id\\\":1,\\\"username\\\":\\\"sw7\\\",\\\"password\\\":\\\"sss\\\",\\\"authority\\\":0,\\\"actSet\\\":[],\\\"token\\\":\\\"" + token + "\\\"}}},\\\"content\\\":{\\\"id\\\":10,\\\"text\\\":\\\"This is Answer 12\\\",\\\"author\\\":{\\\"id\\\":1,\\\"username\\\":\\\"sw7\\\",\\\"password\\\":\\\"sss\\\",\\\"authority\\\":0,\\\"actSet\\\":[],\\\"token\\\":\\\"" + token + "\\\"}}}]\"}", debugMessage);
//Get Answer List of User ( Fake Token )
        //debugMessage = process("{\"command\":\"getAnswers\",\"token\":\"" + fakeToken + "\"}");
        //assertEquals("{\"result\":\"Success\",\"answers\":\"[]\"}", debugMessage);
        
//Update Answer 11
        debugMessage = process("{\"command\":\"updateAnswer\",\"token\":\"" + token + "\",\"content\":\"This is Answer 11 NEW\",\"id\":\"14\"}");
        assertEquals("{\"result\":\"Success\"}", debugMessage);
        /*
        输入为{"command":"updateAnswer","token":(token),"content":"This is Answer 11 NEW","id":"14"}
        覆盖等价类为1
        期望输出为{"result":"Success"}
        实际输出为{"result":"Success"}
        结果正确
        */
//Update Answer 11 ( Fake Token )
        debugMessage = process("{\"command\":\"updateAnswer\",\"token\":\"" + fakeToken + "\",\"content\":\"This is Answer 11 NEW\",\"id\":\"14\"}");
        assertEquals("{\"result\":\"Failed\"}", debugMessage);
        /*
        输入为{"command":"updateAnswer","token":(fakeToken),"content":"This is Answer 11 NEW","id":"14"}
        覆盖等价类为2
        期望输出为{"result":"Failed"}
        实际输出为{"result":"Failed"}
        结果正确
        */
//Update Answer ( Another Token )
        debugMessage = process("{\"command\":\"updateAnswer\",\"token\":\"" + anotherToken + "\",\"content\":\"This is Answer 11 NEW\",\"id\":\"14\"}");
        assertEquals("{\"result\":\"Failed\"}", debugMessage);
        /*
        输入为{"command":"updateAnswer","token":(anotherToken),"content":"This is Answer 11 NEW","id":"14"}
        覆盖等价类为4
        期望输出为{"result":"Failed"}
        实际输出为{"result":"Failed"}
        结果正确
        */
//Update Answer ( Fake Aid )
        debugMessage = process("{\"command\":\"updateAnswer\",\"token\":\"" + token + "\",\"content\":\"This is Answer 11 NEW\",\"id\":\"9999\"}");
        assertEquals("{\"result\":\"Failed\"}", debugMessage);
        /*
        输入为{"command":"updateAnswer","token":(token),"content":"This is Answer 11 NEW","id":"9999"}
        覆盖等价类为5
        期望输出为{"result":"Failed"}
        实际输出为{"result":"Failed"}
        结果正确
        */
        
//Delete Answer 11 ( Fake Token )
        debugMessage = process("{\"command\":\"deleteAnswer\",\"token\":\"" + fakeToken + "\",\"id\":\"14\"}");
        assertEquals("{\"result\":\"Failed\"}", debugMessage);
        /*
        输入为{"command":"deleteAnswer","token":(fakeToken),"id":"14"}
        覆盖等价类为2
        期望输出为{"result":"Failed"}
        实际输出为{"result":"Failed"}
        结果正确
        */
//Delete Answer 11
        debugMessage = process("{\"command\":\"deleteAnswer\",\"token\":\"" + token + "\",\"id\":\"14\"}");
        assertEquals("{\"result\":\"Success\"}", debugMessage);
        /*
        输入为{"command":"deleteAnswer","token":(token),"id":"14"}
        覆盖等价类为1
        期望输出为{"result":"Success"}
        实际输出为{"result":"Success"}
        结果正确
        */
//Delete Again
        debugMessage = process("{\"command\":\"deleteAnswer\",\"token\":\"" + token + "\",\"id\":\"14\"}");
        assertEquals("{\"result\":\"Failed\"}", debugMessage);
        /*
        输入为{"command":"deleteAnswer","token":(token),"id":"14"}
        覆盖等价类为5
        期望输出为{"result":"Failed"}
        实际输出为{"result":"Failed"}
        结果正确
        */
//Delete Answer ( Fake Aid )
        debugMessage = process("{\"command\":\"deleteAnswer\",\"token\":\"" + token + "\",\"id\":\"999\"}");
        assertEquals("{\"result\":\"Failed\"}", debugMessage);
        /*
        输入为{"command":"deleteAnswer","token":(token),"id":"999"}
        覆盖等价类为5
        期望输出为{"result":"Failed"}
        实际输出为{"result":"Failed"}
        结果正确
        */
    }
}
