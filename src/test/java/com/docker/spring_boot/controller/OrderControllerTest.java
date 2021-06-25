package com.docker.spring_boot.controller;

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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {


	@Test
	void createOrder(@Autowired MockMvc mvc) throws Exception {

		User user =  new User("teste","cafe");

		mvc.perform(MockMvcRequestBuilders
				.post("/customer/create")
				.accept(MediaType.APPLICATION_JSON).content(new Gson().toJson(user))
				.contentType(MediaType.APPLICATION_JSON));


		String response = mvc.perform(MockMvcRequestBuilders
				.post("/customer/login")
				.accept(MediaType.APPLICATION_JSON).content(new Gson().toJson(user))
				.contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();


		final String token = String.format("Bearer %s",new Gson().fromJson(response, JsonObject.class).get("token").getAsString());

		mvc.perform(MockMvcRequestBuilders
				.post("/product").content(new Gson().toJson(new Product("teste"," teste de um produto", 9.5)))
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


		mvc.perform(MockMvcRequestBuilders
				.post("/order").content(new Gson().toJson(new Order(Collections.singletonList(new Product(1L)), user)))
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

}
