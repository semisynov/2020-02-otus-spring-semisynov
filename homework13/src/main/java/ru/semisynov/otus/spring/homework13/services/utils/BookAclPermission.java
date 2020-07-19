package ru.semisynov.otus.spring.homework13.services.utils;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;

public class BookAclPermission extends BasePermission {
    public static final Permission ALL_OPERATIONS = new BookAclPermission(15);
    public static final Permission READ_WRITE = new BookAclPermission(5);

    protected BookAclPermission(int mask) {
        super(mask);
    }
}
