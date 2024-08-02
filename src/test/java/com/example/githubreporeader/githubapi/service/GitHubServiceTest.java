package com.example.githubreporeader.githubapi.service;

import com.example.githubreporeader.githubapi.client.GitHubClient;
import com.example.githubreporeader.githubapi.domain.BranchResponseDto;
import com.example.githubreporeader.githubapi.domain.CommitResponseDto;
import com.example.githubreporeader.githubapi.domain.OwnerResponseDto;
import com.example.githubreporeader.githubapi.domain.RepositoryResponseDto;
import com.example.githubreporeader.request_response.RepositoryResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(GitHubService.class)
class GitHubServiceTest {

    @Autowired
    private GitHubService gitHubService;
    @MockBean
    private GitHubClient gitHubClient;

    @Test
    public void shouldGetUserRepositories() {
        //Given
        String userName = "username";
        RepositoryResponseDto repoDto = RepositoryResponseDto.builder()
                .name("repo1")
                .owner(OwnerResponseDto.builder().login(userName).build())
                .build();
        BranchResponseDto branchDto = BranchResponseDto.builder()
                .name("branch_name")
                .commit(CommitResponseDto.builder().sha("sha").build())
                .build();
        RepositoryResponse expectedRepoResponse = RepositoryResponse.builder()
                .name("repo1")
                .ownerName(userName)
                .branchList(List.of(branchDto.branchResponse()))
                .build();

        when(gitHubClient.getGitHubRepositories(userName)).thenReturn(List.of(repoDto));
        when(gitHubClient.getRepositoryBranches(userName, "repo1")).thenReturn(List.of(branchDto));

        //When
        List<RepositoryResponse> result = gitHubService.getRepositories(userName);

        //Then
        assertEquals(List.of(expectedRepoResponse), result);
    }
}