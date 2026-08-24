package com.dallman.lookingforgame.dao;

import com.dallman.lookingforgame.DTO.IgdbResponse;

public interface IgdbGameService {
    IgdbResponse findGameByName(String name);
}
