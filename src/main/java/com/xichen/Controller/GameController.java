package com.xichen.Controller;

import com.xichen.Common.CommonResponse;
import com.xichen.Entity.Converter.GameConverter;
import com.xichen.Entity.DTO.GameQueryDTO;
import com.xichen.Entity.Request.GameCreateRequest;
import com.xichen.Entity.Response.GameDetailResponse;
import com.xichen.Entity.Response.GameListItemResponse;
import com.xichen.Service.GameService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
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
    public CommonResponse<List<GameListItemResponse>> getAllGames() {
        List<GameQueryDTO> gameQueryDTOList = gameService.getAllGames();
        List<GameListItemResponse> gameListItemVOList = gameQueryDTOList.stream()
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
    public CommonResponse<GameDetailResponse> getGameById(@PathVariable Long id) {
        GameQueryDTO gameQueryDTO = gameService.getGameById(id);
        GameDetailResponse response = GameConverter.convertToDetailVO(gameQueryDTO);
        return CommonResponse.success(response);
    }

    /**
     * 创建一个新桌游
     *
     * @param request 创建桌游请求
     * @return 创建的新桌游ID
     */
    @PostMapping("")
    public CommonResponse<Long> createGame(@RequestBody @Validated GameCreateRequest request) {
        Long gameId = gameService.createGame(request);
        return CommonResponse.success(gameId);
    }
}
