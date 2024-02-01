package com.widetech.menuapp.dao.repository;

import com.widetech.menuapp.dao.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    public Admin findByEmail(String email);
}
