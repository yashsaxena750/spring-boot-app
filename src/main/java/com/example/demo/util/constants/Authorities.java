package com.example.demo.util.constants;

public enum Authorities {
    RESET_ANY_USER_PASSWORD(1l, "RESET_ANY_USER_PASSWORD"),
    ACCESS_ADMIN_PANEL(2l, "ACCESS_ADMIN_PANEL");

    private Long id;
    private String authority;
    private Authorities(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }
    public Long getId() {
        return id;
    }
    public String getAuthority() {
        return authority;
    }
}
