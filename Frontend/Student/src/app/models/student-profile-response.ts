export interface StudentProfileResponse {
  studentId:    number;
  firstName:    string;
  lastName:     string;
  fullName:     string;
  email:        string;
  role:         string;
  className:    string;
  gradeName:    string;
  phoneNumbers: number[];  
  nationalId:   number;
  birthDate:    string;
  governorate:  string;
  placeOfBirth: string;
  profileImage: string | null;
}