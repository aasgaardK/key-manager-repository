package cz.spsbrno.keymanager.dto;

import java.util.Objects;

public class Key {
    private Integer id;
    private String number;
    private boolean borrowed; //otázka jestli vůbec je vhodné, aby takový param byl? Nebude to duplicitní vzhledem k tab borrowing?

    public Key(Integer id, String number, boolean borrowed) {
        this.id = id;
        this.number = number;
        this.borrowed = borrowed;
    }

    public Key() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String name) {
        this.number = name;
    }

    public  Boolean getBorrowed() { return borrowed; }

    public void setBorrowed(boolean borrowed) { this.borrowed = borrowed; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key key = (Key) o;
        return Objects.equals(getId(), key.getId()) &&
                Objects.equals(getNumber(), key.getNumber()) &&
                Objects.equals(getBorrowed(), key.getBorrowed());

        }
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumber(), getBorrowed());
    }


}
