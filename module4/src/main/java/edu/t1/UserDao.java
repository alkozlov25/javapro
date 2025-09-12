package edu.t1;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
class UserDao {
    private final JdbcTemplate jdbc;

    public UserDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    List<User> findAll() {
        return jdbc.query("select id, username from USERS", this::mapRowToUser);
    }

    Optional<User> findById(Integer id) {
        List<User> results = jdbc.query("select id, username from USERS where id=?", this::mapRowToUser, id);
        return results.isEmpty() ?
                Optional.empty() :
                Optional.of(results.get(0));
    }

    void addUser(User newUser) {
        jdbc.update("insert into USERS (id, username) values ( ?, ? )", newUser.getId(), newUser.getUsername());
    }

    void delUserById(Integer id) {
        jdbc.update("delete from USERS where id=?", id);
    }

    private User mapRowToUser(ResultSet row, int rowNum) throws SQLException {
        return new User(row.getInt("id"), row.getString("username"));
    }
}
