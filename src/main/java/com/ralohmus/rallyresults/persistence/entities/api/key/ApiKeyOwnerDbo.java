package com.ralohmus.rallyresults.persistence.entities.api.key;

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

@Data
@Entity
@Table
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ApiKeyOwnerDbo extends AuditableDbo<ApiKeyOwnerDbo> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name= "email", nullable = false, length = 70)
    private String email;

    @Column(name = "tel_number", nullable = false, length = 20)
    private String telNumber;
}
