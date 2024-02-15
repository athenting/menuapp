package com.widetech.menuapp.controller;

import com.widetech.menuapp.constants.ErrorCode;
import com.widetech.menuapp.dao.entity.Menu;
import com.widetech.menuapp.dao.entity.MenuItem;
import com.widetech.menuapp.dto.requests.MenuItemRegisterDto;
import com.widetech.menuapp.dto.requests.MenuItemUpdateDto;
import com.widetech.menuapp.dto.requests.MenuRegisterDto;
import com.widetech.menuapp.dto.responses.MenuItemResultDto;
import com.widetech.menuapp.dto.responses.MenuResult;
import com.widetech.menuapp.dto.responses.RestResponse;
import com.widetech.menuapp.exception.BusinessException;
import com.widetech.menuapp.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "MenuManageController", description = "Menu and menu item management module")
@RestController
@RequestMapping("/api/menu")
public class MenuManageController {

    private final MenuService menuService;

    @Autowired
    public MenuManageController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/item")
    @Operation(summary = "register menu item")
    public RestResponse<MenuItemResultDto> registerMenuItem(@Parameter(ref = "register menu item one by one") @RequestBody MenuItemRegisterDto request) {
        try {
            MenuItemResultDto savedMenuItemResult = menuService.registerNewItem(request);
            return RestResponse.success(savedMenuItemResult);
        } catch (EntityNotFoundException e) {
            return RestResponse.fail(ErrorCode.ENTITY_NOT_FOUND);
        }
    }

    @PutMapping("/item/{id}")
    @Operation(summary = "update menu item info, menu id cannot be changed")
    public RestResponse<MenuItemResultDto> updateItem(@PathVariable String id, @RequestBody MenuItemUpdateDto menuItemUpdateDto) {
        try {
            MenuItemResultDto updatedItemResultDto = menuService.updateItem(id, menuItemUpdateDto);
            return RestResponse.success(updatedItemResultDto);
        } catch (BusinessException e) {
            return RestResponse.fail(ErrorCode.ENTITY_NOT_FOUND);
        }
    }

    @GetMapping("/item/{id}")
    @Operation(summary = "get menu item by name")
    public RestResponse<MenuItemResultDto> getItem(@Parameter(description = "item id") @PathVariable String id) {
        MenuItem menuItem = menuService.getItemById(id);

        return RestResponse.success(MenuItemResultDto.builder().
                itemId(String.valueOf(menuItem.getId())).
                name(menuItem.getName()).
                price(String.valueOf(menuItem.getPrice())).
                menuId(String.valueOf(menuItem.getMenu().getId())).
                description(menuItem.getDescription()).
                build());
    }

    @GetMapping("/items")
    @Operation(summary = "get all of the menu items")
    public List<MenuItemResultDto> getAllMenuItems(@Parameter(description = "limit") @NonNull Integer limit) {
        return menuService.getAllMenuItems(limit);
    }

    @DeleteMapping("/item/{itemId}")
    @Operation(summary = "delete menu item by id")
    public RestResponse<String> deleteItem(@PathVariable("itemId") Integer id) {
        menuService.deleteItem(id);
        return RestResponse.success("Menu item deleted successfully");
    }


    @PostMapping("/register")
    public RestResponse<String> registerMenu(@Parameter(ref = "register menu") @RequestBody MenuRegisterDto request) {
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
    public RestResponse<String> updateMenu(@PathVariable("id") Integer id, @RequestBody MenuRegisterDto request) {
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