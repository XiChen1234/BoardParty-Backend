package com.xichen.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xichen.Entity.Converter.PunishmentConverter;
import com.xichen.Entity.DO.Punishment;
import com.xichen.Entity.DTO.PunishmentDTO;
import com.xichen.Mapper.PunishmentMapper;
import com.xichen.Service.PunishmentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PunishmentServiceImpl implements PunishmentService {
    @Resource
    private PunishmentMapper punishmentMapper;

    @Override
    public PunishmentDTO getRandomWeightPunishment() {
        List<Punishment> punishmentList = punishmentMapper.selectList(new QueryWrapper<>());
        if (punishmentList == null || punishmentList.isEmpty()) {
            // 没有惩罚，null是报错
            return null;
        }

        // 根据全部权重进行随机
        int totalWeight = punishmentList.stream()
                .mapToInt(Punishment::getWeight)
                .sum();
        int randomWeight = (int) (Math.random() * totalWeight);

        int currentWeight = 0;

        for (Punishment punishment : punishmentList) {
            currentWeight += punishment.getWeight();
            if (randomWeight < currentWeight) {
                return PunishmentConverter.convertToDTO(punishment);
            }
        }

        return null;
    }
}
