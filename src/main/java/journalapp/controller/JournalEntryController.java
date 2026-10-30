package journalapp.controller;


import journalapp.entity.JournalEntry;
import journalapp.services.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public ResponseEntity<?> getById(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
//        ResponseEntity<JournalEntry> journalEntry =  journalEntryService.findById(id, user);
        return journalEntryService.findById(id, user);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return journalEntryService.deleteEntry(id, name);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<JournalEntry> updateEntry(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        return journalEntryService.updateEntry(id, newEntry);
    }
}
