package com.xichen.Controller;

import com.xichen.Annotation.IgnoreAuth;
import com.xichen.Common.CommonResponse;
import com.xichen.Entity.Converter.PunishmentConverter;
import com.xichen.Entity.DTO.PunishmentDTO;
import com.xichen.Entity.Response.PunishmentResponse;
import com.xichen.Service.PunishmentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/punishments")
public class PunishmentController {
    @Resource
    private PunishmentService punishmentService;

    /**
     * 获取公共的惩罚
     * @return 公共的惩罚信息
     */
    @IgnoreAuth // 公开惩罚
    @GetMapping("/public/random")
    public CommonResponse<PunishmentResponse> getPunishment() {
        PunishmentDTO punishmentDTO = punishmentService.getPublicPunishment();
        return CommonResponse.success(PunishmentConverter.dtoToVO(punishmentDTO));
    }

    /**
     * 根据小圈id随机抽取惩罚
     * @param id 小圈id
     * @return 惩罚信息
     */
    @GetMapping("/groups/{id}/random")
    public CommonResponse<PunishmentResponse> getPunishmentByGroup(
            @PathVariable("id") Long id) {
        PunishmentDTO punishmentDTO = punishmentService.getPunishmentByGroupId(id);
        return CommonResponse.success(PunishmentConverter.dtoToVO(punishmentDTO));
    }
}
