package com.example.tyfserver.banner.repository;

import com.example.tyfserver.banner.domain.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, Long> {
    List<Banner> findAllByMemberId(Long id);
}
