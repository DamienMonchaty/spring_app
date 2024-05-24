package fr.formation.app.controller.api;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.formation.app.dto.PersonDTO;
import fr.formation.app.services.ICarService;
import fr.formation.app.services.IPersonService;

@WebMvcTest(PersonRestController.class)
@WithMockUser(username = "admin", roles = { "Admin" })
public class PersonRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IPersonService personService;

	@MockBean
	private ICarService carService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testGetAll() throws Exception {
		List<PersonDTO> mockPersonList = new ArrayList<>();
		mockPersonList.add(new PersonDTO(1, "John", "Doe", 30));
		mockPersonList.add(new PersonDTO(2, "Jane", "Doe", 28));

		when(personService.findAll()).thenReturn(mockPersonList);

		mockMvc.perform(get("/api/v1/persons")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(1))).andExpect(jsonPath("$[0].firstName", is("John")))
				.andExpect(jsonPath("$[0].lastName", is("Doe"))).andExpect(jsonPath("$[0].age", is(30)))
				.andExpect(jsonPath("$[1].id", is(2))).andExpect(jsonPath("$[1].firstName", is("Jane")))
				.andExpect(jsonPath("$[1].lastName", is("Doe"))).andExpect(jsonPath("$[1].age", is(28)));

		verify(personService, times(1)).findAll();
		verifyNoMoreInteractions(personService);
	}

	@Test
	public void testGetOne() throws Exception {
		int id = 1;
		PersonDTO mockPerson = new PersonDTO(id, "PRENOM1", "NOM1", 35);
		when(personService.findById(any(int.class))).thenReturn(Optional.of(mockPerson));

		mockMvc.perform(get("/api/v1/persons/{id}", id)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id", is(id)))
				.andExpect(jsonPath("$.firstName", is("PRENOM1"))).andExpect(jsonPath("$.lastName", is("NOM1")))
				.andExpect(jsonPath("$.age", is(1500)));

		verify(personService, times(1)).findById(id);
		verifyNoMoreInteractions(personService);
	}

	@Test
	public void testDeletePerson() throws Exception {
		int id = 1;
		PersonDTO deletedPerson = new PersonDTO(id, "PRENOM1", "NOM1", 35);
		when(personService.findById(any(int.class))).thenReturn(Optional.of(deletedPerson));

		mockMvc.perform(delete("/api/v1/persons/{id}", id).with(csrf())).andExpect(status().isOk());

		verify(personService, times(1)).findById(id);
		verify(personService, times(1)).deleteById(id);
		verifyNoMoreInteractions(personService);
	}

	@Test
	public void testCreate() throws Exception {
		PersonDTO newPerson = new PersonDTO(1, "PRENOM1", "NOM1", 35);
		when(personService.saveOrUpdate(any(PersonDTO.class))).thenReturn(newPerson);

		mockMvc.perform(post("/api/v1/persons").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newPerson)).accept(MediaType.APPLICATION_JSON).with(csrf()))
				.andDo(print()).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.firstName", is("PRENOM1"))).andExpect(jsonPath("$.lastName", is("NOM1")))
				.andExpect(jsonPath("$.age", is(35)));
	}

	@Test
	public void testEditPerson() throws Exception {
		int id = 1;
		PersonDTO updatedPerson = new PersonDTO(id, "PRENOM1", "NOM1", 35);
		when(personService.findById(id)).thenReturn(Optional.of(updatedPerson));
		when(personService.saveOrUpdate(any(PersonDTO.class))).thenReturn(updatedPerson);

		mockMvc.perform(put("/api/v1/persons/{id}", id).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatedPerson)).with(csrf()))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(id))).andExpect(jsonPath("$.firstName", is("PRENOM1")))
				.andExpect(jsonPath("$.lastName", is("NOM1"))).andExpect(jsonPath("$.age", is(35)));

	}

}
