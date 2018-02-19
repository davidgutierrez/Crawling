package co.edu.uniandes.bigdata.crawling.Facultad;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import co.edu.uniandes.bigdata.crawling.Facultad.DepartamentosGenerico;
import co.edu.uniandes.bigdata.dto.InormacionProfesorDTO;

public class DepartamentoDerecho extends DepartamentosGenerico{

	private static String dependencia;

	public static Collection<? extends InormacionProfesorDTO> obtenerinformacion(String linkFacultadDpto, String facultad) throws IOException {
		dependencia = facultad;
		Document doc = Jsoup.connect(linkFacultadDpto).timeout(0).get();
		Elements links = doc.select("a[href]").select("[href*=/docencia]");
			doc = Jsoup.connect(links.get(0).attr("abs:href")).timeout(0).get();
		Elements menus = doc.select(".internal_content").select(".menu.left-menu.nav").select("a[href]").select("[href*=/profesores]");
		for (Element menu : menus) {
			String linkProfesores = menu.attr("abs:href");
			if(!linksProfesores.contains(linkProfesores)){
					verificarPaginaProfesores(linkProfesores);
				//		            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
				linksProfesores.add(linkProfesores);
			}
		}
		return infoProfesores;
	}


	private static void verificarPaginaProfesores(String linkProfesores) throws IOException {
		Document doc = Jsoup.connect(linkProfesores).timeout(0).get();
		Elements links = doc.select(".col-lg-9.center_side").select(".content-text").select("li").select("a[href]");
		for (Element alfabetico : links) {
			doc = Jsoup.connect(alfabetico.attr("abs:href")).timeout(0).get();
			Elements list = doc.select(".blog").select(".contentpaneopen").select(".docentes");
			for (Element element : list) {
				Elements profesor = element.select("td");
				String nombreProfesor = nombreProfesor(profesor);
				String escalafon = escalafonProfesor(profesor);
				String email = emailProfesor(profesor);
				String extension = extensionProfesor(profesor);;
				String sitioweb = sitiowebProfesor(profesor);;
				InormacionProfesorDTO infoProfesor = new InormacionProfesorDTO(nombreProfesor,
						escalafon,dependencia,email,extension,sitioweb);
				infoProfesores.add(infoProfesor);
				
			}
		}
	}


	private static String sitiowebProfesor(Elements profesor) {
		Elements resul = profesor.select("a[href]");
		return resul.attr("abs:href");
	}

	private static String extensionProfesor(Elements profesor) {
		String[] subcadena = profesor.get(2).html().replaceAll("(?i)<br[^>]*>", "br2n").split("br2n");
		return subcadena[subcadena.length-1].split("-")[0];
	}

	private static String emailProfesor(Elements profesor) {
		return null;
	}

	private static String escalafonProfesor(Elements profesor) {
		String[] subcadena = profesor.get(2).html().replaceAll("(?i)<br[^>]*>", "br2n").split("br2n");
		return subcadena[1];
	}

	private static String nombreProfesor(Elements profesor) {
		return profesor.select("strong").get(0).text();
	}

	private static String formatearWeb(Elements nivel, String linkProfesores) {
		linkProfesores =linkProfesores.split("/index")[0]+"/";
		String retorno= nivel.select("a").attr("href");
		return retorno.contains("http")?retorno:(linkProfesores+retorno);
	}


	private static String formatearEmail(Elements nivel) {
		String retorno="";
		for (Element string : nivel) {
			if(string.text().contains("e-mail:")){
				retorno = string.text().split(" - ")[0].replace("e-mail:","").trim();
			}
		}
		return retorno;
	}

	private static String formatearExt(Elements nivel) {
		String retorno="";
		for (Element string : nivel) {
			if(string.text().contains("Extensión: ")){
				retorno = string.text().split(" - ")[0].replace("Extensión: ","").trim();
			}
		}
		return retorno;
	}

	private static String formatearNivel(String nivel) {
		String[] split = nivel.replace("\n","").split("</div>");
		return split[0].replace("<div>","").replace("<p>","").replace("<p id=\"gul\" align=\"left\">", "").trim();
	}



}
