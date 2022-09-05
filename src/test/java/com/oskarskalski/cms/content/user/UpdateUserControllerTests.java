package com.oskarskalski.cms.content.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oskarskalski.cms.content.user.model.dto.UserDto;
import com.oskarskalski.cms.global.model.dto.UserRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;

import static com.oskarskalski.cms.TestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("updateUser")
class UpdateUserControllerTests {
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Try update user without having bearer token")
    void givenIsUserWithoutJwtInHeaders__ExceptedHttpStatus__Returned403Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserRequest user = new UserRequest();
        String jsonWithNullValues = objectMapper.writeValueAsString(user);
        MvcResult mvcResult = mvc.perform(put("/api/users/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(403, httpStatus);
    }

    @Test
    @DisplayName("Update first name")
    void givenIsUserWithFirstName__ExceptedUpdatedFirstName__ReturnedUpdatedFirstName() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserRequest user = new UserRequest();
        String firstName = TEST_FIRST_NAME;
        user.setFirstName(firstName);
        String jsonWithNullValues = objectMapper.writeValueAsString(user);
        MvcResult updateUser = mvc.perform(put("/api/users/update")
                        .header("Authorization", "Bearer " + TEST_JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWithNullValues))
                .andReturn();

        MvcResult getUser = mvc.perform(get("/api/users/" + TEST_ID)
                        .header("Authorization", "Bearer " + TEST_JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWithNullValues))
                .andReturn();

        UserDto userDto = objectMapper.readValue(getUser.getResponse().getContentAsString(), UserDto.class);


        assertEquals(firstName, userDto.getFirstName());
    }

    @Test
    @DisplayName("Update last name")
    void givenIsUserWithLastName__ExceptedUpdatedLastName__ReturnedUpdatedLastName() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserRequest user = new UserRequest();
        String lastName = TEST_LAST_NAME;
        user.setLastName(lastName);
        String jsonWithNullValues = objectMapper.writeValueAsString(user);
        MvcResult updateUser = mvc.perform(put("/api/users/update")
                        .header("Authorization", "Bearer " + TEST_JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWithNullValues))
                .andReturn();

        MvcResult getUser = mvc.perform(get("/api/users/" + TEST_ID)
                        .header("Authorization", "Bearer " + TEST_JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWithNullValues))
                .andReturn();

        UserDto userDto = objectMapper.readValue(getUser.getResponse().getContentAsString(), UserDto.class);


        assertEquals(lastName, userDto.getLastName());
    }

    @Test
    @DisplayName("Update email")
    void givenIsUserWithEmail__ExceptedUpdatedEmail__ReturnedUpdatedEmail() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserRequest user = new UserRequest();
        String email = TEST_EMAIL;
        user.setEmail(email);
        String jsonWithNullValues = objectMapper.writeValueAsString(user);
        MvcResult updateUser = mvc.perform(put("/api/users/update")
                        .header("Authorization", TEST_JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWithNullValues))
                .andReturn();

        MvcResult getUser = mvc.perform(get("/api/users/" + TEST_ID)
                        .header("Authorization", TEST_JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWithNullValues))
                .andReturn();

        UserDto userDto = objectMapper.readValue(getUser.getResponse().getContentAsString(), UserDto.class);


        assertEquals(email, userDto.getEmail());
    }
}
