package com.ralohmus.rallyresults.persistence.entities.api.key;

import com.ralohmus.rallyresults.persistence.entities.AuditableDbo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Table
@Entity
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ApiKeyDbo  extends AuditableDbo<ApiKeyDbo> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "value", nullable = false, length = 100)
    private String value;

    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
    private ApiKeyOwnerDbo apiKeyOwner;

    @Column(name = "valid", nullable = false)
    private Boolean valid;

}
