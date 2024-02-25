package com.google.cms.models;

import com.google.cms.utilities.Shared.Audittrails;
import lombok.*;

import javax.persistence.*;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "roles")
public class Role extends Audittrails {
    @Column(length = 250, nullable = false, unique = true)
    private String name;
}