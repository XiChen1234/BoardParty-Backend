package com.xichen.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xichen.Entity.Converter.PunishmentConverter;
import com.xichen.Entity.DO.Punishment;
import com.xichen.Entity.DTO.PunishmentDTO;
import com.xichen.Mapper.PunishmentMapper;
import com.xichen.Service.PunishmentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class PunishmentServiceImpl implements PunishmentService {
    @Resource
    private PunishmentMapper punishmentMapper;

    @Override
    public PunishmentDTO getPublicPunishment() {
        LambdaQueryWrapper<Punishment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Punishment::getGroupId, 0)
                .eq(Punishment::getEnabled, true)
                .eq(Punishment::getDeleted, false);
        List<Punishment> punishmentList = punishmentMapper.selectList(queryWrapper);

        // 没有匹配数据，则返回null，由前端自行生成默认数据
        if (punishmentList == null || punishmentList.isEmpty()) {
            return null;
        }

        // 从列表中随机获取
        Random random = new Random();
        int index = random.nextInt(punishmentList.size());
        Punishment punishment = punishmentList.get(index);
        return PunishmentConverter.doToDTO(punishment);
    }

    /**
     * 小圈用户随机抽取惩罚
     *
     * @param groupId 小圈id
     * @return 惩罚信息
     */
    @Override
    public PunishmentDTO getPunishmentByGroupId(Long groupId) {
        LambdaQueryWrapper<Punishment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.and(wrapper ->
                        wrapper.eq(Punishment::getGroupId, groupId)
                                .or()
                                .eq(Punishment::getGroupId, 0)
                )
                .eq(Punishment::getEnabled, true)
                .eq(Punishment::getDeleted, false);
        List<Punishment> punishmentList = punishmentMapper.selectList(queryWrapper);

        // 没有匹配数据，则返回null，由前端自行生成默认数据
        if (punishmentList == null || punishmentList.isEmpty()) {
            return null;
        }

        // 从列表中随机获取
        Random random = new Random();
        int index = random.nextInt(punishmentList.size());
        Punishment punishment = punishmentList.get(index);
        return PunishmentConverter.doToDTO(punishment);
    }
}
