package com.oskarskalski.cms.content.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oskarskalski.cms.global.model.dto.UserRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.oskarskalski.cms.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("addUser")
public class AddUserControllerTests {
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Adding user with valid data")
    public void givenAreAllRequiredFieldsAsParams__ExceptedHttpStatus__Returned200HttpStatus()
            throws Exception {
        UserRequest user = new UserRequest();
        user.setFirstName(TEST_FIRST_NAME);
        user.setLastName(TEST_LAST_NAME);
        user.setEmail(TEST_EMAIL);
        user.setNewPassword(TEST_PASSWORD);
        user.setRepeatNewPassword(TEST_PASSWORD);

        ObjectMapper objectMapper = new ObjectMapper();
        String createJsonWithData = objectMapper.writeValueAsString(user);

        MvcResult mvcResult = mvc.perform(post("/api/users/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJsonWithData))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();

        assertEquals(200, httpStatus);
    }

    @Test
    @DisplayName("Adding user without first name")
    public void givenAllRequiredFieldsWithoutFirstNameAsParams__ExceptedHttpStatus__Returned400HttpStatus()
            throws Exception {
        UserRequest user = new UserRequest();
        user.setFirstName(null);
        user.setLastName(TEST_LAST_NAME);
        user.setEmail(TEST_EMAIL);
        user.setNewPassword(TEST_PASSWORD);
        user.setRepeatNewPassword(TEST_PASSWORD);

        ObjectMapper objectMapper = new ObjectMapper();
        String createJsonWithData = objectMapper.writeValueAsString(user);

        MvcResult mvcResult = mvc.perform(post("/api/users/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJsonWithData))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();

        assertEquals(400, httpStatus);
    }

    @Test
    @DisplayName("Adding user without last name")
    public void givenAllRequiredFieldsWithoutLastNameAsParams__ExceptedHttpStatus__Returned400HttpStatus()
            throws Exception {
        UserRequest user = new UserRequest();
        user.setFirstName(TEST_FIRST_NAME);
        user.setLastName(null);
        user.setEmail(TEST_EMAIL);
        user.setNewPassword(TEST_PASSWORD);
        user.setRepeatNewPassword(TEST_PASSWORD);

        ObjectMapper objectMapper = new ObjectMapper();
        String createJsonWithData = objectMapper.writeValueAsString(user);

        MvcResult mvcResult = mvc.perform(post("/api/users/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJsonWithData))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();

        assertEquals(400, httpStatus);
    }

    @Test
    @DisplayName("Adding user without password")
    public void givenAllRequiredFieldsWithoutPasswordsAsParams__ExceptedHttpStatus__Returned400HttpStatus()
            throws Exception {
        UserRequest user = new UserRequest();
        user.setFirstName(TEST_FIRST_NAME);
        user.setLastName(TEST_LAST_NAME);
        user.setEmail(TEST_EMAIL);
        user.setNewPassword(null);
        user.setRepeatNewPassword(null);

        ObjectMapper objectMapper = new ObjectMapper();
        String createJsonWithData = objectMapper.writeValueAsString(user);

        MvcResult mvcResult = mvc.perform(post("/api/users/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJsonWithData))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();

        assertEquals(400, httpStatus);
    }

    @Test
    @DisplayName("Adding user without password")
    public void givenAllRequiredFieldsWithoutPasswordAsParams__ExceptedHttpStatus__Returned400HttpStatus()
            throws Exception {
        UserRequest user = new UserRequest();
        user.setFirstName(TEST_FIRST_NAME);
        user.setLastName(TEST_LAST_NAME);
        user.setEmail(TEST_EMAIL);
        user.setNewPassword(null);
        user.setRepeatNewPassword(TEST_PASSWORD);

        ObjectMapper objectMapper = new ObjectMapper();
        String createJsonWithData = objectMapper.writeValueAsString(user);

        MvcResult mvcResult = mvc.perform(post("/api/users/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJsonWithData))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();

        assertEquals(400, httpStatus);
    }

    @Test
    @DisplayName("Adding user without password")
    public void givenAllRequiredFieldsWithoutRepeatedPasswordAsParams__ExceptedHttpStatus__Returned400HttpStatus()
            throws Exception {
        UserRequest user = new UserRequest();
        user.setFirstName(TEST_FIRST_NAME);
        user.setLastName(TEST_LAST_NAME);
        user.setEmail(TEST_EMAIL);
        user.setNewPassword(TEST_PASSWORD);
        user.setRepeatNewPassword(null);

        ObjectMapper objectMapper = new ObjectMapper();
        String createJsonWithData = objectMapper.writeValueAsString(user);

        MvcResult mvcResult = mvc.perform(post("/api/users/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJsonWithData))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();

        assertEquals(400, httpStatus);
    }


    @Test
    @DisplayName("Adding user with too short first name")
    public void givenAllRequiredFieldsWithTooShortFirstNameAsParams__ExceptedHttpStatus__Returned400HttpStatus()
            throws Exception {
        UserRequest user = new UserRequest();
        user.setFirstName(TEST_FIRST_NAME.substring(0, 1));
        user.setLastName(TEST_LAST_NAME);
        user.setEmail(TEST_EMAIL);
        user.setNewPassword(TEST_PASSWORD);
        user.setRepeatNewPassword(TEST_PASSWORD);

        ObjectMapper objectMapper = new ObjectMapper();
        String createJsonWithData = objectMapper.writeValueAsString(user);

        MvcResult mvcResult = mvc.perform(post("/api/users/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJsonWithData))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();

        assertEquals(400, httpStatus);
    }

    @Test
    @DisplayName("Adding user with too short last name")
    public void givenAllRequiredFieldsWithTooShortLastNameAsParams__ExceptedHttpStatus__Returned400HttpStatus()
            throws Exception {
        UserRequest user = new UserRequest();
        user.setFirstName(TEST_FIRST_NAME);
        user.setLastName(TEST_LAST_NAME.substring(0, 1));
        user.setEmail(TEST_EMAIL);
        user.setNewPassword(TEST_PASSWORD);
        user.setRepeatNewPassword(TEST_PASSWORD);

        ObjectMapper objectMapper = new ObjectMapper();
        String createJsonWithData = objectMapper.writeValueAsString(user);

        MvcResult mvcResult = mvc.perform(post("/api/users/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJsonWithData))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();

        assertEquals(400, httpStatus);
    }

    @Test
    @DisplayName("Adding user with too short password")
    public void givenAllRequiredFieldsWithTooShortPasswordAsParams__ExceptedHttpStatus__Returned400HttpStatus()
            throws Exception {
        UserRequest user = new UserRequest();
        user.setFirstName(TEST_FIRST_NAME);
        user.setLastName(TEST_LAST_NAME);
        user.setEmail(TEST_EMAIL);
        user.setNewPassword(TEST_PASSWORD.substring(0, 4));
        user.setRepeatNewPassword(TEST_PASSWORD.substring(0, 4));

        ObjectMapper objectMapper = new ObjectMapper();
        String createJsonWithData = objectMapper.writeValueAsString(user);

        MvcResult mvcResult = mvc.perform(post("/api/users/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJsonWithData))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();

        assertEquals(400, httpStatus);
    }

    @Test
    @DisplayName("Adding user with too long first name")
    public void givenAllRequiredFieldsWithTooLongFirstNameAsParams__ExceptedHttpStatus__Returned400HttpStatus()
            throws Exception {
        UserRequest user = new UserRequest();
        user.setFirstName(TEST_FIRST_NAME + TEST_FIRST_NAME + TEST_FIRST_NAME + TEST_FIRST_NAME);
        user.setLastName(TEST_LAST_NAME);
        user.setEmail(TEST_EMAIL);
        user.setNewPassword(TEST_PASSWORD);
        user.setRepeatNewPassword(TEST_PASSWORD);

        ObjectMapper objectMapper = new ObjectMapper();
        String createJsonWithData = objectMapper.writeValueAsString(user);

        MvcResult mvcResult = mvc.perform(post("/api/users/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJsonWithData))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();

        assertEquals(400, httpStatus);
    }

    @Test
    @DisplayName("Adding user with too long last name")
    public void givenAllRequiredFieldsWithTooLongLastNameAsParams__ExceptedHttpStatus__Returned400HttpStatus()
            throws Exception {
        UserRequest user = new UserRequest();
        user.setFirstName(TEST_FIRST_NAME);
        user.setLastName(TEST_LAST_NAME + TEST_LAST_NAME + TEST_LAST_NAME + TEST_LAST_NAME + TEST_LAST_NAME);
        user.setEmail(TEST_EMAIL);
        user.setNewPassword(TEST_PASSWORD);
        user.setRepeatNewPassword(TEST_PASSWORD);

        ObjectMapper objectMapper = new ObjectMapper();
        String createJsonWithData = objectMapper.writeValueAsString(user);

        MvcResult mvcResult = mvc.perform(post("/api/users/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJsonWithData))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();

        assertEquals(400, httpStatus);
    }
}
