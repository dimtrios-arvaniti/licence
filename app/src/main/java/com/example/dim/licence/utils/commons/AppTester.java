package com.example.dim.licence.utils.commons;

import android.util.Log;

import static com.example.dim.licence.utils.commons.Commons.MAIL_REGEX;
import static com.example.dim.licence.utils.commons.Commons.TEL_REGEX;

public class AppTester {

    private static final String ARG_TEST = "ARG_TEST";

    public void testAppValidator() {
        String validMail1 = "dmail@pmail.com";
        String validMail2 = "rmail.mail@yahuo.tv";
        String validMail3 = "ato_gl12pz@enteupr.org";

        String invalidMail1 = "@pmail.com";
        String invalidMail2 = "rmail.mail@.tv";
        String invalidMail3 = "ato_gl12pz@enteupr";
        String invalidMail4 = "ato_gl12pz@enteupr.com@gmaol.fer";
        String invalidMail5 = "@pmail.c";

        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("AppTester: Test 1 : ").append("\n")
                .append("Valide email 1 :").append(validMail1).append("\n")
                .append("test 1 result : ").append(validMail1.matches(MAIL_REGEX)).append("\n\n");

        resultBuilder.append("AppTester: Test 2 : ").append("\n")
                .append("Valide email 2 :").append(validMail2).append("\n")
                .append("test 2 result : ").append(validMail2.matches(MAIL_REGEX)).append("\n\n");


        resultBuilder.append("AppTester: Test 3 : ").append("\n")
                .append("Valide email 3 :").append(validMail3).append("\n")
                .append("test 3 result : ").append(validMail3.matches(MAIL_REGEX)).append("\n\n");


        resultBuilder.append("AppTester: Test 4 : ").append("\n")
                .append("Invalide email 1 :").append(invalidMail1).append("\n")
                .append("test 4 result : ").append(invalidMail1.matches(MAIL_REGEX)).append("\n\n");

        resultBuilder.append("AppTester: Test 5 : ").append("\n")
                .append("Invalide email 2 :").append(invalidMail2).append("\n")
                .append("test 5 result : ").append(invalidMail2.matches(MAIL_REGEX)).append("\n\n");

        resultBuilder.append("AppTester: Test 6 : ").append("\n")
                .append("Invalide email 3 :").append(invalidMail3).append("\n")
                .append("test 6 result : ").append(invalidMail3.matches(MAIL_REGEX)).append("\n\n");

        resultBuilder.append("AppTester: Test 7 : ").append("\n")
                .append("Invalide email 4 :").append(invalidMail4).append("\n")
                .append("test 7 result : ").append(invalidMail4.matches(MAIL_REGEX)).append("\n\n");

        resultBuilder.append("AppTester: Test 8 : ").append("\n")
                .append("Invalide email 5 :").append(invalidMail5).append("\n")
                .append("test 8 result : ").append(invalidMail5.matches(MAIL_REGEX)).append("\n\n");

        resultBuilder.append("AppTester: Test 9 : ").append("\n")
                .append("valide tel 1 :").append("0101010101").append("\n")
                .append("test 9 result : ").append("0101010101".matches(TEL_REGEX)).append("\n\n");

        resultBuilder.append("AppTester: Test 10 : ").append("\n")
                .append("valide tel 2 :").append("+330606060606").append("\n")
                .append("test 10 result : ").append("+330606060606".matches(TEL_REGEX)).append("\n\n");

        resultBuilder.append("AppTester: Test 11 : ").append("\n")
                .append("Invalide tel 1 :").append("123456789101112131415").append("\n")
                .append("test 11 result : ").append("123456789101112131415".matches(TEL_REGEX)).append("\n\n");


        resultBuilder.append("AppTester: Test 12 : ").append("\n")
                .append("Invalide tel 2 :").append("12aker456").append("\n")
                .append("test 12 result : ").append("12aker456".matches(TEL_REGEX)).append("\n\n");

        Log.i(ARG_TEST, "AppTester: \n"+resultBuilder.toString());
    }
}
