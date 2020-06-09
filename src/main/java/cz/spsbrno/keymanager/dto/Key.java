package cz.spsbrno.keymanager.dto;

import java.util.Objects;

public class Key {
    private Integer id;
    private String code;

    public Key(Integer id, String code) {
        this.id = id;
        this.code = code;
    }

    public Key() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String name) {
        this.code = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key key = (Key) o;
        return Objects.equals(getId(), key.getId()) &&
                Objects.equals(getCode(), key.getCode());

        }
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCode());
    }


}
