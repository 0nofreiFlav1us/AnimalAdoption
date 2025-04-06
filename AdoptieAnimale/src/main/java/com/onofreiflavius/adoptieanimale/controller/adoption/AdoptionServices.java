package com.onofreiflavius.adoptieanimale.controller.adoption;

import com.itextpdf.text.pdf.draw.LineSeparator;
import com.onofreiflavius.adoptieanimale.controller.authentication.AuthenticationServices;
import com.onofreiflavius.adoptieanimale.controller.database.DatabaseServices;
import com.onofreiflavius.adoptieanimale.model.Animal;
import com.onofreiflavius.adoptieanimale.model.User;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;

/**
 * The AdoptionServices class handles operations related to adoption requests,
 * including the creation of adoption request PDFs and managing adoption request directories.
 *
 * @author Onofrei Flavius
 */
public class AdoptionServices {

    /**
     * Path where adoption request directories are created.
     */
    private static final String adoptionRequestsDirectoryPath = "/home/onofreiflavius/Desktop/animals/adoption_requests/";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a directory for the specified animal ID if it doesn't already exist.
     * The directory is created under the adoptionRequestsDirectoryPath.
     *
     * @param animalId the unique identifier for the animal
     */
    public static void createAnimalDirectory(String animalId) {
        String animalDirectoryPath = adoptionRequestsDirectoryPath + animalId + "/";
        File animalDirectory = new File(animalDirectoryPath);
        if (!animalDirectory.exists()) {
            if (animalDirectory.mkdirs()) {
                System.out.println("Directory created successfully: " + animalDirectoryPath);
            } else {
                System.out.println("Failed to create directory: " + animalDirectoryPath);
            }
        } else {
            System.out.println("Directory already exists: " + animalDirectoryPath);
        }
    }

    /**
     * Generates a PDF document for the adoption request.
     * The PDF includes details about the user, animal, and adoption request.
     * The PDF is saved in the directory for the specified animal.
     *
     * @param user   the user making the adoption request
     * @param animal the animal the user is requesting to adopt
     */
    public static void generateAdoptionRequestPDF(User user, Animal animal) {
        createAnimalDirectory(animal.getAnimalID());

        String uniqueAdoptionRequestName = System.currentTimeMillis() + "_AR.pdf";
        String dest = adoptionRequestsDirectoryPath + animal.getAnimalID() + "/" + uniqueAdoptionRequestName;

        DatabaseServices.adoptionRequest(AuthenticationServices.getEmailFromSession(), animal.getId(), dest);

        try {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, BaseColor.DARK_GRAY);
            Paragraph title = new Paragraph("Adoption Request", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph("\n"));
            document.add(new Chunk(new LineSeparator()));

            Font sectionTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLUE);
            Font textFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            Paragraph userDetailsTitle = new Paragraph("User Details", sectionTitleFont);
            userDetailsTitle.setSpacingBefore(20);
            userDetailsTitle.setSpacingAfter(10);
            document.add(userDetailsTitle);

            document.add(new Paragraph("Name: " + user.getFirstname() + " " + user.getLastname(), textFont));
            document.add(new Paragraph("Email: " + user.getEmail(), textFont));
            document.add(new Paragraph("Phone Number: " + user.getPhoneNumber(), textFont));
            document.add(new Paragraph("Living Conditions: " + user.getLivingConditions(), textFont));
            document.add(new Paragraph("Pet Experience: " + user.getPetExperience(), textFont));
            document.add(new Paragraph("Motivation: " + user.getMotivation(), textFont));

            document.add(new Paragraph("\n"));
            document.add(new Chunk(new LineSeparator()));

            Paragraph animalDetailsTitle = new Paragraph("Animal Details", sectionTitleFont);
            animalDetailsTitle.setSpacingBefore(20);
            animalDetailsTitle.setSpacingAfter(10);
            document.add(animalDetailsTitle);

            document.add(new Paragraph("Animal ID: " + animal.getAnimalID(), textFont));
            document.add(new Paragraph("Species: " + animal.getSpecies(), textFont));
            document.add(new Paragraph("Breed: " + animal.getBreed(), textFont));
            document.add(new Paragraph("Age: " + animal.getAge() + " years", textFont));
            document.add(new Paragraph("Gender: " + animal.getGender(), textFont));
            document.add(new Paragraph("Size: " + animal.getSize(), textFont));
            document.add(new Paragraph("Description: " + animal.getDescription(), textFont));

            document.add(new Paragraph("\n"));

            if (animal.getImagePath() != null && !animal.getImagePath().isEmpty()) {
                File imageFile = new File(animal.getImagePath());
                if (imageFile.exists()) {
                    Image animalImage = Image.getInstance(animal.getImagePath());
                    animalImage.scaleToFit(200, 200); // Scale the image
                    animalImage.setAlignment(Image.ALIGN_CENTER);
                    document.add(animalImage);
                } else {
                    document.add(new Paragraph("Animal image not found at: " + animal.getImagePath(), textFont));
                }
            } else {
                document.add(new Paragraph("No image provided for this animal.", textFont));
            }

            document.add(new Paragraph("\n"));
            document.add(new Chunk(new LineSeparator()));

            Paragraph footer = new Paragraph("Thank you for submitting your adoption request!", textFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(20);
            document.add(footer);

            document.close();

            System.out.println("Adoption request PDF created successfully at: " + dest);
        } catch (Exception e) {
            System.out.println("PDF creation failed: " + e.getMessage());
        }
    }

    /**
     * Removes the adoption request PDF file located at the specified path.
     * If the file exists, it is deleted.
     *
     * @param pdfPath the path to the PDF file to delete
     */
    public static void removeAdoptionRequestPDF(String pdfPath) {
        if (pdfPath != null && !pdfPath.isEmpty()) {
            File file = new File(pdfPath);
            if (file.exists()) {
                boolean deleted = file.delete();
                if (!deleted) {
                    System.out.println("Error deleting pdf.");
                } else {
                    System.out.println("Successfully deleted pdf: " + pdfPath);
                }
            }
        }
    }
}
