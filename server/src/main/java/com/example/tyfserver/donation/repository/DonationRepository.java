package com.example.tyfserver.donation.repository;

import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.member.domain.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query("select d from Donation d where d.member =:member and d.message.secret =:secret")
    List<Donation> findPublicDonations(
            @Param("member") Member member, @Param("secret") boolean secret, Pageable pageable);
}
