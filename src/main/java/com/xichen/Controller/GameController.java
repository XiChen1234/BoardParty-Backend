package com.xichen.Controller;

import com.xichen.Common.CommonResponse;
import com.xichen.Entity.Converter.GameConverter;
import com.xichen.Entity.DTO.GameDTO;
import com.xichen.Entity.VO.GameVO;
import com.xichen.Service.GameService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {
    @Resource
    private GameService gameService;


    @GetMapping("")
    public CommonResponse<List<GameVO>> getAllGames() {
        List<GameDTO> gameDTOList = gameService.getAllGames();
        List<GameVO> gameVOList = gameDTOList.stream()
                .map(GameConverter::convertToVO)
                .toList();
        return CommonResponse.success(gameVOList);
    }
}
