package com.example.githubreporeader.request_response;

import lombok.Builder;

@Builder
public record BranchResponse(
        String name,
        String sha
) {
}
