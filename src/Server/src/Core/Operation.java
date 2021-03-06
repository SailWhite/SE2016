/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import Contents.*;
import static Contents.Answer_.question;
import Contents.exceptions.NonexistentEntityException;
import Notice.*;
import static java.lang.Thread.sleep;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sailw
 */
public class Operation {
    private final EntityManagerFactory emf;
    private final UserJpaController userJpaController;
    private final ActivityJpaController activityJpaController;
    private final AnswerJpaController answerJpaController;
    private final ContentJpaController contentJpaController;
    private final QuestionJpaController questionJpaController;
    private final NoticeJpaController noticeJpaController;
    
    public Operation(EntityManagerFactory emf) {
        this.emf = emf;
        userJpaController=new UserJpaController(emf);
        activityJpaController=new ActivityJpaController(emf);
        answerJpaController=new AnswerJpaController(emf);
        contentJpaController=new ContentJpaController(emf);
        questionJpaController=new QuestionJpaController(emf);
        noticeJpaController=new NoticeJpaController(emf);
    }
    
    public User login(String userName, String password) {
        User user=userJpaController.findUser(userName);
        if(user!=null && user.ckeckPwd(password))return user;
        return null;
    }
    
    public User regist(String userName, String password) {
        User user=userJpaController.findUser(userName);
        if(user!=null)return null;
        user=new User(userName, password, 0);
        userJpaController.create(user);
        return user;
    }
    
    public Activity addActivity(String token, String string) {
        User user=userJpaController.findUserByToken(token);
        if(user==null)return null;
        Content content=new Content(user, string);
        contentJpaController.create(content);
        Activity activity =new Activity(content);
        activityJpaController.create(activity);
        return activity;
    }
    
    public List<Activity> getActivities() {
        return activityJpaController.findActivityEntities();
    }
    
    public List<Activity> getActivities(String token) {
        User user=userJpaController.findUserByToken(token);
        return activityJpaController.findActivitiesByUser(user);
    }
    
    public void participate(String token, String id) throws Exception {
        User user=userJpaController.findUserByToken(token);
        if(user==null)throw new Exception();
        //System.out.println(id);
        Activity activity =activityJpaController.findActivity(Long.parseLong(id));
        user.participate(activity);
        activity.participated(user);
        userJpaController.edit(user);
        activityJpaController.edit(activity);
    }
    
    public boolean updateActivity(String token, String id, String string) throws Exception {
        User user=userJpaController.findUserByToken(token);
        Activity activity =activityJpaController.findActivity(Long.parseLong(id));
        if(!activity.isAuth(user) || user==null)throw new Exception();
        contentJpaController.destroy(activity.getContent().getId());
        Content content=new Content(user, string);
        contentJpaController.create(content);
        activity.setContent(content);
        activityJpaController.edit(activity);
        return true;
    }
    
    public boolean deleteActivity(String token, String id) throws Exception {
        User user=userJpaController.findUserByToken(token);
        Activity activity =activityJpaController.findActivity(Long.parseLong(id));
        if(!activity.isAuth(user) || user==null)throw new Exception();
        activityJpaController.destroy(activity.getId());
        return true;
    }
    
    public Notice addNotice(String token, String id, String string, String push_time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date date = sdf.parse(push_time);  
        User user=userJpaController.findUserByToken(token);
        Activity activity =activityJpaController.findActivity(Long.parseLong(id));
        if(activity==null || !activity.isAuth(user) || user==null)return null;
        Content content=new Content(user, string);
        contentJpaController.create(content);
        Notice notice=new Notice(activity, content, date);
        
        noticeJpaController.create(notice);
        return notice;
    }
    
    public Notice updateNotice(String token, String id, String string, String push_time) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date date = sdf.parse(push_time); 
        User user=userJpaController.findUserByToken(token);
        Notice notice=noticeJpaController.findNotice(Long.parseLong(id));
        if(!notice.isAuth(user) || user==null)throw new Exception();
        contentJpaController.destroy(notice.getContent().getId());
        Content content=new Content(user, string);
        contentJpaController.create(content);
        notice.setContent(content);
        notice.setPushtime(date);
        noticeJpaController.edit(notice);
        return notice;
    }
    
    public Notice deleteNotice(String token, String id) throws Exception {
        User user=userJpaController.findUserByToken(token);
        Notice notice=noticeJpaController.findNotice(Long.parseLong(id));
        if(!notice.isAuth(user) || user==null)throw new Exception();
        noticeJpaController.destroy(notice.getId());
        return notice;
    }
    
    public Question addQuestion(String token, String string){
        User user=userJpaController.findUserByToken(token);
        if(user==null)return null;
        Content content=new Content(user, string);
        contentJpaController.create(content);
        Question question =new Question(content);
        questionJpaController.create(question);
        return question;
    }
    
    public List<Question> getQuestions() {
        return questionJpaController.findQuestionEntities();
    }
    
    public List<Question> getQuestions(String token) {
        User user=userJpaController.findUserByToken(token);
        return questionJpaController.findQuestionsByUser(user);
    }
    
    public boolean updateQuestion(String token, String id, String string) throws Exception {
        User user=userJpaController.findUserByToken(token);
        Question question =questionJpaController.findQuestion(Long.parseLong(id));
        if(question==null || !question.isAuth(user) || user==null)
            throw new Exception();
        contentJpaController.destroy(question.getContent().getId());
        Content content=new Content(user, string);
        contentJpaController.create(content);
        question.setContent(content);
        questionJpaController.edit(question);
        return true;
    }
    
    public boolean deleteQuestion(String token, String id) throws Exception {
        User user=userJpaController.findUserByToken(token);
        Question question =questionJpaController.findQuestion(Long.parseLong(id));
        if(question==null || !question.isAuth(user) || user==null)throw new Exception();
        questionJpaController.destroy(question.getId());
        return true;
    }

     public Answer addAnswer(String token, String id, String string) {
        User user=userJpaController.findUserByToken(token);
        if(user==null)return null;
        Question question=questionJpaController.findQuestion(Long.parseLong(id));
        if(question==null)return null;
        Content content=new Content(user, string);
        contentJpaController.create(content);
        Answer answer =new Answer(question, content);
        answerJpaController.create(answer);
        return answer;
    }
    
    public List<Answer> getAnswers(String id) {
        Question question = questionJpaController.findQuestion(Long.parseLong(id));
        return answerJpaController.findAnswersByQuestion(question);
    }
    
    public boolean updateAnswer(String token, String id, String string) throws Exception {
        User user=userJpaController.findUserByToken(token);
        Answer answer =answerJpaController.findAnswer(Long.parseLong(id));
        if(answer==null || !answer.isAuth(user) || user==null)throw new Exception();
        contentJpaController.destroy(answer.getContent().getId());
        Content content=new Content(user, string);
        contentJpaController.create(content);
        answer.setContent(content);
        answerJpaController.edit(answer);
        return true;
    }
    
    public boolean deleteAnswer(String token, String id) throws Exception {
        User user=userJpaController.findUserByToken(token);
        Answer answer =answerJpaController.findAnswer(Long.parseLong(id));
        if(answer==null || !answer.isAuth(user) || user==null)throw new Exception();
        answerJpaController.destroy(answer.getId());
        return true;
    }
    
    public List<Notice> getPushNotices(String token) {
        User user=userJpaController.findUserByToken(token);
        List<Notice> notices=noticeJpaController.findNoticesOnPush(user);
        for(Notice notice:notices) {
            notice.push();
            try {
                noticeJpaController.edit(notice);
            } catch (Exception ex) {
                Logger.getLogger(Operation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return notices;
    }
}
