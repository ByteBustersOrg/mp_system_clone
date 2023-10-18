package org.vyatsu.localApiModule.entity.item;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
@Entity
@Table(name = "items")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_item", nullable = false)
    private Item item;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "taked_at", nullable = false)
    private LocalDate takedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;
}