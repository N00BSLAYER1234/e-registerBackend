// API Configuration
const API_CONFIG = {
    baseUrl: 'http://localhost:8080/api',
    endpoints: {
        auth: {
            login: '/auth/login'
        },
        student: {
            profile: '/student/profile',
            attendance: '/student/attendance',
            markAttendance: '/student/attendance',
            justification: '/student/justification',
            stats: '/student/stats'
        },
        tutor: {
            students: '/tutor/students',
            allStudents: '/tutor/students',
            attendance: '/tutor/attendance',
            approve: '/tutor/approve',
            disapprove: '/tutor/disapprove',
            stats: '/tutor/stats',
            export: '/tutor/export'
        }
    }
};

// Global state management
const AppState = {
    currentUser: null,
    userType: null,
    currentMonth: new Date().getMonth(),
    currentYear: new Date().getFullYear(),
    attendanceData: {},
    notifications: []
};

// Utility functions
const Utils = {
    formatDate(date) {
        return new Date(date).toLocaleDateString('en-US', {
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        });
    },

    formatTime(date) {
        return new Date(date).toLocaleTimeString('en-US', {
            hour: '2-digit',
            minute: '2-digit'
        });
    },

    showNotification(message, type = 'info') {
        const notification = document.createElement('div');
        notification.className = `notification ${type}`;
        notification.textContent = message;
        document.body.appendChild(notification);

        setTimeout(() => notification.classList.add('show'), 100);
        setTimeout(() => {
            notification.classList.remove('show');
            setTimeout(() => document.body.removeChild(notification), 300);
        }, 3000);
    },

    animateElement(element, animationClass) {
        element.classList.add(animationClass);
        element.addEventListener('animationend', () => {
            element.classList.remove(animationClass);
        }, { once: true });
    },

    getDaysInMonth(month, year) {
        return new Date(year, month + 1, 0).getDate();
    },

    getMonthName(month) {
        const months = [
            'January', 'February', 'March', 'April', 'May', 'June',
            'July', 'August', 'September', 'October', 'November', 'December'
        ];
        return months[month];
    }
};

// API Service
const ApiService = {
    async request(endpoint, options = {}) {
        const url = `${API_CONFIG.baseUrl}${endpoint}`;
        const config = {
            headers: {
                'Content-Type': 'application/json',
                ...options.headers
            },
            ...options
        };

        try {
            const response = await fetch(url, config);
            
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const contentType = response.headers.get('content-type');
            if (contentType && contentType.includes('application/json')) {
                return await response.json();
            } else {
                return await response.text();
            }
        } catch (error) {
            console.error('API Error:', error);
            throw error;
        }
    },

    // Authentication
    async login(credentials) {
        return await this.request(API_CONFIG.endpoints.auth.login, {
            method: 'POST',
            body: JSON.stringify(credentials)
        });
    },

    // Student endpoints
    async getStudentProfile(id) {
        return await this.request(`${API_CONFIG.endpoints.student.profile}/${id}`);
    },

    async markAttendance(data) {
        return await this.request(API_CONFIG.endpoints.student.markAttendance, {
            method: 'POST',
            body: JSON.stringify(data)
        });
    },

    async getStudentAttendance(id) {
        return await this.request(`${API_CONFIG.endpoints.student.attendance}/${id}`);
    },

    async submitJustification(data) {
        return await this.request(API_CONFIG.endpoints.student.justification, {
            method: 'POST',
            body: JSON.stringify(data)
        });
    },

    async getStudentStats(id, month, year) {
        const params = new URLSearchParams();
        if (month) params.append('month', month);
        if (year) params.append('year', year);
        const queryString = params.toString();
        
        return await this.request(`${API_CONFIG.endpoints.student.stats}/${id}${queryString ? '?' + queryString : ''}`);
    },

    // Tutor endpoints
    async getTutorStudents(tutorId) {
        return await this.request(`${API_CONFIG.endpoints.tutor.students}/${tutorId}`);
    },

    async getAllStudents() {
        return await this.request(API_CONFIG.endpoints.tutor.allStudents);
    },

    async getAttendanceByMonth(monthId) {
        return await this.request(`${API_CONFIG.endpoints.tutor.attendance}/month/${monthId}`);
    },

    async approveAttendance(presenceId, tutorId) {
        return await this.request(`${API_CONFIG.endpoints.tutor.approve}/${presenceId}`, {
            method: 'POST',
            body: JSON.stringify({ tutorId })
        });
    },

    async disapproveAttendance(presenceId) {
        return await this.request(`${API_CONFIG.endpoints.tutor.disapprove}/${presenceId}`, {
            method: 'POST'
        });
    },

    async getTutorStats(tutorId) {
        return await this.request(`${API_CONFIG.endpoints.tutor.stats}/${tutorId}`);
    },

    async exportData(tutorId) {
        return await this.request(`${API_CONFIG.endpoints.tutor.export}/${tutorId}`);
    }
};

