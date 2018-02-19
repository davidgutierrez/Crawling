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

public class DepartamentoAntropologia {

	public static List<InormacionProfesorDTO> obtenerinformacion(Document paginaFacultad) {
		List<String> urlProfesoresLst = obtenerUrlProfesores(paginaFacultad);
		List<InormacionProfesorDTO> informacionProfesores = obtenerInformacionProfesores(urlProfesoresLst);
		return informacionProfesores;
	}

	private static List<InormacionProfesorDTO> obtenerInformacionProfesores(List<String> urlProfesoresLst) {
		List<InormacionProfesorDTO> infoProfesores = new ArrayList<InormacionProfesorDTO>();
		for(String urlProfesor : urlProfesoresLst){
			try {
				InormacionProfesorDTO profesor = new InormacionProfesorDTO();
				profesor.setEmail("protegido iframe");
				profesor.setSitioWeb(urlProfesor);
				profesor.setNombre(Jsoup.connect(urlProfesor).get().title());
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
			System.out.println(linkPagina.toString());
			if(cargar && linkPagina.toString().contains("/profesores")){
				try {
					Document paginaProfesores = Jsoup.connect(linkPagina.attr("abs:href")).get();
					Elements linkPaginas = paginaProfesores.getElementsByClass("menu").select("a[href]");
					for(Element link : linkPaginas){
						if(link.attr("abs:href").contains("/profesores/")){
							paginadores.add(link.attr("abs:href"));
						}
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
