package com.docker.spring_boot.controller;

import com.docker.spring_boot.domain.JsonMessage;
import com.docker.spring_boot.domain.User;
import com.docker.spring_boot.domain.Order;
import com.docker.spring_boot.domain.Product;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {


	@Test
	void createDraftOrder(@Autowired MockMvc mvc) throws Exception {

		User userAdm = new User("admin","123");

		String response = mvc.perform(MockMvcRequestBuilders
				.post("/user/login")
				.accept(MediaType.APPLICATION_JSON).content(new Gson().toJson(userAdm))
				.contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();


		final String token = String.format("Bearer %s",new Gson().fromJson(response, JsonObject.class).get("token").getAsString());

		mvc.perform(MockMvcRequestBuilders
				.post("/product").content(new Gson().toJson(new Product("teste"," teste de um produto", 9.5)))
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


		mvc.perform(MockMvcRequestBuilders
				.post("/order").content(new Gson().toJson(new Order(Collections.singleton(new Product(1L)), userAdm)))
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}


	@Test
	void deleteOrder(@Autowired MockMvc mvc) throws Exception {

		User userAdm = new User("admin","123");

		String response = mvc.perform(MockMvcRequestBuilders
				.post("/user/login")
				.accept(MediaType.APPLICATION_JSON).content(new Gson().toJson(userAdm))
				.contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();


		final String token = String.format("Bearer %s",new Gson().fromJson(response, JsonObject.class).get("token").getAsString());

		mvc.perform(MockMvcRequestBuilders
				.delete("/order")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void createOrder(@Autowired MockMvc mvc) throws Exception {

		User userAdm = new User("admin","123");

		String response = mvc.perform(MockMvcRequestBuilders
				.post("/user/login")
				.accept(MediaType.APPLICATION_JSON).content(new Gson().toJson(userAdm))
				.contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();


		final String token = String.format("Bearer %s",new Gson().fromJson(response, JsonObject.class).get("token").getAsString());

		mvc.perform(MockMvcRequestBuilders
				.post("/product").content(new Gson().toJson(new Product("teste"," teste de um produto", 9.5)))
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


		mvc.perform(MockMvcRequestBuilders
				.post("/order").content(new Gson().toJson(new Order(Collections.singleton(new Product(1L)), userAdm)))
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		String resposta = mvc.perform(MockMvcRequestBuilders
				.post("/order/finish")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();

		assertEquals(new JsonMessage(("Pedido criado com sucesso")).getMessage(), new Gson().fromJson(resposta,JsonMessage.class).getMessage());

	}



	@Test
	void createTwoDraftOrdersAtSameTime(@Autowired MockMvc mvc) throws Exception {

		User userAdm = new User("admin","123");

		String response = mvc.perform(MockMvcRequestBuilders
				.post("/user/login")
				.accept(MediaType.APPLICATION_JSON).content(new Gson().toJson(userAdm))
				.contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();


		final String token = String.format("Bearer %s",new Gson().fromJson(response, JsonObject.class).get("token").getAsString());

		mvc.perform(MockMvcRequestBuilders
				.post("/product").content(new Gson().toJson(new Product("teste"," teste de um produto", 9.5)))
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


		mvc.perform(MockMvcRequestBuilders
				.post("/order").content(new Gson().toJson(new Order(Collections.singleton(new Product(1L)), userAdm)))
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


		mvc.perform(MockMvcRequestBuilders
				.post("/order").content(new Gson().toJson(new Order(Collections.singleton(new Product(1L)), userAdm)))
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}



	@Test
	void createOrderWithoutToken(@Autowired MockMvc mvc) throws Exception {

		User userAdm = new User("admin","123");

		String response = mvc.perform(MockMvcRequestBuilders
				.post("/user/login")
				.accept(MediaType.APPLICATION_JSON).content(new Gson().toJson(userAdm))
				.contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();


		final String token = String.format("Bearer %s",new Gson().fromJson(response, JsonObject.class).get("token").getAsString());

		mvc.perform(MockMvcRequestBuilders
				.post("/product").content(new Gson().toJson(new Product("teste"," teste de um produto", 9.5)))
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


		mvc.perform(MockMvcRequestBuilders
				.post("/order").content(new Gson().toJson(new Order(Collections.singleton(new Product(1L)), userAdm)))
				.accept(MediaType.APPLICATION_JSON)
				//.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());

	}


	@Test
	void createDraftOrderWithoutBody(@Autowired MockMvc mvc) throws Exception {

		User userAdm = new User("admin","123");

		String response = mvc.perform(MockMvcRequestBuilders
				.post("/user/login")
				.accept(MediaType.APPLICATION_JSON).content(new Gson().toJson(userAdm))
				.contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();


		final String token = String.format("Bearer %s",new Gson().fromJson(response, JsonObject.class).get("token").getAsString());

		mvc.perform(MockMvcRequestBuilders
				.post("/product").content(new Gson().toJson(new Product("teste"," teste de um produto", 9.5)))
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


		mvc.perform(MockMvcRequestBuilders
				.post("/order")//.content(new Gson().toJson(new Order(Collections.singletonList(new Product(1L)), userAdm)))
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}

}
