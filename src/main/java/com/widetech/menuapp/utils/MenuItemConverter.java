package com.widetech.menuapp.utils;

import com.widetech.menuapp.constants.ErrorCode;
import com.widetech.menuapp.dao.entity.Menu;
import com.widetech.menuapp.dao.entity.MenuItem;
import com.widetech.menuapp.dao.repository.MenuItemRepository;
import com.widetech.menuapp.dao.repository.MenuRepository;
import com.widetech.menuapp.dto.requests.MenuItemRegisterDto;
import com.widetech.menuapp.dto.requests.MenuItemUpdateDto;
import com.widetech.menuapp.dto.responses.MenuItemResultDto;
import com.widetech.menuapp.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Author: athen
 * Date: 2/13/2024
 * Description:
 */
@Component
public class MenuItemConverter {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuItemRepository itemRepository;

    public MenuItem prepareNewItemFromDto(MenuItemRegisterDto registerDto) {
        Integer menuId = Integer.parseInt(registerDto.getMenuId());

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

        MenuItem existingItem = itemRepository.findByName(registerDto.getName());
        if (existingItem != null) {
            throw new BusinessException(ErrorCode.ITEM_NAME_ALREADY_REGISTERED);
        }

        MenuItem newItem = new MenuItem();
        newItem.setMenu(menu);
        newItem.setName(registerDto.getName());
        newItem.setPrice(new BigDecimal(registerDto.getPrice()));

        return newItem;
    }

    public MenuItemResultDto getResultAfterSavingItem(MenuItem newItem) {
        MenuItem savedItem = itemRepository.save(newItem);

        return MenuItemResultDto.builder()
                .itemId(String.valueOf(savedItem.getId()))
                .price(savedItem.getPrice().toString())
                .name(savedItem.getName())
                .description(savedItem.getDescription())
                .menuId(String.valueOf(savedItem.getMenu().getId()))
                .build();
    }

    public void updateItemFromDto(MenuItemUpdateDto updateDto, MenuItem menuItem) {
        menuItem.setName(updateDto.getName());
        menuItem.setPrice(new BigDecimal(updateDto.getPrice()));
        menuItem.setDescription(updateDto.getDescription());
    }

    public MenuItemResultDto convertEntityToDto(MenuItem menuItem) {
        return MenuItemResultDto.builder()
                .itemId(String.valueOf(menuItem.getId()))
                .name(menuItem.getName())
                .description(menuItem.getDescription())
                .price(menuItem.getPrice().toString())
                .menuId(String.valueOf(menuItem.getMenu().getId()))
                .build();
    }
}
