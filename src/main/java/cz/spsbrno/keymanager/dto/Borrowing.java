package cz.spsbrno.keymanager.dto;

import java.sql.Date;
import java.util.Objects;

public class Borrowing {
    private Key key;
    private User user;
    private Date dateFrom;
    private Date dateTo;

    public Borrowing(Key key, User user, Date dateFrom, Date dateTo) {
        this.key = key;
        this.user = user;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Borrowing() {};

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Borrowing)) return false;
        Borrowing borrowing = (Borrowing) o;
        return Objects.equals(getKey(), borrowing.getKey()) &&
                Objects.equals(getUser(), borrowing.getUser()) &&
                Objects.equals(getDateTo(), borrowing.getDateTo()) &&
                Objects.equals(getDateFrom(), borrowing.getDateFrom());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getUser(), getDateTo(), getDateFrom());
    }

}
