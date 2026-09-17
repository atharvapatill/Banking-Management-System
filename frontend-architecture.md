# Frontend Architecture

This project currently exposes a Spring Boot REST API for banking operations. The frontend layer should be designed as a separate client application that consumes the backend securely and presents role-based banking workflows for admins, managers, clerks, and cashiers.

## 1. Recommended Stack

- React
- Vite
- JavaScript (JSX)
- React Router
- React Query / custom hooks (or Redux Toolkit for larger state needs)
- Axios or Fetch wrapper
- CSS Modules or Tailwind CSS

This combination is lightweight, fast, and suitable for a banking dashboard with forms, tables, filters, and protected pages.

## 2. High-Level Architecture

```text
+---------------------+
| Browser / User      |
| - login page        |
| - dashboard views   |
| - role-based pages  |
+----------+----------+
           |
           v
+---------------------+
| Frontend App        |
| - Routes            |
| - Pages             |
| - Components        |
| - Auth Guard        |
| - State Management  |
+----------+----------+
           |
           v
+---------------------+
| API Layer           |
| - auth service      |
| - user service      |
| - customer service  |
| - account service   |
| - transaction api   |
+----------+----------+
           |
           v
+---------------------+
| Spring Boot Backend |
| /auth               |
| /users              |
| /customers          |
| /accounts           |
| /transactions       |
| /transfers          |
+---------------------+
```

## 3. Core Responsibilities

### Presentation Layer
- Displays login, dashboards, customer records, account details, transfers, and reports.
- Uses reusable components such as cards, tables, modals, forms, and filters.
- Keeps business logic out of UI components as much as possible.

### Routing Layer
- Handles public and protected routes.
- Controls access based on authenticated user role.

Example routes:
- `/login`
- `/dashboard`
- `/customers`
- `/accounts`
- `/transactions`
- `/transfers`
- `/users`

### Auth Layer
- Login form posts credentials to `/auth/login`.
- Session cookie is managed by the browser automatically.
- Protected pages check session state and role before rendering.

Recommended role rules:
- `ADMIN`: full access to users, roles, permissions, and system settings
- `MANAGER`: monitoring and management operations
- `CLERK`: customer and account related tasks
- `CASHIER`: deposits, withdrawals, and transfer workflows

### API Layer
- Centralizes backend communication.
- Uses plain JS objects for request and response data.
- Wraps all HTTP calls with consistent error handling and toast notifications.

## 4. Suggested Folder Structure

```text
src/
  app/
    api/
      authApi.js
      userApi.js
      customerApi.js
      accountApi.js
      transactionApi.js
      transferApi.js
    auth/
      AuthContext.jsx
      ProtectedRoute.jsx
      roleGuard.js
    components/
      common/
        Button.jsx
        Modal.jsx
        Table.jsx
        Input.jsx
      layout/
        Sidebar.jsx
        Header.jsx
        Navbar.jsx
    features/
      dashboard/
      customers/
      accounts/
      transactions/
      transfers/
      users/
    hooks/
      useAuth.js
      useCustomers.js
      useAccounts.js
    pages/
      LoginPage.jsx
      DashboardPage.jsx
      CustomersPage.jsx
      AccountsPage.jsx
      TransactionsPage.jsx
      UsersPage.jsx
    routes/
      AppRoutes.jsx
    store/
      authSlice.js
      queryClient.js
    utils/
      formatCurrency.js
      apiError.js
      roleHelpers.js
```

## 5. Authentication and Authorization Flow

1. User opens `/login`.
2. Frontend sends a POST request to `/auth/login`.
3. Backend validates credentials and creates a session.
4. Browser stores the session cookie.
5. App loads current authenticated user or role state.
6. Protected routes check authorization.
7. UI renders only allowed modules.

Important rule:
- Never rely only on client-side hiding of buttons for security.
- Backend authorization must remain the source of truth.

## 6. API Integration Pattern

Each module should use a dedicated service file and plain JavaScript data objects.

Example pattern:

```jsx
export const getCustomers = async () => {
  const response = await api.get('/customers');
  return response.data;
};
```

Use query hooks for read operations:

```jsx
const { data, isLoading, error } = useQuery({
  queryKey: ['customers'],
  queryFn: getCustomers,
});
```

Use mutation hooks for create/update/delete actions:

```jsx
const mutation = useMutation({
  mutationFn: createCustomer,
  onSuccess: () => queryClient.invalidateQueries({ queryKey: ['customers'] }),
});
```

## 7. Page Modules

### Dashboard
- overview cards
- account summaries
- transaction summary
- recent activity

### Customer Management
- create customer
- view customer details
- update customer information
- customer search and filtering

### Account Management
- list customer accounts
- open new account
- close account
- view balance and status

### Transactions
- deposit workflow
- withdraw workflow
- transaction history
- filters by account or date

### Transfers
- transfer between accounts
- validation for sufficient balance
- status tracking

### User Management
- create/update user roles
- activate/deactivate users
- manage account permissions

## 8. State Strategy

For a bank application, a simple and effective state model is:

- Global auth state for logged-in user and role
- React Query for server data caching and refresh
- Local UI state for forms, filters, modal visibility, and table selection

This keeps data fetching predictable without overcomplicating the app.

## 9. Key Frontend Principles

- Keep pages thin and move business logic into hooks/services.
- Validate forms on both client and server sides.
- Show clear loading and error states for every API call.
- Use consistent currency formatting and date formatting.
- Protect sensitive actions behind role checks.
- Standardize backend error handling UI via toast messages or inline alerts.

## 10. Recommended Implementation Plan

1. Create a `login` page and auth guard.
2. Build a shared layout with sidebar and header.
3. Implement customer and account pages first.
4. Add transaction and transfer flows next.
5. Add user management and role-based restrictions.
6. Add dashboard analytics and reporting.

## 11. Final Architecture Summary

The frontend should be a separate React application that acts as a secure client for the existing Spring Boot banking backend. It should follow a layered architecture with route protection, central API services, role-based access control, and a clean presentation layer for banking operations. This keeps the application maintainable, scalable, and aligned with the current backend API design.
