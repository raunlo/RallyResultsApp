package com.ralohmus.rallyresults.persistence.entities.rally;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.ralohmus.rallyresults.persistence.entities.AuditableDbo;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Accessors(chain = true)
@Table(name = "rally")
public class RallyDbo extends AuditableDbo<RallyDbo> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "start_date", nullable = false, length = 7)
    private LocalDate start;

    @Column(name = "end_date", nullable = false, length = 7)
    private LocalDate end;

    @Column(name = "country", length = 50)
    private String country;

    @OneToMany(mappedBy = "rally", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<StageDbo> stages = new ArrayList<>();
}
