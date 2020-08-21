package cz.spsbrno.keymanager.dto;

import java.util.Objects;

public class Door {
        private Integer id;
        private String code;

        public Door(Integer id, String code) {
            this.id = id;
            this.code = code;
        }

        public Door() { }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof cz.spsbrno.keymanager.dto.Key)) return false;
            cz.spsbrno.keymanager.dto.Key key = (cz.spsbrno.keymanager.dto.Key) o;
            return Objects.equals(getId(), key.getId()) &&
                    Objects.equals(getCode(), key.getCode());

        }
        @Override
        public int hashCode() {
            return Objects.hash(getId(), getCode());
        }


    }