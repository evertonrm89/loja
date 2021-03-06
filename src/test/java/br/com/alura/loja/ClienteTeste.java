package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import junit.framework.Assert;

public class ClienteTeste {
	
	private HttpServer server;

	@Before
	public void startServidor(){
		
		server = Servidor.inicializaServidor();
		
	}
	
	@After
	public void stopServidor(){
		server.stop();
	}
	
	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado(){	
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		String conteudo = target.path("/carrinhos/1").request().get(String.class);
		Assert.assertTrue(conteudo.contains("<rua>Rua Vergueiro 3185, 8 anda"));
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
		
		System.out.println(conteudo);
		
	}
	


	@Test
    public void testaQueSuportaNovosCarrinhos(){
		
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080");
        
        Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
        carrinho.setRua("Rua Vergueiro");
        carrinho.setCidade("Sao Paulo");
        String xml =  carrinho.toXml();

        Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);

        Response response = target.path("/carrinhos").request().post(entity);
        Assert.assertEquals("<status>sucesso</status>", response.readEntity(String.class));
        Assert.assertEquals("<status>sucesso</status>", response);
    }


	

}
