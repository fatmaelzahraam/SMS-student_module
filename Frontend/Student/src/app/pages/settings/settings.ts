import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PasswordService } from './passwordservice/passwordservice';


@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './settings.html',
  styleUrl: './settings.css'
})
export class Settings {

  private router         = inject(Router);
  private passwordService = inject(PasswordService);

  // ===========================
  // Language
  // ===========================
  isArabic = false;
  langText = 'English';

  // ===========================
  // Theme
  // ===========================
  isDark    = false;
  themeText = 'Light';

  // ===========================
  // UI Labels – Title
  // ===========================
  title    = 'Settings';
  subtitle = 'Manage Your Account And Appearance';

  // ===========================
  // UI Labels – Sidebar
  // ===========================
  dashboard    = 'Dashboard';
  grade        = 'Grade';
  className    = 'Class';
  student      = 'Student';
  report       = 'Report';
  notification = 'Notification';
  setting      = 'Setting';
  profile      = 'Profile';

  // ===========================
  // UI Labels – Settings Items
  // ===========================
  languageLabel   = 'Language';
  appearanceLabel = 'Appearance';
  supportLabel    = 'Support';
  passwordLabel   = 'Change Password';
  aboutLabel      = 'About';
  supportText     = 'Need Help?';

  // ===========================
  // Popup – Change Password
  // ===========================
  showPasswordPopup = false;
  currentPassword   = '';
  newPassword       = '';
  confirmPassword   = '';
  showCurrentPwd    = false;
  showNewPwd        = false;
  showConfirmPwd    = false;
  passwordError     = '';
  passwordSuccess   = '';
  isLoading         = false;   // disables the button while request is in-flight

  // ===========================
  // Popup – App Version / About
  // ===========================
  showAboutPopup = false;

  // ===========================
  // Language Toggle
  // ===========================
  toggleLanguage() {
    this.isArabic = !this.isArabic;

    if (this.isArabic) {
      document.documentElement.lang = 'ar';
      document.documentElement.dir  = 'rtl';

      this.langText  = 'العربية';
      this.title     = 'الإعدادات';
      this.subtitle  = 'إدارة الحساب والمظهر';

      this.dashboard    = 'لوحة التحكم';
      this.grade        = 'الدرجات';
      this.className    = 'الفصول';
      this.student      = 'الطلاب';
      this.report       = 'التقارير';
      this.notification = 'الإشعارات';
      this.setting      = 'الإعدادات';
      this.profile      = 'الملف الشخصي';

      this.languageLabel   = 'اللغة';
      this.appearanceLabel = 'المظهر';
      this.supportLabel    = 'الدعم';
      this.passwordLabel   = 'تغيير كلمة المرور';
      this.aboutLabel      = 'حول';
      this.supportText     = 'تحتاج مساعدة؟';
      this.themeText       = this.isDark ? 'داكن' : 'فاتح';

    } else {
      document.documentElement.lang = 'en';
      document.documentElement.dir  = 'ltr';

      this.langText  = 'English';
      this.title     = 'Settings';
      this.subtitle  = 'Manage Your Account And Appearance';

      this.dashboard    = 'Dashboard';
      this.grade        = 'Grade';
      this.className    = 'Class';
      this.student      = 'Student';
      this.report       = 'Report';
      this.notification = 'Notification';
      this.setting      = 'Setting';
      this.profile      = 'Profile';

      this.languageLabel   = 'Language';
      this.appearanceLabel = 'Appearance';
      this.supportLabel    = 'Support';
      this.passwordLabel   = 'Change Password';
      this.aboutLabel      = 'About';
      this.supportText     = 'Need Help?';
      this.themeText       = this.isDark ? 'Dark' : 'Light';
    }
  }

  // ===========================
  // Theme Toggle
  // ===========================
  toggleTheme() {
    this.isDark = !this.isDark;
    document.body.classList.toggle('dark');
    this.themeText = this.isDark
      ? (this.isArabic ? 'داكن'  : 'Dark')
      : (this.isArabic ? 'فاتح' : 'Light');
  }

  // ===========================
  // Support
  // ===========================
  support() {
    const msg = this.isArabic
      ? '📧 البريد الإلكتروني:\nsupport@student.com'
      : '📧 Email:\nsupport@student.com';
    alert(msg);
  }

  // ===========================
  // About Popup
  // ===========================
  openAboutPopup()  { this.showAboutPopup = true;  }
  closeAboutPopup() { this.showAboutPopup = false; }

  // ===========================
  // Change Password Popup
  // ===========================
  openPasswordPopup() {
    this.showPasswordPopup = true;
    this.currentPassword   = '';
    this.newPassword       = '';
    this.confirmPassword   = '';
    this.passwordError     = '';
    this.passwordSuccess   = '';
    this.showCurrentPwd    = false;
    this.showNewPwd        = false;
    this.showConfirmPwd    = false;
    this.isLoading         = false;
  }

  closePasswordPopup() {
    if (this.isLoading) return;  
    this.showPasswordPopup = false;
  }

  toggleVisibility(field: 'current' | 'new' | 'confirm') {
    if (field === 'current') this.showCurrentPwd  = !this.showCurrentPwd;
    if (field === 'new')     this.showNewPwd      = !this.showNewPwd;
    if (field === 'confirm') this.showConfirmPwd  = !this.showConfirmPwd;
  }


  updatePassword() {
    this.passwordError   = '';
    this.passwordSuccess = '';

    if (!this.currentPassword || !this.newPassword || !this.confirmPassword) {
      this.passwordError = this.isArabic
        ? 'يرجى ملء جميع الحقول.'
        : 'Please fill in all fields.';
      return;
    }

    if (this.newPassword.length < 8) {
      this.passwordError = this.isArabic
        ? 'يجب أن تكون كلمة المرور 8 أحرف على الأقل.'
        : 'New password must be at least 8 characters.';
      return;
    }

    if (this.newPassword !== this.confirmPassword) {
      this.passwordError = this.isArabic
        ? 'كلمة المرور الجديدة وتأكيدها غير متطابقتين.'
        : 'New password and confirmation do not match.';
      return;
    }

  
    this.isLoading = true;

    this.passwordService.changePassword({
      currentPassword: this.currentPassword,
      newPassword:     this.newPassword,
      confirmPassword: this.confirmPassword
    }).subscribe({
      next: () => {
        this.isLoading = false;
        this.passwordSuccess = this.isArabic
          ? 'تم تغيير كلمة المرور بنجاح ✓'
          : 'Password updated successfully ✓';
        setTimeout(() => this.closePasswordPopup(), 1500);
      },
      error: (err) => {
        this.isLoading = false;
        // Backend returns { message: "..." } for all error cases
        this.passwordError = err?.error?.message
          ?? (this.isArabic ? 'حدث خطأ، حاول مرة أخرى.' : 'Something went wrong. Please try again.');
      }
    });
  }

  // ===========================
  // Logout
  // ===========================
  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('studentId');
    localStorage.removeItem('role');
    this.router.navigate(['/login']);
  }
}