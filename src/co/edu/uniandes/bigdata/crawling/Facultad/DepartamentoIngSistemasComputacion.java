package co.edu.uniandes.bigdata.crawling.Facultad;

import java.io.IOException;
import java.util.Collection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import co.edu.uniandes.bigdata.dto.InormacionProfesorDTO;

public class DepartamentoIngSistemasComputacion extends DepartamentosGenerico {

	private static String dependecia="Departamento de Ingeniería de Sistemas y Computación";

	public static Collection<? extends InormacionProfesorDTO> obtenerinformacion(String linkFacultadDpto) throws IOException {
		Document doc = Jsoup.connect(linkFacultadDpto).timeout(0).get();
		Elements links = doc.select("a[href]").select("[href*=/profesores-]");
		for (Element link : links) {
			String linkProfesores = link.attr("abs:href");
			if(!linksProfesores.contains(linkProfesores)){
				if(linkProfesores.contains("planta"))
					verificarPaginaProfesoresPlanta(linkProfesores);
				if(linkProfesores.contains("vistantes"))
					verificarPaginaProfesoresVistantes(linkProfesores);
				if(linkProfesores.contains("catedra"))
					verificarPaginaProfesoresCatedra(linkProfesores);
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
		Elements lineas = doc.select(".span8");
		for (Element element : lineas) {
			String nombreProfesor = element.select(".name").text().trim();
			String escalafon = element.select(".cargo").text();
			String email = element.select("a[href]").text();
			Elements subconsulta = element.select(".tab-content").select(".active").select("p");
			String extension = subconsulta.get(subconsulta.size()-1).text();
			String sitioweb = null;
			InormacionProfesorDTO infoProfesor = new InormacionProfesorDTO(nombreProfesor,
					escalafon,dependecia,email,extension,sitioweb);
			infoProfesores.add(infoProfesor);
		} 
	}


	private static void verificarPaginaProfesoresVistantes(String linkProfesores) throws IOException {
		Document doc = Jsoup.connect(linkProfesores).timeout(0)
				.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
				.referrer("http://www.google.com")              
				.get();
		Elements lineas = doc.select(".active").select(".span9");
		for (Element element : lineas) {
			String nombreProfesor = element.select("h3").text().trim();
			String escalafon = "Profesor Visitante";
			String email = element.select("a[href]").text();
			String extension = null;
			String sitioweb = null;
			InormacionProfesorDTO infoProfesor = new InormacionProfesorDTO(nombreProfesor,
					escalafon,dependecia,email,extension,sitioweb);
			infoProfesores.add(infoProfesor);
		} 
	}


	private static void verificarPaginaProfesoresCatedra(String linkProfesores) throws IOException {
		Document doc = Jsoup.connect(linkProfesores).timeout(0)
				.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
				.referrer("http://www.google.com")              
				.get();
		Elements filas = doc.select("tr");
		for (Element fila : filas) {
			Elements columna = fila.select("td");			
			String nombreProfesor = columna.get(0).text().trim();
			String escalafon = "Catedra";
			String email = columna.get(2).text().trim();
			String extension = null;
			String sitioweb = null;
			if(nombreProfesor.equals("Nombre")&&(email.equals("Correo electrónico uniandes")||email.equals("información de contacto")))
				continue;
			InormacionProfesorDTO infoProfesor = new InormacionProfesorDTO(nombreProfesor,
					escalafon,dependecia,email,extension,sitioweb);
			infoProfesores.add(infoProfesor);
		} 
	}


}
