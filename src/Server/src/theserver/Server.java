package theserver;

import Contents.Answer;
import Contents.Question;
import Contents.exceptions.NonexistentEntityException;
import Core.Activity;
import Core.Operation;
import Core.User;
import Notice.Notice;
import com.google.gson.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sailw
 */
public class Server {

    /**
     * @param args the command line arguments
     * @throws Contents.exceptions.NonexistentEntityException
     */
    private static Operation operation;
    static{
        EntityManagerFactory emf=javax.persistence.Persistence.createEntityManagerFactory("TheServerPU");
        operation=new Operation(emf);
    }
    
    public static void main(String[] args) throws NonexistentEntityException {
        /*EntityManagerFactory emf=javax.persistence.Persistence.createEntityManagerFactory("TheServerPU");
        UserJpaController ujc=new UserJpaController(emf);
        User user=new User("0.3","0.6",0);
        ujc.create(user);
        System.out.println(ujc.findUser(user.getId()));
        ujc.findUserEntities();
        String str = null;
        JsonObject  jsonObj = new JsonObject();
        Gson gson = new Gson();
        //ujc.destroy(user.getId());*/
        Gson gson = new Gson();
        Map<String, String> tokenMap=new HashMap<>();
        System.out.println(process("{\"command\":\"regist\",\"username\":\"sw7\",\"password\":\"sss\"}"));
        String tokenJson=process("{\"command\":\"login\",\"username\":\"sw7\",\"password\":\"sss\"}");
        System.out.println(tokenJson);
        tokenMap=gson.fromJson(tokenJson, tokenMap.getClass());
        String token=tokenMap.get("token");
        System.out.println(process("{\"command\":\"addActivity\",\"token\":\""+token+"\",\"content\":\"ssscontents\"}"));
        System.out.println(process("{\"command\":\"addActivity\",\"token\":\""+token+"\",\"content\":\"Sailwhite is a god.\"}"));
        System.out.println(process("{\"command\":\"getActivities\",\"token\":\""+token+"\",\"content\":\"ssscontents\"}"));
        System.out.println(process("{\"command\":\"participate\",\"token\":\""+token+"\",\"id\":\"3\"}"));
        System.out.println(process("{\"command\":\"updateActivity\",\"token\":\""+token+"\",\"content\":\"Sailwhite is not a god.\",\"id\":\"5\"}"));
        System.out.println(process("{\"command\":\"deleteActivity\",\"token\":\""+token+"\",\"id\":\"5\"}"));
        // code below is added by Yuan Xuan
        //System.out.println(process("{\"command\":\"addNotice\",\"token\":\""+token+"\",\"content\":\"aSampleNotice\",\"time\":\"2016-05-25 14:23:23\",\"id\":\"3\"}"));
        System.out.println(process("{\"command\":\"addQuestion\",\"token\":\""+token+"\",\"content\":\"Is it working?\"}"));
        System.out.println(process("{\"command\":\"getQuestions\",\"token\":\""+token+"\"}"));
    }
    
