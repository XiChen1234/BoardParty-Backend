package com.xichen.Controller;

import com.xichen.Common.CommonResponse;
import com.xichen.Common.ResponseCode;
import com.xichen.Entity.Converter.PunishmentConverter;
import com.xichen.Entity.DTO.PunishmentDTO;
import com.xichen.Entity.VO.PunishmentVO;
import com.xichen.Service.PunishmentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/punishments")
public class PunishmentController {
    @Resource
    private PunishmentService punishmentService;

    @GetMapping("/random-weight")
    public CommonResponse<PunishmentVO> getRandomWeightPunishment() {
        PunishmentDTO punishmentDTO = punishmentService.getRandomWeightPunishment();
        if (punishmentDTO == null) {
            return CommonResponse.fail(ResponseCode.ERROR,"没有查找到对应的惩罚信息");
        }
        PunishmentVO punishmentVO = PunishmentConverter.convertToVO(punishmentDTO);
        return CommonResponse.success(punishmentVO);
    }
}
