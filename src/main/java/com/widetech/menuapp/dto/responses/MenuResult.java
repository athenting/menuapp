package com.widetech.menuapp.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Author: athen
 * Date: 2/6/2024
 * Description:
 */
@AllArgsConstructor
@Getter
@Setter
public class MenuResult {

    private String menuName;
    private String menuDesc;
}
