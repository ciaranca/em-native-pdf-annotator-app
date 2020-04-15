package uk.gov.hmcts.reform.em.npa.redaction;

import org.apache.commons.io.FilenameUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.em.npa.Application;
import uk.gov.hmcts.reform.em.npa.TestSecurityConfiguration;
import uk.gov.hmcts.reform.em.npa.service.dto.external.redaction.RedactionDTO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, TestSecurityConfiguration.class})
public class ImageRedactionTest {

    private static final File TEST_IMAGE_FILE = new File(
            ClassLoader.getSystemResource("fist.png").getPath()
    );

    @Autowired
    private ImageRedaction imageRedaction;

    private List<RedactionDTO> redactionDTOList = new ArrayList<>();

    @Before
    public void setup() {
        initRedactionDTOList();
    }

    public void initRedactionDTOList() {
        for (int i = 0; i < 5 ; i++) {
            RedactionDTO redactionDTO = new RedactionDTO();

            redactionDTO.setPageNumber(i + 1);
            redactionDTO.setXCoordinate(100 * (i + 1));
            redactionDTO.setYCoordinate(100 * (i + 1));
            redactionDTO.setHeight(100 * (i + 1));
            redactionDTO.setWidth(100 * (i + 1));

            redactionDTOList.add(redactionDTO);
        }
    }

    @Test
    public void pdfRedactionTest() throws IOException {
        File result = imageRedaction.redaction(TEST_IMAGE_FILE, redactionDTOList);
        Assert.assertTrue(result.getName().contains("altered"));
        Assert.assertTrue(result.getName().contains(FilenameUtils.getExtension(TEST_IMAGE_FILE.getName())));
    }

    @Test(expected = IOException.class)
    public void pdfRedactionFailureTest() throws IOException {
        File result = imageRedaction.redaction(new File("invalid_file"), redactionDTOList);
    }
}
