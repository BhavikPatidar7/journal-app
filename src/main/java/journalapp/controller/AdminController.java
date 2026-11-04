package journalapp.controller;

import journalapp.entity.User;
import journalapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-admin")
    public ResponseEntity<User> createAdminUser(@RequestBody User user){
        return userService.createAdminUser(user);
    }

    @GetMapping("/get-users")
    public ResponseEntity<List<User>> getAllUsers(){
        return userService.getAll();
    }
}
