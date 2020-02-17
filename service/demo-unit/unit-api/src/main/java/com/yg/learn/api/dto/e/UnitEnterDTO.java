package com.yg.learn.api.dto.e;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitEnterDTO implements Serializable {
    private static final long serialVersionUID = 5517234530139929398L;

    private String unitname;

    private String code;

    private String username;

}
