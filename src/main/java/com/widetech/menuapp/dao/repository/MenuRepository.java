package com.widetech.menuapp.dao.repository;

import com.widetech.menuapp.dao.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Integer> {
}
