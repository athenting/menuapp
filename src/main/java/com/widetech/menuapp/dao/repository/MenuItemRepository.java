package com.widetech.menuapp.dao.repository;

import com.widetech.menuapp.dao.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
}
