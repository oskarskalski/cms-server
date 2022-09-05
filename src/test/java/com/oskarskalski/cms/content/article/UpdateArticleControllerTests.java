package com.oskarskalski.cms.content.article;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oskarskalski.cms.content.article.model.dto.ArticleDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.oskarskalski.cms.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("updateArticle")
public class UpdateArticleControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Update article without authorization token and data")
    public void givenIsTeamWithoutJwtInHeadersAndData__ExceptedHttpStatus__Returned403Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ArticleDto article = new ArticleDto();
        String jsonWithNullValues = objectMapper.writeValueAsString(article);
        MvcResult mvcResult = mvc.perform(put("/api/article/update/" + TEST_ARTICLE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(403, httpStatus);
    }

    @Test
    @DisplayName("Update article without data")
    public void givenIsTeamWithoutData__ExceptedHttpStatus__Returned400Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ArticleDto article = new ArticleDto();
        article.setId(TEST_ARTICLE_ID);
        String jsonWithNullValues = objectMapper.writeValueAsString(article);
        MvcResult mvcResult = mvc.perform(put("/api/article/update")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TEST_JWT)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(400, httpStatus);
    }

    @Test
    @DisplayName("Update article with too short title")
    public void givenIsTeamWithTooShortTitle__ExceptedHttpStatus__Returned400Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ArticleDto article = new ArticleDto();
        article.setId(TEST_ARTICLE_ID);
        article.setTitle(TEST_ARTICLE_TITLE.substring(0, 3));
        String jsonWithNullValues = objectMapper.writeValueAsString(article);
        MvcResult mvcResult = mvc.perform(put("/api/article/update")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TEST_JWT)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(400, httpStatus);
    }

    @Test
    @DisplayName("Update article with too long title")
    public void givenIsTeamWithTooLongTitle__ExceptedHttpStatus__Returned400Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ArticleDto article = new ArticleDto();
        article.setTitle(TEST_ARTICLE_TITLE);
        article.setId(TEST_ARTICLE_ID);
        String jsonWithNullValues = objectMapper.writeValueAsString(article);
        MvcResult mvcResult = mvc.perform(put("/api/article/update")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TEST_JWT)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(400, httpStatus);
    }

    @Test
    @DisplayName("Update article with valid title")
    public void givenIsTeamWithValidTitle__ExceptedHttpStatus__Returned200Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ArticleDto article = new ArticleDto();
        article.setTitle(TEST_ARTICLE_TITLE.substring(0, 20));
        article.setId(TEST_ARTICLE_ID);
        String jsonWithNullValues = objectMapper.writeValueAsString(article);
        MvcResult mvcResult = mvc.perform(put("/api/article/update")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TEST_JWT)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(200, httpStatus);
    }

    @Test
    @DisplayName("Update article with too short content")
    public void givenIsTeamWithTooShortContent__ExceptedHttpStatus__Returned400Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ArticleDto article = new ArticleDto();
        article.setContent(TEST_ARTICLE_CONTENT.substring(0, 40));
        article.setId(TEST_ARTICLE_ID);
        String jsonWithNullValues = objectMapper.writeValueAsString(article);
        MvcResult mvcResult = mvc.perform(put("/api/article/update")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TEST_JWT)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(400, httpStatus);
    }


    @Test
    @DisplayName("Update article with too long content")
    public void givenIsTeamWithTooLongContent__ExceptedHttpStatus__Returned400Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ArticleDto article = new ArticleDto();
        article.setId(TEST_ARTICLE_ID);
        article.setContent(TEST_ARTICLE_CONTENT + TEST_ARTICLE_CONTENT + TEST_ARTICLE_CONTENT + TEST_ARTICLE_CONTENT);
        String jsonWithNullValues = objectMapper.writeValueAsString(article);
        MvcResult mvcResult = mvc.perform(put("/api/article/update")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TEST_JWT)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(400, httpStatus);
    }

}
