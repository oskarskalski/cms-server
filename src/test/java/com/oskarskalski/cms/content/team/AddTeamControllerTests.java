package com.oskarskalski.cms.content.team;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oskarskalski.cms.content.team.model.dto.TeamDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static com.oskarskalski.cms.TestData.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("addTeam")
public class AddTeamControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Add team without authorization token and data")
    public void givenIsTeamWithoutJwtInHeadersAndData__ExceptedHttpStatus__Returned403Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TeamDto team = new TeamDto();
        String jsonWithNullValues = objectMapper.writeValueAsString(team);
        MvcResult mvcResult = mvc.perform(post("/api/team/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(403, httpStatus);
    }

    @Test
    @DisplayName("Add team without authorization token but with valid data")
    public void givenIsTeamWithoutJwtInHeaders__ExceptedHttpStatus__Returned403Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TeamDto team = new TeamDto();
        team.setName(TEST_TEAM_NAME);
        team.setDescription(TEST_TEAM_DESCRIPTION);
        String jsonWithNullValues = objectMapper.writeValueAsString(team);
        MvcResult mvcResult = mvc.perform(post("/api/team/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(403, httpStatus);
    }

    @Test
    @DisplayName("Add team with valid data")
    public void givenIsTeamWithValidData__ExceptedHttpStatus__Returned200Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TeamDto team = new TeamDto();
        team.setName(TEST_TEAM_NAME);
        team.setDescription(TEST_TEAM_DESCRIPTION);
        String jsonWithNullValues = objectMapper.writeValueAsString(team);
        MvcResult mvcResult = mvc.perform(post("/api/team/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TEST_JWT)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        //Problem with added user to teammember db in this test. But it works in postman
        assertEquals(200, httpStatus);
    }

    @Test
    @DisplayName("Add team with too short name")
    public void givenIsTeamWithTooShortName__ExceptedHttpStatus__Returned400Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TeamDto team = new TeamDto();
        team.setName(TEST_TEAM_NAME.substring(0, 2));
        team.setDescription(TEST_TEAM_DESCRIPTION);
        String jsonWithNullValues = objectMapper.writeValueAsString(team);
        MvcResult mvcResult = mvc.perform(post("/api/team/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TEST_JWT)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(400, httpStatus);
    }

    @Test
    @DisplayName("Add team with too long name")
    public void givenIsTeamWithTooLongName__ExceptedHttpStatus__Returned400Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TeamDto team = new TeamDto();
        team.setName(TEST_TEAM_NAME + TEST_TEAM_NAME + TEST_TEAM_NAME + TEST_TEAM_NAME + TEST_TEAM_NAME + TEST_TEAM_NAME + TEST_TEAM_NAME);
        team.setDescription(TEST_TEAM_DESCRIPTION);
        String jsonWithNullValues = objectMapper.writeValueAsString(team);
        MvcResult mvcResult = mvc.perform(post("/api/team/add")
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
        team.setName(TEST_TEAM_NAME);
        team.setDescription(TEST_TEAM_DESCRIPTION.substring(0, 2));
        String jsonWithNullValues = objectMapper.writeValueAsString(team);
        MvcResult mvcResult = mvc.perform(post("/api/team/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TEST_JWT)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(400, httpStatus);
    }

    @Test
    @DisplayName("Add team with too description name")
    public void givenIsTeamWithTooLongDescription__ExceptedHttpStatus__Returned400Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TeamDto team = new TeamDto();
        team.setName(TEST_TEAM_NAME);
        team.setDescription(TEST_TEAM_DESCRIPTION + TEST_TEAM_DESCRIPTION);
        String jsonWithNullValues = objectMapper.writeValueAsString(team);
        MvcResult mvcResult = mvc.perform(post("/api/team/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TEST_JWT)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(400, httpStatus);
    }
}
