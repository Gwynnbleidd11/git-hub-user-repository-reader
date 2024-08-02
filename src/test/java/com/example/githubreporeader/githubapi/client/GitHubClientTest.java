package com.example.githubreporeader.githubapi.client;

import com.example.githubreporeader.githubapi.domain.BranchResponseDto;
import com.example.githubreporeader.githubapi.domain.CommitResponseDto;
import com.example.githubreporeader.githubapi.domain.OwnerResponseDto;
import com.example.githubreporeader.githubapi.domain.RepositoryResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class GitHubClientTest {

    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private GitHubClient gitHubClient;
    private final String gitHubApiEndpoint = "https://api.github.com";

    @BeforeEach
    void setUp() {
        gitHubClient = new GitHubClient(restTemplate);
        gitHubClient.setGitHubApiEndpoint(gitHubApiEndpoint);
    }

    @Test
    void shouldGetGitHubRepositories() {
        // Given
        String username = "username";
        RepositoryResponseDto[] responseDtos = {
                RepositoryResponseDto.builder()
                        .name("repo1")
                        .owner(OwnerResponseDto.builder().login("username").build())
                        .build()
        };

        URI uri = UriComponentsBuilder.fromHttpUrl(gitHubApiEndpoint + "/users/{username}/repos")
                .buildAndExpand(username)
                .encode()
                .toUri();

        when(restTemplate.getForObject(uri, RepositoryResponseDto[].class)).thenReturn(responseDtos);

        // When
        List<RepositoryResponseDto> result = gitHubClient.getGitHubRepositories(username);

        // Then
        assertEquals(Arrays.asList(responseDtos), result);
    }

    @Test
    void shouldGetRepositoryBranches() {
        // Given
        String username = "username";
        String repositoryName = "repo1";
        BranchResponseDto[] branchResponseDtos = {
                BranchResponseDto.builder()
                        .name("branch_name")
                        .commit(CommitResponseDto.builder().sha("sha").build())
                        .build()
        };

        URI uri = UriComponentsBuilder.fromHttpUrl(gitHubApiEndpoint + "/repos/{username}/{repositoryName}/branches")
                .buildAndExpand(username, repositoryName)
                .encode()
                .toUri();

        when(restTemplate.getForObject(uri, BranchResponseDto[].class)).thenReturn(branchResponseDtos);

        // When
        List<BranchResponseDto> result = gitHubClient.getRepositoryBranches(username, repositoryName);

        // Then
        assertEquals(Arrays.asList(branchResponseDtos), result);
    }
}