/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import Contents.Content;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author sailw
 */
@Entity
@Table(name="JPAActivity")
public class Activity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @JoinColumn(nullable=false)
    private Content content;
    @ManyToMany(cascade={CascadeType.PERSIST})     
    @JoinTable(name="participate",    
            joinColumns={        @JoinColumn(name="aid",referencedColumnName="id")    },    
            inverseJoinColumns={         @JoinColumn(name="uid",referencedColumnName="id")    })     
    protected Set<User> participater=new HashSet<>();

    public Activity() {
    }

    public Activity(Content content) {
        this.content = content;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    //@Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Activity)) {
            return false;
        }
        Activity other = (Activity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    //@Override
    public String toString() {
        return "Core.Activity[ id=" + id + " ]";
    }

    public boolean isAuth(User user) {
        return this.content.getAuthor().equals(user);
    }

    public void participated(User user) {
        participater.add(user);
    }

    public void setContent(Content content) {
        this.content=content;
        
    }

    public User getAuthor() {
        return content.getAuthor();
    }

    public Content getContent() {
        return content;
    }

    Set<User> getParticipater() {
        return this.participater;
    }
    
}
