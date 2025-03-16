package edu.simbie.rechecdi.works;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Works {
    private @Id @GeneratedValue Long id;
    private String name;
}