// Mock data for demonstration
const MockData = {
    students: [
        { id: 1, name: 'Alice Johnson', email: 'alice@example.com', absences: 2 },
        { id: 2, name: 'Bob Smith', email: 'bob@example.com', absences: 1 },
        { id: 3, name: 'Charlie Brown', email: 'charlie@example.com', absences: 5 },
        { id: 4, name: 'Diana Prince', email: 'diana@example.com', absences: 0 }
    ],
    
    attendance: {
        1: { '2024-01-15': true, '2024-01-16': false, '2024-01-17': true },
        2: { '2024-01-15': true, '2024-01-16': true, '2024-01-17': false },
        3: { '2024-01-15': false, '2024-01-16': false, '2024-01-17': true },
        4: { '2024-01-15': true, '2024-01-16': true, '2024-01-17': true }
    },
    
    justifications: [
        { id: 1, studentId: 1, date: '2024-01-16', reason: 'Doctor appointment', status: 'pending' },
        { id: 2, studentId: 2, date: '2024-01-17', reason: 'Family emergency', status: 'approved' },
        { id: 3, studentId: 3, date: '2024-01-15', reason: 'Sick', status: 'rejected' }
    ]
};

// Authentication
const Auth = {
    async login(username, password, userType) {
        try {
            const response = await ApiService.login({
                username: username,
                password: password,
                userType: userType
            });

            if (response.success) {
                AppState.currentUser = response.user;
                AppState.userType = response.userType;
                
                localStorage.setItem('currentUser', JSON.stringify(response.user));
                localStorage.setItem('userType', response.userType);
                
                return true;
            } else {
                Utils.showNotification(response.message || 'Login failed', 'error');
                return false;
            }
        } catch (error) {
            console.error('Login error:', error);
            Utils.showNotification('Login failed. Please check your connection.', 'error');
            return false;
        }
    },
    
    logout() {
        AppState.currentUser = null;
        AppState.userType = null;
        localStorage.removeItem('currentUser');
        localStorage.removeItem('userType');
        window.location.href = 'index.html';
    },
    
    isAuthenticated() {
        return AppState.currentUser !== null;
    },
    
    checkAuth() {
        const user = localStorage.getItem('currentUser');
        const userType = localStorage.getItem('userType');
        
        if (user && userType) {
            try {
                AppState.currentUser = JSON.parse(user);
                AppState.userType = userType;
                return true;
            } catch (error) {
                console.error('Error parsing user data:', error);
                this.logout();
                return false;
            }
        }
        return false;
    }
};

