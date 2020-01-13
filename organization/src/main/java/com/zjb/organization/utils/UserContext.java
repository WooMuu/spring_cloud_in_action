package com.zjb.organization.utils;

/**
 * Created by zjb on 2019/12/19.
 */
public class UserContext {
    public static final String CORRELATION_ID = "zjb-correlation-id";
    public static final String AUTH_TOKEN = "Authorization";
    public static final String USER_ID = "zjb-user-id";
    public static final String ORG_ID = "zjb-org-id";


    private static final ThreadLocal<String> correlationId = new ThreadLocal<String>();
    private static final ThreadLocal<String> authToken = new ThreadLocal<String>();
    private static final ThreadLocal<String> userId = new ThreadLocal<String>();
    private static final ThreadLocal<String> orgId = new ThreadLocal<String>();

    public static String getCorrelationId() {
        return correlationId.get();
    }

    public static void setCorrelationId(String cid) {
        correlationId.set(cid);
    }

    public static String getAuthToken() {
        return authToken.get();
    }

    public static void setAuthToken(String aToken) {
        authToken.set(aToken);
    }

    public static String getUserId() {
        return userId.get();
    }

    public static void setUserId(String aUser) {
        userId.set(aUser);
    }

    public static String getOrgId() {
        return orgId.get();
    }

    public static void setOrgId(String aOrg) {
        orgId.set(aOrg);
    }

    public Builder builder() {
        return new Builder(this);
    }

    static class Builder {
        private UserContext userContext;

        public Builder(UserContext userContext) {
            this.userContext = userContext;
        }

        public Builder setCorrelationId(String correlationId) {
            userContext.setCorrelationId(correlationId);
            return this;
        }

        public Builder setAuthToken(String authToken) {
            userContext.setAuthToken(authToken);
            return this;
        }

        public Builder setUserId(String userId) {
            userContext.setUserId(userId);
            return this;
        }

        public Builder setOrgId(String orgId) {
            userContext.setOrgId(orgId);
            return this;
        }
    }
}
