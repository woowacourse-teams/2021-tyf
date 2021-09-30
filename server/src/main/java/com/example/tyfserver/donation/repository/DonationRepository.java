package com.example.tyfserver.donation.repository;

import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.DonationStatus;
import com.example.tyfserver.member.domain.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long>, DonationQueryRepository {

    @EntityGraph(attributePaths = "creator")
    List<Donation> findDonationByCreatorOrderByCreatedAtDesc(Member member, Pageable pageable);

    @EntityGraph(attributePaths = "creator")
    List<Donation> findDonationByStatusAndCreator(DonationStatus status, Member member);
}
