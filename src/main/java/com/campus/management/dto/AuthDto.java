package com.campus.management.dto;

public class AuthDto {

    public static class LoginRequest {
        private String username;
        private String password;

        public LoginRequest() {}
        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class LoginResponse {
        private String token;
        private Long id;
        private String username;
        private String email;
        private String fullName;
        private String role;
        private String department;

        public LoginResponse() {}
        public LoginResponse(String token, Long id, String username, String email, String fullName, String role, String department) {
            this.token = token;
            this.id = id;
            this.username = username;
            this.email = email;
            this.fullName = fullName;
            this.role = role;
            this.department = department;
        }

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }

        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }
    }
}
