package com.example.tyfserver.donation.repository;

import com.example.tyfserver.donation.domain.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Long> {
}
