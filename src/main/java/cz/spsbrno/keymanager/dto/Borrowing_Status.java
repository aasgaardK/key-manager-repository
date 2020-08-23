package cz.spsbrno.keymanager.dto;

import java.util.Date;
import java.util.Objects;

public class Borrowing_Status {
    private Integer borrowing_status_id;
    private Integer user_user_id;
    private Integer key_key_id;
    private Date date_from;
    private Date date_to;

    public Borrowing_Status(Integer borrowing_status_id, Integer user_user_id,
                            Integer key_key_id, Date date_from, Date date_to) {
        this.borrowing_status_id = borrowing_status_id;
        this.user_user_id = user_user_id;
        this.key_key_id = key_key_id;
        this.date_from = date_from;
        this.date_to = date_to;
    }

    public Borrowing_Status() {
    }

//    public static String getCode() {
//    }

    public Integer getBorrowing_status_id() {
        return borrowing_status_id;
    }

    public void setBorrowing_status_id(Integer borrowing_status_id) {
        this.borrowing_status_id = borrowing_status_id;
    }

    public Integer getUser_user_id() {
        return user_user_id;
    }

    public void setUser_user_id(Integer user_user_id) {
        this.user_user_id = user_user_id;
    }

    public Integer getKey_key_id() {
        return key_key_id;
    }

    public void setKey_key_id(Integer key_key_id) {
        this.key_key_id = key_key_id;
    }

    public Date getDate_from() {
        return date_from;
    }

    public void setDate_from(Date date_from) {
        this.date_from = date_from;
    }

    public Date getDate_to() {
        return date_to;
    }

    public void setDate_to(Date date_to) {
        this.date_to = date_to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Borrowing_Status)) return false;
        Borrowing_Status that = (Borrowing_Status) o;
        return getBorrowing_status_id().equals(that.getBorrowing_status_id()) &&
                getUser_user_id().equals(that.getUser_user_id()) &&
                getKey_key_id().equals(that.getKey_key_id()) &&
                getDate_from().equals(that.getDate_from()) &&
                getDate_to().equals(that.getDate_to());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBorrowing_status_id(), getUser_user_id(), getKey_key_id(), getDate_from(), getDate_to());
    }
}
