package co.edu.uniandes.bigdata.crawling.Facultad;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import co.edu.uniandes.bigdata.dto.InormacionProfesorDTO;

public class DepartamentoQuimica  extends DepartamentosGenerico{


	private static String dependencia;

	public static Collection<? extends InormacionProfesorDTO> obtenerinformacion(String linkFacultadDpto, String facultad) throws IOException {
		dependencia = facultad;
		Document doc = Jsoup.connect(linkFacultadDpto).timeout(0).get();
		List<DataNode> links = doc.select("script").get(0).dataNodes();
		doc = Jsoup.connect(links.get(0).toString().replace("location.href=","").replace("\"", "")).timeout(0).get();
		Elements menus = doc.select(".mega.haschild").select(".mega.first.haschild").
				select(".childcontent.cols1").select(".megamenu.level2").get(0).select("a");
		for (Element menu : menus) {
			String linkProfesores = menu.attr("abs:href");
			if(!linksProfesores.contains(linkProfesores)){
				if(menu.text().equals("Catedra")||("Visitantes").equals(menu.text())){
					verificarPaginaProfesoresCatedra(linkProfesores);					
				}
				else
					verificarPaginaProfesores(linkProfesores);
				//		            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
				linksProfesores.add(linkProfesores);
			}
		}
		return infoProfesores;
	}

	private static void verificarPaginaProfesoresCatedra(String linkProfesores) throws IOException {
		Document doc = Jsoup.connect(linkProfesores).timeout(0).get();
		Elements links = doc.select(".item-page").get(0).children();
		Element columnas;
		for (Iterator<?> iterator = links.iterator(); iterator.hasNext();) {
			String nombreProfesor = "";
			while(nombreProfesor.isEmpty()||nombreProfesor.equals(" ")||nombreProfesor.contains("Más información")){
				if(iterator.hasNext())
					columnas = (Element) iterator.next();
				else
					return;
				nombreProfesor = columnas.select("p").text().trim();
				if(nombreProfesor.isEmpty())
					nombreProfesor = columnas.select("strong").text().trim();
			}
			String escalafon = "";
			while(escalafon.isEmpty()){
				columnas = (Element) iterator.next();
				escalafon = columnas.select("p").text();
				if(escalafon.isEmpty())
					escalafon = columnas.select("strong").text();
			}
			String email = "";
			while(email.isEmpty()){
				columnas = (Element) iterator.next();
				email = columnas.select("p").text();
				if(email.isEmpty())
					email = columnas.select("strong").text();
			}
			email = email.replace("e-mail:","").trim();
			String extension = "";
			while(extension.isEmpty()){
				columnas = (Element) iterator.next();
				extension = columnas.select("p").text();
				if(extension.isEmpty())
					extension = columnas.select("strong").text();
			}
			extension = extension.replace("Extensión:","").trim();
			String sitioweb = null;
			InormacionProfesorDTO infoProfesor = new InormacionProfesorDTO(nombreProfesor,
					escalafon,dependencia,email,
					extension,sitioweb);
			infoProfesores.add(infoProfesor);
		}

	}

	private static void verificarPaginaProfesores(String linkProfesores) throws IOException {
		Document doc = Jsoup.connect(linkProfesores).timeout(0).get();
		Elements links = doc.select(".item-page").select("table").select("tr");
		Element columnas;
		for (Iterator<?> iterator = links.iterator(); iterator.hasNext();) {
			String nombreProfesor = "";
			while(nombreProfesor.isEmpty()){
				columnas = (Element) iterator.next();
				nombreProfesor = columnas.select("td").select("p").text();
				if(nombreProfesor.isEmpty())
					nombreProfesor = columnas.select("td").select("strong").text();
			}
			columnas = (Element) iterator.next();
			Elements nivel = columnas.select("p");
			String escalafon = nivel.get(0).text();
			String email = formatearEmail(nivel);
			String extension = formatearExt(nivel);
			String sitioweb = formatearWeb(nivel,linkProfesores);
			InormacionProfesorDTO infoProfesor = new InormacionProfesorDTO(nombreProfesor,
					escalafon,dependencia,email,
					extension,sitioweb);
			infoProfesores.add(infoProfesor);
		}
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




}
