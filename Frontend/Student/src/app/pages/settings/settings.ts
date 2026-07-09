import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [],
  templateUrl: './settings.html',
  styleUrl: './settings.css'
})
export class Settings {

  constructor(private router: Router) {}

  // Language
  isArabic = false;
  langText = 'English';

  // Theme
  isDark = false;
  themeText = 'Light';

  // Title
  title = 'Settings';
  subtitle = 'Manage Your Account And Appearance';

  // Sidebar
  dashboard = 'Dashboard';
  grade = 'Grade';
  className = 'Class';
  student = 'Student';
  report = 'Report';
  notification = 'Notification';
  setting = 'Setting';
  profile = 'Profile';

  // Settings
  languageLabel = 'Language';
  appearanceLabel = 'Appearance';
  supportLabel = 'Support';
  passwordLabel = 'Change Password';
  aboutLabel = 'About';

  supportText = 'Need Help?';

  // ===========================
  // Language
  // ===========================

  toggleLanguage() {

    this.isArabic = !this.isArabic;

    if (this.isArabic) {

      document.documentElement.lang = 'ar';
      document.documentElement.dir = 'rtl';

      this.langText = 'العربية';

      this.title = 'الإعدادات';
      this.subtitle = 'إدارة الحساب والمظهر';

      this.dashboard = 'لوحة التحكم';
      this.grade = 'الدرجات';
      this.className = 'الفصول';
      this.student = 'الطلاب';
      this.report = 'التقارير';
      this.notification = 'الإشعارات';
      this.setting = 'الإعدادات';
      this.profile = 'الملف الشخصي';

      this.languageLabel = 'اللغة';
      this.appearanceLabel = 'المظهر';
      this.supportLabel = 'الدعم';
      this.passwordLabel = 'تغيير كلمة المرور';
      this.aboutLabel = 'حول';

      this.supportText = 'تحتاج مساعدة؟';

      this.themeText = this.isDark ? 'داكن' : 'فاتح';

    } else {

      document.documentElement.lang = 'en';
      document.documentElement.dir = 'ltr';

      this.langText = 'English';

      this.title = 'Settings';
      this.subtitle = 'Manage Your Account And Appearance';

      this.dashboard = 'Dashboard';
      this.grade = 'Grade';
      this.className = 'Class';
      this.student = 'Student';
      this.report = 'Report';
      this.notification = 'Notification';
      this.setting = 'Setting';
      this.profile = 'Profile';

      this.languageLabel = 'Language';
      this.appearanceLabel = 'Appearance';
      this.supportLabel = 'Support';
      this.passwordLabel = 'Change Password';
      this.aboutLabel = 'About';

      this.supportText = 'Need Help?';

      this.themeText = this.isDark ? 'Dark' : 'Light';

    }

  }

  // ===========================
  // Theme
  // ===========================

  toggleTheme() {

    this.isDark = !this.isDark;

    document.body.classList.toggle('dark');

    this.themeText = this.isDark
      ? (this.isArabic ? 'داكن' : 'Dark')
      : (this.isArabic ? 'فاتح' : 'Light');

  }

  // ===========================
  // Support
  // ===========================

  support() {

    if (this.isArabic) {

      alert('📧 البريد الإلكتروني:\nsupport@student.com');

    } else {

      alert('📧 Email:\nsupport@student.com');

    }

  }

  // ===========================
  // About
  // ===========================

  about() {

    if (this.isArabic) {

      alert('نظام إدارة الطلاب\nالإصدار 1.0');

    } else {

      alert('Student Management System\nVersion 1.0');

    }

  }

  // ===========================
  // Change Password
  // ===========================

  goToChangePassword() {

    this.router.navigate(['/change-password']);

  }


logout(): void {
  localStorage.removeItem('token');
  localStorage.removeItem('studentId');
  localStorage.removeItem('role');
  this.router.navigate(['/login']);
}
}