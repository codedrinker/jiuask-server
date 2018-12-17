package com.codedrinker.session;

import com.codedrinker.dto.UserDTO;

/**
 * Created by codedrinker on 2018/12/2.
 */
public class SessionUtil {
    private static ThreadLocal<UserDTO> userThreadLocal = new ThreadLocal<>();

    public static void setUser(UserDTO user) {
        userThreadLocal.set(user);
    }

    public static UserDTO getUser() {
        return userThreadLocal.get();
    }

    public static void removeUser() {
        userThreadLocal.remove();
    }
}
