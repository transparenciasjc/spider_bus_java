package org.spider.bus.sondas;

import java.util.List;

import javax.inject.Inject;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.spider.bus.business.parse.HtmlParseLinhaTransporte;
import org.spider.bus.constantes.TipoConducao;
import org.spider.bus.model.LinhaModel;
import org.spider.bus.pojo.HoraItinerarioOnibus;

public class Raspar implements Job {

	@Inject
	private LinhaModel linhaModel;

	private HtmlParseLinhaTransporte parseDados;
	
	@Inject
	protected Logger log;

	public void rasparDados() throws Exception { // RANGE 101 ao 360

		log.info("###################### ATIVANDO SONDA ######################");

		linhaModel.removerTodos(); // Limpar Collection para entrada de novos dados

		log.info("###################### ALTERNATIVO ######################");

		Integer linhaAlternativo;
		Integer numeroMaximoAlternativo = 40;

		for ( linhaAlternativo = 10; linhaAlternativo <= numeroMaximoAlternativo; linhaAlternativo++ ) {
			parseDados = new HtmlParseLinhaTransporte(linhaAlternativo.toString());

			List<HoraItinerarioOnibus> linhas = parseDados.montaConteudoHorarioItinerario();
			linhaModel.salvarLinha(linhas, TipoConducao.ALTERNATIVO);
		}

		log.info("###################### ONIBUS ######################");

		Integer linhaOnibus;
		Integer numeroMaximoOnibus = 360;

		for ( linhaOnibus = 101; linhaOnibus <= numeroMaximoOnibus; linhaOnibus++ ) {
			parseDados = new HtmlParseLinhaTransporte(linhaOnibus.toString());

			List<HoraItinerarioOnibus> linhas = parseDados.montaConteudoHorarioItinerario();
			linhaModel.salvarLinha(linhas, TipoConducao.ONIBUS);
		}

		log.info("###################### FIM SONDA ######################");
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		try {
			rasparDados();
		} catch ( Exception e ) {
			log.error(e.getMessage());
		}
	}
}
