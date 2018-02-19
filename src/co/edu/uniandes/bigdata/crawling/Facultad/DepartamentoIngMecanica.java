package co.edu.uniandes.bigdata.crawling.Facultad;

import java.io.IOException;
import java.util.Collection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import co.edu.uniandes.bigdata.dto.InormacionProfesorDTO;

public class DepartamentoIngMecanica extends DepartamentosGenerico {


	private static String dependecia="Departamento de Ingeniería Mecánica";

	public static Collection<? extends InormacionProfesorDTO> obtenerinformacion(String linkFacultadDpto) throws IOException {
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
		Document doc = Jsoup.connect(linkProfesores).timeout(0)
				.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
				.referrer("http://www.google.com")              
				.get();
		Elements lineas = doc.select(".entry-content").select("p,table,h1");
		String escalafon =  lineas.get(3).text().trim();
		for (int i = 4; i < lineas.size(); i++) {
			InormacionProfesorDTO infoProfesor = null;
			if(lineas.get(i).text().trim().equals(" "))
				i++;
			String nombreProfesor =lineas.get(i).text().trim();
			if(nombreProfesor.contains("  PROFESOR INVITADO")||nombreProfesor.contains("PROFESORES JUBILADOS - CÁTEDRA")){
				escalafon=nombreProfesor;
				i++;
			}
			if(nombreProfesor.contains("  PROFESORES DE CÁTEDRA")){
				escalafon=nombreProfesor;
				i++;
			}
			if((i+1)==lineas.size())
				return;
			if(escalafon.equals("  PROFESORES DE CÁTEDRA")){
				nombreProfesor =lineas.get(i).text();
				i++;
				infoProfesor = new InormacionProfesorDTO(nombreProfesor,
						escalafon,dependecia,null,null,null);
			}else{
				nombreProfesor =lineas.get(i).text();
				i=i+2;
				String sitioweb = lineas.get(i).select("a").attr("abs:href");
				i=i+2;
				String[] emailst = lineas.get(i).text().split("Email:")[1].split("Oficina:");
				String email = emailst[0].trim();
				String[] extensionlist = lineas.get(i).text().split("Extensión:");
				String extension = extensionlist.length<2?null:extensionlist[1].replace("-","").trim();
				infoProfesor = new InormacionProfesorDTO(nombreProfesor,
						escalafon,dependecia,email,extension,sitioweb);
			}
			infoProfesores.add(infoProfesor);
		} 
	}


}
