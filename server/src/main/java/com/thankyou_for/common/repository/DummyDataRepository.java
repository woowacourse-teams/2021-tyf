package com.thankyou_for.common.repository;

import com.thankyou_for.donation.domain.Donation;
import com.thankyou_for.member.domain.Account;
import com.thankyou_for.member.domain.Member;
import com.thankyou_for.payment.domain.Payment;
import com.thankyou_for.payment.domain.RefundFailure;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DummyDataRepository {

    private final JdbcTemplate jdbcTemplate;

    @Value("${batch_size}")
    private int batchSize;

    public void saveAllMember(List<Member> members, int startIdx) {
        int batchCount = 0;
        List<Member> subItems = new ArrayList<>();
        for (int i = 0; i < members.size(); i++) {
            subItems.add(members.get(i));
            if ((i + 1) % batchSize == 0) {
                batchCount = batchInsertMember(batchCount, subItems, startIdx);
            }
        }
        if (!subItems.isEmpty()) {
            batchCount = batchInsertMember(batchCount, subItems, startIdx);
        }
    }

    public void saveAllAccount(List<Account> accounts) {
        int batchCount = 0;
        List<Account> subItems = new ArrayList<>();
        for (int i = 0; i < accounts.size(); i++) {
            subItems.add(accounts.get(i));
            if ((i + 1) % batchSize == 0) {
                batchCount = batchInsertAccount(batchCount, subItems);
            }
        }
        if (!subItems.isEmpty()) {
            batchCount = batchInsertAccount(batchCount, subItems);
        }
    }


    private int batchInsertAccount(int batchCount, List<Account> accounts) {
        jdbcTemplate.batchUpdate("INSERT INTO account (created_at, account_holder, account_number, bank, bankbook_url, status) VALUES (?,?,?,?,?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, LocalDateTime.now().toString());
                        ps.setString(2, accounts.get(i).getAccountHolder());
                        ps.setString(3, accounts.get(i).getAccountNumber());
                        ps.setString(4, accounts.get(i).getBank());
                        ps.setString(5, accounts.get(i).getBankbookUrl());
                        ps.setString(6, accounts.get(i).getStatus().name());
                    }

                    @Override
                    public int getBatchSize() {
                        return accounts.size();
                    }
                });
        accounts.clear();
        batchCount++;
        return batchCount;
    }

    private int batchInsertMember(int batchCount, List<Member> members, int startIdx) {
        jdbcTemplate.batchUpdate("INSERT INTO member (created_at, bio, email, nickname, oauth2type, page_name, point, profile_image, account_id) VALUES (?,?,?,?,?,?,?,?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, LocalDateTime.now().toString());
                        ps.setString(2, members.get(i).getBio());
                        ps.setString(3, members.get(i).getEmail());
                        ps.setString(4, members.get(i).getNickname());
                        ps.setString(5, members.get(i).getOauth2Type().name());
                        ps.setString(6, members.get(i).getPageName());
                        ps.setLong(7, members.get(i).getPoint());
                        ps.setString(8, members.get(i).getProfileImage());
                        ps.setLong(9, startIdx + i);
                    }

                    @Override
                    public int getBatchSize() {
                        return members.size();
                    }
                });
        members.clear();
        batchCount++;
        return batchCount;
    }

    public void saveAllRefundFailure(List<RefundFailure> refundFailures) {
        int batchCount = 0;
        List<RefundFailure> subItems = new ArrayList<>();
        for (int i = 0; i < refundFailures.size(); i++) {
            subItems.add(refundFailures.get(i));
            if ((i + 1) % batchSize == 0) {
                batchCount = batchInsertRefundFailure(batchCount, subItems);
            }
        }
        if (!subItems.isEmpty()) {
            batchCount = batchInsertRefundFailure(batchCount, subItems);
        }
    }

    private int batchInsertRefundFailure(int batchCount, List<RefundFailure> refundFailures) {
        jdbcTemplate.batchUpdate("INSERT INTO refund_failure (created_at, remain_try_count) VALUES (?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, LocalDateTime.now().toString());
                        ps.setInt(2, refundFailures.get(i).getRemainTryCount());
                    }

                    @Override
                    public int getBatchSize() {
                        return refundFailures.size();
                    }
                });
        refundFailures.clear();
        batchCount++;
        return batchCount;
    }

    public void saveAllPayment(List<Payment> payments, int startIdx) {
        int batchCount = 0;
        List<Payment> subItems = new ArrayList<>();
        for (int i = 0; i < payments.size(); i++) {
            subItems.add(payments.get(i));
            if ((i + 1) % batchSize == 0) {
                batchCount = batchInsertPayment(batchCount, subItems, startIdx);
            }
        }
        if (!subItems.isEmpty()) {
            batchCount = batchInsertPayment(batchCount, subItems, startIdx);
        }
    }

    private int batchInsertPayment(int batchCount, List<Payment> payments, int startIdx) {
        jdbcTemplate.batchUpdate("INSERT INTO payment (created_at, amount, email, imp_uid, item_name, " +
                        "merchant_uid, status, member_id, refund_failure_id) VALUES (?,?,?,?,?,?,?,?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, LocalDateTime.now().toString());
                        ps.setLong(2, payments.get(i).getAmount());
                        ps.setString(3, "e1@test.com");
                        ps.setString(4, payments.get(i).getImpUid());
                        ps.setString(5, payments.get(i).getItemName());
                        ps.setString(6, "123-123");
                        ps.setString(7, payments.get(i).getStatus().name());
                        ps.setLong(8, startIdx + i);
                        ps.setLong(9, startIdx + i);
                    }

                    @Override
                    public int getBatchSize() {
                        return payments.size();
                    }
                });
        payments.clear();
        batchCount++;
        return batchCount;
    }

    public void saveAllDonation(List<Donation> donations) {
        int batchCount = 0;
        List<Donation> subItems = new ArrayList<>();
        for (int i = 0; i < donations.size(); i++) {
            subItems.add(donations.get(i));
            if ((i + 1) % batchSize == 0) {
                batchCount = batchInsertDonation(batchCount, subItems);
            }
        }
        if (!subItems.isEmpty()) {
            batchCount = batchInsertDonation(batchCount, subItems);
        }
    }

    private int batchInsertDonation(int batchCount, List<Donation> donations) {
        jdbcTemplate.batchUpdate("INSERT INTO donation (created_at, message, name, secret, point, " +
                        "status, creator_id, donator_id) VALUES (?,?,?,?,?,?,?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, LocalDateTime.now().toString());
                        ps.setString(2, "message");
                        ps.setString(3, "n1");
                        ps.setBoolean(4, false);
                        ps.setLong(5, donations.get(i).getPoint());
                        ps.setString(6, "WAITING_FOR_EXCHANGE");
                        ps.setLong(7, 1);
                        ps.setLong(8, 2);
                    }

                    @Override
                    public int getBatchSize() {
                        return donations.size();
                    }
                });
        donations.clear();
        batchCount++;
        return batchCount;
    }
}
