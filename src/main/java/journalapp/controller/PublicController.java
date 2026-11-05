package journalapp.controller;

import journalapp.entity.User;

import journalapp.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public")
@Slf4j
public class PublicController {
    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String checkHealth(){
        log.trace("Trace");
        log.debug("debugging");
        log.info("info log");
        log.warn("warning");
        log.error("error");
        return "OK";
    }

    @PostMapping("/create")
    public ResponseEntity<User> creat(@RequestBody User user) {
        return userService.create(user);
    }

}
