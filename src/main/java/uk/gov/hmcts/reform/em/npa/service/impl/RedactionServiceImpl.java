package uk.gov.hmcts.reform.em.npa.service.impl;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import uk.gov.hmcts.reform.em.npa.redaction.ImageRedaction;
import uk.gov.hmcts.reform.em.npa.redaction.PdfRedaction;
import uk.gov.hmcts.reform.em.npa.service.RedactionService;
import uk.gov.hmcts.reform.em.npa.service.dto.external.redaction.RedactionDTO;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class RedactionServiceImpl implements RedactionService {

    private final Logger log = LoggerFactory.getLogger(RedactionService.class);

    @Autowired
    private PdfRedaction pdfRedaction;
    @Autowired
    private ImageRedaction imageRedaction;

    @Value("#{'${redaction.multipart.image-ext}'.split(',')}")
    java.util.List<String> imageExtensionsList;

    @Override
    public String redactFile(UUID documentId, List<RedactionDTO> redactionDTOList) {
        try {
            // call doc store to get doc by documentId
            // remove this placeholder with a call to doc store client code:
            File file = new File("");
            String fileType = FilenameUtils.getExtension(file.getName());
            if (fileType.equals("pdf")) {
                log.info("Applying redaction to PDF file");
                File redactedPDF = pdfRedaction.redaction(file, redactionDTOList);
            } else if (imageExtensionsList.contains(fileType)) {
                log.info("Applying redaction to Image Document");
                File redactedImage = imageRedaction.redaction(file, redactionDTOList);
            } else {
                throw new FileTypeException("Redaction cannot be applied to the file type provided");
            }
            // call to save the redactedImage file to the doc store
            return "doc_store_id";
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
