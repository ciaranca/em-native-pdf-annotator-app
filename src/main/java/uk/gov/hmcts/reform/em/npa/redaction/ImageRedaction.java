package uk.gov.hmcts.reform.em.npa.redaction;

import com.sun.tools.javac.util.List;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.em.npa.service.dto.external.redaction.RedactionDTO;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class ImageRedaction {

    /**
     * Apply multiple redactions to an image file
     *
     * @param imageFile the image to which the redactions are to be applied to
     * @param redactionDTOList a list containing the information for the redactions to be applied to the image
     * @return the redacted image
     * @throws IOException
     */
    public File redaction(File imageFile, List<RedactionDTO> redactionDTOList) throws IOException {
        BufferedImage img = ImageIO.read(imageFile);
        Graphics2D graph = img.createGraphics();

        redactionDTOList.stream().forEach(redactionDTO -> {
            graph.setColor(Color.BLACK);
            graph.fill(new Rectangle(
                    redactionDTO.getXCoordinate(),
                    redactionDTO.getYCoordinate(),
                    redactionDTO.getWidth(),
                    redactionDTO.getHeight()));
        });
        graph.dispose();

        String fileType = FilenameUtils.getExtension(imageFile.getName());
        final File alteredImage = File.createTempFile("altered", "." + fileType);
        ImageIO.write(img, fileType, alteredImage);
        return alteredImage;
    }

    /**
     * Apply a redaction to an image file
     *
     * @param imageFile the image to which the redactions are to be applied to
     * @param redactionDTO information containing the redaction to be applied to the image
     * @return the redacted image
     * @throws IOException
     */
    public File redaction(File imageFile, RedactionDTO redactionDTO) throws IOException {
        BufferedImage img = ImageIO.read(imageFile);
        Graphics2D graph = img.createGraphics();

        graph.setColor(Color.BLACK);
        graph.fill(new Rectangle(
                        redactionDTO.getXCoordinate(),
                        redactionDTO.getYCoordinate(),
                        redactionDTO.getWidth(),
                        redactionDTO.getHeight()));
        graph.dispose();

        String fileType = FilenameUtils.getExtension(imageFile.getName());
        final File alteredImage = File.createTempFile("altered", "." + fileType);
        ImageIO.write(img, fileType, alteredImage);
        return alteredImage;
    }
}
