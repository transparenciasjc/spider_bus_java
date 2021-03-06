package org.spider.bus.recurso;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.spider.bus.business.recurso.SpiderBusiness;

@Path("todos")
public class SpiderRecurso {

	@Inject
	private SpiderBusiness spiderBusiness;

	@GET
	@Path("/")
	@Produces("application/json; charset=UTF-8")
	// TODOS independente do tipo
	public Response buscarTodos() {
		return spiderBusiness.buscarTodos();
	}

	@GET
	@Path("/{valor}")
	@Produces("application/json; charset=UTF-8")
	// por rua ou por numero independente do tipo
	public Response buscarPorNumero(@PathParam("valor") String valor) {
		return spiderBusiness.buscar(valor);
	}

}
