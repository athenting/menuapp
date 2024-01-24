package com.widetech.menuapp.controller;

import com.widetech.menuapp.dao.entity.Menu;
import com.widetech.menuapp.dto.responses.RestResponse;
import com.widetech.menuapp.service.MenuService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
public class MenuRegisterController {

    private final MenuService menuService;

    @Autowired
    public MenuRegisterController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/register")
    public RestResponse<String> registerMenu(@ApiParam("Menu Details") @RequestBody Menu menu) {
        // 在这里执行菜单注册逻辑
        // 可以调用 menuService 进行处理
        // 例如：menuService.registerMenu(menu);
        return RestResponse.success("Menu registered successfully");
    }
}
