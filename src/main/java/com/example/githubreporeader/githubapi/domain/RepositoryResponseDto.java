package com.example.githubreporeader.githubapi.domain;

import com.example.githubreporeader.request_response.BranchResponse;
import com.example.githubreporeader.request_response.RepositoryResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryResponseDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("owner")
    private OwnerResponseDto owner;

    public RepositoryResponse repoResponse(List<BranchResponse> branchResponses) {
        return RepositoryResponse.builder()
                .name(name)
                .ownerName(owner.getLogin())
                .branchList(branchResponses)
                .build();
    }

}
