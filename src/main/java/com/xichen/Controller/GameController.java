package com.xichen.Controller;

import com.xichen.Common.CommonResponse;
import com.xichen.Entity.Converter.GameConverter;
import com.xichen.Entity.DTO.GameQueryDTO;
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
        List<GameQueryDTO> gameQueryDTOList = gameService.getAllGames();
        List<GameVO> gameVOList = gameQueryDTOList.stream()
                .map(GameConverter::convertToVO)
                .toList();
        return CommonResponse.success(gameVOList);
    }
}
