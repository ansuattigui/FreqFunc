/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template report, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctex.cport.controle.Bean;

import com.ctex.cport.modelo.Divisao;
import com.ctex.cport.modelo.Movimento;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.swing.ImageIcon;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author ralfh
 */
@ManagedBean(name = "relatorioMovimento")
@RequestScoped
public class RelatorioMovimento implements Serializable {
    
    private String jasper;
    private String relatorio;
    private ExternalContext context;
    private JRDataSource jrDataSource;
    private JasperPrint jasperPrint;
    private Movimento[] arrayMovimento;
    private Object modelo;
    private String hora;
    private String nomeRelatorio;
    private String escopoRelatorio;
    private Date dataInicio;
    private Date dataFim;
    private Divisao divisao;
    private SimpleDateFormat sdf;
    
        
    @EJB 
    private MovimentoFacade movimentoFacade;

 
    /**
     * Creates a new instance of RelatorioDivisao
     */
    public RelatorioMovimento() { 
        sdf = new SimpleDateFormat("dd-mm-yyyy");
    }    

    /**
     * @return the jasper
     */
    public String getJasper() {
        jasper = getContext().getRealPath("WEB-INF/relatorios/movimento/movimento.jasper");
        return jasper;
    }

    /**
     * @param jasper the jasper to set
     */
    public void setJasper(String jasper) {
        this.jasper = jasper;
    }

