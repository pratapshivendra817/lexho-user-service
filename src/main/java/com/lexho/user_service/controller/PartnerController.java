package com.lexho.user_service.controller;

import com.lexho.user_service.dto.ApiResponse;
import com.lexho.user_service.dto.PartnerResponseDTO;
import com.lexho.user_service.entity.Partner;
import com.lexho.user_service.repository.PartnerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partners")
public class PartnerController {

    private final PartnerRepository repo;

    public PartnerController(PartnerRepository repo) {
        this.repo = repo;
    }

    // ✅ CREATE (with duplicate check 🔥)
    @PostMapping
    public ApiResponse<Partner> create(@RequestBody Partner partner) {

        // 🔥 DUPLICATE CHECK
        List<Partner> existing = repo.findByNameAndSubCategoryId(
                partner.getName(),
                partner.getSubCategoryId()
        );

        if (!existing.isEmpty()) {
            return ApiResponse.failure("Partner already exists");
        }

        return ApiResponse.success("Partner created", repo.save(partner));
    }

    // ✅ GET LIST (FILTER + SORT + DTO)
    @GetMapping
    public ApiResponse<List<PartnerResponseDTO>> getPartners(
            @RequestParam Long subCategoryId,
            @RequestParam(required = false) String sort
    ) {

        List<Partner> partners;

        if ("rating".equalsIgnoreCase(sort)) {
            partners = repo.findBySubCategoryIdAndAvailableTrueOrderByRatingDesc(subCategoryId);
        } else if ("price".equalsIgnoreCase(sort)) {
            partners = repo.findBySubCategoryIdAndAvailableTrueOrderByPriceAsc(subCategoryId);
        } else {
            partners = repo.findBySubCategoryIdAndAvailableTrue(subCategoryId);
        }

        List<PartnerResponseDTO> response = partners.stream()
                .map(p -> new PartnerResponseDTO(
                        p.getId(),
                        p.getName(),
                        p.getRating(),
                        p.getPrice(),
                        p.getExperience(),
                        p.getImage(),
                        p.getCategoryId(),
                        p.getSubCategoryId()
                ))
                .toList();

        return ApiResponse.success("Partners fetched successfully", response);
    }

    // ✅ GET SINGLE
    @GetMapping("/{id}")
    public ApiResponse<PartnerResponseDTO> getById(@PathVariable Long id) {

        Partner p = repo.findById(id).orElse(null);

        if (p == null) {
            return ApiResponse.failure("Partner not found");
        }

        PartnerResponseDTO dto = new PartnerResponseDTO(
                p.getId(),
                p.getName(),
                p.getRating(),
                p.getPrice(),
                p.getExperience(),
                p.getImage(),
                p.getCategoryId(),
                p.getSubCategoryId()
        );

        return ApiResponse.success("Partner fetched", dto);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {

        if (!repo.existsById(id)) {
            return ApiResponse.failure("Partner not found");
        }

        repo.deleteById(id);
        return ApiResponse.success("Partner deleted", null);
    }
}