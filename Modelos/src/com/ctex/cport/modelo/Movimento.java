/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctex.cport.modelo;

import java.io.Serializable;
import java.util.Calendar;

/**
 *
 * @author ralfh
 */
public class Movimento implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;    
    private Funcionario funcionario;
    private Calendar datamovimento;
    private Calendar entrada;
    private Calendar saida;

    public Movimento() {
        this.datamovimento = Calendar.getInstance();
        this.entrada = Calendar.getInstance();
        this.saida = Calendar.getInstance();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the funcionario
     */
    public Funcionario getFuncionario() {
        return funcionario;
    }

    /**
     * @param funcionario the funcionario to set
     */
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    /**
     * @return the datamovimento
     */
    public Calendar getDatamovimento() {
        return datamovimento;
    }

    /**
     * @param datamovimento the datamovimento to set
     */
    public void setDatamovimento(Calendar datamovimento) {
        this.datamovimento = datamovimento;
    }

    /**
     * @return the entrada
     */
    public Calendar getEntrada() {
        return entrada;
    }

    /**
     * @param entrada the entrada to set
     */
    public void setEntrada(Calendar entrada) {
        this.entrada = entrada;
    }

    /**
     * @return the saida
     */
    public Calendar getSaida() {
        return saida;
    }

    /**
     * @param saida the saida to set
     */
    public void setSaida(Calendar saida) {
        this.saida = saida;
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
        if (!(object instanceof Movimento)) {
            return false;
        }
        Movimento other = (Movimento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ctex.cport.modelo.Movimento[ id=" + id + " ]";
    }
    
}
