package com.widetech.menuapp.controller;

import com.widetech.menuapp.dao.entity.Menu;
import com.widetech.menuapp.dto.requests.MenuRegisterRequest;
import com.widetech.menuapp.dto.responses.MenuResult;
import com.widetech.menuapp.dto.responses.RestResponse;
import com.widetech.menuapp.service.MenuService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu")
public class MenuRegisterController {

    private final MenuService menuService;

    @Autowired
    public MenuRegisterController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/register")
    public RestResponse<String> registerMenu(@Parameter(ref = "Menu Details") @RequestBody MenuRegisterRequest request) {
        Menu menu = new Menu();
        menu.setName(request.getName());
        menu.setDescription(request.getDescription());

        menuService.registerMenu(menu);

        return RestResponse.success("Menu registered successfully");
    }

    @GetMapping
    public RestResponse<List<MenuResult>> getMenus() {
        List<MenuResult> menuResponses = menuService.getAllMenus().stream()
                .map(menu -> new MenuResult(menu.getName(), menu.getDescription()))
                .collect(Collectors.toList());

        return RestResponse.success(menuResponses);
    }

    @GetMapping("/{id}")
    public RestResponse<MenuResult> getMenu(@PathVariable("id") Integer id) {
        Menu menu = menuService.getMenuById(id);
        return RestResponse.success(new MenuResult(menu.getName(), menu.getDescription()));
    }

    @PutMapping("/{id}")
    public RestResponse<String> updateMenu(@PathVariable("id") Integer id, @RequestBody MenuRegisterRequest request) {
        Menu menu = new Menu();
        menu.setName(request.getName());
        menu.setDescription(request.getDescription());

        menuService.updateMenu(id, menu);

        return RestResponse.success("Menu updated successfully");
    }

    @DeleteMapping("/{id}")
    public RestResponse<String> deleteMenu(@PathVariable("id") Integer id) {
        menuService.deleteMenu(id);
        return RestResponse.success("Menu deleted successfully");
    }
}