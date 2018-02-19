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

public class DepartamentoCienciasBiologicas {

	public static List<InormacionProfesorDTO> obtenerinformacion(Document paginaFacultad) {
		List<String> urlProfesoresLst = obtenerUrlProfesores(paginaFacultad);
		List<InormacionProfesorDTO> informacionProfesores = obtenerInformacionProfesores(urlProfesoresLst);
		return informacionProfesores;
	}

	private static List<InormacionProfesorDTO> obtenerInformacionProfesores(List<String> urlProfesoresLst) {
		List<InormacionProfesorDTO> infoProfesores = new ArrayList<InormacionProfesorDTO>();
		for(String urlProfesor : urlProfesoresLst){
			try {
				Document paginaProfesor = Jsoup.connect(urlProfesor).get();
				
				paginaProfesor.select("h1").text();
				InormacionProfesorDTO profesor = new InormacionProfesorDTO();
//				profesor.setEmail(paginaProfesor.select("a[href]").first().text());
				profesor.setSitioWeb(urlProfesor);
				profesor.setNombre(paginaProfesor.title());
//				profesor.setEscalafon(paginaProfesor.select("p").text());
//				profesor.setExtencionTelefonica(inf[14]);
				infoProfesores.add(profesor);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return infoProfesores;
	}

	public static List<String> obtenerUrlProfesores(Document paginaFacultad){
		Elements paginas = paginaFacultad.select("a[href]");
		List<String> paginadores = new ArrayList<String>();
		boolean cargar = true;
		for(Element linkPagina: paginas){
			if(cargar && linkPagina.toString().contains("/nuestros-profesores")){
				try {
					Document paginaProfesores = Jsoup.connect(linkPagina.attr("abs:href")).get();
					Elements linkPaginas = paginaProfesores.getElementsByClass("level2").select("a[href]");
					for(Element link : linkPaginas){
						paginadores.add(link.attr("abs:href"));
					}
					cargar = false;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return paginadores;
	}

}
