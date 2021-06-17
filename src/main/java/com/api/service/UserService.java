package com.api.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.dao.IDAO;
import com.api.model.dto.user.SearchUserDTO;
import com.api.model.entity.User;
import com.api.utils.predicate.filter.QueryFilter;

/**
 * User service.
 */
@Service
@Transactional
public class UserService {

    /**
     * User DAO.
     */
    @Autowired
    private IDAO<User, QueryFilter> userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void updateDefaultAdminitratorPassword(final String password) {
        SearchUserDTO filter = new SearchUserDTO();
        filter.setLogin("admin");
        Optional<User> opt = userDAO.loadWithLock(filter);
        if (!opt.isPresent()) {

        }
        User user = opt.get();

        user.setPassword(passwordEncoder.encode(password));

        userDAO.save(user);
    }

    public boolean isDefaultAdminitratorAllowed(final String password) {
        SearchUserDTO filter = new SearchUserDTO();
        filter.setLogin("admin");
        Optional<User> opt = userDAO.load(filter);
        if (!opt.isPresent()) {
            return false;
        }
        User user = opt.get();

        return user.isActive() && passwordEncoder.matches(password, user.getPassword());
    }

    public void setDefaultAdministratorState(final boolean state) {

    }

    public boolean isSecureAdministrator() {
        SearchUserDTO filter = new SearchUserDTO();
        filter.setLogin("admin");
        Optional<User> opt = userDAO.load(filter);
        if (!opt.isPresent()) {
            return true;
        }
        User user = opt.get();

        return !user.isActive() || !passwordEncoder.matches("admin", user.getPassword());
    }
}
