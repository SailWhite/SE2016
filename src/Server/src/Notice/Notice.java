/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Notice;

import Contents.Content;
import Core.Activity;
import Core.User;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author sailw
 */
@Entity
@Table(name="JPANotice")
public class Notice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable=false)
    boolean isPushed;
    @OneToOne
    @JoinColumn(nullable=false)
    private  Content content;
    @ManyToOne
    @JoinColumn(nullable=false)
    private Activity activity;
    @Temporal(TemporalType.DATE)
    @Column(nullable=false)
    private  Date push_time;

    public Notice() {
        content=null;
        activity=null;
        push_time=null;
    }
    
    public Notice(Activity activity, Content content, Date push_time) {
	this.content = content;
	this.push_time = push_time;
        this.isPushed=false;
    }
	
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notice)) {
            return false;
        }
        Notice other = (Notice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Notice.Notice[ id=" + id + " ]";
    }

    public void setPushtime(Date date) {
        this.push_time=date;
    }

    public void setContent(Content content) {
        this.content=content;
    }

    public boolean isAuth(User user) {
        return this.content.getAuthor().equals(user);
    }

    public User getAuthor() {
        return this.content.getAuthor();
    }

    public Content getContent() {
        return content;
    }

    public void push() {
        this.isPushed=true;
    }
}
