package co.edu.uniandes.bigdata.crawling.Facultad;

import java.io.IOException;
import java.util.Collection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import co.edu.uniandes.bigdata.dto.InormacionProfesorDTO;

public class DepartamentoMatematicas extends DepartamentosGenerico{

	private static String dependencia;

	public static Collection<? extends InormacionProfesorDTO> obtenerinformacion(String linkFacultadDpto, String facultad) throws IOException {
		dependencia = facultad;
		Document doc = Jsoup.connect(linkFacultadDpto).timeout(0).get();
		Elements links = doc.select("a[href]").select("[href*=/profesores],[href*=/adjuntos],[href*=/docente]");
		for (Element link : links) {
			String linkProfesores = link.attr("abs:href");
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
		String consulta = doc.getElementById("blockrandom").attr("src");
		doc = Jsoup.connect(consulta).timeout(0).get();
		Elements profesores = doc.select(".tile-parent");
		for (Element link : profesores) {
			String nombreProfesor =link.getElementById("nombre").text();
			String[] lista =link.getElementById("info").html().replaceAll("(?i)<br[^>]*>", "br2n").split("br2n");;
			String escalafon=obtenerEscalafon(lista);
			String email =obtenerEmail(lista);
			String sitioweb = obtenerSitioWeb(lista);
			String extension = formatearExt(lista);
						InormacionProfesorDTO infoProfesor = new InormacionProfesorDTO(nombreProfesor,
										escalafon,dependencia,email,
										extension,sitioweb);
						infoProfesores.add(infoProfesor);
		}
	}


	private static String obtenerSitioWeb(String[] lista) {
		for (String string : lista) {
			if(string.contains("a href")){
				String[] resultado = string.split(">");
				return resultado[0].replace("\n<a href=\"", "").replace("\"", "").trim();
			}
		}
		return "";
	}


	private static String obtenerEmail(String[] lista) {
		for (String string : lista) {
			if(string.contains("E-Mail"))
				return string.replace("E-Mail: ","").replace("\n", "").trim();
		}
		return "";
	}

	private static String obtenerEscalafon(String[] lista) {
		for (String string : lista) {
			if(!(string.isEmpty()||string.equals("\n")))
				return string.replace("\n","").trim();
		}
		return "";
	}

	private static String formatearExt(Object[] objects) {
		String retorno="";
		for (Object object : objects) {
			String string = (String)object;
			if(string.contains("Ext.:")){
				String[] offext = string.split(" - ");
				for (int i = 0; i < offext.length; i++) {
					if(offext[i].contains("Ext."))
						retorno = offext[i].replace("Ext.:","").trim();

				}
			}
		}
		return retorno;
	}



}
