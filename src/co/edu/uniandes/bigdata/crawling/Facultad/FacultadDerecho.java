package co.edu.uniandes.bigdata.crawling.Facultad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import co.edu.uniandes.bigdata.dto.InormacionProfesorDTO;

public class FacultadDerecho {

	public static List<InormacionProfesorDTO> obtenerinformacion(Document paginaFacultad) {
		List<String> paginadores = obtenerPaginadores(paginaFacultad);
		List<String> urlProfesoresLst = obtenerUrlProfesores(paginadores);
		List<InormacionProfesorDTO> informacionProfesores = obtenerInformacionProfesores(urlProfesoresLst);
		return informacionProfesores;
	}

	private static List<InormacionProfesorDTO> obtenerInformacionProfesores(List<String> urlProfesoresLst) {
		List<InormacionProfesorDTO> infoProfesores = new ArrayList<InormacionProfesorDTO>();
		for(String urlProfesor : urlProfesoresLst){
			try {
				Elements paginaProfesor = Jsoup.connect(urlProfesor).get().getElementsByClass("content-text");
				if(paginaProfesor.isEmpty())
					continue;
				Jsoup.clean(paginaProfesor.text(), Whitelist.basic());
				//
			    Elements doc = Jsoup.parse(paginaProfesor.toString(),"utf-8").getElementsByClass("content-text");  
			      
			    Element text = doc.select("div[style=text-align: left;]").first();
			    
				InormacionProfesorDTO profesor = new InormacionProfesorDTO();
				profesor.setEmail("protegido javascript");
				profesor.setSitioWeb(urlProfesor);
				profesor.setNombre(doc.select("strong").first().text());
//				profesor.setEscalafon(text.toString().split(""));
//				profesor.setExtencionTelefonica(inf[14]);
				infoProfesores.add(profesor);
//				for(Element dato : paginaProfesor){
//					String[] inf = dato.toString().split("<br");
//					InormacionProfesorDTO profesor = new InormacionProfesorDTO();
//					profesor.setEmail("protegido javascript");
//					profesor.setSitioWeb(urlProfesor);
//					profesor.setNombre(inf[8]);
//					profesor.setEscalafon(inf[9].split(">")[2]);
//					profesor.setExtencionTelefonica(inf[14]);
//					infoProfesores.add(profesor);
//				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return infoProfesores;
	}

	private static List<String> obtenerUrlProfesores(List<String> paginadores) {
		List<String> profesores = new ArrayList<String>();
		for(String pagina : paginadores){
			try {
				Document paginaPorPaginador = Jsoup.connect(pagina).get();
				Elements urlProfesores = paginaPorPaginador.select("a[href]");
				for(Element urlProfesor : urlProfesores){
					if(urlProfesor.toString().contains("es/docencia/profesores-de-planta/")){
						profesores.add(urlProfesor.attr("abs:href"));
						System.out.println(urlProfesor.attr("abs:href"));
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return profesores;
	}

	public static List<String> obtenerPaginadores(Document paginaFacultad){
		Elements paginas = paginaFacultad.select("a[href]");
		List<String> paginadores = new ArrayList<String>();
		boolean cargar = true;
		for(Element linkPagina: paginas){
			if(cargar && linkPagina.toString().contains("docencia")){
				try {
					Document paginaProfesores = Jsoup.connect(linkPagina.attr("abs:href")).get();
					Elements linkPaginas = paginaProfesores.getElementsByClass("moduletable_menu").select("a[href]");
					for(Element link : linkPaginas){
						if(link.attr("abs:href").contains("docencia/profesores")){
							paginadores.add(link.attr("abs:href"));
							if(link.text().contains("Profesores de planta >>")){
								paginadores.remove(0);
								Document profesoresPorLetra = Jsoup.connect(link.attr("abs:href")).get();
								Elements linkProfesores = profesoresPorLetra.getElementsByClass("moduletable_menu").select("a[href]");
								for(Element lp : linkProfesores){
									if(lp.attr("abs:href").contains("docencia/profesores-de-planta/")){
										paginadores.add(lp.attr("abs:href"));
									}
								}
							}
						}
					}
					cargar = false;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return paginadores;
	}

}
