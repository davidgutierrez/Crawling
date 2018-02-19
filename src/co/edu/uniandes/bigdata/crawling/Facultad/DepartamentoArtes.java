package co.edu.uniandes.bigdata.crawling.Facultad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import co.edu.uniandes.bigdata.dto.InormacionProfesorDTO;

public class DepartamentoArtes {

	public static List<InormacionProfesorDTO> obtenerinformacion(Document paginaFacultad) {
		List<String> urlProfesoresLst = obtenerUrlProfesores(paginaFacultad);
		List<InormacionProfesorDTO> informacionProfesores = obtenerInformacionProfesores(urlProfesoresLst);
		return informacionProfesores;
	}

	private static List<InormacionProfesorDTO> obtenerInformacionProfesores(List<String> urlProfesoresLst) {
		List<InormacionProfesorDTO> infoProfesores = new ArrayList<InormacionProfesorDTO>();
		for(String urlProfesor : urlProfesoresLst){
			try {
				if(urlProfesor.contains("@"))
					continue;
				
				Elements paginaProfesor = Jsoup.connect(urlProfesor).get().getElementsByClass("infoProfe");
				paginaProfesor.select("h1").text();
				InormacionProfesorDTO profesor = new InormacionProfesorDTO();
				profesor.setEmail(paginaProfesor.select("a[href]").first().text());
				profesor.setSitioWeb(paginaProfesor.select("a[href]").get(1).text());
				profesor.setNombre(paginaProfesor.select("h1").text());
				profesor.setEscalafon(paginaProfesor.select("p").text());
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
			if(cargar && linkPagina.toString().contains("/profesores/")){
				try {
					Document paginaProfesores = Jsoup.connect(linkPagina.attr("abs:href")).get();
					Elements linkPaginas = paginaProfesores.getElementsByClass("teacher").select("a[href]");
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