// Page navigation
const Navigation = {
    init() {
        // Handle login form
        const loginForm = document.getElementById('loginForm');
        if (loginForm) {
            loginForm.addEventListener('submit', this.handleLogin);
        }
        
        // Handle logout buttons
        document.addEventListener('click', (e) => {
            if (e.target.classList.contains('logout-btn')) {
                Auth.logout();
            }
        });
    },
    
    async handleLogin(e) {
        e.preventDefault();
        const formData = new FormData(e.target);
        const username = formData.get('username');
        const password = formData.get('password');
        const userType = formData.get('userType');
        
        const loginSuccess = await Auth.login(username, password, userType);
        
        if (loginSuccess) {
            Utils.showNotification('Login successful!', 'success');
            setTimeout(() => {
                if (userType === 'student') {
                    window.location.href = 'student-dashboard.html';
                } else {
                    window.location.href = 'tutor-dashboard.html';
                }
            }, 1000);
        }
    }
};

// Student Dashboard functionality
const StudentDashboard = {
    init() {
        if (!Auth.checkAuth()) {
            window.location.href = 'index.html';
            return;
        }
        
        if (AppState.userType !== 'student') {
            window.location.href = 'index.html';
            return;
        }
        
        this.loadDashboard();
        this.setupEventListeners();
    },
    
    async loadDashboard() {
        try {
            await this.loadAttendanceData();
            await this.updateAttendanceStats();
            this.loadAttendanceCalendar();
            this.loadJustifications();
        } catch (error) {
            console.error('Error loading dashboard:', error);
            Utils.showNotification('Error loading dashboard data', 'error');
        }
    },

    async loadAttendanceData() {
        try {
            const attendance = await ApiService.getStudentAttendance(AppState.currentUser.id);
            AppState.attendanceData = {};
            
            attendance.forEach(record => {
                AppState.attendanceData[record.data] = record.stato;
            });
        } catch (error) {
            console.error('Error loading attendance data:', error);
        }
    },
    
    setupEventListeners() {
        // Mark attendance
        const markAttendanceBtn = document.getElementById('markAttendanceBtn');
        if (markAttendanceBtn) {
            markAttendanceBtn.addEventListener('click', this.markAttendance);
        }
        
        // Submit justification
        const justificationForm = document.getElementById('justificationForm');
        if (justificationForm) {
            justificationForm.addEventListener('submit', this.submitJustification);
        }
        
        // Month navigation
        const prevMonthBtn = document.getElementById('prevMonth');
        const nextMonthBtn = document.getElementById('nextMonth');
        
        if (prevMonthBtn) {
            prevMonthBtn.addEventListener('click', () => this.changeMonth(-1));
        }
        
        if (nextMonthBtn) {
            nextMonthBtn.addEventListener('click', () => this.changeMonth(1));
        }
    },
    
    async markAttendance() {
        const today = new Date().toISOString().split('T')[0];
        const hasMarkedToday = AppState.attendanceData[today];
        
        if (hasMarkedToday) {
            Utils.showNotification('Attendance already marked for today!', 'info');
            return;
        }
        
        try {
            const result = await ApiService.markAttendance({
                studentId: AppState.currentUser.id,
                date: today,
                monthId: 1 // This should be dynamically determined
            });
            
            if (result) {
                AppState.attendanceData[today] = true;
                Utils.showNotification('Attendance marked successfully!', 'success');
                
                // Update UI
                await this.updateAttendanceStats();
                this.loadAttendanceCalendar();
                
                // Animate button
                const btn = document.getElementById('markAttendanceBtn');
                btn.classList.add('pulse-once');
                btn.disabled = true;
                btn.textContent = 'Marked for Today';
            }
        } catch (error) {
            console.error('Error marking attendance:', error);
            Utils.showNotification('Failed to mark attendance', 'error');
        }
    },
    
    submitJustification(e) {
        e.preventDefault();
        const formData = new FormData(e.target);
        const date = formData.get('date');
        const reason = formData.get('reason');
        
        if (!date || !reason) {
            Utils.showNotification('Please fill in all fields!', 'error');
            return;
        }
        
        // Add to mock data
        MockData.justifications.push({
            id: Date.now(),
            studentId: 1,
            date: date,
            reason: reason,
            status: 'pending'
        });
        
        Utils.showNotification('Justification submitted successfully!', 'success');
        e.target.reset();
        this.loadJustifications();
    },
    
    async updateAttendanceStats() {
        try {
            const currentMonth = new Date().getMonth() + 1;
            const currentYear = new Date().getFullYear();
            
            const stats = await ApiService.getStudentStats(AppState.currentUser.id, currentMonth, currentYear);
            
            // Update UI
            document.getElementById('attendanceRate').textContent = `${stats.attendanceRate.toFixed(1)}%`;
            document.getElementById('presentDays').textContent = stats.presentDays;
            document.getElementById('totalDays').textContent = stats.totalDays;
            
            // Update progress bar
            const progressBar = document.querySelector('.progress-bar');
            if (progressBar) {
                progressBar.style.width = `${stats.attendanceRate}%`;
            }
        } catch (error) {
            console.error('Error updating attendance stats:', error);
            
            // Fallback to local calculation
            const currentMonth = new Date().getMonth();
            const currentYear = new Date().getFullYear();
            const daysInMonth = Utils.getDaysInMonth(currentMonth, currentYear);
            
            // Calculate attendance for current month
            let presentDays = 0;
            let totalDays = 0;
            
            for (let day = 1; day <= daysInMonth; day++) {
                const date = `${currentYear}-${String(currentMonth + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
                const dayOfWeek = new Date(date).getDay();
                
                // Skip weekends
                if (dayOfWeek !== 0 && dayOfWeek !== 6) {
                    totalDays++;
                    if (AppState.attendanceData[date]) {
                        presentDays++;
                    }
                }
            }
            
            const attendanceRate = totalDays > 0 ? (presentDays / totalDays * 100).toFixed(1) : 0;
            
            // Update UI
            document.getElementById('attendanceRate').textContent = `${attendanceRate}%`;
            document.getElementById('presentDays').textContent = presentDays;
            document.getElementById('totalDays').textContent = totalDays;
            
            // Update progress bar
            const progressBar = document.querySelector('.progress-bar');
            if (progressBar) {
                progressBar.style.width = `${attendanceRate}%`;
            }
        }
    },
    
    loadAttendanceCalendar() {
        const calendar = document.getElementById('attendanceCalendar');
        if (!calendar) return;
        
        const daysInMonth = Utils.getDaysInMonth(AppState.currentMonth, AppState.currentYear);
        const monthName = Utils.getMonthName(AppState.currentMonth);
        
        // Update month display
        document.getElementById('currentMonth').textContent = `${monthName} ${AppState.currentYear}`;
        
        let calendarHTML = '';
        
        for (let day = 1; day <= daysInMonth; day++) {
            const date = `${AppState.currentYear}-${String(AppState.currentMonth + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
            const dayOfWeek = new Date(date).getDay();
            const isToday = new Date().toISOString().split('T')[0] === date;
            
            // Skip weekends
            if (dayOfWeek === 0 || dayOfWeek === 6) continue;
            
            const attendance = AppState.attendanceData[date];
            let statusClass = '';
            let statusText = '';
            
            if (attendance === true) {
                statusClass = 'present';
                statusText = 'Present';
            } else if (attendance === false) {
                statusClass = 'absent';
                statusText = 'Absent';
            } else {
                statusClass = '';
                statusText = 'Not marked';
            }
            
            calendarHTML += `
                <div class="calendar-day ${statusClass} ${isToday ? 'today' : ''} p-4 rounded-lg border text-center">
                    <div class="font-semibold">${day}</div>
                    <div class="text-xs mt-1">${statusText}</div>
                </div>
            `;
        }
        
        calendar.innerHTML = calendarHTML;
    },
    
    loadJustifications() {
        const container = document.getElementById('justificationsList');
        if (!container) return;
        
        const studentJustifications = MockData.justifications.filter(j => j.studentId === 1);
        
        if (studentJustifications.length === 0) {
            container.innerHTML = '<p class="text-gray-500 text-center py-8">No justifications submitted yet.</p>';
            return;
        }
        
        let html = '';
        studentJustifications.forEach(justification => {
            html += `
                <div class="bg-white p-4 rounded-lg shadow-sm border hover-lift">
                    <div class="flex justify-between items-start mb-2">
                        <span class="font-medium text-gray-800">${Utils.formatDate(justification.date)}</span>
                        <span class="px-2 py-1 text-xs rounded-full status-${justification.status}">
                            ${justification.status.charAt(0).toUpperCase() + justification.status.slice(1)}
                        </span>
                    </div>
                    <p class="text-gray-600 text-sm">${justification.reason}</p>
                </div>
            `;
        });
        
        container.innerHTML = html;
    },
    
    changeMonth(direction) {
        AppState.currentMonth += direction;
        
        if (AppState.currentMonth < 0) {
            AppState.currentMonth = 11;
            AppState.currentYear--;
        } else if (AppState.currentMonth > 11) {
            AppState.currentMonth = 0;
            AppState.currentYear++;
        }
        
        this.loadAttendanceCalendar();
        this.updateAttendanceStats();
    }
};

// Tutor Dashboard functionality
const TutorDashboard = {
    init() {
        if (!Auth.checkAuth()) {
            window.location.href = 'index.html';
            return;
        }
        
        if (AppState.userType !== 'tutor') {
            window.location.href = 'index.html';
            return;
        }
        
        this.loadDashboard();
        this.setupEventListeners();
    },
    
    async loadDashboard() {
        try {
            await this.loadStudentsList();
            await this.loadPendingJustifications();
            await this.loadAttendanceOverview();
            await this.loadTutorStats();
        } catch (error) {
            console.error('Error loading dashboard:', error);
            Utils.showNotification('Error loading dashboard data', 'error');
        }
    },

    async loadTutorStats() {
        try {
            const stats = await ApiService.getTutorStats(AppState.currentUser.id);
            
            // Update stats display
            const totalStudentsEl = document.querySelector('[data-stat="total-students"]');
            const avgAttendanceEl = document.querySelector('[data-stat="avg-attendance"]');
            const pendingJustificationsEl = document.querySelector('[data-stat="pending-justifications"]');
            
            if (totalStudentsEl) totalStudentsEl.textContent = stats.totalStudents;
            if (avgAttendanceEl) avgAttendanceEl.textContent = `${stats.averageAttendance.toFixed(1)}%`;
            if (pendingJustificationsEl) pendingJustificationsEl.textContent = stats.pendingJustifications;
        } catch (error) {
            console.error('Error loading tutor stats:', error);
        }
    },
    
    setupEventListeners() {
        // Export data
        const exportBtn = document.getElementById('exportData');
        if (exportBtn) {
            exportBtn.addEventListener('click', this.exportData);
        }
        
        // Month navigation
        const prevMonthBtn = document.getElementById('prevMonth');
        const nextMonthBtn = document.getElementById('nextMonth');
        
        if (prevMonthBtn) {
            prevMonthBtn.addEventListener('click', () => this.changeMonth(-1));
        }
        
        if (nextMonthBtn) {
            nextMonthBtn.addEventListener('click', () => this.changeMonth(1));
        }
        
        // Handle justification actions
        document.addEventListener('click', (e) => {
            if (e.target.classList.contains('approve-btn')) {
                this.handleJustification(e.target.dataset.id, 'approved');
            } else if (e.target.classList.contains('reject-btn')) {
                this.handleJustification(e.target.dataset.id, 'rejected');
            }
        });
    },
    
    async loadStudentsList() {
        const container = document.getElementById('studentsList');
        if (!container) return;
        
        try {
            let students;
            
            // Get students based on user role
            if (AppState.currentUser.ruolo === 'TUTOR') {
                students = await ApiService.getTutorStudents(AppState.currentUser.id);
            } else {
                students = await ApiService.getAllStudents();
            }
            
            let html = '';
            
            for (const student of students) {
                try {
                    const stats = await ApiService.getStudentStats(student.id);
                    const attendanceRate = stats.attendanceRate || 0;
                    const statusClass = attendanceRate >= 80 ? 'text-green-600' : 'text-red-600';
                    
                    html += `
                        <div class="bg-white p-6 rounded-lg shadow-sm border hover-lift">
                            <div class="flex justify-between items-start mb-4">
                                <div>
                                    <h3 class="font-semibold text-lg text-gray-800">${student.nome} ${student.cognome}</h3>
                                    <p class="text-gray-600 text-sm">${student.email}</p>
                                </div>
                                <div class="text-right">
                                    <div class="text-2xl font-bold ${statusClass}">${attendanceRate.toFixed(1)}%</div>
                                    <div class="text-sm text-gray-500">Attendance</div>
                                </div>
                            </div>
                            <div class="flex justify-between items-center">
                                <span class="text-sm text-gray-600">Absences: ${stats.absentDays || 0}</span>
                                <button class="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors">
                                    View Details
                                </button>
                            </div>
                        </div>
                    `;
                } catch (error) {
                    console.error(`Error loading stats for student ${student.id}:`, error);
                    
                    // Fallback display without stats
                    html += `
                        <div class="bg-white p-6 rounded-lg shadow-sm border hover-lift">
                            <div class="flex justify-between items-start mb-4">
                                <div>
                                    <h3 class="font-semibold text-lg text-gray-800">${student.nome} ${student.cognome}</h3>
                                    <p class="text-gray-600 text-sm">${student.email}</p>
                                </div>
                                <div class="text-right">
                                    <div class="text-2xl font-bold text-gray-500">-</div>
                                    <div class="text-sm text-gray-500">Attendance</div>
                                </div>
                            </div>
                            <div class="flex justify-between items-center">
                                <span class="text-sm text-gray-600">Absences: -</span>
                                <button class="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors">
                                    View Details
                                </button>
                            </div>
                        </div>
                    `;
                }
            }
            
            container.innerHTML = html;
        } catch (error) {
            console.error('Error loading students list:', error);
            container.innerHTML = '<p class="text-red-500 text-center py-8">Error loading students</p>';
        }
    },
    
    loadPendingJustifications() {
        const container = document.getElementById('pendingJustifications');
        if (!container) return;
        
        const pendingJustifications = MockData.justifications.filter(j => j.status === 'pending');
        
        if (pendingJustifications.length === 0) {
            container.innerHTML = '<p class="text-gray-500 text-center py-8">No pending justifications.</p>';
            return;
        }
        
        let html = '';
        pendingJustifications.forEach(justification => {
            const student = MockData.students.find(s => s.id === justification.studentId);
            
            html += `
                <div class="bg-white p-6 rounded-lg shadow-sm border hover-lift">
                    <div class="flex justify-between items-start mb-4">
                        <div>
                            <h3 class="font-semibold text-lg text-gray-800">${student.name}</h3>
                            <p class="text-gray-600 text-sm">${Utils.formatDate(justification.date)}</p>
                        </div>
                        <span class="px-2 py-1 text-xs rounded-full status-pending">Pending</span>
                    </div>
                    <p class="text-gray-700 mb-4">${justification.reason}</p>
                    <div class="flex gap-2">
                        <button class="approve-btn px-4 py-2 btn-success text-white rounded-lg" data-id="${justification.id}">
                            Approve
                        </button>
                        <button class="reject-btn px-4 py-2 btn-danger text-white rounded-lg" data-id="${justification.id}">
                            Reject
                        </button>
                    </div>
                </div>
            `;
        });
        
        container.innerHTML = html;
    },
    
    loadAttendanceOverview() {
        const container = document.getElementById('attendanceOverview');
        if (!container) return;
        
        const monthName = Utils.getMonthName(AppState.currentMonth);
        document.getElementById('currentMonth').textContent = `${monthName} ${AppState.currentYear}`;
        
        let html = '<div class="overflow-x-auto"><table class="w-full border-collapse">';
        html += '<thead><tr class="bg-gray-50"><th class="border p-3 text-left">Student</th>';
        
        // Add day headers
        const daysInMonth = Utils.getDaysInMonth(AppState.currentMonth, AppState.currentYear);
        for (let day = 1; day <= daysInMonth; day++) {
            html += `<th class="border p-2 text-center text-sm">${day}</th>`;
        }
        html += '</tr></thead><tbody>';
        
        // Add student rows
        MockData.students.forEach(student => {
            html += `<tr class="table-row"><td class="border p-3 font-medium">${student.name}</td>`;
            
            for (let day = 1; day <= daysInMonth; day++) {
                const date = `${AppState.currentYear}-${String(AppState.currentMonth + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
                const dayOfWeek = new Date(date).getDay();
                
                if (dayOfWeek === 0 || dayOfWeek === 6) {
                    html += '<td class="border p-2 bg-gray-100"></td>';
                } else {
                    const attendance = MockData.attendance[student.id] && MockData.attendance[student.id][date];
                    const statusClass = attendance === true ? 'bg-green-100 text-green-800' : 
                                      attendance === false ? 'bg-red-100 text-red-800' : 'bg-gray-50';
                    const statusText = attendance === true ? '✓' : attendance === false ? '✗' : '-';
                    
                    html += `<td class="border p-2 text-center ${statusClass}">${statusText}</td>`;
                }
            }
            
            html += '</tr>';
        });
        
        html += '</tbody></table></div>';
        container.innerHTML = html;
    },
    
    calculateAttendanceRate(studentId) {
        const attendance = MockData.attendance[studentId] || {};
        const attendanceArray = Object.values(attendance);
        const present = attendanceArray.filter(a => a === true).length;
        const total = attendanceArray.length;
        
        return total > 0 ? Math.round((present / total) * 100) : 0;
    },
    
    handleJustification(justificationId, action) {
        const justification = MockData.justifications.find(j => j.id == justificationId);
        if (justification) {
            justification.status = action;
            Utils.showNotification(`Justification ${action}!`, 'success');
            this.loadPendingJustifications();
        }
    },
    
    exportData() {
        const data = {
            students: MockData.students,
            attendance: MockData.attendance,
            justifications: MockData.justifications,
            exportDate: new Date().toISOString()
        };
        
        const dataStr = JSON.stringify(data, null, 2);
        const dataBlob = new Blob([dataStr], { type: 'application/json' });
        const url = URL.createObjectURL(dataBlob);
        
        const link = document.createElement('a');
        link.href = url;
        link.download = `attendance-data-${new Date().toISOString().split('T')[0]}.json`;
        link.click();
        
        URL.revokeObjectURL(url);
        Utils.showNotification('Data exported successfully!', 'success');
    },
    
    changeMonth(direction) {
        AppState.currentMonth += direction;
        
        if (AppState.currentMonth < 0) {
            AppState.currentMonth = 11;
            AppState.currentYear--;
        } else if (AppState.currentMonth > 11) {
            AppState.currentMonth = 0;
            AppState.currentYear++;
        }
        
        this.loadAttendanceOverview();
    }
};

// Initialize the application
document.addEventListener('DOMContentLoaded', () => {
    // Initialize mock data
    AppState.attendanceData = {
        '2024-01-15': true,
        '2024-01-16': false,
        '2024-01-17': true,
        '2024-01-18': true,
        '2024-01-19': true
    };
    
    // Initialize based on current page
    const currentPage = window.location.pathname.split('/').pop();
    
    switch (currentPage) {
        case 'index.html':
        case '':
            Navigation.init();
            break;
        case 'student-dashboard.html':
            StudentDashboard.init();
            break;
        case 'tutor-dashboard.html':
            TutorDashboard.init();
            break;
    }
});

// Export for global access
window.Auth = Auth;
window.Utils = Utils;