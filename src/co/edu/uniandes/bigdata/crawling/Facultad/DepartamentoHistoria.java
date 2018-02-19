package co.edu.uniandes.bigdata.crawling.Facultad;

import java.io.IOException;
import java.util.Collection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import co.edu.uniandes.bigdata.dto.InormacionProfesorDTO;
//"Departamento de Lenguajes y Estudios Socioculturales"
//Departamento de Psicología
public class DepartamentoHistoria extends DepartamentosGenerico{

	private static String dependencia;

	public static Collection<? extends InormacionProfesorDTO> obtenerinformacion(String linkFacultadDpto, String facultad) throws IOException {
		dependencia = facultad;
		Document doc = Jsoup.connect(linkFacultadDpto).timeout(0).get();
		Elements links = doc.select("a[href]").select("[href*=/profesores]");
		for (Element link : links) {
			String linkProfesores = link.attr("abs:href");
			if(!linksProfesores.contains(linkProfesores)){
				verificarPaginaProfesores(linkProfesores);
				linksProfesores.add(linkProfesores);
			}
		}
		return infoProfesores;
	}

	private static void verificarPaginaProfesores(String linkProfesores) throws IOException {
		Document doc = Jsoup.connect(linkProfesores).timeout(0).get();
		Elements links = doc.select(".boxgrid.captionfull");
		for (Element link : links) {
			Elements nombre = link.select(".cover.boxcaption").select(".teaser-title");
			Elements sitiowebElem = nombre.select("a");
			String sitioweb = sitiowebElem.attr("abs:href");
			String nombreProfesor =nombre.text();
			Elements nivel = link.select(".cover.boxcaption").select(".teaser-text");
			String[] lista = nivel.html().replaceAll("(?i)<br[^>]*>", "br2n").split("br2n");
			String escalafon=formatearNivel(lista[0]);
			String email =nivel.select("a[href]").text();
			String extension = formatearExt(lista);
			InormacionProfesorDTO infoProfesor = new InormacionProfesorDTO(nombreProfesor,
							escalafon,dependencia,email,
							extension,sitioweb);
			infoProfesores.add(infoProfesor);
		}
	}


//	private static String formatearOficina(String ext, String oficina) {
//		String[] split = ext.split(" - ");
//		if(split.length==2)
//			oficina = split[1];
//		oficina = oficina.equals("")?ext:oficina;
//		return  oficina.replace("Oficina:","").replace("</p>","").trim();
//	}

	private static String formatearExt(String[] lista) {
		String retorno="";
		for (String string : lista) {
			if(string.contains("Ext.: ")){
				retorno = string.split(" - ")[0].replace("Ext.: ","").trim();
			}
		}
		return retorno;
	}

	private static String formatearNivel(String nivel) {
		String[] split = nivel.replace("\n","").split("</div>");
		return split[0].replace("<div>","").replace("<p>","").replace("<p id=\"gul\" align=\"left\">", "").trim();
	}



}
