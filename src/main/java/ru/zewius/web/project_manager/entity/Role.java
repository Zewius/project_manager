package ru.zewius.web.project_manager.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    MANAGER, TECHNICIAN, ADMIN;

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
