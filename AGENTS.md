## 1. Project Structure Design
- Create **Core**, **Shared**, and **Feature** modules.
- Features: `auth`, `student-dashboard`, `tutor-dashboard`, `home`.

## 2. TypeScript Models & Interfaces
- Define types for users, attendance records, justifications, notifications, API
 responses, and forms.

## 3. Core Services Implementation
- `ApiService` for HTTP requests and interceptors.
- `AuthService` for JWT authentication and role management.
- `WebSocketService` for real-time communication.
- `NotificationService` for toast and database notifications.

## 4. Guards & Interceptors
- `AuthGuard` and `RoleGuard` for route protection.
- `AuthInterceptor` for attaching and refreshing JWT tokens.
- `ErrorInterceptor` for global error handling.

## 5. Feature Services
- `StudentAttendanceService` and `StudentJustificationService` with WebSocket up
dates.
- `TutorAttendanceService`, `TutorJustificationService`, and `StudentManagementS
ervice` for tutor operations.

## 6. Component Architecture
- Use reactive forms, Observables, and the OnPush change detection strategy.
- Apply the `takeUntil` pattern to avoid memory leaks.

## 7. Real-time Synchronization Strategy
- WebSocket events with polling fallback.
- Optimistic UI updates with rollback on failure.

## 8. UI Component Conversion
- Transform vanilla components into smart/dumb Angular components.
- Maintain responsive design and integrate Angular animations.

## 9. State Management Pattern
- Services hold state via `BehaviorSubject` streams.
- Components subscribe for single-source-of-truth updates.

## 10. Development Workflow
- Use Angular CLI, strict TypeScript mode, ESLint, Prettier, and environment con
figs.
- Enable lazy loading for feature modules.

## Deliverables
- Organized Angular project with typed models and services.
- Real-time student and tutor dashboards.
- Authentication with role-based routing.
- Error handling, loading states, and responsive design.

## Success Criteria
- Attendance updates appear immediately on tutor dashboards.
- Type safety across the codebase.
- Modular structure with seamless real-time updates and OnPush performance.