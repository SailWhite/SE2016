package theserver;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Contents.exceptions.NonexistentEntityException;
import Core.User;
import Core.UserJpaController;
import javax.persistence.*;

/**
 *
 * @author sailw
 */
public class TheServer {

    /**
     * @param args the command line arguments
     * @throws Contents.exceptions.NonexistentEntityException
     */
    public static void main(String[] args) throws NonexistentEntityException {
        EntityManagerFactory emf=javax.persistence.Persistence.createEntityManagerFactory("TheServerPU");
        UserJpaController ujc=new UserJpaController(emf);
        User user=new User("0.3","0.6",0);
        ujc.create(user);
        System.out.println(ujc.findUser(user.getId()));
        ujc.findUserEntities();
        //ujc.destroy(user.getId());
    }
    
}
