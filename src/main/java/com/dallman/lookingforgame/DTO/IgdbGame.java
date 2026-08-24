package com.dallman.lookingforgame.DTO;

import java.util.List;

public record IgdbGame(
        long id,
        String name,
        String summary,
        Long released,  // Unix timestamp or date string depending on API
        List<IgdbPlatform> platforms
) {}
