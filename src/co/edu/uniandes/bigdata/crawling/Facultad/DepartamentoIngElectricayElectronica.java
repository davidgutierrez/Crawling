package co.edu.uniandes.bigdata.crawling.Facultad;

import java.io.IOException;
import java.util.Collection;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import co.edu.uniandes.bigdata.dto.InormacionProfesorDTO;

public class DepartamentoIngElectricayElectronica extends DepartamentosGenerico {

	private static String dependecia="Departamento de Ingeniería Eléctrica y Electrónica";

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
	

	public static void verificarPaginaProfesores(String linkProfesores) throws IOException {
//		Document doc = Jsoup.connect(linkProfesores).timeout(0).get();
//		Elements consulta = doc.select("iframe");
//		Connection doco = Jsoup.connect(consulta.attr("src")).timeout(0);
//		try {
//			doco.wait(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		doc = doco.get();
//		Element profesores = doc.getElementById("x-auto-6");//(".x-grid3-body");
//		for (Element profesor : profesores) {
//			
//			String nombreProfesor = profesor.select(".nombreProfesor").text();
//			String email = profesor.select(".correoProfesor").text();
//			
//			String[] lista = profesor.select(".infoFacultad").html().replaceAll("(?i)<br[^>]*>", "br2n").split("br2n");
//
//			String escalafon = lista[0];
//			String telefono = null;
//			Elements sitiowebEle = profesor.select(".urlPagina").select("a");
//			String sitioweb = (sitiowebEle.attr("onclick"));
//			sitioweb = sitioweb.replace("window.open('", "").replace("')", "");
//			InormacionProfesorDTO infoProfesor = new InormacionProfesorDTO(nombreProfesor,escalafon
//					,dependecia,email,telefono,sitioweb);
//			infoProfesores.add(infoProfesor);
//		}
		System.out.println("pendiente"+dependecia+" por ACADEMIA");
	}

}
