package cz.spsbrno.keymanager.dto;

import java.util.Objects;

public class BorrowingCreation {
    private String userId;
    private String keyId;

    public BorrowingCreation(String userId, String keyId) {
        this.userId = userId;
        this.keyId = keyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BorrowingCreation)) return false;
        BorrowingCreation that = (BorrowingCreation) o;
        return Objects.equals(getUserId(), that.getUserId()) &&
                Objects.equals(getKeyId(), that.getKeyId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getKeyId());
    }
}
