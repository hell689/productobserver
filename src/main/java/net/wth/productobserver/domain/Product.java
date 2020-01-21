
package net.wth.productobserver.domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.IdName.class)
    private Long id;
    @JsonView(Views.IdName.class)
    private String name;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonView(Views.FullData.class)
    private Set<ObserveredPage> pages = new HashSet<>();

}
