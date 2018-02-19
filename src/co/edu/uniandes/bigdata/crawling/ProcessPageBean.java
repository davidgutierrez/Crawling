package co.edu.uniandes.bigdata.crawling;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import co.edu.uniandes.bigdata.crawling.Facultad.DepartamentoAntropologia;
import co.edu.uniandes.bigdata.crawling.Facultad.DepartamentoArtes;
import co.edu.uniandes.bigdata.crawling.Facultad.DepartamentoCienciasPoliticas;
import co.edu.uniandes.bigdata.crawling.Facultad.DepartamentoDerecho;
import co.edu.uniandes.bigdata.crawling.Facultad.DepartamentoEconomia;
import co.edu.uniandes.bigdata.crawling.Facultad.DepartamentoHistoria;
import co.edu.uniandes.bigdata.crawling.Facultad.DepartamentoHumanidadesLiteratura;
import co.edu.uniandes.bigdata.crawling.Facultad.DepartamentoIngCivilAmbiental;
import co.edu.uniandes.bigdata.crawling.Facultad.DepartamentoIngElectricayElectronica;
import co.edu.uniandes.bigdata.crawling.Facultad.DepartamentoIngIndustrial;
import co.edu.uniandes.bigdata.crawling.Facultad.DepartamentoIngMecanica;
import co.edu.uniandes.bigdata.crawling.Facultad.DepartamentoIngQuimica;
import co.edu.uniandes.bigdata.crawling.Facultad.DepartamentoIngSistemasComputacion;
import co.edu.uniandes.bigdata.crawling.Facultad.DepartamentoIngeriariaBiomedica;
import co.edu.uniandes.bigdata.crawling.Facultad.DepartamentoMatematicas;
import co.edu.uniandes.bigdata.crawling.Facultad.DepartamentoMusica;
import co.edu.uniandes.bigdata.crawling.Facultad.DepartamentoQuimica;
import co.edu.uniandes.bigdata.crawling.Facultad.FacultadAdministracion;
import co.edu.uniandes.bigdata.crawling.Facultad.FacultadArquitecturaDisenio;
import co.edu.uniandes.bigdata.crawling.Facultad.FacultadArtesHumanidades;
import co.edu.uniandes.bigdata.crawling.Facultad.FacultadCiencias;
import co.edu.uniandes.bigdata.crawling.Facultad.FacultadIngenieria;
import co.edu.uniandes.bigdata.crawling.Facultad.FacultadMedicina;
import co.edu.uniandes.bigdata.dto.InormacionProfesorDTO;


@ManagedBean(name = "proccesPage")
@SessionScoped
public class ProcessPageBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;
	private List<InormacionProfesorDTO> infoProfesores;
	


