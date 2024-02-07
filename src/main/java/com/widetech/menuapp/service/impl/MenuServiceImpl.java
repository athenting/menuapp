package com.widetech.menuapp.service.impl;

import com.widetech.menuapp.dao.entity.Menu;
import com.widetech.menuapp.dao.entity.MenuItem;
import com.widetech.menuapp.dao.repository.MenuItemRepository;
import com.widetech.menuapp.dao.repository.MenuRepository;
import com.widetech.menuapp.service.MenuService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuItemRepository itemRepository;

    // 创建新菜单
    public Menu registerMenu(Menu newMenu) {
        return menuRepository.save(newMenu);
    }

    // 更新菜单信息
    public Menu updateMenu(Integer id, Menu updatedMenu) {
        // 检查菜单是否存在
        Menu existingMenu = menuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Menu not found"));

        // 更新菜单信息
        existingMenu.setName(updatedMenu.getName());
        existingMenu.setDescription(updatedMenu.getDescription());

        // 保存更新后的菜单
        return menuRepository.save(existingMenu);
    }

    // 删除菜单
    public void deleteMenu(Integer menuId) {
        // 检查菜单是否存在
        Menu existingMenu = menuRepository.findById(menuId)
                .orElseThrow(() -> new EntityNotFoundException("Menu not found"));

        // 删除菜单
        menuRepository.delete(existingMenu);
    }

    public void deleteItem(Integer itemId){
        //find the menu and item
        Menu existingMenu = menuRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Menu not found"));
        List<MenuItem> menuItems = existingMenu.getMenuItems();

        //find the item to be removed
        MenuItem itemToRemove = menuItems.stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Menu item not found"));
        //remove the item and persist in db
        existingMenu.removeMenuItem(itemToRemove);
        menuRepository.save(existingMenu);
    }


    // 查询所有菜单
    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    // 根据ID查询菜单
    public Menu getMenuById(Integer menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new EntityNotFoundException("Menu not found"));
    }

}
