/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctex.cport.controle.Bean;

import com.ctex.cport.modelo.User;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    
    public User validate(String usuario, String senha)  {        
        User user;        
        TypedQuery<User> tq = getEntityManager().createNamedQuery("User.findByLogin", User.class);
        tq.setParameter("pusername", usuario);
        tq.setParameter("ppassword", encryptPassword(senha));
        try {
            user = tq.getSingleResult();
        } catch (NoResultException nre) {
            user = null;
        }
        return user;
    }
    
   public String encryptPassword(String password) {
        String encPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            encPassword = bigInt.toString(16);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            //Logger.getLogger(PasswordTest.class.getName()).log(Level.SEVERE, null, ex);
        }       
        return encPassword;
    }    
    
    
    
}
