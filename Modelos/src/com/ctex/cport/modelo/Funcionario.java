/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctex.cport.modelo;

import com.ctex.cport.modelo.enumerated.PostoGradSc;
import com.ctex.cport.modelo.enumerated.TipoPessoal;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ralfh
 */
public class Funcionario implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;    
    private TipoPessoal tipopessoal;
    private PostoGradSc postogradsc;    
    private String nome;    
    private Divisao divisao;
    private String ramal;
    private List<Movimento> movimentos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the tipopessoal
     */
    public TipoPessoal getTipopessoal() {
        return tipopessoal;
    }

    /**
     * @param tipopessoal the tipopessoal to set
     */
    public void setTipopessoal(TipoPessoal tipopessoal) {
        this.tipopessoal = tipopessoal;
    }

    /**
     * @return the postogradsc
     */
    public PostoGradSc getPostogradsc() {
        return postogradsc;
    }

    /**
     * @param postogradsc the postogradsc to set
     */
    public void setPostogradsc(PostoGradSc postogradsc) {
        this.postogradsc = postogradsc;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the divisao
     */
    public Divisao getDivisao() {
        return divisao;
    }

    /**
     * @param divisao the divisao to set
     */
    public void setDivisao(Divisao divisao) {
        this.divisao = divisao;
    }

    /**
     * @return the ramal
     */
    public String getRamal() {
        return ramal;
    }

    /**
     * @param ramal the ramal to set
     */
    public void setRamal(String ramal) {
        this.ramal = ramal;
    }

    /**
     * @return the movimentos
     */
    public List<Movimento> getMovimentos() {
        return movimentos;
    }

    /**
     * @param movimentos the movimentos to set
     */
    public void setMovimentos(List<Movimento> movimentos) {
        this.movimentos = movimentos;
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
        if (!(object instanceof Funcionario)) {
            return false;
        }
        Funcionario other = (Funcionario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ctex.cport.modelo.Funcionario[ id=" + id + " ]";
    }
    
}
