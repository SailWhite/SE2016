package theserver;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Contents.Answer;
import Contents.Question;
import Contents.exceptions.NonexistentEntityException;
import Core.Activity;
import Core.Operation;
import Core.User;
import com.google.gson.*;
import java.util.HashMap;
import java.util.Map;
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
        System.out.println(process("{\"command\":\"test\",\"user\":\"xxx\"}"));
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
            if(token==null || token.isEmpty()) {
                result.put("id", new Gson().toJson(operation.getActivities()));
            } else {
                result.put("id", new Gson().toJson(operation.getActivities(token)));
            }
        }

        if(command.equals("getActivitiesOfUser")) {
            if(token==null || token.isEmpty()|| id==null || id.isEmpty())return gson.toJson(icc);
            try {
                operation.participate(token, id);
                result.put("result", "Success");
            } catch (Exception ex) {
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
                result.put("result", "Failed");
            }
        }
        
        if(command.equals("addNotice")) {
            if(token==null || token.isEmpty()|| content==null || content.isEmpty() || id==null || id.isEmpty() || time==null || time.isEmpty())return gson.toJson(icc);
            try {
                operation.addNotice(token, id, content, time);
                result.put("result", "Success");
            } catch (Exception ex) {
                result.put("result", "Failed");
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
            if(token==null || token.isEmpty()|| content==null || content.isEmpty())return gson.toJson(icc);
            Question question=operation.addQuestion(token, content);
            result.put("result", question==null?"Failed":"Success");
            result.put("id", question==null?"":question.getId().toString());
        }
        
        if(command.equals("getQuestions")) {
            result.put("result", "Success");
            if(token==null || token.isEmpty()) {
                result.put("id", new Gson().toJson(operation.getQuestions()));
            } else {
                result.put("result", "Failed");
            }
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
                result.put("id", new Gson().toJson(operation.getAnswers(id)));
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

