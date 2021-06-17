package com.oskarskalski.cms.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oskarskalski.cms.dto.UserDto;
import com.oskarskalski.cms.model.User;
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

import java.util.Date;

import static com.oskarskalski.cms.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@Tag("addUser")
public class AddUserControllerTests {
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Adding user with valid data")
    public void givenAreAllRequiredFieldsAsParams__ExceptedHttpStatus__Returned200HttpStatus()
            throws Exception {
        User user = new User();
        user.setFirstName(testFirstName);
        user.setLastName(testLastName);
        user.setEmail(testEmail);
        user.setPassword(testPassword);

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
        User user = new User();
        user.setFirstName(null);
        user.setLastName(testLastName);
        user.setEmail(testEmail);
        user.setPassword(testPassword);

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
        User user = new User();
        user.setFirstName(testFirstName);
        user.setLastName(null);
        user.setEmail(testEmail);
        user.setPassword(testPassword);

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
        User user = new User();
        user.setFirstName(testFirstName);
        user.setLastName(testLastName);
        user.setEmail(testEmail);
        user.setPassword(null);

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
        User user = new User();
        user.setFirstName(testFirstName.substring(0, 1));
        user.setLastName(testLastName);
        user.setEmail(testEmail);
        user.setPassword(testPassword);

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
        User user = new User();
        user.setFirstName(testFirstName);
        user.setLastName(testLastName.substring(0, 1));
        user.setEmail(testEmail);
        user.setPassword(testPassword);

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
        User user = new User();
        user.setFirstName(testFirstName);
        user.setLastName(testLastName);
        user.setEmail(testEmail);
        user.setPassword(testPassword.substring(0, 4));

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
        User user = new User();
        user.setFirstName(testFirstName + testFirstName + testFirstName + testFirstName);
        user.setLastName(testLastName);
        user.setEmail(testEmail);
        user.setPassword(testPassword);

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
        User user = new User();
        user.setFirstName(testFirstName);
        user.setLastName(testLastName + testLastName + testLastName + testLastName + testLastName);
        user.setEmail(testEmail);
        user.setPassword(testPassword);

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
