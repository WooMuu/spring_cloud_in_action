package com.zjb.specialroutes.utils;

/**
 * Created by zjb on 2019/12/19.
 */
public class UserContext {
    public static final String CORRELATION_ID = "zjb-correlation-id";
    public static final String AUTH_TOKEN = "zjb-auth-token";
    public static final String USER_ID = "zjb-user-id";
    public static final String ORG_ID = "zjb-org-id";


    private String correlationId = new String();
    private String authToken = new String();
    private String userId = new String();
    private String orgId = new String();


    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
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
