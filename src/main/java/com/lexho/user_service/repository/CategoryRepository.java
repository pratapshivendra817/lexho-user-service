package com.lexho.user_service.repository;

import java.util.Optional;
import com.lexho.user_service.entity.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<ServiceCategory, Long> {
    Optional<ServiceCategory> findByName(String name);
}
