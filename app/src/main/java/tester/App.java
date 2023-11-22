/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package tester;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPart;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.relationships.Relationship;

public class App {

    public static void main(String[] args) throws Exception {

        // Load the docx file
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
        .load(new File("severalImages.docx"));
                // .load(new File("/home/danortega/Desktop/waterthing.docx"));

        // Iterate through the parts (e.g., Main Document Part, Header Parts, Footer
        // Parts)
        for (Relationship relationship : wordMLPackage.getMainDocumentPart().getRelationshipsPart().getRelationships()
                .getRelationship()) {
            if (relationship.getType().equals(Namespaces.IMAGE)) {
                // Found an image part
                var part = wordMLPackage.getMainDocumentPart().getRelationshipsPart().getPart(relationship);

                if (part instanceof BinaryPart) {
                    BinaryPart binaryPart = (BinaryPart) part;

                    System.out.println("Found image " + binaryPart.getPartName().getName());

                    // Replace the image
                    if (binaryPart.getPartName().getName().equals("/word/media/image4.png")) {
                        byte[] newImageBytes = Files.readAllBytes(Paths.get("test.png"));
                        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wordMLPackage,
                                newImageBytes);
                        binaryPart.setBinaryData(newImageBytes);
                        break;
                    }
                }
            }
        }

        // Save the updated document
        wordMLPackage.save(new File("severalImages2.docx"));

    }

}
