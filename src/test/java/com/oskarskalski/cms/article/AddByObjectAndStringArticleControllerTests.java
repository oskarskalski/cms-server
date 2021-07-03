package com.oskarskalski.cms.article;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oskarskalski.cms.dto.ArticleDto;
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
@DisplayName("addArticle")
public class AddByObjectAndStringArticleControllerTests {
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Add article without authorization token and data")
    public void givenIsTeamWithoutJwtInHeadersAndData__ExceptedHttpStatus__Returned403Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ArticleDto article = new ArticleDto();
        String jsonWithNullValues = objectMapper.writeValueAsString(article);
        MvcResult mvcResult = mvc.perform(post("/api/article/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(403, httpStatus);
    }

    @Test
    @DisplayName("Add article without data")
    public void givenIsTeamWithoutData__ExceptedHttpStatus__Returned400Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ArticleDto article = new ArticleDto();
        String jsonWithNullValues = objectMapper.writeValueAsString(article);
        MvcResult mvcResult = mvc.perform(post("/api/article/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TEST_JWT)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(400, httpStatus);
    }

    @Test
    @DisplayName("Add article without title")
    public void givenIsTeamWithoutTitle__ExceptedHttpStatus__Returned400Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ArticleDto article = new ArticleDto();
        article.setContent(TEST_ARTICLE_CONTENT);
        String jsonWithNullValues = objectMapper.writeValueAsString(article);
        MvcResult mvcResult = mvc.perform(post("/api/article/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TEST_JWT)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(400, httpStatus);
    }

    @Test
    @DisplayName("Add article without content")
    public void givenIsTeamWithoutContent__ExceptedHttpStatus__Returned400Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ArticleDto article = new ArticleDto();
        article.setTitle(TEST_ARTICLE_TITLE);
        String jsonWithNullValues = objectMapper.writeValueAsString(article);
        MvcResult mvcResult = mvc.perform(post("/api/article/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TEST_JWT)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(400, httpStatus);
    }

    @Test
    @DisplayName("Add article with too short title")
    public void givenIsTeamWithTooShortTitle__ExceptedHttpStatus__Returned400Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ArticleDto article = new ArticleDto();
        article.setTitle(TEST_ARTICLE_TITLE.substring(0, 3));
        article.setContent(TEST_ARTICLE_CONTENT);
        String jsonWithNullValues = objectMapper.writeValueAsString(article);
        MvcResult mvcResult = mvc.perform(post("/api/article/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TEST_JWT)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(400, httpStatus);
    }

    @Test
    @DisplayName("Add article with too short content")
    public void givenIsTeamWithTooShortContent__ExceptedHttpStatus__Returned400Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ArticleDto article = new ArticleDto();
        article.setTitle(TEST_ARTICLE_TITLE);
        article.setContent(TEST_ARTICLE_CONTENT.substring(0, 10));
        String jsonWithNullValues = objectMapper.writeValueAsString(article);
        MvcResult mvcResult = mvc.perform(post("/api/article/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TEST_JWT)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(400, httpStatus);
    }

    @Test
    @DisplayName("Add article with too short content and title")
    public void givenIsTeamWithTooShortContentAndTitle__ExceptedHttpStatus__Returned400Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ArticleDto article = new ArticleDto();
        article.setTitle(TEST_ARTICLE_TITLE.substring(0, 3));
        article.setContent(TEST_ARTICLE_CONTENT.substring(0, 10));
        String jsonWithNullValues = objectMapper.writeValueAsString(article);
        MvcResult mvcResult = mvc.perform(post("/api/article/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TEST_JWT)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(400, httpStatus);
    }

    @Test
    @DisplayName("Add article with valid data")
    public void givenIsTeamWithValidData__ExceptedHttpStatus__Returned200Status() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ArticleDto article = new ArticleDto();
        article.setTitle(TEST_ARTICLE_TITLE);
        article.setContent(TEST_ARTICLE_CONTENT);
        String jsonWithNullValues = objectMapper.writeValueAsString(article);
        MvcResult mvcResult = mvc.perform(post("/api/article/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", TEST_JWT)
                .content(jsonWithNullValues))
                .andReturn();

        int httpStatus = mvcResult.getResponse().getStatus();
        assertEquals(200, httpStatus);
    }
}
