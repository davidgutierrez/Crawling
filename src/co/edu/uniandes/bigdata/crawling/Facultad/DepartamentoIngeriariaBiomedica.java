package co.edu.uniandes.bigdata.crawling.Facultad;

import java.io.IOException;
import java.util.Collection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import co.edu.uniandes.bigdata.dto.InormacionProfesorDTO;

public class DepartamentoIngeriariaBiomedica extends DepartamentosGenerico {
	
	private static String dependecia="Departamento de Ingeniería Biomédica";

	public static Collection<? extends InormacionProfesorDTO> obtenerinformacion(String linkFacultadDpto) throws IOException {
		Document doc = Jsoup.connect(linkFacultadDpto).timeout(0).get();
		Elements links = doc.select("a[href]").select("[href*=/profesores]");
		for (Element link : links) {
			String linkProfesores = link.attr("abs:href");
			if(!linksProfesores.contains(linkProfesores)){
				String nombreProfesor = link.text();
				String escalafon=null;
				InormacionProfesorDTO infoProfesor = new InormacionProfesorDTO(nombreProfesor,
						escalafon,dependecia,null,null,linkProfesores);
				infoProfesores.add(infoProfesor);
				linksProfesores.add(linkProfesores);
			}
		}
		return infoProfesores;
	}


}
