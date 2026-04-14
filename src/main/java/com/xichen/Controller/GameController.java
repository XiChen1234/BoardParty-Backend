package com.xichen.Controller;

import com.xichen.Common.CommonResponse;
import com.xichen.Entity.Converter.GameConverter;
import com.xichen.Entity.DTO.GameQueryDTO;
import com.xichen.Entity.Request.GameCreateRequest;
import com.xichen.Entity.VO.GameDetailVO;
import com.xichen.Entity.VO.GameListItemVO;
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
    public CommonResponse<List<GameListItemVO>> getAllGames() {
        List<GameQueryDTO> gameQueryDTOList = gameService.getAllGames();
        List<GameListItemVO> gameListItemVOList = gameQueryDTOList.stream()
                .map(GameConverter::convertToListItemVO)
                .toList();
        return CommonResponse.success(gameListItemVOList);
    }

    /**
     * 根据ID获取桌游信息
     *
     * @param id 桌游ID
     * @return 桌游信息
     */
    @GetMapping("/{id}")
    public CommonResponse<GameDetailVO> getGameById(@PathVariable Long id) {
        GameQueryDTO gameQueryDTO = gameService.getGameById(id);
        GameDetailVO gameDetailVO = GameConverter.convertToDetailVO(gameQueryDTO);
        return CommonResponse.success(gameDetailVO);
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
