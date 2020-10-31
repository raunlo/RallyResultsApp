package com.ralohmus.rallyresults.persistence.entities.competition;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.ralohmus.rallyresults.persistence.entities.AuditableDbo;

import javax.persistence.*;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Accessors(chain = true)
@Table(name = "competition")
public class CompetitionDbo extends AuditableDbo<CompetitionDbo> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "start", nullable = false, length = 7)
    private LocalDate start;

    @Column(name = "end", nullable = false, length = 7)
    private LocalDate end;

    @Column(name = "country", length = 50)
    private String country;
}
