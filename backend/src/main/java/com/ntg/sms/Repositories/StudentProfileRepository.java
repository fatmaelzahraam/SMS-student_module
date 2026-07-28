package com.ntg.sms.Repositories;

import com.ntg.sms.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ntg.sms.Mapper.StudentProfileMapper;

@Repository
public interface StudentProfileRepository extends JpaRepository<Student, Long> {

    @Query(
            value = """
            SELECT
                s.STUDENT_ID                       AS "studentId",
                u.USER_ID                          AS "userId",
                u.FIRST_NAME                        AS "firstName",
                u.LAST_NAME                         AS "lastName",
                u.FIRST_NAME || ' ' || u.LAST_NAME AS "fullName",
                u.EMAIL                            AS "email",
                r.ROLE_NAME                         AS "role",
                u.NATIONAL_NUMBER                  AS "nationalId",
                u.BIRTH_DATE                       AS "birthDate",
                s.GOVERNORATE                      AS "governorate",
                s.PLACE_OF_BIRTH                   AS "placeOfBirth",
                c.NAME                             AS "className",
                g.NAME                             AS "gradeName"
            FROM STUDENT s
            JOIN USERS u
                ON s.USER_ID = u.USER_ID
            JOIN ROLES r
                ON u.ROLE_ID = r.ROLE_ID
            LEFT JOIN CLASS c
                ON s.CLASS_ID = c.CLASS_ID
            LEFT JOIN GRADE g
                ON c.GRADE_ID = g.GRADE_ID
            WHERE s.STUDENT_ID = :studentId
            """,
            nativeQuery = true
    )
    StudentProfileMapper getStudentProfile(@Param("studentId") Long studentId);

    @Query(
            value = """
            SELECT
                s.STUDENT_ID                       AS "studentId",
                u.USER_ID                          AS "userId",
                u.FIRST_NAME                        AS "firstName",
                u.LAST_NAME                         AS "lastName",
                u.FIRST_NAME || ' ' || u.LAST_NAME AS "fullName",
                u.EMAIL                            AS "email",
                r.ROLE_NAME                         AS "role",
                u.NATIONAL_NUMBER                  AS "nationalId",
                u.BIRTH_DATE                       AS "birthDate",
                s.GOVERNORATE                      AS "governorate",
                s.PLACE_OF_BIRTH                   AS "placeOfBirth",
                c.NAME                             AS "className",
                g.NAME                             AS "gradeName"
            FROM STUDENT s
            JOIN USERS u
                ON s.USER_ID = u.USER_ID
            JOIN ROLES r
                ON u.ROLE_ID = r.ROLE_ID
            LEFT JOIN CLASS c
                ON s.CLASS_ID = c.CLASS_ID
            LEFT JOIN GRADE g
                ON c.GRADE_ID = g.GRADE_ID
            WHERE u.EMAIL = :email
            """,
            nativeQuery = true
    )
    StudentProfileMapper getStudentProfileByEmail(@Param("email") String email);
}