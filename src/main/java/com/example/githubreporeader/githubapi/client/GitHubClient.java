package com.example.githubreporeader.githubapi.client;

import com.example.githubreporeader.githubapi.domain.RepositoryResponseDto;
import com.example.githubreporeader.githubapi.domain.BranchResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Setter
@RequiredArgsConstructor
public class GitHubClient {

    private final RestTemplate restTemplate;

    @Value("${github.api.host}")
    private String gitHubApiEndpoint;

    public List<RepositoryResponseDto> getGitHubRepositories(String username) {
        var url = buildUri("/users/{username}/repos", username);
        var response = restTemplate.getForObject(url, RepositoryResponseDto[].class);
        return Arrays.asList(Objects.requireNonNull(response));
    }

    public List<BranchResponseDto> getRepositoryBranches(String username, String repositoryName) {
        var url = buildUri("/repos/{username}/{repositoryName}/branches", username, repositoryName);
        var branchResponse = restTemplate.getForObject(url, BranchResponseDto[].class);
        return Arrays.asList(Objects.requireNonNull(branchResponse));
    }

    private URI buildUri(String path, Object... uriVariables) {
        return UriComponentsBuilder.fromHttpUrl(gitHubApiEndpoint + path)
                .buildAndExpand(uriVariables)
                .encode()
                .toUri();
    }
}