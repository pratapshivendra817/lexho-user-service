package com.lexho.user_service.repository;

import com.lexho.user_service.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartnerRepository extends JpaRepository<Partner, Long> {

    List<Partner> findBySubCategoryIdAndAvailableTrue(Long subCategoryId);

    List<Partner> findBySubCategoryIdAndAvailableTrueOrderByRatingDesc(Long subCategoryId);

    List<Partner> findBySubCategoryIdAndAvailableTrueOrderByPriceAsc(Long subCategoryId);

    List<Partner> findByNameAndSubCategoryId(String name, Long subCategoryId);
}