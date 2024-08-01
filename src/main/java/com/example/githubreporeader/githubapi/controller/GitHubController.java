package com.example.githubreporeader.githubapi.controller;

import com.example.githubreporeader.githubapi.service.GitHubService;
import com.example.githubreporeader.request_response.RepositoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "github")
public class GitHubController {

    private final GitHubService gitHubService;

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<RepositoryResponse> getBranchesForUserRepositories(@PathVariable final String username) {
        return gitHubService.getRepositories(username);
    }
}
