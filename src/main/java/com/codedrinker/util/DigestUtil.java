package com.codedrinker.util;

import com.codedrinker.error.CommonErrorCode;
import com.codedrinker.error.ErrorCodeException;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by codedrinker on 2018/11/25.
 */
public class DigestUtil {
    public static void checkDigest(String rawData, String sessionKey, String signature) {
        try {
            String sha1 = DigestUtils.sha1Hex((rawData + sessionKey).getBytes("UTF-8"));
            boolean equals = sha1.equals(signature);
            if (!equals) {
                throw new ErrorCodeException(CommonErrorCode.SIGNATURE_ERROR);
            }
        } catch (UnsupportedEncodingException e) {
            throw new ErrorCodeException(CommonErrorCode.SIGNATURE_ERROR);
        }
    }
}
