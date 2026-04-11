package com.xichen.Controller;

import com.xichen.Common.CommonResponse;
import com.xichen.Entity.Converter.GameConverter;
import com.xichen.Entity.DTO.GameQueryDTO;
import com.xichen.Entity.Request.GameCreateRequest;
import com.xichen.Entity.VO.GameVO;
import com.xichen.Service.GameService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {
    @Resource
    private GameService gameService;

    /**
     * 获取所有桌游信息
     *
     * @return 桌游列表
     */
    @GetMapping("")
    public CommonResponse<List<GameVO>> getAllGames() {
        List<GameQueryDTO> gameQueryDTOList = gameService.getAllGames();
        List<GameVO> gameVOList = gameQueryDTOList.stream()
                .map(GameConverter::convertToVO)
                .toList();
        return CommonResponse.success(gameVOList);
    }

    /**
     * 创建一个新桌游
     *
     * @param request 创建桌游请求
     * @return 创建的新桌游ID
     */
    @PostMapping("")
    public CommonResponse<Long> createGame(@RequestBody GameCreateRequest request) {
        Long gameId = gameService.createGame(request);
        return CommonResponse.success(gameId);
    }
}
