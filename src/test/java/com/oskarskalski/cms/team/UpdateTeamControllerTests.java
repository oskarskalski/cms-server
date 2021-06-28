package com.oskarskalski.cms.team;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oskarskalski.cms.dto.TeamDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.oskarskalski.cms.TestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("updateTeam")
public class UpdateTeamControllerTests {
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Update team without authorization token and data")
    public void givenIsTeamWithoutJwtInHeadersAndData__ExceptedHttpStatus__Returned403Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TeamDto team = new TeamDto();
        String jsonWithNullValues = objectMapper.writeValueAsString(team);
        MvcResult mvcResult = mvc.perform(put("/api/team/update/" + TEST_TEAM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(403, httpStatus);
    }

    @Test
    @DisplayName("Update team without data")
    public void givenIsTeamWithoutData__ExceptedHttpStatus__Returned400Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TeamDto team = new TeamDto();
        String jsonWithNullValues = objectMapper.writeValueAsString(team);
        MvcResult mvcResult = mvc.perform(put("/api/team/update/" + TEST_TEAM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TEST_JWT)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(400, httpStatus);
    }

    @Test
    @DisplayName("Update team by valid name")
    public void givenIsTeamWitValidName__ExceptedHttpStatus__Returned400Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TeamDto team = new TeamDto();
        team.setName(TEST_TEAM_NAME + Math.floor(Math.random() * 10));
        String jsonWithNullValues = objectMapper.writeValueAsString(team);
        MvcResult mvcResult = mvc.perform(put("/api/team/update/" + TEST_TEAM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TEST_JWT)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(200, httpStatus);
    }
    @Test
    @DisplayName("Update team by valid desciption")
    public void givenIsTeamWitValidDescription__ExceptedHttpStatus__Returned400Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TeamDto team = new TeamDto();
        team.setDescription(TEST_TEAM_DESCRIPTION + Math.floor(Math.random() * 10));
        String jsonWithNullValues = objectMapper.writeValueAsString(team);
        MvcResult mvcResult = mvc.perform(put("/api/team/update/" + TEST_TEAM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TEST_JWT)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(200, httpStatus);
    }


    @Test
    @DisplayName("Update team with too short name")
    public void givenIsTeamWithTooShortName__ExceptedHttpStatus__Returned400Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TeamDto team = new TeamDto();
        team.setName(TEST_TEAM_NAME.substring(0, 2));
        String jsonWithNullValues = objectMapper.writeValueAsString(team);
        MvcResult mvcResult = mvc.perform(put("/api/team/update/" + TEST_TEAM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TEST_JWT)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(400, httpStatus);
    }

    @Test
    @DisplayName("Update team with too long name")
    public void givenIsTeamWithTooLongName__ExceptedHttpStatus__Returned400Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TeamDto team = new TeamDto();
        team.setName(TEST_TEAM_NAME + TEST_TEAM_NAME + TEST_TEAM_NAME + TEST_TEAM_NAME + TEST_TEAM_NAME + TEST_TEAM_NAME + TEST_TEAM_NAME);

        String jsonWithNullValues = objectMapper.writeValueAsString(team);
        MvcResult mvcResult = mvc.perform(put("/api/team/update/" + TEST_TEAM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TEST_JWT)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(400, httpStatus);
    }

    @Test
    @DisplayName("Add team with too short description")
    public void givenIsTeamWithTooShortDescription__ExceptedHttpStatus__Returned400Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TeamDto team = new TeamDto();
        team.setDescription(TEST_TEAM_DESCRIPTION.substring(0, 2));
        String jsonWithNullValues = objectMapper.writeValueAsString(team);
        MvcResult mvcResult = mvc.perform(put("/api/team/update/" + TEST_TEAM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TEST_JWT)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(400, httpStatus);
    }

    @Test
    @DisplayName("Add team with too long description")
    public void givenIsTeamWithTooLongDescription__ExceptedHttpStatus__Returned400Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TeamDto team = new TeamDto();
        team.setDescription(TEST_TEAM_DESCRIPTION + TEST_TEAM_DESCRIPTION);
        String jsonWithNullValues = objectMapper.writeValueAsString(team);
        MvcResult mvcResult = mvc.perform(put("/api/team/update/" + TEST_TEAM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TEST_JWT)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(400, httpStatus);
    }
}
