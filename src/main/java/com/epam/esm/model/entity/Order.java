package com.epam.esm.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@EqualsAndHashCode(exclude = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gift_order", schema = "module_4")
public class Order extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinTable(
        name = "order_has_gift_certificate", schema = "module_4",
        joinColumns = @JoinColumn(name = "gift_order_id"),
        inverseJoinColumns = @JoinColumn(name = "gift_certificate_id"))
    private List<Certificate> certificates;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal cost;

    @Column(name = "order_date")
    private ZonedDateTime orderTime;

    @PrePersist
    public void onPersist() {
        setOrderTime(ZonedDateTime.now(ZoneId.systemDefault()));
    }
}
