package com.rabia.rest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class JerseyClientJsonPost {

	public static void main(String[] args) {

		try {

			Client client = Client.create();

			WebResource webResource = client
					.resource("http://localhost:8181/zk-spring-hibernate/restful/car/add");

			String input = "{\"model\":\"Alphard\",\"make\":\"Toyota\",\"description\":\"Toyota Alphard\",\"price\":380}";

			ClientResponse response = webResource.type("application/json")
					.post(ClientResponse.class, input);

			System.out.println(response.getStatus());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
