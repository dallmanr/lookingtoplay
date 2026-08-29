package com.dallman.lookingforgame.DTO;

import java.util.List;

public record IgdbResponse(
        String id,
        String name,
        String summary,
        String releaseDate,  // Changed from Long to String
        List<Integer> platforms
) {}

