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

public class FacultadMedicina {

	public static List<InormacionProfesorDTO> obtenerinformacion(Document paginaFacultad) {
		List<String> urlProfesoresLst = obtenerUrlProfesores(paginaFacultad);
		List<InormacionProfesorDTO> informacionProfesores = obtenerInformacionProfesores(urlProfesoresLst);
		return informacionProfesores;
	}

	private static List<InormacionProfesorDTO> obtenerInformacionProfesores(List<String> urlProfesoresLst) {
		List<InormacionProfesorDTO> infoProfesores = new ArrayList<InormacionProfesorDTO>();
		for(String urlProfesor : urlProfesoresLst){
			try {
				Element paginaProfesor = Jsoup.connect(urlProfesor).get().getElementById("datos_profesor");
				InormacionProfesorDTO profesor = new InormacionProfesorDTO();
				profesor.setEmail(paginaProfesor.getElementsByClass("email").first().text());
				profesor.setSitioWeb(urlProfesor);
				profesor.setNombre(paginaProfesor.getElementsByClass("titulo").first().text());
				profesor.setEscalafon(paginaProfesor.getElementsByClass("texto").first().text().split(",")[0]);
				profesor.setExtencionTelefonica(paginaProfesor.getElementsByClass("small").get(1).text());
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
			if(cargar && linkPagina.toString().contains("/profesores")){
				try {
					Document paginaProfesores = Jsoup.connect(linkPagina.attr("abs:href")).get();
					Elements linkPaginas = paginaProfesores.getElementsByClass("moduletable").select("a[href]");
					for(Element link : linkPaginas){
						if(link.attr("abs:href").contains("profesores/planta")){
							Document profesoresPorLetra = Jsoup.connect(link.attr("abs:href")).get();
							Elements linkProfesores = profesoresPorLetra.getElementsByClass("tablelist").select("a[href]");
							for(Element lp : linkProfesores){
								paginadores.add(lp.attr("abs:href"));
							}
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
