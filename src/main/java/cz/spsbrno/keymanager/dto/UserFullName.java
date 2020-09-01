package cz.spsbrno.keymanager.dto;

import java.util.Objects;

public class UserFullName {
    private int id;
    private String fullname;

    public UserFullName(int id, String fullname) {
        this.id = id;
        this.fullname = fullname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserFullName)) return false;
        UserFullName that = (UserFullName) o;
        return getId() == that.getId() &&
                Objects.equals(getFullname(), that.getFullname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFullname());
    }
}
