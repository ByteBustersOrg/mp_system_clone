package org.vyatsu.localApiModule.entity.item;

import org.vyatsu.localApiModule.entity.item.Order;
import org.vyatsu.localApiModule.entity.item.PriceHistory;
import org.vyatsu.localApiModule.entity.purchase.Ad;
import org.vyatsu.localApiModule.entity.purchase.PurchaseItem;
import org.vyatsu.localApiModule.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "first_price")
    private Double firstPrice;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Column(name = "category")
    private String category;

    @Column(name = "mp_link")
    private String mpLink;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDate createdAt;

    @OneToMany(mappedBy = "item")
    private Set<Order> orders = new LinkedHashSet<>();

    @OneToMany(mappedBy = "item")
    private Set<PriceHistory> priceHistory = new LinkedHashSet<>();

    @OneToMany(mappedBy = "item")
    private Set<Ad> ads = new LinkedHashSet<>();

    @OneToMany(mappedBy = "item")
    private Set<PurchaseItem> purchaseItems = new LinkedHashSet<>();
}
