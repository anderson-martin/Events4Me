package com.metropolia.events4me.DAO;

import com.metropolia.events4me.Model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDAO extends CrudRepository<User, Integer> {

    User findByEmail(String email);
//?? why tihs one doesnt work properly?
    User findByUsername(String username);

    List<User> findAll();



}
