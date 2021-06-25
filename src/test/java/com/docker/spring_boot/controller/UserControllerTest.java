package com.docker.spring_boot.controller;

import com.docker.spring_boot.domain.User;
import com.google.gson.Gson;
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
public class UserControllerTest {


	@Test
	void createCustomer(@Autowired MockMvc mvc) throws Exception {

		User user =  new User("teste","cafe");

		mvc.perform(MockMvcRequestBuilders
				.post("/user/create")
				.accept(MediaType.APPLICATION_JSON).content(new Gson().toJson(user))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}


	@Test
	void loginCustomerNotInsideDb(@Autowired MockMvc mvc) throws Exception {

		User user =  new User("teste2","cafe");

		mvc.perform(MockMvcRequestBuilders
				.post("/user/login")
				.accept(MediaType.APPLICATION_JSON).content(new Gson().toJson(user))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}



	@Test
	void loginCustomer(@Autowired MockMvc mvc) throws Exception {

		User user =  new User("queroToken","cafe");


		mvc.perform(MockMvcRequestBuilders
				.post("/user/create")
				.accept(MediaType.APPLICATION_JSON).content(new Gson().toJson(user))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		mvc.perform(MockMvcRequestBuilders
				.post("/user/login")
				.accept(MediaType.APPLICATION_JSON).content(new Gson().toJson(user))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}



	@Test
	void createTwoCustomersWithSameEmail(@Autowired MockMvc mvc) throws Exception {

		User user =  new User("carro","cafe");
		User user2 =  new User("carro","cafe");

		mvc.perform(MockMvcRequestBuilders
				.post("/user/create")
				.accept(MediaType.APPLICATION_JSON).content(new Gson().toJson(user))
				.contentType(MediaType.APPLICATION_JSON));

		mvc.perform(MockMvcRequestBuilders
				.post("/user/create")
				.accept(MediaType.APPLICATION_JSON).content(new Gson().toJson(user2))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
}

