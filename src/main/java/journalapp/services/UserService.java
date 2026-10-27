package journalapp.services;

import journalapp.entity.User;
import journalapp.reposetory.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.Arrays;
import java.util.List;

@Component
public class UserService {

    @Autowired
    public UserRepo userRepo;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public ResponseEntity<User> create(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("User"));
            userRepo.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<User> updateUser(User user, String userName) {
        User oldUser = userRepo.findByUserName(userName);
        if (oldUser != null) {
            oldUser.setUserName(!user.getUserName().isEmpty() ? user.getUserName() : oldUser.getUserName());
            oldUser.setPassword(!user.getPassword().isEmpty() ? passwordEncoder.encode(user.getPassword()) : oldUser.getPassword());
            userRepo.save(oldUser);
            return new ResponseEntity<>(oldUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> deleteUser(String userName){
        return new ResponseEntity<>(userRepo.deleteByUserName(userName), HttpStatus.NO_CONTENT);
    }

}
