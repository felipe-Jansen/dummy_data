package com.lifescan.dummy.data.service.Util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

public class Util {

    private Util() {}

    public static String generateRequestToken(String emailAddress) {
        return DigestUtils.sha1Hex(
                DigestUtils.sha1Hex(emailAddress).concat(emailAddress)
        );
    }

    public static boolean isRequestTokenValid(String requestToken, String email)
            throws UnsupportedEncodingException {

        String emailDecoded = URLDecoder.decode(email, StandardCharsets.UTF_8.name());
        String tokenChallenge = DigestUtils.sha1Hex(DigestUtils.sha1Hex(emailDecoded) + emailDecoded);

        return tokenChallenge.equals(requestToken);
    }

    public static String extractCountryFromLanguage(String language) {
        String country = null;
        try {
           country = language.split("-")[1];
        } catch (ArrayIndexOutOfBoundsException ex) {
            // Do nothing for a while
        } try {
            country = language.split("_")[1];
        } catch (ArrayIndexOutOfBoundsException ex) {
            // Do nothing for a while
        }
        return country;
    }

    public static String generateDateOfBirth() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int date = Calendar.getInstance().get(Calendar.DATE);
        return String.valueOf(year-20)
                        .concat(month < 10 ? "0".concat(String.valueOf(month)) : String.valueOf(month))
                        .concat(date < 10 ? "0".concat(String.valueOf(date)) : String.valueOf(date));
    }
}
