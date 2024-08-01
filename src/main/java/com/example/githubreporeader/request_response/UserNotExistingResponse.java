package com.example.githubreporeader.request_response;

import lombok.Builder;

@Builder
public record UserNotExistingResponse(
        int status,
        String message
) {
}
