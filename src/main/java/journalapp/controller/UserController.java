package journalapp.controller;

import journalapp.entity.User;
import journalapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    public UserService userService;


    @PutMapping("update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.updateUser(user, authentication.getName());
    }

    @DeleteMapping("delete")
    public  ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.deleteUser(authentication.getName());
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getProfile(authentication.getName());
    }
}
