package com.example.offlinedatabaseapi;

public class Constants {

    public static final class HTTP {
        public static final String BASE_URL = "http://localhost/project/coustomer.php";
    }

    public static final class DATABASE {

        public static final String DB_NAME = "coustomerDB";
        public static final int DB_VERSION = 1;
        public static final String TABLE_NAME = "coustomerInfo";

        public static final String DROP_QUERY = "DROP TABLE IF EXIST " + TABLE_NAME;

        public static final String GET_FLOWERS_QUERY = "SELECT * FROM " + TABLE_NAME;

        public static final String COUSTOMER_ID = "coustomerId";
        public static final String NAME = "name";
        public static final String MOBILE = "mobile";



        public static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "" +
                "(" + COUSTOMER_ID + " INTEGER PRIMARY KEY not null," +

                NAME + " TEXT not null," +
                MOBILE + " TEXT not null," + " TEXT not null)";
    }

    public static final class REFERENCE {
        public static final String COUSTOMER = Config.PACKAGE_NAME + "coustomer";
    }

    public static final class Config {
        public static final String PACKAGE_NAME = "com.example.offlinedatabaseapi";
    }
}
