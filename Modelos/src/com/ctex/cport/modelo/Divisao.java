/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctex.cport.modelo;

import java.io.Serializable;

/**
 *
 * @author Ralfh
 */
@Entity
@Table(name = "divisao")
@NamedQueries({
    @NamedQuery(name = "Divisao.findAll", query = "SELECT d FROM Divisao d"),
    @NamedQuery(name = "Divisao.findById", query = "SELECT d FROM Divisao d WHERE d.id = :id"),
    @NamedQuery(name = "Divisao.findBySigla", query = "SELECT d FROM Divisao d WHERE d.sigla = :sigla"),
    @NamedQuery(name = "Divisao.findByNome", query = "SELECT d FROM Divisao d WHERE d.nome = :nome")})
public class Divisao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Size(max = 25)
    @Column(name = "sigla")
    private String sigla;
    @Size(max = 255)
    @Column(name = "nome")
    private String nome;

    public Divisao() {
    }

    public Divisao(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
        if (!(object instanceof Divisao)) {
            return false;
        }
        Divisao other = (Divisao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        
//         return String.format("%s[id=%d]", getClass().getSimpleName(), getId());
        
        return "com.ctex.cport.modelo.Divisao[ id=" + id + " ]";
    }
    
}
