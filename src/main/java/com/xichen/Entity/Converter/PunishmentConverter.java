package com.xichen.Entity.Converter;

import com.xichen.Entity.DO.Punishment;
import com.xichen.Entity.DTO.PunishmentDTO;
import com.xichen.Entity.Response.PunishmentResponse;

public class PunishmentConverter {
    /**
     * DO转换为DTO
     *
     * @param punishment 数据对象
     * @return DTO对象
     */
    public static PunishmentDTO convertToDTO(Punishment punishment) {
        PunishmentDTO punishmentDTO = new PunishmentDTO();
        punishmentDTO.setId(punishment.getId());
        punishmentDTO.setName(punishment.getName());
        punishmentDTO.setContent(punishment.getContent());
        punishmentDTO.setImage(punishment.getImage());

        return punishmentDTO;
    }

    /**
     * DTO转换为VO
     *
     * @param punishmentDTO 数据传输对象
     * @return 视图对象
     */
    public static PunishmentResponse convertToVO(PunishmentDTO punishmentDTO) {
        PunishmentResponse punishmentResponse = new PunishmentResponse();
        punishmentResponse.setId(punishmentDTO.getId());
        punishmentResponse.setName(punishmentDTO.getName());
        punishmentResponse.setContent(punishmentDTO.getContent());
        punishmentResponse.setImage(punishmentDTO.getImage());

        return punishmentResponse;
    }
}
