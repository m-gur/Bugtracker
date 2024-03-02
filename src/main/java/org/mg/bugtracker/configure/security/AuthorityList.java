package org.mg.bugtracker.configure.security;

public class AuthorityList {
    private static final String ADMIN = "ADMIN";
    private static final String TECHNICIAN = "TECHNICIAN";
    private static final String USER = "USER";
    protected static String[] anyAuthority = {ADMIN, TECHNICIAN, USER};
    protected static String[] extendedAuthority = {ADMIN, TECHNICIAN};
    protected static String adminAuthority = ADMIN;
}
