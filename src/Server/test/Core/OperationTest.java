/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import Contents.Answer;
import Contents.Question;
import Notice.Notice;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import Contents.*;
import static Contents.Answer_.question;
import Contents.exceptions.NonexistentEntityException;
import Notice.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
/**
 *
 * @author sailw,gosh
 */
@FixMethodOrder(MethodSorters.DEFAULT)
public class OperationTest {
    private Operation operation;
    private final UserJpaController userJpaController;
    private final ActivityJpaController activityJpaController;
    private final AnswerJpaController answerJpaController;
    private final ContentJpaController contentJpaController;
    private final QuestionJpaController questionJpaController;
    private final NoticeJpaController noticeJpaController;
    private Long uid;
    private String token;
    
    public OperationTest() {
        EntityManagerFactory emf=javax.persistence.Persistence.createEntityManagerFactory("TheServerPU");
        operation=new Operation(emf);
        userJpaController=new UserJpaController(emf);
        activityJpaController=new ActivityJpaController(emf);
        answerJpaController=new AnswerJpaController(emf);
        contentJpaController=new ContentJpaController(emf);
        questionJpaController=new QuestionJpaController(emf);
        noticeJpaController=new NoticeJpaController(emf);
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of regist method, of class Operation.
     */
    @Test
    public void testRegist() {
        System.out.println("regist");
        String userName = "gosh";
        String password = "abc";
        Operation instance = this.operation;
        User user = instance.regist(userName, password);
        uid= user.getId();
        User result = userJpaController.findUser(uid);
        assertNotNull("Test Regist Error",result);
        result = userJpaController.findUser(userName);
        assertNotNull("Test Regist Error",result);
        
        user = instance.regist(userName, password);
        assertNull("Test Regist Error",user);
       }
    
    
    /**
     * Test of login method, of class Operation.
     */
    @Test
    public void testLogin() {
        System.out.println("login");
        String userName = "gosh";
        String password = "abc";
        Operation instance = this.operation;
        User result = instance.login(userName, password);
        assertNotNull("Test Login Error",result);
       
        this.token=result.getToken();
        result = userJpaController.findUserByToken(this.token);
        assertNotNull("Test Login Error",result);
        
        password = "abd";
        result = instance.login(userName, password);
        assertNull("Test Login Error",result);
        
        userName = "a";
        result = instance.login(userName, password);
        assertNull("Test Login Error",result);
        
//assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }


    /**
     * Test of addActivity method, of class Operation.
     */
    @Test
    public void testAddActivity() {
        System.out.println("addActivity");
        String token = "";
        String string = "";
        Operation instance = null;
        Activity expResult = null;
        Activity result = instance.addActivity(token, string);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getActivities method, of class Operation.
     */
    @Test
    public void testGetActivities_0args() {
        System.out.println("getActivities");
        Operation instance = null;
        List<Activity> expResult = null;
        List<Activity> result = instance.getActivities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getActivities method, of class Operation.
     */
    @Test
    public void testGetActivities_String() {
        System.out.println("getActivities");
        String token = "";
        Operation instance = null;
        List<Activity> expResult = null;
        List<Activity> result = instance.getActivities(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of participate method, of class Operation.
     */
    @Test
    public void testParticipate() throws Exception {
        System.out.println("participate");
        String token = "";
        String id = "";
        Operation instance = null;
        instance.participate(token, id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateActivity method, of class Operation.
     */
    @Test
    public void testUpdateActivity() throws Exception {
        System.out.println("updateActivity");
        String token = "";
        String id = "";
        String string = "";
        Operation instance = null;
        boolean expResult = false;
        boolean result = instance.updateActivity(token, id, string);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteActivity method, of class Operation.
     */
    @Test
    public void testDeleteActivity() throws Exception {
        System.out.println("deleteActivity");
        String token = "";
        String id = "";
        Operation instance = null;
        boolean expResult = false;
        boolean result = instance.deleteActivity(token, id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addNotice method, of class Operation.
     */
    @Test
    public void testAddNotice() throws Exception {
        System.out.println("addNotice");
        String token = "";
        String id = "";
        String string = "";
        String push_time = "";
        Operation instance = null;
        Notice expResult = null;
        Notice result = instance.addNotice(token, id, string, push_time);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateNotice method, of class Operation.
     */
    @Test
    public void testUpdateNotice() throws Exception {
        System.out.println("updateNotice");
        String token = "";
        String id = "";
        String string = "";
        String push_time = "";
        Operation instance = null;
        Notice expResult = null;
        Notice result = instance.updateNotice(token, id, string, push_time);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteNotice method, of class Operation.
     */
    @Test
    public void testDeleteNotice() throws Exception {
        System.out.println("deleteNotice");
        String token = "";
        String id = "";
        Operation instance = null;
        Notice expResult = null;
        Notice result = instance.deleteNotice(token, id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addQuestion method, of class Operation.
     */
    @Test
    public void testAddQuestion() {
        System.out.println("addQuestion");
        String token = "";
        String string = "";
        Operation instance = null;
        Question expResult = null;
        Question result = instance.addQuestion(token, string);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getQuestions method, of class Operation.
     */
    @Test
    public void testGetQuestions_0args() {
        System.out.println("getQuestions");
        Operation instance = null;
        List<Question> expResult = null;
        List<Question> result = instance.getQuestions();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getQuestions method, of class Operation.
     */
    @Test
    public void testGetQuestions_String() {
        System.out.println("getQuestions");
        String token = "";
        Operation instance = null;
        List<Question> expResult = null;
        List<Question> result = instance.getQuestions(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateQuestion method, of class Operation.
     */
    @Test
    public void testUpdateQuestion() throws Exception {
        System.out.println("updateQuestion");
        String token = "";
        String id = "";
        String string = "";
        Operation instance = null;
        boolean expResult = false;
        boolean result = instance.updateQuestion(token, id, string);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteQuestion method, of class Operation.
     */
    @Test
    public void testDeleteQuestion() throws Exception {
        System.out.println("deleteQuestion");
        String token = "";
        String id = "";
        Operation instance = null;
        boolean expResult = false;
        boolean result = instance.deleteQuestion(token, id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addAnswer method, of class Operation.
     */
    @Test
    public void testAddAnswer() {
        System.out.println("addAnswer");
        String token = "";
        String id = "";
        String string = "";
        Operation instance = null;
        Answer expResult = null;
        Answer result = instance.addAnswer(token, id, string);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAnswers method, of class Operation.
     */
    @Test
    public void testGetAnswers() {
        System.out.println("getAnswers");
        String id = "";
        Operation instance = null;
        List<Answer> expResult = null;
        List<Answer> result = instance.getAnswers(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateAnswer method, of class Operation.
     */
    @Test
    public void testUpdateAnswer() throws Exception {
        System.out.println("updateAnswer");
        String token = "";
        String id = "";
        String string = "";
        Operation instance = null;
        boolean expResult = false;
        boolean result = instance.updateAnswer(token, id, string);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteAnswer method, of class Operation.
     */
    @Test
    public void testDeleteAnswer() throws Exception {
        System.out.println("deleteAnswer");
        String token = "";
        String id = "";
        Operation instance = null;
        boolean expResult = false;
        boolean result = instance.deleteAnswer(token, id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPushNotices method, of class Operation.
     */
    @Test
    public void testGetPushNotices() {
        System.out.println("getPushNotices");
        String token = "";
        Operation instance = null;
        List<Notice> expResult = null;
        List<Notice> result = instance.getPushNotices(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
