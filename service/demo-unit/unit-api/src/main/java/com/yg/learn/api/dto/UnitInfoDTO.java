package com.yg.learn.api.dto;

import com.yg.learn.api.dto.o.UserOutDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitInfoDTO implements Serializable {
    private static final long serialVersionUID = 5517234530139929398L;

    private UserOutDTO user;

    private String unitname;

    private String code;

}
