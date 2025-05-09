package org.sifanur.library.libraryapp;

public class UserSession {

    private static String currentUserTC;

    public static String getCurrentUserTC() {
        return currentUserTC;
    }
    public static void setCurrentUserTC(String currentUserTC) {
        UserSession.currentUserTC = currentUserTC;
    }

}
