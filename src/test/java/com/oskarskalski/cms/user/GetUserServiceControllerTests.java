package com.oskarskalski.cms.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oskarskalski.cms.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("getUser")
public class GetUserServiceControllerTests {

    @Autowired
    private MockMvc mvc;


    @Test
    @Order(2)
    public void givenIsLongValueParam__ExceptedUserDtoObject__ReturnedUserDtoObjectWithNonNullFields() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult mvcResult = mvc.perform(get("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        UserDto userDto = objectMapper.readValue(response, UserDto.class);

        assertAll(
                () -> assertNotNull(userDto.getEmail()),
                () -> assertNotNull(userDto.getFirstName()),
                () -> assertNotNull(userDto.getEmail())
        );
    }

    @Test
    @Order(3)
    public void givenIsLongValueParam__Excepted200HttpStatus__Returned200HttpStatus() throws Exception {
        MvcResult mvcResult = mvc.perform(delete("/api/users/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int responseStatus = mvcResult.getResponse().getStatus();

        assertEquals(200, responseStatus);
    }
}
