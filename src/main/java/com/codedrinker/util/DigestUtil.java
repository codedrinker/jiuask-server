package com.codedrinker.util;

import com.codedrinker.error.CommonErrorCode;
import com.codedrinker.error.ErrorCodeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by codedrinker on 2018/11/25.
 */
@Slf4j
public class DigestUtil {
    public static void checkDigest(String rawData, String sessionKey, String signature) {
        log.info("rawData:{},sessionKey:{},signature:{}", rawData, sessionKey, signature);
        // 调用 apache 的公共包进行 SHA1 加密处理
        String sha1 = DigestUtils.sha1Hex((rawData + sessionKey).getBytes());
        boolean equals = sha1.equals(signature);
        if (!equals) {
            throw new ErrorCodeException(CommonErrorCode.SIGNATURE_ERROR);
        }
    }
}
