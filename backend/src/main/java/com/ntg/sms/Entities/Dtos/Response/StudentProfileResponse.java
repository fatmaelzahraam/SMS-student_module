package com.ntg.sms.Entities.Dtos.Response;

import lombok.*;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor

public class StudentProfileResponse {
    private Long studentId;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String role;
    private String className;
    private Long phoneNumber;
    private Long nationalId;
    private LocalDate birthDate;
    private String governorate;
    private String placeOfBirth;
    private String gradeName;
    private String profileImage;

    @Generated
    public static StudentProfileResponseBuilder builder() {
        return new StudentProfileResponseBuilder();
    }

    @Generated
    public Long getStudentId() {
        return this.studentId;
    }

    @Generated
    public String getFullName() {
        return this.fullName;
    }

    @Generated
    public String getEmail() {
        return this.email;
    }

    @Generated
    public Long getPhoneNumber() {
        return this.phoneNumber;
    }

    @Generated
    public Long getNationalId() {
        return this.nationalId;
    }

    @Generated
    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    @Generated
    public String getGovernorate() {
        return this.governorate;
    }

    @Generated
    public String getPlaceOfBirth() {
        return this.placeOfBirth;
    }

    @Generated
    public String getClassName() {
        return this.className;
    }

    @Generated
    public String getGradeName() {
        return this.gradeName;
    }

    @Generated
    public String getProfileImage() {
        return this.profileImage;
    }

    @Generated
    public void setStudentId(final Long studentId) {
        this.studentId = studentId;
    }

    @Generated
    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    @Generated
    public void setEmail(final String email) {
        this.email = email;
    }

    @Generated
    public void setPhoneNumber(final Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Generated
    public void setNationalId(final Long nationalId) {
        this.nationalId = nationalId;
    }

    @Generated
    public void setBirthDate(final LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Generated
    public void setGovernorate(final String governorate) {
        this.governorate = governorate;
    }

    @Generated
    public void setPlaceOfBirth(final String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    @Generated
    public void setClassName(final String className) {
        this.className = className;
    }

    @Generated
    public void setGradeName(final String gradeName) {
        this.gradeName = gradeName;
    }

    @Generated
    public void setProfileImage(final String profileImage) {
        this.profileImage = profileImage;
    }

    @Generated
    public StudentProfileResponse() {
    }

    @Generated
    public StudentProfileResponse(final Long studentId, final String fullName, final String email, final Long phoneNumber, final Long nationalId, final LocalDate birthDate, final String governorate, final String placeOfBirth, final String className, final String gradeName, final String profileImage) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.nationalId = nationalId;
        this.birthDate = birthDate;
        this.governorate = governorate;
        this.placeOfBirth = placeOfBirth;
        this.className = className;
        this.gradeName = gradeName;
        this.profileImage = profileImage;
    }

    @Generated
    public static class StudentProfileResponseBuilder {
        @Generated
        private Long studentId;
        @Generated
        private String fullName;
        @Generated
        private String email;
        @Generated
        private Long phoneNumber;
        @Generated
        private Long nationalId;
        @Generated
        private LocalDate birthDate;
        @Generated
        private String governorate;
        @Generated
        private String placeOfBirth;
        @Generated
        private String className;
        @Generated
        private String gradeName;
        @Generated
        private String profileImage;

        @Generated
        StudentProfileResponseBuilder() {
        }

        @Generated
        public StudentProfileResponseBuilder studentId(final Long studentId) {
            this.studentId = studentId;
            return this;
        }

        @Generated
        public StudentProfileResponseBuilder fullName(final String fullName) {
            this.fullName = fullName;
            return this;
        }

        @Generated
        public StudentProfileResponseBuilder email(final String email) {
            this.email = email;
            return this;
        }

        @Generated
        public StudentProfileResponseBuilder phoneNumber(final Long phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        @Generated
        public StudentProfileResponseBuilder nationalId(final Long nationalId) {
            this.nationalId = nationalId;
            return this;
        }

        @Generated
        public StudentProfileResponseBuilder birthDate(final LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        @Generated
        public StudentProfileResponseBuilder governorate(final String governorate) {
            this.governorate = governorate;
            return this;
        }

        @Generated
        public StudentProfileResponseBuilder placeOfBirth(final String placeOfBirth) {
            this.placeOfBirth = placeOfBirth;
            return this;
        }

        @Generated
        public StudentProfileResponseBuilder className(final String className) {
            this.className = className;
            return this;
        }

        @Generated
        public StudentProfileResponseBuilder gradeName(final String gradeName) {
            this.gradeName = gradeName;
            return this;
        }

        @Generated
        public StudentProfileResponseBuilder profileImage(final String profileImage) {
            this.profileImage = profileImage;
            return this;
        }

        @Generated
        public StudentProfileResponse build() {
            return new StudentProfileResponse(this.studentId, this.fullName, this.email, this.phoneNumber, this.nationalId, this.birthDate, this.governorate, this.placeOfBirth, this.className, this.gradeName, this.profileImage);
        }

        @Generated
        public String toString() {
            Long var10000 = this.studentId;
            return "StudentProfileResponse.StudentProfileResponseBuilder(studentId=" + var10000 + ", fullName=" + this.fullName + ", email=" + this.email + ", phoneNumber=" + this.phoneNumber + ", nationalId=" + this.nationalId + ", birthDate=" + String.valueOf(this.birthDate) + ", governorate=" + this.governorate + ", placeOfBirth=" + this.placeOfBirth + ", className=" + this.className + ", gradeName=" + this.gradeName + ", profileImage=" + this.profileImage + ")";
        }
    }
}
