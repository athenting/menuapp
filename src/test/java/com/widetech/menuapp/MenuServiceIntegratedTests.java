package com.widetech.menuapp;

import com.widetech.menuapp.dao.entity.Menu;
import com.widetech.menuapp.dao.entity.MenuItem;
import com.widetech.menuapp.dao.repository.MenuItemRepository;
import com.widetech.menuapp.dto.requests.MenuItemRegisterDto;
import com.widetech.menuapp.dto.requests.MenuItemUpdateDto;
import com.widetech.menuapp.dto.responses.MenuItemResultDto;
import com.widetech.menuapp.service.MenuService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: athen
 * Date: 2/7/2024
 * Description:
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class MenuServiceIntegratedTests {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuItemRepository itemRepository;

    @Test
    @Rollback(false)//this will actually change the dataset in db
    public void registerMenuTestInsertDbSuccessfully() {
        // arrange
        Menu menu = new Menu();
        menu.setName("testMenu");
        menu.setDescription("test desc");
        // inject test data to Database
        entityManager.persist(menu);
        entityManager.flush();

        // act
        Menu savedMenu = menuService.registerMenu(menu);

        Menu menu1 = menuService.getMenuById(savedMenu.getId());
        System.out.println(menu1);

        // assert
        assertNotNull(savedMenu.getId());
        assertEquals(menu.getName(), savedMenu.getName());
    }

    @Test
    public void registerMenuTestInsertDbFail() {
        Menu menu = new Menu();
        menu.setName(null); // Required field set to invalid value
        menu.setDescription("test desc");

        // act and assert
        assertThrows(ConstraintViolationException.class, () -> {
            // Attempts to persist should fail due to invalid data.
            entityManager.persist(menu);
            entityManager.flush();
            Menu savedMenu = menuService.registerMenu(menu);
        });
    }

    @Test
    @Transactional
    public void updateMenuTestSuccessful() {
        // Initial menu
        Menu menu = new Menu();
        menu.setName("OriginalTestMenu");
        menu.setDescription("Original Test Description");
        entityManager.persist(menu);
        entityManager.flush();

        // Menu to be updated
        Menu updatedMenu = new Menu();
        updatedMenu.setId(menu.getId());
        updatedMenu.setName("UpdatedTestMenu");
        updatedMenu.setDescription("Updated Test Description");

        // act
        menuService.updateMenu(menu.getId(), updatedMenu);

        // Retrieving updated menu from DB
        Menu actualMenu = entityManager.find(Menu.class, menu.getId());

        // assert
        assertNotNull(actualMenu);
        assertEquals(updatedMenu.getName(), actualMenu.getName());
        assertEquals(updatedMenu.getDescription(), actualMenu.getDescription());

        // Cleaning up the data
        entityManager.remove(actualMenu);
        entityManager.flush();
    }

    @Test
    public void deleteMenuTest() {
        // Initial menu
        Menu menu = new Menu();
        menu.setName("TestMenuToDelete");
        menu.setDescription("Test Description");
        entityManager.persist(menu);
        entityManager.flush();

        assertNotNull(menu.getId()); // Make sure that the menu object got an id

        // Delete the menu
        int menuId = menu.getId();
        System.out.println("id to be deleted: " + menuId);
        menuService.deleteMenu(menuId);

        // Flush changes and clear the persistence context.
        entityManager.flush();
        entityManager.clear();

        // Attempt to retrieve the deleted menu
        Menu deletedMenu = entityManager.find(Menu.class, menuId);
        System.out.println("deletedMenu= " + deletedMenu);

        // Assert
        assertNull(deletedMenu);
    }

    // 测试创建MenuItem
    @Test
    @Rollback(value = false)
    public void testCreateMenuItem() {
        MenuItemRegisterDto registerDto = new MenuItemRegisterDto();
        registerDto.setMenuId("52");
        registerDto.setName("TestItem9");
        registerDto.setPrice("100");

        MenuItemResultDto result = menuService.registerNewItem(registerDto);

        MenuItem itemInDb = itemRepository.findByName("TestItem9");

        assertNotNull(itemInDb);
        assertEquals(result.getName(), itemInDb.getName());
        assertEquals(result.getPrice(), itemInDb.getPrice().toPlainString());
    }


    // test update MenuItem
    @Test
    public void testUpdateMenuItem() {
        // 创建一个菜单项用于更新
        MenuItemRegisterDto itemTobeUpdated = new MenuItemRegisterDto();
        itemTobeUpdated.setName("TestItem000");
        itemTobeUpdated.setMenuId("102");
        itemTobeUpdated.setDescription("descccccc");
        itemTobeUpdated.setPrice(String.valueOf(BigDecimal.valueOf(100)));
        MenuItemResultDto registeredNewItem = menuService.registerNewItem(itemTobeUpdated);

        MenuItemUpdateDto updateDto = new MenuItemUpdateDto();
        updateDto.setName("TestItem000Updated");
        updateDto.setPrice("200");
        updateDto.setDescription("updatedDesc");

        MenuItemResultDto result = menuService.updateItem(String.valueOf(registeredNewItem.getItemId()), updateDto);

        MenuItem updatedItemInDb = itemRepository.findById(Integer.valueOf(registeredNewItem.getItemId())).orElse(null);

        assertNotNull(updatedItemInDb);
        assertEquals(result.getName(), updatedItemInDb.getName());
        assertEquals(result.getPrice(), updatedItemInDb.getPrice().toPlainString());
    }

    // 测试删除MenuItem
    @Test
    public void testDeleteMenuItem() {
        // 创建一个菜单项用于删除
        MenuItem itemTobeDeleted = new MenuItem();
        itemTobeDeleted.setName("TestItem");
        itemTobeDeleted.setPrice(new BigDecimal(100));
        itemTobeDeleted = itemRepository.save(itemTobeDeleted);

        menuService.deleteItem(itemTobeDeleted.getId());

        MenuItem itemInDb = itemRepository.findById(itemTobeDeleted.getId()).orElse(null);

        assertNull(itemInDb);
    }

    @Test
    public void testGetItemById() {
        // 创建一个菜单项用于查找
        MenuItem itemTobeFound = new MenuItem();
        itemTobeFound.setName("TestItem");
        itemTobeFound.setPrice(new BigDecimal(100));
        itemTobeFound = itemRepository.save(itemTobeFound);

        MenuItem itemInDb = menuService.getItemById(String.valueOf(itemTobeFound.getId()));

        assertNotNull(itemInDb);
        assertEquals(itemTobeFound.getName(), itemInDb.getName());
    }


}