package co.edu.uniandes.bigdata.crawling.Facultad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import co.edu.uniandes.bigdata.dto.InormacionProfesorDTO;

public class FacultadArquitecturaDisenio {

	private static List<InormacionProfesorDTO> infoProfesores = new ArrayList<InormacionProfesorDTO>();

	public static List<InormacionProfesorDTO> obtenerinformacion(Document paginaFacultad) {
		List<String> paginadores = obtenerPaginadores(paginaFacultad);
		List<String> urlProfesoresLst = obtenerUrlProfesores(paginadores);
		List<InormacionProfesorDTO> informacionProfesores = obtenerInformacionProfesores(urlProfesoresLst);
		return informacionProfesores;
	}

	private static List<InormacionProfesorDTO> obtenerInformacionProfesores(List<String> urlProfesoresLst) {
		List<InormacionProfesorDTO> infoProfesores = new ArrayList<InormacionProfesorDTO>();
		for(String urlProfesor : urlProfesoresLst){
			try {
				Elements paginaProfesor = Jsoup.connect(urlProfesor).get().getElementsByClass("descripcionDocente");
				for(Element dato : paginaProfesor){
					if(dato.text().contains("@uniandes")){
						InormacionProfesorDTO profesor = new InormacionProfesorDTO();
						profesor.setNombre(dato.getElementsByClass("txtVerde").text().split("Perfil Académico:")[0]);
						String[] info =  dato.text().split(":");
						profesor.setEscalafon(info[0]);
						profesor.setEmail(info[2]);
						profesor.setExtencionTelefonica(info[4]);
						profesor.setSitioWeb(urlProfesor);
						System.out.println(dato.text());
						infoProfesores.add(profesor);
					}
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return infoProfesores;
	}

	private static List<String> obtenerUrlProfesores(List<String> paginadores) {
		List<String> profesores = new ArrayList<String>();
		for(String pagina : paginadores){
			try {
				Document paginaPorPaginador = Jsoup.connect(pagina).get();
				Elements urlProfesores = paginaPorPaginador.select("a[href]");
				for(Element urlProfesor : urlProfesores){
					if(urlProfesor.toString().contains("profesores-de-planta/")){
						profesores.add(urlProfesor.attr("abs:href"));
						System.out.println(urlProfesor.attr("abs:href"));
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return profesores;
	}

	public static List<String> obtenerPaginadores(Document paginaFacultad){
		Elements paginas = paginaFacultad.select("a[href]");
		List<String> paginadores = new ArrayList<String>();
		for(Element linkPagina: paginas){
			if(linkPagina.toString().contains("profesores-de-planta")){
				try {
					Document paginaProfesoresPlanta = Jsoup.connect(linkPagina.attr("abs:href")).get();
					Elements linkProfesoresPlanta = paginaProfesoresPlanta.select("a[href]");
					paginadores.add(linkPagina.attr("abs:href"));
					for(Element linkProfesor: linkProfesoresPlanta){
						if(linkProfesor.toString().contains("profesores-de-planta?start") && !paginadores.contains(linkProfesor.attr("abs:href"))){
							paginadores.add(linkProfesor.attr("abs:href"));
							System.out.println(linkProfesor.attr("abs:href"));
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(linkPagina.toString().contains("profesores-de-catedra")){
				System.out.println("pc " + linkPagina);
			}
		}
		return paginadores;
	}

}
