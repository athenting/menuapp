package com.widetech.menuapp.service;

import com.widetech.menuapp.dao.entity.Menu;
import com.widetech.menuapp.dao.entity.MenuItem;
import com.widetech.menuapp.dto.requests.MenuItemRegisterDto;
import com.widetech.menuapp.dto.requests.MenuItemUpdateDto;
import com.widetech.menuapp.dto.responses.MenuItemResultDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MenuService {

    public MenuItemResultDto registerNewItem(MenuItemRegisterDto menuItem);

    public void deleteItem(Integer itemId);

    public MenuItemResultDto updateItem(String id, MenuItemUpdateDto updatedItem);

    public MenuItem getItemById(String id);

    public List<MenuItemResultDto> getAllMenuItems(Integer limit);

    public Menu registerMenu(Menu newMenu);

    public Menu updateMenu(Integer id, Menu updatedMenu);

    public void deleteMenu(Integer menuId);

    public List<Menu> getAllMenus();

    public Menu getMenuById(Integer menuId);
}
