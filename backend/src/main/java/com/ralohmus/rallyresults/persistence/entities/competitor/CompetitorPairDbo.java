package com.ralohmus.rallyresults.persistence.entities.competitor;

import com.ralohmus.rallyresults.persistence.entities.AuditableDbo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "competitor_pair")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CompetitorPairDbo extends AuditableDbo<CompetitorPairDbo> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "competition_class_id", referencedColumnName = "id", nullable = false)
    private CompetitorClassDbo competitionClass;

    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id", nullable = false)
    private CompetitorDbo driver;

    @ManyToOne
    @JoinColumn(name = "co_driver_id", referencedColumnName = "id", nullable = false)
    private CompetitorDbo coDriver;
}
