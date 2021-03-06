/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctex.cport.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ralfh
 */
@Entity
@Table(name = "uprofiles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Uprofiles.findAll", query = "SELECT u FROM Uprofiles u"),
    @NamedQuery(name = "Uprofiles.findById", query = "SELECT u FROM Uprofiles u WHERE u.id = :id"),
    @NamedQuery(name = "Uprofiles.findByProfile", query = "SELECT u FROM Uprofiles u WHERE u.profile = :profile")})
public class Uprofiles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "profile")
    private String profile;
    @JoinTable(name = "user_profile", joinColumns = {
        @JoinColumn(name = "profile_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "user_id", referencedColumnName = "ID")})
    @ManyToMany
    private List<Users> usersList;

    public Uprofiles() {
    }

    public Uprofiles(Integer id) {
        this.id = id;
    }

    public Uprofiles(Integer id, String profile) {
        this.id = id;
        this.profile = profile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @XmlTransient
    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
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
        if (!(object instanceof Uprofiles)) {
            return false;
        }
        Uprofiles other = (Uprofiles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ctex.cport.modelo.Uprofiles[ id=" + id + " ]";
    }
    
}
