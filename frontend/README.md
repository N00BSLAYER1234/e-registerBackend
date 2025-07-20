# Electronic Register - Frontend Integration Guide

This document explains how to run the integrated Electronic Register system with the backend API and H2 database.

## 🚀 Quick Start

### 1. Start the Backend Server

```bash
# Navigate to the project root
cd /Users/wendyas/Documents/RegistroElettronico_GRUPPO2

# Run the Spring Boot application
mvn spring-boot:run
```

The backend will start on `http://localhost:8080` and automatically:
- Initialize the H2 database with sample data
- Create REST API endpoints at `/api/*`
- Enable CORS for frontend communication

### 2. Access the Frontend

Open the frontend files in a web browser:
- **Login Page**: `frontend/index.html`
- **Student Dashboard**: `frontend/student-dashboard.html`
- **Tutor Dashboard**: `frontend/tutor-dashboard.html`

### 3. Demo Credentials

**Students:**
- Username: `student` | Password: `password`
- Username: `student2` | Password: `password`
- Username: `student3` | Password: `password`

**Tutors:**
- Username: `tutor` | Password: `password`
- Username: `tutor2` | Password: `password`

## 🔧 Technical Architecture

### Backend (Spring Boot)
- **Database**: H2 in-memory database
- **API Base URL**: `http://localhost:8080/api`
- **Sample Data**: Automatically loaded on startup

### Frontend (Vanilla JS + Tailwind)
- **Modern UI**: Clean, responsive design
- **Real-time Data**: Dynamic content from H2 database
- **API Integration**: RESTful communication

## 📊 Features Implemented

### Student Features
- ✅ **Authentication**: Login with username/password
- ✅ **Dashboard**: Real-time attendance statistics
- ✅ **Attendance Tracking**: Mark daily attendance
- ✅ **Calendar View**: Visual monthly attendance overview
- ✅ **Justifications**: Submit absence justifications
- ✅ **Statistics**: Personal attendance metrics

### Tutor Features
- ✅ **Authentication**: Login with username/password
- ✅ **Student Management**: View assigned students
- ✅ **Attendance Overview**: Monitor student attendance
- ✅ **Approval System**: Approve/disapprove attendance
- ✅ **Statistics**: Class-wide attendance metrics
- ✅ **Data Export**: Export attendance data as CSV

## 🛠️ API Endpoints

### Authentication
- `POST /api/auth/login` - User login

### Student Endpoints
- `GET /api/student/profile/{id}` - Get student profile
- `POST /api/student/attendance` - Mark attendance
- `GET /api/student/attendance/{id}` - Get attendance records
- `POST /api/student/justification` - Submit justification
- `GET /api/student/stats/{id}` - Get attendance statistics

### Tutor Endpoints
- `GET /api/tutor/students/{tutorId}` - Get tutor's students
- `GET /api/tutor/students` - Get all students
- `GET /api/tutor/attendance/{studentId}` - Get student attendance
- `POST /api/tutor/approve/{presenceId}` - Approve attendance
- `POST /api/tutor/disapprove/{presenceId}` - Disapprove attendance
- `GET /api/tutor/stats/{tutorId}` - Get tutor statistics
- `GET /api/tutor/export/{tutorId}` - Export attendance data

## 🎨 UI Components

### Modern Design Elements
- **Color Scheme**: Blue-to-purple gradients with clean whites
- **Typography**: Inter font family for readability
- **Animations**: Smooth transitions and hover effects
- **Responsive**: Works on desktop, tablet, and mobile

### Interactive Features
- **Real-time Updates**: Data refreshes automatically
- **Loading States**: Visual feedback for API calls
- **Error Handling**: User-friendly error messages
- **Toast Notifications**: Success/error notifications

## 📱 Browser Compatibility

- ✅ Chrome 90+
- ✅ Firefox 88+
- ✅ Safari 14+
- ✅ Edge 90+

## 🔍 Database Schema

### Sample Data Includes:
- **2 Tutors**: Marco Rossi, Anna Verdi
- **3 Students**: Alice Johnson, Bob Smith, Charlie Brown
- **Attendance Records**: January 2024 data
- **Relationships**: Students assigned to tutors

## 🚦 Getting Started Steps

1. **Start Backend**: `mvn spring-boot:run`
2. **Wait for Initialization**: Watch for "Sample data initialized successfully!" log
3. **Open Frontend**: Navigate to `frontend/index.html`
4. **Login**: Use demo credentials
5. **Explore**: Test attendance marking, justifications, and statistics

## 📈 Future Enhancements

- Real-time notifications
- Advanced reporting
- Mobile app integration
- Email notifications
- Multi-language support

## 🐛 Troubleshooting

### Common Issues:
1. **CORS Errors**: Ensure backend is running on port 8080
2. **Login Failures**: Check network tab for API responses
3. **Data Not Loading**: Verify H2 database initialization
4. **UI Issues**: Clear browser cache and refresh

### Debug Mode:
Open browser console (F12) to see detailed API logs and error messages.

---

**Note**: This is a demonstration system with sample data. In production, implement proper authentication, data persistence, and security measures.