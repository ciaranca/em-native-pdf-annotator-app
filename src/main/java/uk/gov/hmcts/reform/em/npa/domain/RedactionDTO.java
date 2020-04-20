package uk.gov.hmcts.reform.em.npa.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class RedactionDTO {
    // Json ignore for which fields?
    private int pageNumber;
    private int xCoordinate;
    private int yCoordinate;
    private int width;
    private int height;
}
