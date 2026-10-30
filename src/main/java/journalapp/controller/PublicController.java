package journalapp.controller;

import journalapp.entity.User;

import journalapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public")
public class PublicController {
    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String checkHealth(){
        return "OK";
    }

    @PostMapping("/create")
    public ResponseEntity<User> creat(@RequestBody User user) {
        return userService.create(user);
    }

}
