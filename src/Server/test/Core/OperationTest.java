/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import Contents.Answer;
import Contents.Question;
import Notice.Notice;
import static org.junit.Assert.*;
import Contents.*;
import Notice.*;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
/**
 *
 * @author sailw,gosh
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class OperationTest {
    private Operation operation;
    private final UserJpaController userJpaController;
    private final ActivityJpaController activityJpaController;
    private final AnswerJpaController answerJpaController;
    private final ContentJpaController contentJpaController;
    private final QuestionJpaController questionJpaController;
    private final NoticeJpaController noticeJpaController;
    static private Long uid;
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
    
    public OperationTest() {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("TheServerPU");
        operation = new Operation(emf);
        userJpaController = new UserJpaController(emf);
        activityJpaController = new ActivityJpaController(emf);
        answerJpaController = new AnswerJpaController(emf);
        contentJpaController = new ContentJpaController(emf);
        questionJpaController = new QuestionJpaController(emf);
        noticeJpaController = new NoticeJpaController(emf);
    }
    /*
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }*/

    /**
     * Test of regist method, of class Operation.
     */
    @Test
    public void test_a_Regist() {
        System.out.println("regist");
        String userName = "gosh";
        String password = "abc";
        Operation instance = this.operation;
        User user = instance.regist(userName, password);
        uid= user.getId();
        User result = userJpaController.findUser(uid);
        assertNotNull("Test Regist Error", result);
        result = userJpaController.findUser(userName);
        assertNotNull("Test Regist Error", result);
        
        user = instance.regist(userName, password);
        assertNull("Test Regist Error", user);
        
        String anotherUserName = "bendan";
        String anotherPassword = "bendan";
        user = instance.regist(anotherUserName, anotherPassword);
        anotherToken = user.getToken();
       }
    
    
    /**
     * Test of login method, of class Operation.
     */
    @Test
    public void test_b_Login() {
        System.out.println("login");
        String userName = "gosh";
        String password = "abc";
        Operation instance = this.operation;
        User result = instance.login(userName, password);
        assertNotNull("Test Login Error", result);
       
        OperationTest.token=result.getToken();
        result = userJpaController.findUserByToken(OperationTest.token);
        assertNotNull("Test Login Error", result);
        
        password = "abd";
        result = instance.login(userName, password);
        assertNull("Test Login Error", result);
        
        userName = "a";
        result = instance.login(userName, password);
        assertNull("Test Login Error", result);
        System.out.println("Token = " + OperationTest.token);
    }


    /**
     * Test of addActivity method, of class Operation.
     */
    @Test
    public void test_c_AddActivity() {
        System.out.println("addActivity");
        System.out.println("Token = " + OperationTest.token);
        String token = OperationTest.token;
        System.out.println("Token = " + OperationTest.token);
        String string = "Activity 1";
        Operation instance = this.operation;
        Activity result = instance.addActivity(token, string);
        assertNotNull("Test Addactivity Error", result);
        aid=result.getId();

        result = instance.addActivity(fakeToken, string);
        assertNull("Test Regist Error", result);
    }

    /**
     * Test of getActivities method, of class Operation.
     */
    @Test
    public void test_d_GetActivities_0args() {
        System.out.println("getActivities");
        Operation instance = this.operation;
        List<Activity> result = instance.getActivities();
        assertNotNull("Test Getactivities Error", result);
    }

    /**
     * Test of getActivities method, of class Operation.
     */
    @Test
    public void test_e_GetActivities_String() {
        System.out.println("getActivities");
        String token = OperationTest.token;
        Operation instance = this.operation;
        List<Activity> result = instance.getActivities(token);
        assertNotNull("Test Getactivities Error", result);
        
        result = instance.getActivities(fakeToken);
        assertTrue(result.isEmpty());
    }

    /**
     * Test of participate method, of class Operation.
     */
    @Test
    public void test_f_Participate() throws Exception {
        System.out.println("participate");
        String token = OperationTest.token;
        Operation instance = this.operation;
        instance.participate(token, aid.toString());
        Activity activity=this.activityJpaController.findActivity(aid);
        if (!activity.getParticipater().contains(userJpaController.findUser(uid)))
            fail("Test Participate Error");
        assertNotNull("Test Participate Error", activity);
               
    }

    /**
     * Test of updateActivity method, of class Operation.
     */
    @Test
    public void test_g_UpdateActivity() throws Exception {
        System.out.println("updateActivity");
        String token = OperationTest.token;
        String string = "Updated Activity";
        Operation instance = this.operation;
        try {
            instance.updateActivity(token, aid.toString(), string);
        } catch (Exception ex) {
            //ex.printStackTrace();
            fail("Test UpdateActivity Error");
        }
        
        token = fakeToken;
        try {
            instance.updateActivity(token, aid.toString(), string);
            fail("Test UpdateActivity Error");
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
        
        token = anotherToken;
        try {
            instance.updateActivity(token, aid.toString(), string);
            fail("Test UpdateActivity Error");
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
    }

    /**
     * Test of deleteActivity method, of class Operation.
     */
    @Test
    public void test_zh_DeleteActivity() throws Exception {
        System.out.println("deleteActivity");
        String token = OperationTest.token;
        Operation instance = this.operation;
        try {
            System.out.println(token + "#" + aid);
            boolean result = instance.deleteActivity(token, aid.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Test DeleteActivity Error");
        }
        
        token=fakeToken;
        try {
            boolean result = instance.deleteActivity(token, aid.toString());
            fail("Test DeleteActivity Error");
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
        token=anotherToken;
        try {
            boolean result = instance.deleteActivity(token, aid.toString());
            fail("Test DeleteActivity Error");
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
    }

    /**
     * Test of addNotice method, of class Operation.
     */
    @Test
    public void test_i_AddNotice() throws Exception {
        System.out.println("addNotice");
        String token = OperationTest.token;
        String string = "This is a Notice";
        String push_time = "2016-05-24 16:59:48";
        Operation instance = this.operation;
        System.out.println(aid);
        Notice result = instance.addNotice(token, aid.toString(), string, push_time);
        assertNotNull("Test AddNotice Error",result);
        nid = result.getId();
        
        result = instance.addNotice(token, fakeAid.toString(), string, push_time);
        assertNull("Test AddNotice Error",result);
        
        result = instance.addNotice(fakeToken, aid.toString(), string, push_time);
        assertNull("Test AddNotice Error",result);
    }

    /**
     * Test of updateNotice method, of class Operation.
     */
    @Test
    public void test_j_UpdateNotice() throws Exception {
        System.out.println("updateNotice");
        String token = OperationTest.token;
        String string = "This is another Notice";
        String push_time = "2016-05-24 16:59:48";
        Operation instance = this.operation;
        Notice result;
        
        try{
            result = instance.updateNotice(token, nid.toString(), string, push_time);
        } catch (Exception ex){
            fail("Test UpdateNotice Error");
            //ex.printStackTrace();
        }
        
        try{
            result = instance.updateNotice(token, fakeNid.toString(), string, push_time);
            fail("Test UpdateNotice Error");
        } catch (Exception ex){
            //ex.printStackTrace();
        }
        
        try{
            result = instance.updateNotice(fakeToken, nid.toString(), string, push_time);
            fail("Test UpdateNotice Error");
        } catch (Exception ex){
            //ex.printStackTrace();
        }
        
        try{
            result = instance.updateNotice(anotherToken, nid.toString(), string, push_time);
            fail("Test UpdateNotice Error");
        } catch (Exception ex){
            //ex.printStackTrace();
        }
    }

    /**
     * Test of deleteNotice method, of class Operation.
     */
    @Test
    public void test_ka_DeleteNotice() throws Exception {
        System.out.println("deleteNotice");
        String token = OperationTest.token;
        Operation instance = this.operation;
        Notice result = instance.deleteNotice(token, nid.toString());
        assertNotNull("Test UpdateNotice Error", result);
    }

    /**
     * Test of addQuestion method, of class Operation.
     */
    @Test
    public void test_l_AddQuestion() {
        System.out.println("addQuestion");
        String token = OperationTest.token;
        String string = "This is a question";
        Operation instance = this.operation;
        Question result = instance.addQuestion(token, string);
        assertNotNull("Test AddQuestion Error", result);
        qid = result.getId();
        
        result = instance.addQuestion(fakeToken, string);
        assertNull("Test AddQuestion Error", result);
    }

    /**
     * Test of getQuestions method, of class Operation.
     */
    @Test
    public void test_m_GetQuestions_0args() {
        System.out.println("getQuestions");
        Operation instance = this.operation;
        List<Question> result = instance.getQuestions();
        assertNotNull("Test GetQuestions_0args Error", result);
    }

    /**
     * Test of getQuestions method, of class Operation.
     */
    @Test
    public void test_n_GetQuestions_String() {
        System.out.println("getQuestions");
        String token = OperationTest.token;
        Operation instance = this.operation;
        List<Question> result = instance.getQuestions(token);
        assertFalse("Test GetQuestions_String Error", result.isEmpty());
        
        result = instance.getQuestions(fakeToken);
        assertTrue("Test GetQuestions_String Error", result.isEmpty());
    }

    /**
     * Test of updateQuestion method, of class Operation.
     */
    @Test
    public void test_o_UpdateQuestion() throws Exception {
        System.out.println("updateQuestion");
        String token = OperationTest.token;
        String id = qid.toString();
        String string = "This is a new question";
        Operation instance = this.operation;
        boolean result = instance.updateQuestion(token, id, string);
        if (!result)
            fail("Test UpdateQuestion Error");
        
        try{
            result = instance.updateQuestion(fakeToken, id, string);
            fail("Test UpdateQuestion Error");
        } catch (Exception ex){
            //ex.printStackTrace();
        }
        
        id = fakeQid.toString();
        try{
            result = instance.updateQuestion(token, id, string);
            fail("Test UpdateQuestion Error");
        } catch (Exception ex){
            //ex.printStackTrace();
        }
    }

    /**
     * Test of deleteQuestion method, of class Operation.
     */
    @Test
    public void test_ta_DeleteQuestion() throws Exception {
        System.out.println("deleteQuestion");
        String token = OperationTest.token;
        String id = qid.toString();
        Operation instance = this.operation;
        boolean result = instance.deleteQuestion(token, id);
        if (!result)
            fail("Test DeleteQuestion Error");
        
        try{
            result = instance.deleteQuestion(fakeToken, id);
            fail("Test DeleteQuestion Error");
        } catch (Exception ex){
            //ex.printStackTrace();
        }
        
        id = fakeQid.toString();
        try{
            result = instance.deleteQuestion(token, id);
            fail("Test DeleteQuestion Error");
        } catch (Exception ex){
            //ex.printStackTrace();
        }
    }

    /**
     * Test of addAnswer method, of class Operation.
     */
    @Test
    public void test_q_AddAnswer() {
        System.out.println("addAnswer");
        String token = OperationTest.token;
        String id = qid.toString();
        String string = "This is an answer";
        Operation instance = this.operation;
        Answer result = instance.addAnswer(token, id, string);
        assertNotNull("Test AddAnswer Error", result);
        asid=result.getId();
        
        result = instance.addAnswer(fakeToken, id, string);
        assertNull("Test AddAnswer Error", result);
        
        id = fakeQid.toString();
        result = instance.addAnswer(token, id, string);
        assertNull("Test AddAnswer Error", result);
    }

    /**
     * Test of getAnswers method, of class Operation.
     */
    @Test
    public void test_r_GetAnswers() {
        System.out.println("getAnswers");
        String id = qid.toString();
        Operation instance = this.operation;
        List<Answer> result = instance.getAnswers(id);
        assertNotNull("Test GetAnswers Error", result);
        
        id = fakeQid.toString();
        result = instance.getAnswers(id);
        assertTrue(result.isEmpty());
    }

    /**
     * Test of updateAnswer method, of class Operation.
     */
    @Test
    public void test_s_UpdateAnswer() throws Exception {
        System.out.println("updateAnswer");
        String token = OperationTest.token;
        String id = asid.toString();
        String string = "This is a new answer";
        Operation instance = this.operation;
        boolean result = instance.updateAnswer(token, id, string);
        if (!result)
            fail("Test UpdateAnswer Error");
        
        try{
            result = instance.updateAnswer(fakeToken, id, string);
            fail("Test UpdateAnswer Error");
        } catch (Exception ex){
            //ex.printStackTrace();
        }
        
        id = fakeAsid.toString();
        try{
            result = instance.updateAnswer(token, id, string);
            fail("Test UpdateAnswer Error");
        } catch (Exception ex){
            //ex.printStackTrace();
        }
    }

    /**
     * Test of deleteAnswer method, of class Operation.
     */
    @Test
    public void test_t_DeleteAnswer() throws Exception {
        System.out.println("deleteAnswer");
        String token = OperationTest.token;
        String id = asid.toString();
        Operation instance = this.operation;
        boolean result = instance.deleteAnswer(token, id);
        if (!result)
            fail("Test DeleteAnswer Error");
        
        try{
            result = instance.deleteAnswer(fakeToken, id);
            fail("Test DeleteAnswer Error");
        } catch (Exception ex){
            //ex.printStackTrace();
        }
        
        id = fakeAsid.toString();
        try{
            result = instance.deleteAnswer(token, id);
            fail("Test DeleteAnswer Error");
        } catch (Exception ex){
            //ex.printStackTrace();
        }
    }

    /**
     * Test of getPushNotices method, of class Operation.
     */
    @Test
    public void test_k_GetPushNotices() {
        System.out.println("getPushNotices");
        String token = OperationTest.token;
        Operation instance = this.operation;
        List<Notice> result = instance.getPushNotices(token);
        assertNotNull("Test GetPushNotices Error", result);
        result = instance.getPushNotices(fakeToken);
        assertTrue(result.isEmpty());
    }
}
