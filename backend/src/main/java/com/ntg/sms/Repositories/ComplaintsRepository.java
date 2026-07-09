package com.ntg.sms.Repositories;

import com.ntg.sms.Entities.Complaints;
import com.ntg.sms.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ComplaintsRepository  extends JpaRepository<Complaints,Long>{
    List<Complaints> findByUserOrderBySubmittedAtDesc(User user);

    Optional<Complaints> findByComplaintIdAndUser(Long complaintId, User user);

    long countByUser(User user);

    long countByUserAndStatus(User user, String status);
}
