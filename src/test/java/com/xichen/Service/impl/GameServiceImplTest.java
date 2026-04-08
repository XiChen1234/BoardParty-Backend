package com.xichen.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xichen.Entity.DO.Game;
import com.xichen.Entity.DO.GameTag;
import com.xichen.Entity.DO.Tag;
import com.xichen.Entity.DTO.GameDTO;
import com.xichen.Mapper.GameMapper;
import com.xichen.Mapper.GameTagMapper;
import com.xichen.Mapper.TagMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameServiceImplTest {

    @Mock
    private GameMapper gameMapper;

    @Mock
    private GameTagMapper gameTagMapper;

    @Mock
    private TagMapper tagMapper;

    @InjectMocks
    private GameServiceImpl gameService;

    private Game createGame(Long id, String name, Boolean enabled) {
        Game game = new Game();
        game.setId(id);
        game.setName(name);
        game.setEnabled(enabled);
        game.setGroupId(1L);
        game.setIcon("icon.png");
        game.setDescription("Test Description");    
        game.setMinPlayer(2);
        game.setMaxPlayer(4);
        game.setImages("[\"image1.png\",\"image2.png\"]");
        game.setStar(5);
        return game;
    }

    private Tag createTag(Long id, String name) {
        Tag tag = new Tag();
        tag.setId(id);
        tag.setName(name);
        return tag;
    }

    @Test
    void testGetAllGames_ReturnsEmptyListWhenNoGames() {
        when(gameMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(Collections.emptyList());

        List<GameDTO> result = gameService.getAllGames();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetAllGames_ReturnsEmptyListWhenGamesIsNull() {
        when(gameMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(null);

        List<GameDTO> result = gameService.getAllGames();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetAllGames_GamesWithoutTags() {
        List<Game> games = List.of(createGame(1L, "Game1", true));
        when(gameMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(games);
        when(gameTagMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(Collections.emptyList());

        List<GameDTO> result = gameService.getAllGames();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Game1", result.get(0).getName());
        assertNull(result.get(0).getTags());
    }

    @Test
    void testGetAllGames_GamesWithTags() {
        Game game = createGame(1L, "Game1", true);
        Tag tag1 = createTag(10L, "Tag1");
        Tag tag2 = createTag(20L, "Tag2");
        GameTag gameTag1 = new GameTag();
        gameTag1.setGameId(1L);
        gameTag1.setTagId(10L);
        GameTag gameTag2 = new GameTag();
        gameTag2.setGameId(1L);
        gameTag2.setTagId(20L);

        when(gameMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(List.of(game));
        when(gameTagMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(List.of(gameTag1, gameTag2));
        when(tagMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(List.of(tag1, tag2));

        List<GameDTO> result = gameService.getAllGames();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Game1", result.get(0).getName());
        assertNotNull(result.get(0).getTags());
        assertEquals(2, result.get(0).getTags().size());
    }

    @Test
    void testGetAllGames_MultipleGamesWithMixedTags() {
        Game game1 = createGame(1L, "Game1", true);
        Game game2 = createGame(2L, "Game2", true);
        Tag tag1 = createTag(10L, "Tag1");

        GameTag gameTag1 = new GameTag();
        gameTag1.setGameId(1L);
        gameTag1.setTagId(10L);

        when(gameMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(List.of(game1, game2));
        when(gameTagMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(List.of(gameTag1));
        when(tagMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(List.of(tag1));

        List<GameDTO> result = gameService.getAllGames();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Game1", result.get(0).getName());
        assertEquals("Game2", result.get(1).getName());
        assertEquals(1, result.get(0).getTags().size());
        assertNull(result.get(1).getTags());
    }
}
