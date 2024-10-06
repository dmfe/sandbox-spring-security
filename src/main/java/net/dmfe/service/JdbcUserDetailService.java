package net.dmfe.service;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;
import java.util.Optional;

@Service
public class JdbcUserDetailService extends MappingSqlQuery<UserDetails> implements UserDetailsService {

    public JdbcUserDetailService(DataSource ds) {
        super(ds, """
                select
                    ut.username,
                    pt.password,
                    array_agg(at.authority) as authorities
                from user_tab ut
                left join password_tab pt on pt.user_id = ut.id
                left join authority_tab at on at.user_id = ut.id
                where ut.username = :username
                group by username, password
                """
        );
        declareParameter(new SqlParameter("username", Types.VARCHAR));
        compile();
    }

    @Override
    protected UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .username(rs.getString("username"))
                .password(rs.getString("password"))
                .authorities((String[]) rs.getArray("authorities").getArray())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(findObjectByNamedParam(Map.of("username", username)))
                .orElseThrow(() -> new UsernameNotFoundException("User with name %s not found".formatted(username)));
    }

}
