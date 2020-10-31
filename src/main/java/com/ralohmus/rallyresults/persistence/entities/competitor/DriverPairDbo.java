package com.ralohmus.rallyresults.persistence.entities.competitor;

import com.ralohmus.rallyresults.persistence.entities.AuditableDbo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "driver_pair")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DriverPairDbo extends AuditableDbo<DriverPairDbo> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "start", nullable = false, length = 5)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate start;

    @Column(name = "end", nullable = false, length = 5)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate end;

    @Enumerated(EnumType.STRING)
    private CompetitionClassDbo competitionClass;

    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private CompetitorDbo driver;

    @ManyToOne
    @JoinColumn(name = "co_driver_id", referencedColumnName = "id")
    private CompetitorDbo coDriver;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private TeamDbo team;
}
