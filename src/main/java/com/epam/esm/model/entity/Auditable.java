package com.epam.esm.model.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
public abstract class Auditable<U> {

    @CreatedBy
    protected U CreatedBy;

    @CreatedDate
    @Column(name = "create_date_audit", updatable = false)
    protected Timestamp createDateTime;

    @LastModifiedBy
    protected U lastModifiedBy;

    @LastModifiedDate
    @Column(name = "update_date_audit")
    protected Timestamp lastModifiedDateTime;
}
