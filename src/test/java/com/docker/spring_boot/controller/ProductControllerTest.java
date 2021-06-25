package com.docker.spring_boot.controller;

import com.docker.spring_boot.domain.User;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

	@Test
	void getProductsWithoutToken(@Autowired MockMvc mvc) throws Exception {

		mvc.perform(MockMvcRequestBuilders
				.get("/product")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
	}


	@Test
	void createProduct(@Autowired MockMvc mvc) throws Exception {


		User user =  new User("teste","cafe");

		mvc.perform(MockMvcRequestBuilders
				.post("/user/create")
				.accept(MediaType.APPLICATION_JSON).content(new Gson().toJson(user))
				.contentType(MediaType.APPLICATION_JSON));


		String response = mvc.perform(MockMvcRequestBuilders
				.post("/user/login")
				.accept(MediaType.APPLICATION_JSON).content(new Gson().toJson(user))
				.contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();


		final String token = String.format("Bearer %s",new Gson().fromJson(response,JsonObject.class).get("token").getAsString());

		mvc.perform(MockMvcRequestBuilders
				.post("/product").content(new Gson().toJson(new Product("teste"," teste de um produto", 9.5)))
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}




	@Test
	void deleteProduct(@Autowired MockMvc mvc) throws Exception {


		User user =  new User("teste2","cafe");

		mvc.perform(MockMvcRequestBuilders
				.post("/user/create")
				.accept(MediaType.APPLICATION_JSON).content(new Gson().toJson(user))
				.contentType(MediaType.APPLICATION_JSON));


		String response = mvc.perform(MockMvcRequestBuilders
				.post("/user/login")
				.accept(MediaType.APPLICATION_JSON).content(new Gson().toJson(user))
				.contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();

		final String token = String.format("Bearer %s",new Gson().fromJson(response,JsonObject.class).get("token").getAsString());


		mvc.perform(MockMvcRequestBuilders
				.post("/product").content(new Gson().toJson(new Product("teste"," teste de um produto", 9.5)))
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		mvc.perform(MockMvcRequestBuilders
				.delete("/product/1")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void updateProductWithAdminRole(@Autowired MockMvc mvc) throws Exception {


		User user =  new User("admin","123");

		String response = mvc.perform(MockMvcRequestBuilders
				.post("/user/login")
				.accept(MediaType.APPLICATION_JSON).content(new Gson().toJson(user))
				.contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();

		final String token = String.format("Bearer %s",new Gson().fromJson(response,JsonObject.class).get("token").getAsString());


		mvc.perform(MockMvcRequestBuilders
				.post("/product").content(new Gson().toJson(new Product("teste"," teste de um produto", 9.5)))
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


		response  = mvc.perform(MockMvcRequestBuilders
				.get("/product/1")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();


		Product product = new Gson().fromJson(response,Product.class);

		product.setPrice(50.5);


		mvc.perform(MockMvcRequestBuilders
				.put("/product").content(new Gson().toJson(product))
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

}
