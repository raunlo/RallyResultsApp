package com.ralohmus.rallyresults.persistence.entities.rally;

import com.ralohmus.rallyresults.persistence.entities.AuditableDbo;
import com.ralohmus.rallyresults.persistence.entities.competitor.CompetitorPairDbo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Table(name = "stage_result")
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class StageResultDbo extends AuditableDbo<StageResultDbo> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "competitor_pair_id", referencedColumnName = "id", nullable = false)
    private CompetitorPairDbo competitor;

    @ManyToOne
    @JoinColumn(name = "stage_id", referencedColumnName = "id")
    private StageDbo stage;

    @Column(name = "result_time", length = 8)
    private String resultTime;

    @Column(name = "interrupted")
    private Boolean interrupted;

}
