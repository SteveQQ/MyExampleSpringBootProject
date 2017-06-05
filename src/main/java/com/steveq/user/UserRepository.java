package com.steveq.user;

import com.steveq.error.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Adam on 2017-05-29.
 */
@Repository
public class UserRepository {
    private final Map<String, User> userMap = new ConcurrentHashMap<>();

    public User update(String email, User user) throws EntityNotFoundException {
        if (!exists(email)){
            throw new EntityNotFoundException("Uzytkownik " + email + " nie istnieje ! ");
        }
        user.setEmail(email);
        return userMap.put(email, user);
    }

    public User save(String email, User user){
        user.setEmail(email);
        return userMap.put(email, user);
    }

    public User save(User user){
        return save(user.getEmail(), user);
    }

    public User findOne(String email) throws EntityNotFoundException{
        if(!exists(email)){
            throw new EntityNotFoundException("Uzytkownik " + email + " nie istnieje ! ");
        }
        return userMap.get(email);
    }

    public List<User> findAll(){
        return new ArrayList<>(userMap.values());
    }

    public void delete(String email) throws EntityNotFoundException{
        if(!exists(email)){
            throw new EntityNotFoundException("Uzytkownik " + email + " nie istnieje ! ");
        }
        userMap.remove(email);
    }

    public boolean exists(String email){
        return userMap.containsKey(email);
    }
}
