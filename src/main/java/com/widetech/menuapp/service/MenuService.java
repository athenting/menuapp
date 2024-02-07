package com.widetech.menuapp.service;

import com.widetech.menuapp.dao.entity.Menu;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MenuService {

    public Menu registerMenu(Menu newMenu);

    public Menu updateMenu(Integer id,Menu updatedMenu);

    public void deleteMenu(Integer menuId);

    public void deleteItem(Integer itemId);

    public List<Menu> getAllMenus();

    public Menu getMenuById(Integer menuId);
}
