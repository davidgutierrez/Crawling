package co.edu.uniandes.bigdata;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean(name = "principal")
@SessionScoped
public class Principal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void cambiarARss(){
		try{
            FacesContext contex = FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect( "rss.jsf" );
		}catch(  Exception e ){
		 System.out.println(e.getMessage());
		}
	}
	
	public void cambiarACrawling(){
		try{
            FacesContext contex = FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect( "crawling.jsf" );
		}catch(  Exception e ){
		 System.out.println(e.getMessage());
		}
	}
	
	public void volver(){
		try{
            FacesContext contex = FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect( "principal.jsf" );
		}catch(  Exception e ){
		 System.out.println(e.getMessage());
		}
	}

}
