package com.xichen.Controller;

import com.xichen.Common.CommonResponse;
import com.xichen.Common.ResponseCode;
import com.xichen.Entity.Converter.PunishmentConverter;
import com.xichen.Entity.DTO.PunishmentDTO;
import com.xichen.Entity.Response.PunishmentResponse;
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
    public CommonResponse<PunishmentResponse> getRandomWeightPunishment() {
        PunishmentDTO punishmentDTO = punishmentService.getRandomWeightPunishment();
        // TODO: 后期需要把这个放到业务层面进行处理
        if (punishmentDTO == null) {
            return CommonResponse.fail(ResponseCode.RESOURCE_NOT_FOUND, "没有查找到对应的惩罚信息");
        }
        PunishmentResponse punishmentResponse = PunishmentConverter.convertToVO(punishmentDTO);
        return CommonResponse.success(punishmentResponse);
    }
}
