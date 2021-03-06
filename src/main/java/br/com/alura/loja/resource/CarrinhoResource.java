package br.com.alura.loja.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;

@Path("carrinhos")
public class CarrinhoResource {
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String busca(@PathParam("id") long id){
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		
		return carrinho.toXml();
		
	}
	
	@POST
	@Produces(MediaType.APPLICATION_XML)
	public String adiciona(String carrinho){
		Carrinho carrinhoNew = (Carrinho) new XStream().fromXML(carrinho);
		new CarrinhoDAO().adiciona(carrinhoNew);
		
		return "<status>Sucesso</status>";
		
	}

}
