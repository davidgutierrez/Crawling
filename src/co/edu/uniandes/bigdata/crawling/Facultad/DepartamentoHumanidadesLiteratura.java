package co.edu.uniandes.bigdata.crawling.Facultad;

import java.io.IOException;
import java.util.Collection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import co.edu.uniandes.bigdata.dto.InormacionProfesorDTO;

public class DepartamentoHumanidadesLiteratura extends DepartamentosGenerico {
	
	private static String dependecia="Departamento de Humanidades y Literatura";


	public static Collection<? extends InormacionProfesorDTO> obtenerinformacion(String linkFacultadDpto) throws IOException {
		Document doc = Jsoup.connect(linkFacultadDpto).timeout(0).get();
		Elements links = doc.select("a[href]").select("[href*=/profesores]");
		for (Element link : links) {
			String linkProfesores = link.attr("abs:href");
			if(!linksProfesores.contains(linkProfesores)){
				if(linkProfesores.contains("planta"))
				verificarPaginaProfesoresPlanta(linkProfesores);
				linksProfesores.add(linkProfesores);
			}
		}
		return infoProfesores;
		
	}
	


	private static void verificarPaginaProfesoresPlanta(String linkProfesores) throws IOException {
		Document doc = Jsoup.connect(linkProfesores).timeout(0)
				   .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
	               .referrer("http://www.google.com")              
	               .get();
		Elements links = doc.select(".blog.members").select(".item");
		for (Element link : links) {
			Elements nombre = link.select("h2");
			Elements sitiowebElem = nombre.select("a");
			String sitioweb = sitiowebElem.attr("abs:href");
			String nombreProfesor =nombre.text();
			Elements otrainf = link.select("p");
			String email = obtenerEmail(otrainf);
			String escalafon = obtenerEscalafon(otrainf);
			InormacionProfesorDTO infoProfesor = new InormacionProfesorDTO(nombreProfesor,
							escalafon,dependecia,email,null,sitioweb);
			infoProfesores.add(infoProfesor);
		}
	}
	private static String obtenerEmail(Elements otrainf) {
		return (otrainf.size()==2?otrainf.get(0):otrainf.get(1)).text();
	}
	private static String obtenerEscalafon(Elements otrainf) {
		return (otrainf.size()==3?otrainf.get(2):otrainf.get(1)).text();
	}

}
