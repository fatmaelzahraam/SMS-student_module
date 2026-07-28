package com.ntg.sms.Repositories;


import com.ntg.sms.Entities.UserPhoneNumber;
import com.ntg.sms.Entities.UserPhoneNumberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPhoneNumberRepository extends JpaRepository<UserPhoneNumber, UserPhoneNumberId>{
    List<UserPhoneNumber> findById_UserId(Long userId);
}
