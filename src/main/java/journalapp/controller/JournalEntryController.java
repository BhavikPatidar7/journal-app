package journalapp.controller;


import journalapp.entity.JournalEntry;
import journalapp.reposetory.UserRepo;
import journalapp.services.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("journal")
public class JournalEntryController {

    @Autowired
    public JournalEntryService journalEntryService;


    @GetMapping("/getAll")
    public ResponseEntity<List<JournalEntry>> getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return  journalEntryService.getAll(name);
    }

    @PostMapping("/create")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return journalEntryService.createEntry(entry, name);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId id) {
        Optional<JournalEntry> journalEntry =  journalEntryService.findById(id);
        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("id/{id}/{name}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId id, @PathVariable String name) {
        return journalEntryService.deleteEntry(id, name);
//        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<JournalEntry> updateEntry(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        return journalEntryService.updateEntry(id, newEntry);
    }
}
