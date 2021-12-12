package com.thankyou_for.common.service;

import com.thankyou_for.auth.domain.Oauth2Type;
import com.thankyou_for.common.repository.DummyDataRepository;
import com.thankyou_for.donation.domain.Donation;
import com.thankyou_for.donation.domain.Message;
import com.thankyou_for.member.domain.Account;
import com.thankyou_for.member.domain.Member;
import com.thankyou_for.payment.domain.Payment;
import com.thankyou_for.payment.domain.PaymentStatus;
import com.thankyou_for.payment.domain.RefundFailure;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DummyDataService {

    private final DummyDataRepository batchDataRepository;

    @Value("${batch_size}")
    private int batchSize;

    public void putMemberDummyData() {
        List<Member> members = new ArrayList<>();
        List<Account> accounts = new ArrayList<>();
        int idx = 1;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < batchSize; j++) {
                accounts.add(new Account("test", String.valueOf(idx), "900101-1000000", "bank", null));
                members.add(new Member("e" + idx + "@test.com", "n" + idx, "p" + idx, Oauth2Type.KAKAO));
                idx++;
            }

            batchDataRepository.saveAllAccount(accounts);
            batchDataRepository.saveAllMember(members, idx - batchSize);
            accounts.clear();
            members.clear();
        }
    }

    public void putPaymentAndRefundFailiureDummyData() {
        List<RefundFailure> refundFailures = new ArrayList<>();
        List<Payment> payments = new ArrayList<>();
        int idx = 1;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < batchSize; j++) {
                refundFailures.add(new RefundFailure());
                Payment payment = new Payment(10_0000_0000L, "10000포인트 충전", "123-123", UUID.randomUUID());
                payment.updateStatus(PaymentStatus.PAID);
                payments.add(payment);
                idx++;
            }

            batchDataRepository.saveAllRefundFailure(refundFailures);
            batchDataRepository.saveAllPayment(payments, idx - batchSize);
            refundFailures.clear();
            payments.clear();
        }
    }

    public void putDonationDummyData() {
        List<Donation> donations = new ArrayList<>();
        int idx = 1;
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < batchSize; j++) {
                donations.add(new Donation(new Message("n1"), 1000));
                idx++;
            }

            batchDataRepository.saveAllDonation(donations);
            donations.clear();
        }
    }
}
