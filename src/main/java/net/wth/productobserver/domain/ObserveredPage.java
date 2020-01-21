package net.wth.productobserver.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="observeredpages")
@Data
public class ObserveredPage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String site;

    private String page;

    private String regExp;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "product")
    private Product product;

}
