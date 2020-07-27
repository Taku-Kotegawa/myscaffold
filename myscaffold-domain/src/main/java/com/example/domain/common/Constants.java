package com.example.domain.common;

public final class Constants {

    public static final class ROLE {
        public static final String ADMIN = "ADMIN";
        public static final String USER = "USER";
    }

    public static final class STATUS {
        public static final Integer DRAFT = 0;
        public static final Integer PUBLISHED = 1;
        public static final Integer INVALID = 2;
    }

    public static final class OPERATION {
        public static final String CREATE = "create";
        public static final String UPDATE = "update";
        public static final String DELETE = "delete";
        public static final String FIND = "find";
    }

}
