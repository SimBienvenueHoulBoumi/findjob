package edu.simbie.rechecdi.works;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.simbie.rechecdi.utils.exceptions.WorkNotFoundException;
import edu.simbie.rechecdi.utils.exceptions.WorkCreationException;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/works")
public class WorksController {
    private static final Logger logger = LoggerFactory.getLogger(WorksController.class);
    private final WorksRepository repository;

    public WorksController(WorksRepository repository) {
        this.repository = repository;
        logger.info("✅ WorksController loaded!");
    }

    @GetMapping("/all")
    @Operation(summary = "Get all saved works")
    public ResponseEntity<List<Works>> getAllWorks() {
        logger.info("📢 Retrieving all works");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a work by ID")
    public Works getWorkById(@PathVariable Long id) {
        logger.info("🔍 Searching for work with ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new WorkNotFoundException(id));
    }

    @PostMapping("/create")
    @Operation(summary = "Create a work entry")
    public ResponseEntity<Works> createWork(@RequestBody WorksDto worksDTO) {

        if (worksDTO.name() == null || worksDTO.name().isBlank()) {
            throw new WorkCreationException("Work name cannot be empty.");
        }

        Works work = new Works();
        work.setName(worksDTO.name());
        Works savedWork = repository.save(work);
        
        logger.info("✅ Work created with ID: {}", savedWork.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWork);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a work entry")
    public Works updateWork(@RequestBody WorksDto worksDTO, @PathVariable Long id) {
        
        Works work = repository.findById(id)
        .orElseThrow(() -> new WorkNotFoundException(id));
        
        work.setName(worksDTO.name());
        logger.info("🔄 Updating work with ID: {}", id);
        return repository.save(work);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a work entry")
    public ResponseEntity<Void> deleteWork(@PathVariable Long id) {
        Works work = repository.findById(id)
                .orElseThrow(() -> new WorkNotFoundException(id));

        repository.delete(work);
        logger.info("✅ Work with ID {} deleted", id);
        return ResponseEntity.noContent().build();
    }
}
