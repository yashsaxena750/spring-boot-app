package com.example.demo.util.constants;

public enum Privileges {
    RESET_ANY_USER_PASSWORD(1l, "RESET_ANY_USER_PASSWORD"),
    ACCESS_ADMIN_PANEL(2l, "ACCESS_ADMIN_PANEL");

    private Long Id;
    private String privilege;
    private Privileges(Long id, String authority) {
        this.Id = id;
        this.privilege = authority;
    }
    public Long getId() {
        return Id;
    }
    public String getPrivilege() {
        return privilege;
    }
}
