package com.base.nguyencse.java;

public class Settings {
    private static final String DCM_HOST_PRO = "www.base.com";
    private static final String DCM_HOST_STG = "stg.base.com";
    private static final String DCM_HOST_DEV = "dev.base.com";

    private static final String DCM_API_PATH_PRO = "/api/v1/";
    private static final String DCM_API_PATH_STG = "/api/v1/stg";
    private static final String DCM_API_PATH_DEV = "/api/v1/dev/";

    private static final String DCM_API_KEY_PRO = "base";
    private static final String DCM_API_KEY_STG = "stg-base";
    private static final String DCM_API_KEY_DEV = "dev-base";

    private static final String API_HOST;
    private static final String API_PATH;
    public static final String API_KEY;

    static {
        switch (BuildConfig.FLAVOR.toLowerCase()) {
            case "staging":
                API_HOST = DCM_HOST_STG;
                API_PATH = DCM_API_PATH_STG;
                API_KEY = DCM_API_KEY_STG;
                break;
            case "production":
                API_HOST = DCM_HOST_PRO;
                API_PATH = DCM_API_PATH_PRO;
                API_KEY = DCM_API_KEY_PRO;
                break;
            default:
                API_HOST = DCM_HOST_DEV;
                API_PATH = DCM_API_PATH_DEV;
                API_KEY = DCM_API_KEY_DEV;
                break;
        }
    }

    public static final String API_URL = "https://" + API_HOST + API_PATH;
}
