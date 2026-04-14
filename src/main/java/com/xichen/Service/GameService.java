package com.xichen.Service;

import com.xichen.Entity.DTO.GameQueryDTO;
import com.xichen.Entity.Request.GameCreateRequest;

import java.util.List;

public interface GameService {
    List<GameQueryDTO> getAllGames();

    GameQueryDTO getGameById(Long id);

    Long createGame(GameCreateRequest request);
}
