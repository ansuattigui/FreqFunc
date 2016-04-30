/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctex.cport.controle.Bean;

import com.ctex.cport.modelo.enumerated.PostoGradSc;
import com.ctex.cport.modelo.enumerated.TipoPessoal;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author ralfh
 */
@Named(value = "enumeracoes")
@SessionScoped
public class Enumeracoes implements Serializable {

    /**
     * Creates a new instance of Enumeracoes
     */
    public Enumeracoes() {
    }
    
    public TipoPessoal[] getTipos() {
        return TipoPessoal.values();
    }
    
    public PostoGradSc[] getPostos() {
        return PostoGradSc.values();
    }
}
