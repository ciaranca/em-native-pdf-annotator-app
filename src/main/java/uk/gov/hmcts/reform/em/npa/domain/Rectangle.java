package uk.gov.hmcts.reform.em.npa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * A Rectangle.
 */
@Entity
@Table(name = "rectangle")
@Getter
@Setter
@ToString
public class Rectangle extends AbstractAuditingEntity implements Serializable {

    @Id
    private UUID id;

    @Column(name = "x_coordinate", nullable = false)
    private Double x;

    @Column(name = "y_coordinate", nullable = false)
    private Double y;

    @Column(nullable = false)
    private Double width;

    @Column(nullable = false)
    private Double height;

    @ManyToOne
    @JsonIgnoreProperties("rectangles")
    private Redaction redaction;

}
