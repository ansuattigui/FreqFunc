/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctex.cport.controle.Bean;

import com.ctex.cport.modelo.Movimento;
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
@ManagedBean(name = "relatorioMovimentoController")
@SessionScoped
public class RelatorioMovimentoController implements Serializable {
    
    /**
     * Creates a new instance of RelatorioMovimentoController
     */
    public RelatorioMovimentoController() {
    }

    @EJB 
    private MovimentoFacade movimentoFacade;
        
    private Movimento[] arrayMovimentos;
 
     
    public Movimento[] getArrayMovimentos() {
        List<Movimento> lista = movimentoFacade.findAll();        
        arrayMovimentos = lista.toArray(new Movimento[lista.size()]);        
        return arrayMovimentos;
    }
 
    public void setArrayMovimentos(Movimento[] arrayMovimentos) {
        this.arrayMovimentos = arrayMovimentos;
    }
    
    public void geraRelatorio() {
        
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext context = (ServletContext) externalContext.getContext();
        String arquivo = context.getRealPath("WEB-INF/relatorios/movimento/movimento.jasper");
        String destino = "movimento.html";
 
        JRDataSource jrds = new JRBeanArrayDataSource(getArrayMovimentos());   
        gerarRelatorioWeb(jrds, null, arquivo, destino);
    }
    
    private void gerarRelatorioWeb(JRDataSource jrds, Map<String, Object> parametros, String arquivo, String destino) {

        ServletOutputStream servletOutputStream = null;
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=report.pdf");
 
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
