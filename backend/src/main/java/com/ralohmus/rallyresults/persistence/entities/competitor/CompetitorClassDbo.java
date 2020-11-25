package com.ralohmus.rallyresults.persistence.entities.competitor;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Table(name = "competition_class")
@Entity
@Data
@Accessors(chain = true)
public class CompetitorClassDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "value", length = 50)
    private String value;
}
