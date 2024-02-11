package com.widetech.menuapp;

import com.widetech.menuapp.dao.entity.Menu;
import com.widetech.menuapp.dao.entity.MenuItem;
import com.widetech.menuapp.dao.repository.MenuItemRepository;
import com.widetech.menuapp.dao.repository.MenuRepository;
import com.widetech.menuapp.service.impl.MenuServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Author: athen
 * Date: 2/7/2024
 * Description:
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MenuServiceUnitTests {

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private MenuItemRepository itemRepository;

    @InjectMocks
    private MenuServiceImpl menuService;

    /**
     * unit tests
     */
    @Test
    public void registerMenuTestSuccessful() {
        Menu menu = new Menu();
        when(menuRepository.save(any(Menu.class))).thenReturn(menu);
        assertEquals(menu, menuService.registerMenu(menu));
    }

    @Test
    public void updateMenuTest() {
        Integer id = 1;
        Menu oldMenu = new Menu();
        Menu newMenu = new Menu();
        when(menuRepository.findById(any(Integer.class))).thenReturn(Optional.of(oldMenu));
        when(menuRepository.save(any(Menu.class))).thenReturn(newMenu);
        assertEquals(newMenu, menuService.updateMenu(id, newMenu));
    }

    @Test
    public void deleteMenuTest() {
        Menu menu = new Menu();
        Integer id = 1;
        when(menuRepository.findById(any(Integer.class))).thenReturn(Optional.of(menu));
        assertDoesNotThrow(() -> {
            menuService.deleteMenu(id);
        });
    }

    @Test
    public void deleteItemTest() {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(1);
        Menu menu = new Menu();
        menu.setMenuItems(Arrays.asList(menuItem));
        when(menuRepository.findById(any(Integer.class))).thenReturn(Optional.of(menu));
        assertDoesNotThrow(() -> {
            menuService.deleteItem(menuItem.getId());
        });
    }

    @Test
    public void getAllMenusTest() {
        List<Menu> menus = new ArrayList<Menu>();
        when(menuRepository.findAll()).thenReturn(menus);
        assertEquals(menus, menuService.getAllMenus());
    }

    @Test
    public void getMenuByIdTest() {
        Menu menu = new Menu();
        Integer id = 1;
        when(menuRepository.findById(any(Integer.class))).thenReturn(Optional.of(menu));
        assertEquals(menu, menuService.getMenuById(id));
    }

}