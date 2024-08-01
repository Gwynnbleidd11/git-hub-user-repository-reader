package com.example.githubreporeader.request_response;

import lombok.Builder;

import java.util.List;

@Builder
public record RepositoryResponse(
        String name,
        String ownerName,
        List<BranchResponse> branchList
) {
}
