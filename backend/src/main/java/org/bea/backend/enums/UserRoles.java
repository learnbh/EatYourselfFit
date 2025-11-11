package org.bea.backend.enums;

public enum UserRoles {
    USER,
    ADMIN;

    public static boolean isValidRole(String role) {
        for (UserRoles userRole : UserRoles.values()) {
            if (userRole.name().equalsIgnoreCase(role)) {
                return true;
            }
        }
        return false;
    }
}
