package com.example.githubreporeader.githubapi.domain;

import com.example.githubreporeader.request_response.BranchResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BranchResponseDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("commit")
    private CommitResponseDto commit;

    public BranchResponse branchResponse() {
        return BranchResponse.builder()
                .name(name)
                .sha(commit.getSha())
                .build();
    }
}
