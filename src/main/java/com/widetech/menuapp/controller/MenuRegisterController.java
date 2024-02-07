package com.widetech.menuapp.controller;

import com.widetech.menuapp.constants.ErrorCode;
import com.widetech.menuapp.dao.entity.Menu;
import com.widetech.menuapp.dao.entity.MenuItem;
import com.widetech.menuapp.dto.requests.MenuRegisterDto;
import com.widetech.menuapp.dto.requests.MenuRegisterItemDto;
import com.widetech.menuapp.dto.responses.MenuResult;
import com.widetech.menuapp.dto.responses.RestResponse;
import com.widetech.menuapp.service.MenuService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    public RestResponse<String> registerMenu(@Parameter(ref = "register menu") @RequestBody MenuRegisterDto request) {
        Menu menu = new Menu();
        menu.setName(request.getName());
        menu.setDescription(request.getDescription());

        menuService.registerMenu(menu);

        return RestResponse.success("Menu registered successfully");
    }
    @PostMapping("/registerItem")
    public RestResponse<String> registerMenuItem(@Parameter(ref = "register menu item one by one") @RequestBody MenuRegisterItemDto request) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(request.getName());
        menuItem.setPrice(new BigDecimal(request.getPrice()));

        Menu menu = menuService.getMenuById(Integer.parseInt(request.getMenuId()));
        if (menu != null) {
            menu.addMenuItem(menuItem);
            menuService.updateMenu(menu.getId(), menu);
        } else {
            // Handle case where no menu with the provided id exists.
            return RestResponse.fail(ErrorCode.MENU_NOT_FOUND);
        }

        return RestResponse.success("Menu item registered successfully");
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
    public RestResponse<String> updateMenu(@PathVariable("id") Integer id, @RequestBody MenuRegisterDto request) {
        Menu menu = new Menu();
        menu.setName(request.getName());
        menu.setDescription(request.getDescription());

        menuService.updateMenu(id, menu);

        return RestResponse.success("Menu updated successfully");
    }

    @DeleteMapping("/{id}")
    public RestResponse<String> deleteMenu(@PathVariable("id") Integer id) {
        menuService.deleteItem(id);
        return RestResponse.success("Menu deleted successfully");
    }

    @DeleteMapping("/{itemId}")
    public RestResponse<String> deleteItem(@PathVariable("itemId") Integer id) {

        return RestResponse.success("Menu deleted successfully");
    }
}