/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctex.cport.controle.Bean;

import com.ctex.cport.controle.Bean.util.JsfUtil;
import com.ctex.cport.controle.Bean.util.SessionContext;
import com.ctex.cport.modelo.User;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Ralfh
 */
@Named("loginController")
@RequestScoped
public class LoginController implements Serializable {
    
    @EJB
    private UserFacade ejbFacade;
    private String senha;
    private String msg;
    private String usuario;
 
    private static final long serialVersionUID = 1094801825228386363L;
    
    public LoginController() {
    }
    
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
        User user = getEjbFacade().validate(usuario, senha);        
        if (user!=null) {  
            SessionContext.getInstance().setAttribute("usuarioLogado", usuario);
            return "/index.xhtml?faces-redirect=true";
        } else {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("UserLoginFailed"));
            FacesContext.getCurrentInstance().validationFailed();
            return "";
        }        
    }
    
    //logout event, invalidate session
    public String logout() {                
        SessionContext.getInstance().encerrarSessao();
        JsfUtil.addSuccessMessage("Logout realizado com sucesso !");
        return  "login.xhtml?faces-redirect=true";
    }    

    /**
     * @return the ejbFacade
     */
    private UserFacade getEjbFacade() {
        return ejbFacade;
    }

}