    /**
     * @return the context
     */
    public ExternalContext getContext() {
        context = FacesContext.getCurrentInstance().getExternalContext();
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(ExternalContext context) {
        this.context = context;
    }

    public JRDataSource getJrDataSource() {
        jrDataSource = new JRBeanArrayDataSource(getArrayMovimento());
        return jrDataSource;
    }   
    
    /**
     * @param filtro
     * @return the jrDataSource
     */
    public JRDataSource getJrDataSource(Object filtro) {
        jrDataSource = new JRBeanArrayDataSource(getArrayMovimento(filtro));
        return jrDataSource;
    }

    /**
     * @param jrDataSource the jrDataSource to set
     */
    public void setJrDataSource(JRDataSource jrDataSource) {
        this.jrDataSource = jrDataSource;
    }

    public Movimento[] getArrayMovimento() {
        List<Movimento> lista;
        lista = movimentoFacade.findAll();        
        arrayMovimento = lista.toArray(new Movimento[lista.size()]);        
        return arrayMovimento;
    }
    

    public Movimento[] getArrayMovimento(Object filtro) {
        List<Movimento> lista = null;
        if (filtro instanceof Divisao) {
            lista = movimentoFacade.findAll((Divisao) filtro);        
        }
        arrayMovimento = lista.toArray(new Movimento[lista.size()]);        
        return arrayMovimento;
    }

    public void setArrayMovimento(Movimento[] arrayMovimento) {
        this.arrayMovimento = arrayMovimento;
    }

    
    public JasperPrint getJasperPrint() {        
        ImageIcon logotipo = new ImageIcon(getContext().getRealPath("/resources/img/logo-ctex.png"));                
        HashMap hm = new HashMap<>();
        hm.put("par_logotipo",logotipo.getImage());
        hm.put("par_nomerelat", nomeRelatorio);
        hm.put("par_escoporelat", escopoRelatorio);
        try {   
            jasperPrint = JasperFillManager.fillReport(getJasper(),hm, getJrDataSource());
        } catch (JRException ex) {
            Logger.getLogger(RelatorioMovimento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jasperPrint;
    }
    
    
    /**
     * @param filtro
     * @return the jasperPrint
     */
    public JasperPrint getJasperPrint(Object filtro) {        
        ImageIcon logotipo = new ImageIcon(getContext().getRealPath("/resources/img/logo-ctex.png"));                
        HashMap hm = new HashMap<>();
        hm.put("par_logotipo",logotipo.getImage());
        hm.put("par_nomerelat", nomeRelatorio);
        hm.put("par_escoporelat", escopoRelatorio);
        try {   
            jasperPrint = JasperFillManager.fillReport(getJasper(),hm, getJrDataSource(filtro));
        } catch (JRException ex) {
            Logger.getLogger(RelatorioMovimento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jasperPrint;
    }

    /**
     * @param jasperPrint the jasperPrint to set
     */
    public void setJasperPrint(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    public void setReport() {        
        relatorio = "/reports/movimento.pdf";
        try { 
            if ("".equals(divisao.getSigla())) {
                JasperExportManager.exportReportToPdfFile(getJasperPrint(), getContext().getRealPath(relatorio));
            } else {
                
            }
            
        } catch (JRException ex) {
            Logger.getLogger(RelatorioMovimento.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    
    
    /**
     * @param filtro
     */
    public void setReport(Object filtro) {        
        relatorio = "/reports/movimento.pdf";
        try { 
            if (filtro instanceof Divisao) {    
                JasperExportManager.exportReportToPdfFile(getJasperPrint(filtro), getContext().getRealPath(relatorio));
            }
        } catch (JRException ex) {
            Logger.getLogger(RelatorioMovimento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void onPrerender(ComponentSystemEvent event) {    
    }      
    
    public String getRelatorio() {
        return relatorio;
    }

    /**
     * @param relatorio the relatorio to set
     */
    public void setRelatorio(String relatorio) {
        this.relatorio = relatorio;
    }

    /**
     * @return the modelo
     */
    public Object getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     * @return 
     */
    public String setModelo(Object modelo) {
        this.modelo = modelo;
        this.nomeRelatorio = "Relação dos que entraram ou saíram fora do horário";
        if (modelo instanceof Divisao) {
            String div = ((Divisao)modelo).getSigla();
            escopoRelatorio = "Por Divisão - ".concat(div);
            setReport(modelo);
        } else if (modelo == null) {
            escopoRelatorio = "Total";
            setReport();
        }
        return "/relatorios/movimento/movimento";
    }
    
    public String setPeriodo() {
        this.nomeRelatorio = "Relação dos que entraram ou saíram fora do horário";
        String escopo;
        if (dataInicio.equals(dataFim)) {
            escopo = "Movimento no dia ".concat(sdf.format(dataInicio));
        } else {
            escopo = "Movimento no periodo de ".concat(sdf.format(dataInicio)).concat(" à ").concat(sdf.format(dataFim));
        }
        if ("".equals(divisao.getSigla())) {
            this.escopoRelatorio = escopo;
        } else {
            this.escopoRelatorio = "Por Divisão - ".concat(divisao.getSigla()).concat(" / ").concat(escopo);
        }            
        
        setReport();
        
        return "/relatorios/movimento/movimento";
    }
    
  
    /**
     * @return the hora
     */
    public String getHora() {
        Calendar horario = Calendar.getInstance();
        return horario.getTime().toString();
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    /**
     * @return the nomeRelatorio
     */
    public String getNomeRelatorio() {
        return nomeRelatorio;
    }

    /**
     * @param nomeRelatorio the nomeRelatorio to set
     */
    public void setNomeRelatorio(String nomeRelatorio) {
        this.nomeRelatorio = nomeRelatorio;
    }

    /**
     * @return the escopoRelatorio
     */
    public String getEscopoRelatorio() {
        return escopoRelatorio;
    }

    /**
     * @param escopoRelatorio the escopoRelatorio to set
     */
    public void setEscopoRelatorio(String escopoRelatorio) {
        this.escopoRelatorio = escopoRelatorio;
    }

    /**
     * @return the dataInicio
     */
    public Date getDataInicio() {
        return dataInicio;
    }

    /**
     * @param dataInicio the dataInicio to set
     */
    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    /**
     * @return the dataFim
     */
    public Date getDataFim() {
        return dataFim;
    }

    /**
     * @param dataFim the dataFim to set
     */
    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }
    
    // Manipulador de Evento para o Calendário da Data do Movimento
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
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
    
    public void prepareCreate() {
        this.divisao = new Divisao();
        this.dataInicio = null;
        this.dataFim = null;
    }
    
    
    
    
    
    
    
    

}
