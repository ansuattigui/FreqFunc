/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctex.cport.controle.Bean;

import com.ctex.cport.modelo.Divisao;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;

/**
 *
 * @author ralfh
 */
@ManagedBean(name = "relatorioDivisaoController")
@SessionScoped
public class RelatorioDivisaoController implements Serializable {

    @EJB 
    private DivisaoFacade divisaoFacade;

    /**
     * Creates a new instance of RelatorioMovimentoController
     */
    public RelatorioDivisaoController() {
    }
    
    private Divisao[] arrayDivisoes; 
     
    /**
     * @return the listaDivisoes
     */
    public Divisao[] getArrayDivisoes() {        
        List<Divisao> lista = divisaoFacade.findAll();        
        arrayDivisoes = lista.toArray(new Divisao[lista.size()]);        
        return arrayDivisoes;
    }

    /**
     * @param arrayDivisoes
     */
    public void setArrayDivisoes(Divisao[] arrayDivisoes) {
        this.arrayDivisoes = arrayDivisoes;
    }
    
    public void geraRelatorio() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext context = (ServletContext) externalContext.getContext();
        String arquivo = context.getRealPath("WEB-INF/relatorios/divisao/divisao.jasper");
        String destino = "divisao.html";
 
        JRDataSource jrds = new JRBeanArrayDataSource(getArrayDivisoes());   
        gerarRelatorioWeb(jrds, null, arquivo, destino);
    }
    
    private void gerarRelatorioWeb(JRDataSource jrds, Map<String, Object> parametros, String arquivo, String destino) {
        ServletOutputStream servletOutputStream = null;
        FacesContext context = FacesContext.getCurrentInstance();
//        ServletResponse response = (ServletResponse) context.getExternalContext().getResponse();
        
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        //response.addHeader("Content-disposition", "attachment; filename=report.pdf");
 
        try {
            servletOutputStream = response.getOutputStream();            
            
//            JasperRunManager.runReportToPdfFile(arquivo, destino , parametros, jrds);
//            JasperRunManager.runReportToHtmlFile(arquivo, destino, parametros, jrds);
//            JasperRunManager.runReportToPdfStream(new FileInputStream(new File(arquivo)), servletOutputStream, parametros, jrds);            
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(arquivo, parametros, jrds);
            JasperExportManager.exportReportToPdfFile(jasperPrint, destino);
            
            response.setContentType("text/html;charset=UTF-8");
            servletOutputStream.flush();
            servletOutputStream.close();
            context.renderResponse();
            context.responseComplete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }        
}
