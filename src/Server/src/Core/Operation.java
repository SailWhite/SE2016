/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import Contents.*;
import Contents.exceptions.NonexistentEntityException;
import Notice.*;
import static java.lang.Thread.sleep;
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
    
    Operation(EntityManagerFactory emf) {
        this.emf = emf;
        userJpaController=new UserJpaController(emf);
        activityJpaController=new ActivityJpaController(emf);
        answerJpaController=new AnswerJpaController(emf);
        contentJpaController=new ContentJpaController(emf);
        questionJpaController=new QuestionJpaController(emf);
        noticeJpaController=new NoticeJpaController(emf);
        
        Thread thread = new Thread(()->{
            List<Notice> notices=noticeJpaController.findOnPushNotices();
            for(Notice notice:notices) {
                //ToDo: push(notice) for all participated users
                try {
                    noticeJpaController.destroy(notice.getId());
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(Operation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                sleep(30);
            } catch (InterruptedException ex) {
                Logger.getLogger(Operation.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
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
    
    public Activity addActivity(User user, String string) {
        Content content=new Content(user, string);
        Activity activity =new Activity(content);
        activityJpaController.create(activity);
        return activity;
    }
    
    List<Activity> getActivities() {
        return activityJpaController.findActivityEntities();
    }
    
    Activity getActivity(Long id) {
        return activityJpaController.findActivity(id);
    }
    
    void participate(User user, Activity activity) throws Exception {
        user.participate(activity);
        activity.participated(user);
        userJpaController.edit(user);
        activityJpaController.edit(activity);
    }
    
    boolean updateActivity(User user, Activity activity) throws Exception {
        if(!activity.isAuth(user))return false;
        activityJpaController.edit(activity);
        return true;
    }
    
    boolean deleteActivity(User user, Activity activity) throws NonexistentEntityException {
        if(!activity.isAuth(user))return false;
        activityJpaController.destroy(activity.getId());
        return true;
    }
    
    Notice addNotice(User user, Activity activity, String string, Date push_time) {
        if(!activity.isAuth(user))return null;
        Content content=new Content(user, string);
        Notice notice=new Notice(activity, content, push_time);
        noticeJpaController.create(notice);
        return notice;
    }
    
    List<Notice> getNoticeOfUser(User user) {
        return noticeJpaController.findNoticeOfUID(user.getId());
    }
    
    Notice updateNotice(User user, Activity activity, Notice notice) throws Exception {
        if(!activity.isAuth(user))return null;
        noticeJpaController.edit(notice);
        return notice;
    }
    
    Notice deleteNotice(User user, Activity activity, Notice notice) throws NonexistentEntityException {
        if(!activity.isAuth(user))return null;
        noticeJpaController.destroy(notice.getId());
        return notice;
    }
    
    Question addQuestion(User user, String string) {
        Content content=new Content(user, string);
        Question question =new Question(content);
        questionJpaController.create(question);
        return question;
    }
    
    List<Question> getQuestions() {
        return questionJpaController.findQuestionEntities();
    }
    
    Question getQuestion(Long id) {
        return questionJpaController.findQuestion(id);
    }
    
    boolean updateQuestion(User user, Question question) throws Exception {
        if(!question.isAuth(user))return false;
        questionJpaController.edit(question);
        return true;
    }
    
    boolean deleteQuestion(User user, Question question) throws NonexistentEntityException {
        if(!question.isAuth(user))return false;
        questionJpaController.destroy(question.getId());
        return true;
    }
    
    Answer addAnswer(User user, Question question, String string) {
        Content content=new Content(user, string);
        Answer answer =new Answer(question, content);
        answerJpaController.create(answer);
        return answer;
    }
    
    List<Answer> getAnswers(Question question) {
        return answerJpaController.findAnswerOf(question);
    }
    
    Answer getAnswer(Long id) {
        return answerJpaController.findAnswer(id);
    }
    
    boolean updateAnswer(User user, Answer answer) throws Exception {
        if(!answer.isAuth(user))return false;
        questionJpaController.edit(answer);
        return true;
    }
    
    boolean deleteAnswer(User user, Answer answer) throws NonexistentEntityException {
        if(!answer.isAuth(user))return false;
        questionJpaController.destroy(answer.getId());
        return true;
    }
}
