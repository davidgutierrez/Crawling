package co.edu.uniandes.bigdata.crawling.Facultad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import co.edu.uniandes.bigdata.dto.InormacionProfesorDTO;

public class FacultadCiencias {

	private static List<InormacionProfesorDTO> infoProfesores = new ArrayList<InormacionProfesorDTO>();

	public static List<InormacionProfesorDTO> obtenerinformacion(Document paginaFacultad) {
		List<String> programasAcademicos = programasAcademicos(paginaFacultad);
		List<InormacionProfesorDTO> informacionProfesores = obtenerInformacionProfesores(programasAcademicos);
		return informacionProfesores;
	}

	private static List<InormacionProfesorDTO> obtenerInformacionProfesores(List<String> programasAcademicos) {
		List<InormacionProfesorDTO> infoProfesores = new ArrayList<InormacionProfesorDTO>();
		for(String urlProgramaAcademico : programasAcademicos){
			try {
				Elements paginaProgramaAcademico = Jsoup.connect(urlProgramaAcademico).get().getElementsByClass("wsp-container-outer");
				for(Element dato : paginaProgramaAcademico){
					Elements infoProfesor = dato.getElementsByClass("wsp-title");
					for(Element tag : infoProfesor){
						InormacionProfesorDTO profesor = new InormacionProfesorDTO();
						profesor.setNombre(tag.getElementsByClass("wsp-title-toggle").text());
						String[] info =  dato.toString().split("<p>");
						profesor.setEmail(info[1]);
						profesor.setDependencia(info[2]);
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

	public static List<String> programasAcademicos(Document paginaFacultad){
		Elements paginas = paginaFacultad.select("a[href]");
		List<String> programasAcademicos = new ArrayList<String>();
		for(Element linkPagina: paginas){
			if(linkPagina.toString().contains("/profesores/profesores")){
				programasAcademicos.add(linkPagina.attr("abs:href"));
			}
		}
		return programasAcademicos;
	}

}
