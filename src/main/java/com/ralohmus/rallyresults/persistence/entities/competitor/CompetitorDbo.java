package com.ralohmus.rallyresults.persistence.entities.competitor;

import com.ralohmus.rallyresults.persistence.entities.AuditableDbo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Table(name = "competitors")
@Entity
@Data
@Accessors(chain = true)
public class CompetitorDbo extends AuditableDbo<CompetitorDbo> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
}
