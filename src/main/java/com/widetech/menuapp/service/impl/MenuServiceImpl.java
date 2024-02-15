package com.widetech.menuapp.service.impl;

import com.widetech.menuapp.constants.ErrorCode;
import com.widetech.menuapp.dao.entity.Menu;
import com.widetech.menuapp.dao.entity.MenuItem;
import com.widetech.menuapp.dao.repository.MenuItemRepository;
import com.widetech.menuapp.dao.repository.MenuRepository;
import com.widetech.menuapp.dto.requests.MenuItemRegisterDto;
import com.widetech.menuapp.dto.requests.MenuItemUpdateDto;
import com.widetech.menuapp.dto.responses.MenuItemResultDto;
import com.widetech.menuapp.exception.BusinessException;
import com.widetech.menuapp.service.MenuService;
import com.widetech.menuapp.utils.MenuItemConverter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuItemRepository itemRepository;

    @Autowired
    MenuItemConverter convert;

    @Override
    @Transactional
    public MenuItemResultDto registerNewItem(MenuItemRegisterDto registerDto) {
        MenuItem newItem = convert.prepareNewItemFromDto(registerDto);
        return convert.getResultAfterSavingItem(newItem);
    }


    @Override
    @Transactional
    public void deleteItem(Integer itemId) {
        itemRepository.deleteById(itemId);
    }

    @Override
    @Transactional
    public MenuItemResultDto updateItem(String id, MenuItemUpdateDto updateDto) {
        Optional<MenuItem> optionalItem = itemRepository.findById(Integer.valueOf(id));

        if (optionalItem.isEmpty()) {
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND);
        }

        MenuItem existingItem = optionalItem.get();

        convert.updateItemFromDto(updateDto, existingItem);

        MenuItem updatedItem = itemRepository.save(existingItem);
        return convert.convertEntityToDto(updatedItem);
    }


    public MenuItem getItemById(String id) {
        Optional<MenuItem> itemOptional = itemRepository.findById(Integer.valueOf(id));
        if (itemOptional.isPresent()) {
            return itemOptional.get();
        } else {
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND);
        }
    }

    @Override
    public List<MenuItemResultDto> getAllMenuItems(Integer limit) {
        PageRequest pageRequest = PageRequest.of(0, limit);
        List<MenuItem> menuItems = itemRepository.findAll(pageRequest).getContent();
        List<MenuItemResultDto> resultList = new ArrayList<>();
        for (MenuItem item : menuItems) {
            MenuItemResultDto resultDto = new MenuItemResultDto();
            resultDto.setName(item.getName());
            resultDto.setDescription(item.getDescription());
            resultDto.setPrice(String.valueOf(item.getPrice()));
            resultDto.setItemId(String.valueOf(item.getId()));
            resultDto.setMenuId(String.valueOf(item.getMenu().getId()));
            resultList.add(resultDto);
        }
        return resultList;
    }

    // create new menu
    @Override
    @Transactional
    public Menu registerMenu(Menu newMenu) {
        return menuRepository.save(newMenu);
    }

    // update menu info
    @Override
    @Transactional
    public Menu updateMenu(Integer id, Menu updatedMenu) {
        // checking if the menu exists
        Menu existingMenu = menuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Menu not found"));

        // update menu info
        existingMenu.setName(updatedMenu.getName());
        existingMenu.setDescription(updatedMenu.getDescription());

        // 保存更新后的菜单
        return menuRepository.save(existingMenu);
    }

    // 删除菜单
    @Override
    @Transactional
    public void deleteMenu(Integer menuId) {
        // checking if the menu exists
        Menu existingMenu = menuRepository.findById(menuId)
                .orElseThrow(() -> new EntityNotFoundException("Menu not found"));

        // delete the menu
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
