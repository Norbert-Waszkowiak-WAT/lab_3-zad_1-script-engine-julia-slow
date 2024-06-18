package pl.edu.wat.knowledge.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
public class Author {
    @MongoId
    private String id;

    private String surname;
    private String name;
    private Integer score;

    @DBRef
    private Affiliation affiliation;
}
