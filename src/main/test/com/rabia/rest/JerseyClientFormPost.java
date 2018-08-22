package com.rabia.rest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

public class JerseyClientFormPost {

	public static void main(String[] args) {
		try {

			Client client = Client.create();

			WebResource webResource = client
					.resource("http://localhost:8181/zk-spring-hibernate/restful/car/add/form");

			Form form = new Form();
			form.add("model", "juke");
			form.add("make", "nissan");
			form.add("description", "nissan juke");
			form.add("price", 240);
			ClientResponse response = webResource.post(ClientResponse.class, form);

			System.out.println(response.getStatus());

		} catch (Exception e) {

			e.printStackTrace();

		}
	}
}