    public static String process(String cmd) {
        Gson gson = new Gson();
        Map<String, String> query=new HashMap<>();
        query=gson.fromJson(cmd, query.getClass());
        Map<String, String> result=new HashMap<>();
        result.put("result", "Incorrect command");
        Map<String, String> icc=new HashMap<>();
        icc.put("result", "Incorrect command");
        String username=query.get("username");
        String password=query.get("password");
        String command=query.get("command");
        String token=query.get("token");
        String content=query.get("content");
        String id=query.get("id");
        String time=query.get("time");
        
        if(command.equals("regist")) {
            if(username==null || username.isEmpty()|| password==null || password.isEmpty())return gson.toJson(icc);
            User user=operation.regist(username, password);
            result.put("result", user==null?"Failed":"Success");
            result.put("id", user==null?"":user.getId().toString());
        }
        
        if(command.equals("login")) {
            if(username==null || username.isEmpty()|| password==null || password.isEmpty())return gson.toJson(icc);
            User user=operation.login(username, password);
            result.put("result", user==null?"Failed":"Success");
            result.put("token", user==null?"":user.getToken().toString());
        }
        
        if(command.equals("addActivity")) {
            if(token==null || token.isEmpty()|| content==null || content.isEmpty())return gson.toJson(icc);
            Activity activity=operation.addActivity(token, content);
            result.put("result", activity==null?"Failed":"Success");
            result.put("id", activity==null?"":activity.getId().toString());
        }
        
        if(command.equals("getActivities")) {
            result.put("result", "Success");
            List<Activity> activities;
            if(token==null || token.isEmpty()) {
                activities=operation.getActivities();
            } else {
                activities=operation.getActivities(token);
            }
            Map<String,String> act=new HashMap<>();
            Map<String,String> usr=new HashMap<>();
            for(Activity activity:activities) {
                act.clear();
                act.put("id", activity.getId().toString());
                act.put("content", activity.getContent().getText());
                User author=activity.getAuthor();
                usr.clear();
                usr.put("id", author.getId().toString());
                usr.put("username", author.getUsername());
                act.put("author", new Gson().toJson(usr));
            }
            result.put("activities", new Gson().toJson(act));
        }

        if(command.equals("participate")) {
            if(token==null || token.isEmpty()|| id==null || id.isEmpty())return gson.toJson(icc);
            try {
                operation.participate(token, id);
                result.put("result", "Success");
            } catch (Exception ex) {
                ex.printStackTrace();
                result.put("result", "Failed");
            }
        }
        
        if(command.equals("updateActivity")) {
            if(token==null || token.isEmpty()|| content==null || content.isEmpty() || id==null || id.isEmpty())return gson.toJson(icc);
            try {
                operation.updateActivity(token, id, content);
                result.put("result", "Success");
            } catch (Exception ex) {
                result.put("result", "Failed");
            }
        }
        
        if(command.equals("deleteActivity")) {
            if(token==null || token.isEmpty()|| id==null || id.isEmpty())return gson.toJson(icc);
            try {
                operation.deleteActivity(token, id);
                result.put("result", "Success");
            } catch (Exception ex) {
                ex.printStackTrace();
                result.put("result", "Failed");
            }
        }
        
        if(command.equals("addNotice")) {
            if(token==null || token.isEmpty()|| content==null || content.isEmpty() || id==null || id.isEmpty() || time==null || time.isEmpty())
                return gson.toJson(icc);
            try {
                operation.addNotice(token, id, content, time);
                result.put("result", "Success");
            } catch (Exception ex) {
                ex.printStackTrace();
                result.put("result", "Failed");
            }
        }
        
        if(command.equals("getNotices")) {
            result.put("result", "Success");
            List<Notice> notices;
            if(token==null || token.isEmpty()) {
                result.put("result", "Failed");
            } else {
                Map<String,String> ntc=new HashMap<>();
                Map<String,String> usr=new HashMap<>();
                notices=operation.getNotices(token);
                for(Notice notice:notices) {
                    
                    ntc.clear();
                    ntc.put("id", notice.getId().toString());
                    ntc.put("content", notice.getContent().getText());
                    User author=notice.getAuthor();
                    usr.clear();
                    usr.put("id", author.getId().toString());
                    usr.put("username", author.getUsername());
                    ntc.put("author", new Gson().toJson(usr));
                }
                result.put("notices", new Gson().toJson(ntc));
            }
        }
        
        if(command.equals("updateNotice")) {
            if(token==null || token.isEmpty()|| content==null || content.isEmpty() || id==null || id.isEmpty() || time==null || time.isEmpty())return gson.toJson(icc);
            try {
                operation.updateNotice(token, id, content, time);
                result.put("result", "Success");
            } catch (Exception ex) {
                result.put("result", "Failed");
            }
        }
        
        if(command.equals("deleteNotice")) {
            if(token==null || token.isEmpty()|| id==null || id.isEmpty())return gson.toJson(icc);
            try {
                operation.deleteNotice(token, id);
                result.put("result", "Success");
            } catch (Exception ex) {
                result.put("result", "Failed");
            }
        }
        
        if(command.equals("addQuestion")) {
            if(token==null || token.isEmpty()|| content==null || content.isEmpty())
                return gson.toJson(icc);
            Question question=operation.addQuestion(token, content);
            result.put("result", question==null?"Failed":"Success");
            result.put("id", question==null?"":question.getId().toString());
        }
        
        if(command.equals("getQuestions")) {
            result.put("result", "Success");
            List<Question> questions;
            if(token==null || token.isEmpty()) {
                questions=operation.getQuestions();
            } else {
                questions=operation.getQuestions(token);
            }
            Map<String,String> qst=new HashMap<>();
            Map<String,String> usr=new HashMap<>();
            for(Question question:questions) {
                qst.clear();
                qst.put("id", question.getId().toString());
                qst.put("content", question.getContent().getText());
                User author=question.getAuthor();
                usr.clear();
                usr.put("id", author.getId().toString());
                usr.put("username", author.getUsername());
                qst.put("author", new Gson().toJson(usr));
            }
            result.put("notices", new Gson().toJson(qst));
        }
        
        
        if(command.equals("updateQuestion")) {
            if(token==null || token.isEmpty()|| content==null || content.isEmpty() || id==null || id.isEmpty())return gson.toJson(icc);
            try {
                operation.updateQuestion(token, id, content);
                result.put("result", "Success");
            } catch (Exception ex) {
                result.put("result", "Failed");
            }
        }
        
        if(command.equals("deleteQuestion")) {
            if(token==null || token.isEmpty()|| id==null || id.isEmpty())return gson.toJson(icc);
            try {
                operation.deleteQuestion(token, id);
                result.put("result", "Success");
            } catch (Exception ex) {
                result.put("result", "Failed");
            }
        }
        
        if(command.equals("addAnswer")) {
            if(token==null || token.isEmpty()|| content==null || content.isEmpty() || id==null || id.isEmpty())return gson.toJson(icc);
            Answer answer=operation.addAnswer(token, id, content);
            result.put("result", answer==null?"Failed":"Success");
            result.put("id", answer==null?"":answer.getId().toString());
        }
        
        if(command.equals("getAnswers")) {
            result.put("result", "Success");
            if(token==null || token.isEmpty() || id==null || id.isEmpty()) {
                result.put("answers", new Gson().toJson(operation.getAnswers(id)));
            } else {
                result.put("result", "Failed");
            }
        }
        
        if(command.equals("updateAnswer")) {
            if(token==null || token.isEmpty()|| content==null || content.isEmpty() || id==null || id.isEmpty())return gson.toJson(icc);
            try {
                operation.updateAnswer(token, id, content);
                result.put("result", "Success");
            } catch (Exception ex) {
                result.put("result", "Failed");
            }
        }
        
        if(command.equals("deleteAnswer")) {
            if(token==null || token.isEmpty()|| id==null || id.isEmpty())return gson.toJson(icc);
            try {
                operation.deleteAnswer(token, id);
                result.put("result", "Success");
            } catch (Exception ex) {
                result.put("result", "Failed");
            }
        }
        
        return gson.toJson(result);
    }
}

