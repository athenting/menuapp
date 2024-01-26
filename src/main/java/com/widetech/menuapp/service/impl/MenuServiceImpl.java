package com.widetech.menuapp.service.impl;

import com.widetech.menuapp.dao.entity.Menu;
import com.widetech.menuapp.dao.repository.MenuRepository;
import com.widetech.menuapp.dto.requests.MenuRegisterRequest;
import com.widetech.menuapp.service.MenuService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    // 创建新菜单
    public Menu registerMenu(MenuRegisterRequest request) {

        Menu menu = new Menu();

        menu.setName(request.getName());
        menu.setDescription(request.getDescription());

        return menuRepository.save(menu);
    }

    // 更新菜单信息
    public Menu updateMenu(Menu updatedMenu) {
        // 检查菜单是否存在
        Menu existingMenu = menuRepository.findById(updatedMenu.getId())
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
