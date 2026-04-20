package com.xichen.Entity.Converter;

import com.xichen.Entity.DO.Punishment;
import com.xichen.Entity.DTO.PunishmentDTO;
import com.xichen.Entity.Response.PunishmentResponse;

public class PunishmentConverter {
    /**
     * 惩罚DO转化成DTO
     * @param punishment 惩罚数据对象
     * @return 惩罚数据传输对象
     */
    public static PunishmentDTO doToDTO(Punishment punishment) {
        if (punishment == null) {
            return null;
        }

        PunishmentDTO punishmentDTO = new PunishmentDTO();
        punishmentDTO.setId(punishment.getId());
        punishmentDTO.setName(punishment.getName());
        punishmentDTO.setContent(punishment.getContent());
        punishmentDTO.setIcon(punishment.getIcon());
        return punishmentDTO;
    }

    /**
     * 惩罚DTO转化成VO
     * @param punishmentDTO 惩罚数据传输对象
     * @return 惩罚数据响应对象
     */
    public static PunishmentResponse dtoToVO(PunishmentDTO punishmentDTO) {
        if (punishmentDTO == null) {
            return null;
        }

        PunishmentResponse response = new PunishmentResponse();
        response.setId(punishmentDTO.getId());
        response.setName(punishmentDTO.getName());
        response.setContent(punishmentDTO.getContent());
        response.setIcon(punishmentDTO.getIcon());
        return response;
    }
}
