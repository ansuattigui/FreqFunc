/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctex.cport.controle.Bean;

import com.ctex.cport.modelo.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author ralfh
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {
    @PersistenceContext(unitName = "controlePortaria")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }    
    
    public Boolean validate(String usuario, String senha)  {
        Boolean valido;
        TypedQuery<User> tq = getEntityManager().createNamedQuery("User.findByLogin", User.class);
        tq.setParameter("pusername", usuario);
        tq.setParameter("ppassword", senha);
        try {
            valido = (tq.getSingleResult() != null);
        } catch (NoResultException nre) {
            valido = false;
        }
        return valido;
    }
    
    
}
