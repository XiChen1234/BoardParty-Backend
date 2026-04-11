package com.xichen.Service;

import com.xichen.Entity.DTO.GameQueryDTO;
import com.xichen.Entity.Request.GameCreateRequest;

import java.util.List;

public interface GameService {
    List<GameQueryDTO> getAllGames();

    Long createGame(GameCreateRequest request);
}
