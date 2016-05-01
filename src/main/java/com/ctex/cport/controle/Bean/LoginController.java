/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctex.cport.controle.Bean;

import com.ctex.cport.controle.Bean.util.JsfUtil;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Ralfh
 */
@Named("loginController")
@SessionScoped
public class LoginController implements Serializable {
    
    @EJB
    private UserFacade ejbFacade;
    private String senha;
    private String msg;
    private String usuario;
 
    private static final long serialVersionUID = 1094801825228386363L;
    
    public String getSenha() {
        return senha;
    }
 
    public void setSenha(String senha) {
        this.senha = senha;
    }
 
    public String getMsg() {
        return msg;
    }
 
    public void setMsg(String msg) {
        this.msg = msg;
    }
 
    public String getUsuario() {
        return usuario;
    }
 
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
 
    //validate login
    public String validaUsuarioSenha() {        
        boolean valido = getEjbFacade().validate(usuario, senha);
        
        if (valido) {
            return "index.xhtml";
        } else {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("UserLoginFailed"));
            return "login.xhtml";
        }
        
    }

    //logout event, invalidate session
    public String logout() {        
        return  "login?faces-redirect=true";
    }    

    /**
     * @return the ejbFacade
     */
    private UserFacade getEjbFacade() {
        return ejbFacade;
    }
    
    public void encryptPassword(String password) {
        String encPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            encPassword = bigInt.toString(16);

            System.out.println(encPassword);

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            //Logger.getLogger(PasswordTest.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
}
