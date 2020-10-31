package com.ralohmus.rallyresults.persistence.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class AuditableDbo<T> {

    @Column(name = "modified_by", nullable = false)
    @LastModifiedBy
    private String modifiedBy;

    @Column(name = "modified_date", nullable = false)
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate modifiedDate;

    @Column(name = "created_by", nullable = false)
    @CreatedBy
    private String createdBy;


    @Column(name = "created_date", nullable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate createdDate;

    @SuppressWarnings("unchecked")
    public T setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return (T) this;
    }

}
