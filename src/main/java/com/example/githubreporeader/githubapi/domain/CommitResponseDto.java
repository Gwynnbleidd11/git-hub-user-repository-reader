package com.example.githubreporeader.githubapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitResponseDto {

    @JsonProperty("sha")
    private String sha;
}
