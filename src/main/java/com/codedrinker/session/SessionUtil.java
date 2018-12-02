package com.codedrinker.session;

import com.codedrinker.model.User;

/**
 * Created by codedrinker on 2018/12/2.
 */
public class SessionUtil {
    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public static void setUser(User user) {
        userThreadLocal.set(user);
    }

    public static User getUser() {
        return userThreadLocal.get();
    }

    public static void removeUser() {
        userThreadLocal.remove();
    }
}