//	public static void main(String []args){
//		try {
//			ProcessPageBean p = new ProcessPageBean();
//			p.processPage("http://www.uniandes.edu.co/");			
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.getMessage();
//			System.out.println(e.getMessage());
//		}
//	}
	
	public void processPage(String url) throws IOException{
		//get useful information
		Document doc = Jsoup.connect(url).get();
		List<Element> direccionesFacultadesDeptos = new ArrayList<Element>();
		Elements facultadPage = doc.select("a[href]");
		for(Element linkFacultades: facultadPage){
			if((linkFacultades.toString().contains("Facultad") && !linkFacultades.toString().contains("Facultades")) || linkFacultades.toString().contains("Departamento")){
				direccionesFacultadesDeptos.add(linkFacultades);
			}
		}
		processPageFacultades(direccionesFacultadesDeptos);
	}
	
	private void processPageFacultades(List<Element> direccionesFacultadesDeptos) {
		infoProfesores = new ArrayList<InormacionProfesorDTO>();
		boolean continuar = true;
		
		if(continuar){
			for(Element linkFacultadDepto: direccionesFacultadesDeptos){
				String nombreFacultad = linkFacultadDepto.toString().substring(linkFacultadDepto.toString().indexOf(">")+1, linkFacultadDepto.toString().lastIndexOf("<"));
				Document paginaFacultad;
				try {
					paginaFacultad = Jsoup.connect(linkFacultadDepto.attr("abs:href")).get();
					String linkFacultadDpto = linkFacultadDepto.attr("abs:href");
					switch (nombreFacultad) {
					case "Departamento de Historia":
						infoProfesores.addAll(DepartamentoHistoria.obtenerinformacion(linkFacultadDpto,nombreFacultad));					
						break;
					case "Departamento de Humanidades y Literatura":
						infoProfesores.addAll(DepartamentoHumanidadesLiteratura.obtenerinformacion(linkFacultadDpto));					
						break;
					case "Departamento de Ingenier�a Biom�dica":
						infoProfesores.addAll(DepartamentoIngeriariaBiomedica.obtenerinformacion(linkFacultadDpto));					
						break;
					case "Departamento de Ingenieria Civil y Ambiental":
						infoProfesores.addAll(DepartamentoIngCivilAmbiental.obtenerinformacion(linkFacultadDpto,nombreFacultad));					
						break;
					case "Departamento de Ingenier�a El�ctrica y Electr�nica":
						infoProfesores.addAll(DepartamentoIngElectricayElectronica.obtenerinformacion(linkFacultadDpto));					
						break;
					case "Departamento de Ingenier�a Industrial":
						infoProfesores.addAll(DepartamentoIngIndustrial.obtenerinformacion(linkFacultadDpto));					
						break;
					case "Departamento de Ingenier�a Mec�nica":
						infoProfesores.addAll(DepartamentoIngMecanica.obtenerinformacion(linkFacultadDpto));					
						break;
					case "Departamento de Ingenier�a Qu�mica":
						infoProfesores.addAll(DepartamentoIngQuimica.obtenerinformacion(linkFacultadDpto));					
						break;
					case "Departamento de Ingenier�a de Sistemas y Computaci�n":
						infoProfesores.addAll(DepartamentoIngSistemasComputacion.obtenerinformacion(linkFacultadDpto));					
						break;
					case "Departamento de Lenguajes y Estudios Socioculturales":
						infoProfesores.addAll(DepartamentoHistoria.obtenerinformacion(linkFacultadDpto,nombreFacultad));					
						break;
					case "Departamento de Matem�ticas":
						infoProfesores.addAll(DepartamentoMatematicas.obtenerinformacion(linkFacultadDpto,nombreFacultad));					
						break;
					case "Departamento de Musica":
						infoProfesores.addAll(DepartamentoMusica.obtenerinformacion(linkFacultadDpto,nombreFacultad));					
						break;
					case "Departamento de Psicolog�a":
						infoProfesores.addAll(DepartamentoHistoria.obtenerinformacion(linkFacultadDpto,nombreFacultad));					
						break;
					case "Departamento de Qu�mica":
						infoProfesores.addAll(DepartamentoQuimica.obtenerinformacion(linkFacultadDpto,nombreFacultad));					
						break;
					case "Facultad de Derecho":
						infoProfesores.addAll(DepartamentoDerecho.obtenerinformacion(linkFacultadDpto,nombreFacultad));					
						break;
					case "Facultad de Econom�a":
						infoProfesores.addAll(DepartamentoEconomia.obtenerinformacion(linkFacultadDpto));					
						break;
					case "Facultad de Ingenier�a":
						infoProfesores.addAll(FacultadIngenieria.obtenerinformacion(linkFacultadDpto));					
						break;
					case "Facultad de Administraci�n":
						infoProfesores.addAll(FacultadAdministracion.obtenerinformacion(paginaFacultad));					
						break;
					case "Facultad de Arquitectura y Dise�o":
						infoProfesores.addAll(FacultadArquitecturaDisenio.obtenerinformacion(paginaFacultad));					
						break;
					case "Facultad de Artes y Humanidades":
						infoProfesores.addAll(FacultadArtesHumanidades.obtenerinformacion(paginaFacultad));					
						break;
					case "Facultad de Ciencias":
						infoProfesores.addAll(FacultadCiencias.obtenerinformacion(paginaFacultad));					
						break;
//					case "Facultad de Ciencias Sociales":
//						infoProfesores.addAll(FacultadCienciasSociales.obtenerinformacion(paginaFacultad));	// pendiente por imagen			
//						break;
//					case "Facultad de Derecho":
//						infoProfesores.addAll(FacultadDerecho.obtenerinformacion(paginaFacultad));					
//						break;
//					case "Facultad de Ingenier�a":
//						infoProfesores.addAll(FacultadIngenieria.obtenerinformacion(paginaFacultad));	 //Esta informacion se encuentra por cada departamente				
//						break;
					case "Facultad de Medicina":
						infoProfesores.addAll(FacultadMedicina.obtenerinformacion(paginaFacultad));					
						break;
					case "Departamento de Antropolog�a":
						infoProfesores.addAll(DepartamentoAntropologia.obtenerinformacion(paginaFacultad));					
						break;
//					case "Departamento de Arquitectura":
//						infoProfesores.addAll(DepartamentoArquitectura.obtenerinformacion(paginaFacultad));		// Error			
//						break;
					case "Departamento de Arte":
						infoProfesores.addAll(DepartamentoArtes.obtenerinformacion(paginaFacultad));					
						break;
					case "Departamento de Ciencia Politica":
						infoProfesores.addAll(DepartamentoCienciasPoliticas.obtenerinformacion(paginaFacultad));					
						break;
//					case "Departamento de Ciencias Biol�gicas":
//						infoProfesores.addAll(DepartamentoCienciasBiologicas.obtenerinformacion(paginaFacultad));		// Ciencias biologicas			
//						break;
//					case "Departamento de Dise�o":
//						infoProfesores.addAll(DepartamentoDisenio.obtenerinformacion(paginaFacultad));	 //Error en la pagina	
//						break;
//					case "Departamento de Filosofia":
//						infoProfesores.addAll(DepartamentoFilosofia.obtenerinformacion(paginaFacultad));					
//						break;
//					case "Departamento de Fisica":
//						infoProfesores.addAll(FacultadCiencias.obtenerinformacion(doc));					
//						break;
					default:
						System.out.println("pendiente "+nombreFacultad);
						break;
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					System.out.print(nombreFacultad+ "\t Error");
				}
				System.out.println();
			}
			continuar = false;
		}
		
		System.out.println("fin calculo");
	}

	public void processPage() throws IOException{
		processPage(this.url);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<InormacionProfesorDTO> getInfoProfesores() {
		return infoProfesores;
	}

	public void setInfoProfesores(List<InormacionProfesorDTO> infoProfesores) {
		this.infoProfesores = infoProfesores;
	}

}
