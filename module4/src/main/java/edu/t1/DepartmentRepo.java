package edu.t1;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class DepartmentRepo {
    private final JdbcTemplate jdbc;

    public DepartmentRepo(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    List<String> findAll() {
        return jdbc.queryForList("SELECT name FROM Department", String.class);
    }
}
