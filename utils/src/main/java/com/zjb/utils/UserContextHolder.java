package com.zjb.utils;

import org.springframework.util.Assert;

/**
 * Created by zjb on 2019/12/19.
 */
public class UserContextHolder {
    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<>();

    public static final UserContext getContext() {
        UserContext context = UserContextHolder.userContext.get();
        if (context == null) {
            context = createEmptyContext();
            userContext.set(context);
        }

        return context;
    }

    private static UserContext createEmptyContext() {
        return new UserContext();
    }

    public static final void setContext(UserContext context) {
        Assert.notNull(context, "Only non-null UserContext instances are permmited.");
        userContext.set(context);
    }


}
