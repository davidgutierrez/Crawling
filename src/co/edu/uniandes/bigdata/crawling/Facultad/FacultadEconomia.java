package co.edu.uniandes.bigdata.crawling.Facultad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import co.edu.uniandes.bigdata.dto.InormacionProfesorDTO;

public class FacultadEconomia {

	public static List<InormacionProfesorDTO> obtenerinformacion(Document paginaFacultad) {
		List<String> paginadores = obtenerPaginadores(paginaFacultad);
		List<InormacionProfesorDTO> informacionProfesores = obtenerInformacionProfesores(paginadores);
		return informacionProfesores;
	}

	private static List<InormacionProfesorDTO> obtenerInformacionProfesores(List<String> urlProfesoresLst) {
		List<InormacionProfesorDTO> infoProfesores = new ArrayList<InormacionProfesorDTO>();
		for(String urlProfesor : urlProfesoresLst){
			try {
				Elements seccionProfesores = Jsoup.connect(urlProfesor).get().getElementsByClass("infoProfesor");
				if(seccionProfesores.isEmpty())
					continue;
				
				for(Element infoProfesor : seccionProfesores){
					Elements datosProfesor = infoProfesor.getElementsByClass("campoProfesor");
					InormacionProfesorDTO profesor = new InormacionProfesorDTO();
					profesor.setDependencia(datosProfesor.get(0).text());
					profesor.setEmail(datosProfesor.get(3).text());
					profesor.setEscalafon(datosProfesor.get(0).text());
					profesor.setExtencionTelefonica(datosProfesor.get(4).text());
					profesor.setNombre(infoProfesor.select("a[href]").first().text());
					profesor.setSitioWeb(infoProfesor.select("a[href]").first().attr("abs:href"));
					infoProfesores.add(profesor);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return infoProfesores;
	}

	public static List<String> obtenerPaginadores(Document paginaFacultad){
		Elements paginas = paginaFacultad.select("a[href]");
		List<String> paginadores = new ArrayList<String>();
		boolean cargar = true;
		for(Element linkPagina: paginas){
			if(cargar && linkPagina.toString().contains("com_profesor")){
				try {
					Document paginaProfesores = Jsoup.connect(linkPagina.attr("abs:href")).get();
					Elements linkPaginas = paginaProfesores.getElementsByClass("paginadorProfesores").select("a[href]");
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
