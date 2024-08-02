package com.example.githubreporeader.githubapi.controller;

import com.example.githubreporeader.exception.GlobalExceptionHandler;
import com.example.githubreporeader.githubapi.service.GitHubService;
import com.example.githubreporeader.request_response.BranchResponse;
import com.example.githubreporeader.request_response.RepositoryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class GitHubControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();
    @Mock
    private GitHubService gitHubService;
    @InjectMocks
    private GitHubController gitHubController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(gitHubController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testGetBranchesForUserRepositories() throws Exception {
        // Given
        String username = "username";
        BranchResponse branchResponse = BranchResponse.builder()
                .name("main")
                .sha("abc123")
                .build();
        RepositoryResponse repoResponse = RepositoryResponse.builder()
                .name("repo1")
                .ownerName("owner1")
                .branchList(List.of(branchResponse))
                .build();

        String expectedResponseJson = mapper.writeValueAsString(List.of(repoResponse));
        when(gitHubService.getRepositories(username)).thenReturn(List.of(repoResponse));

        // When & Then
        mockMvc.perform(get("/github/" + username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson));
        verify(gitHubService, times(1)).getRepositories(username);
    }

    @Test
    void shouldHandleHttpClientErrorException() throws Exception {
        // Given
        String username = "nonexistentuser";
        URI uri = URI.create("https://api.github.com/github/" + username);

        when(gitHubService.getRepositories(username))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        // When & Then
        mockMvc.perform(get("/github/" + username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Provided user does not exist. Please ensure you are using correct username.")));
    }
}