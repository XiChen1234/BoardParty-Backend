package com.xichen.Service;

import com.xichen.Entity.DTO.GameQueryDTO;

import java.util.List;

public interface GameService {
    List<GameQueryDTO> getAllGames();
}
