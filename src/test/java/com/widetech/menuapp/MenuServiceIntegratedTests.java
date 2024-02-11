package com.widetech.menuapp;

import com.widetech.menuapp.dao.entity.Menu;
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

    /**
     * Registers a new menu in the database successfully.
     * <p>
     * This method registers a new menu in the database by performing the following steps:
     * 1. Creates a new Menu object with the provided name and description.
     * 2. Persists the menu object in the database.
     * 3. Retrieves the saved menu from the database.
     * 4. Verifies that the saved menu has a non-null id and its name matches the provided name.
     *
     * @throws AssertionError if the saved menu id is null or its name doesn't match the provided name.
     */
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
}