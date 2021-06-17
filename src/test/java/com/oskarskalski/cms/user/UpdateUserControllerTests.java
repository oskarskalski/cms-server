package com.oskarskalski.cms.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oskarskalski.cms.dto.UserDto;
import com.oskarskalski.cms.dto.UserRequest;
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

import static org.junit.jupiter.api.Assertions.*;

import static com.oskarskalski.cms.TestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@Tag("updateUser")
public class UpdateUserControllerTests {
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Try update user without having bearer token")
    public void givenIsUserWithoutJwtInHeaders__ExceptedHttpStatus__Returned403Status() throws Exception {
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
    public void givenIsUserWithNullFields__ExceptedHttpStatus__Returned400Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserRequest user = new UserRequest();
        user.setFirstName("test123");
        String jsonWithNullValues = objectMapper.writeValueAsString(user);
        MvcResult mvcResult = mvc.perform(put("/api/users/update")
                .header("Authorization", "Bearer " + testJWT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(200, httpStatus);
    }
}
