package com.ntg.sms.Repositories;

import com.ntg.sms.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface StudentProfileRepository extends JpaRepository<Student, Long> {

    @Query(
            value = """
            SELECT
                s.STUDENT_ID                       AS "studentId",
                u.FIRST_NAME || ' ' || u.LAST_NAME AS "fullName",
                u.EMAIL                            AS "email",
                up.PHONE_NUMBER                    AS "phoneNumber",
                u.NATIONAL_NUMBER                  AS "nationalId",
                u.BIRTH_DATE                       AS "birthDate",
                s.GOVERNORATE                      AS "governorate",
                s.PLACE_OF_BIRTH                   AS "placeOfBirth",
                c.NAME                             AS "className",
                g.NAME                             AS "gradeName"
            FROM STUDENT s
            JOIN USERS u
                ON s.USER_ID = u.USER_ID
            LEFT JOIN USER_PHONE_NUMBERS up
                ON up.USER_ID = u.USER_ID
            LEFT JOIN CLASS c
                ON s.CLASS_ID = c.CLASS_ID
            LEFT JOIN GRADE g
                ON c.GRADE_ID = g.GRADE_ID
            WHERE s.STUDENT_ID = :studentId
            FETCH FIRST 1 ROWS ONLY
            """,
            nativeQuery = true
    )
    Map<String, Object> getStudentProfile(@Param("studentId") Long studentId);

    @Query(
            value = """
            SELECT
                s.STUDENT_ID                       AS "studentId",
                u.FIRST_NAME || ' ' || u.LAST_NAME AS "fullName",
                u.EMAIL                            AS "email",
                up.PHONE_NUMBER                    AS "phoneNumber",
                u.NATIONAL_NUMBER                  AS "nationalId",
                u.BIRTH_DATE                       AS "birthDate",
                s.GOVERNORATE                      AS "governorate",
                s.PLACE_OF_BIRTH                   AS "placeOfBirth",
                c.NAME                             AS "className",
                g.NAME                             AS "gradeName"
            FROM STUDENT s
            JOIN USERS u
                ON s.USER_ID = u.USER_ID
            LEFT JOIN USER_PHONE_NUMBERS up
                ON up.USER_ID = u.USER_ID
            LEFT JOIN CLASS c
                ON s.CLASS_ID = c.CLASS_ID
            LEFT JOIN GRADE g
                ON c.GRADE_ID = g.GRADE_ID
            WHERE u.EMAIL = :email
            FETCH FIRST 1 ROWS ONLY
            """,
            nativeQuery = true
    )
    Map<String, Object> getStudentProfileByEmail(@Param("email") String email);
}