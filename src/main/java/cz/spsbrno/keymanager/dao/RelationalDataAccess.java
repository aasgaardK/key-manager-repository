package cz.spsbrno.keymanager.dao;

import cz.spsbrno.keymanager.dto.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class RelationalDataAccess {
    private final JdbcTemplate jdbcTemplate;

    public RelationalDataAccess(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User createUser(User user)
    {
        KeyHolder holder = new GeneratedKeyHolder();
//        jdbcTemplate.update("insert into public.user(name, surname) values (?, ?)",
//                user.getName(), user.getSurname());

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("insert into public.user(id, name, surname) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getSurname());

                return ps;
            }
        }, holder);
        System.out.println(holder.getKey());

        return null;
    }
}
