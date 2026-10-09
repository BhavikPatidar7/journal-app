package journalapp.controller;

import journalapp.entity.User;
import journalapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    public UserService userService;

    @PostMapping("/create")
    public ResponseEntity<User> creat(@RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping("getAll")
    public ResponseEntity<List<User>> getUser() {
        List<User> users =  userService.getAll();
        if(!users.isEmpty()){
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("update/name/{name}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable String name) {
        return userService.updateUser(user, name);
    }
}
