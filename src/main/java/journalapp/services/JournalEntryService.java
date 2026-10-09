package journalapp.services;

import journalapp.entity.JournalEntry;
import journalapp.entity.User;
import journalapp.reposetory.JournalEntryRepo;
import journalapp.reposetory.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    public JournalEntryRepo journalEntryRepo;
    @Autowired
    public UserRepo userRepo;

    public ResponseEntity<JournalEntry> createEntry(JournalEntry entry, String user) {
        try {
            User userDetail = userRepo.findByUserName(user);
            entry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepo.save(entry);
            userDetail.getJournalEntries().add(saved);
            userRepo.save(userDetail);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<JournalEntry>> getAll(String name) {
        List<JournalEntry> entries = userRepo.findByUserName(name).getJournalEntries();
        if(!entries.isEmpty()){
            return new ResponseEntity<>(entries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepo.findById(id);
    }

    public ResponseEntity<?> deleteEntry(ObjectId id, String name) {
        User user = userRepo.findByUserName(name);
        user.getJournalEntries().removeIf(entry -> entry.getId().equals(id));
        userRepo.save(user);
        journalEntryRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<JournalEntry> updateEntry(ObjectId id, JournalEntry newEntry) {
        JournalEntry old = journalEntryRepo.findById(id).orElse(null);
        if (old != null) {
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
            journalEntryRepo.save(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
