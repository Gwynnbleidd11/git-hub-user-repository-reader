package com.example.githubreporeader.githubapi.service;

import com.example.githubreporeader.githubapi.client.GitHubClient;
import com.example.githubreporeader.githubapi.domain.BranchResponseDto;
import com.example.githubreporeader.request_response.RepositoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GitHubService {

    private final GitHubClient gitHubClient;

    public List<RepositoryResponse> getRepositories(final String username) {
        return gitHubClient.getGitHubRepositories(username).stream()
                .map(r -> r.repoResponse(
                        gitHubClient.getRepositoryBranches(username, r.getName()).stream()
                                .map(BranchResponseDto::branchResponse)
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }
}
