package uk.gov.hmcts.reform.em.npa.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import uk.gov.hmcts.reform.em.npa.service.dto.external.annotation.AnnotationDTO;
import uk.gov.hmcts.reform.em.npa.service.impl.DocumentTaskProcessingException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AnnotationSetDTOToPDAnnotationMapper {

    void toNativeAnnotationsPerPage(PDDocument document, Set<AnnotationDTO> annotations) throws DocumentTaskProcessingException;
         ;

}
