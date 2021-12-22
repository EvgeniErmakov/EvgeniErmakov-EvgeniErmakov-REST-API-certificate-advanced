package com.epam.esm.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(exclude = "orders")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "clientele")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 300, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;
}
