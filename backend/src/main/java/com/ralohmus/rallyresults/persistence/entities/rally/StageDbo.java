package com.ralohmus.rallyresults.persistence.entities.rally;

import com.ralohmus.rallyresults.persistence.entities.AuditableDbo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "rally_stage")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class StageDbo  extends AuditableDbo<StageDbo> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "track_name", length = 50, nullable = false)
    private String trackName;

    @Column(name = "stage_number", nullable = false)
    private Short stageNumber;

    @ManyToOne
    @JoinColumn(name = "rally_id", referencedColumnName = "id")
    private RallyDbo rally;

    @Column(name = "stage_length", precision = 2, nullable = false)
    private BigDecimal length;

    @OneToMany(mappedBy = "stage", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<StageResultDbo> stageResults;
}
