package co.edu.uniandes.bigdata.crawling.Facultad;

import java.io.IOException;
import java.util.Collection;

import co.edu.uniandes.bigdata.dto.InormacionProfesorDTO;

public class DepartamentoMusica extends DepartamentosGenerico{
	
	private static String dependecia="Departamento de Musica";


	public static Collection<? extends InormacionProfesorDTO> obtenerinformacion(String linkFacultadDpto, String facultad) throws IOException {
		dependecia = facultad;
		System.out.println("departamento: "+dependecia+" NO Hecha por javascript");
//		Document doc = Jsoup.connect(linkFacultadDpto).timeout(0).get();
//		Elements links = doc.select(".tablaUno").select(".bkMainMenu2").select(".moduletable").select("td");
		//docs = links.getElementById("cmSubMenuID8");
//		links = links.select("language=JavaScript");
//		for (Element link : links) {
//			String linkProfesores = link.attr("abs:href");
//			if(!linksProfesores.contains(linkProfesores)){
//					verificarPaginaProfesores(linkProfesores);
//				linksProfesores.add(linkProfesores);
//			}
//		}
		return infoProfesores;
	}


//	public static void verificarPaginaProfesores(String linkProfesores) throws IOException {
//		Document doc = Jsoup.connect(linkProfesores).timeout(0).get();
//		String consulta = doc.getElementById("iFrmResBusProf").attr("src");
//		doc = Jsoup.connect(linkProfesores.replaceAll("/profesores",consulta)).timeout(0).get();
//		Elements profesores = doc.select(".marcoListaProfesore").select(".itemProfesor").select(".informacionProfesor").select(".datosContacto");
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
//	}

}
