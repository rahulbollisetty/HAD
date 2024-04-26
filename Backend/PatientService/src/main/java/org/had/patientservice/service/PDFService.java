package org.had.patientservice.service;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.io.FilenameUtils;
import org.had.patientservice.entity.*;
import org.had.patientservice.repository.OpConsultationRepository;
import org.had.patientservice.repository.PatientVitalsRepository;
import org.had.patientservice.repository.PrescriptionDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@Service
public class PDFService {

    @Autowired
    private OpConsultationRepository opConsultationRepository;

    @Autowired
    private PatientVitalsRepository patientVitalsRepository;

    @Autowired
    private PrescriptionDetailsRepository prescriptionDetailsRepository;

    @Value("${hospital.name}")
    private String hospitalName;

    @Value("${hospital.id}")
    private String hospitalId;

    @Autowired
    private TemplateEngine templateEngine;


//    public void generatePdf(String id) throws IOException, InterruptedException, DocumentException {
//        // Load data from repositories
//        OpConsultation opConsultation = opConsultationRepository.findById(Integer.valueOf(id)).get();
//        PatientVitals patientVitals = patientVitalsRepository.findByOpId(Integer.valueOf(id)).getFirst();
//        List<PrescriptionDetails> prescriptionDetailsList = prescriptionDetailsRepository.findByOpConsultation(Integer.valueOf(id));
//        AppointmentDetails appointmentDetails = opConsultation.getAppointmentDetails();
//        PatientDetails patientDetails = appointmentDetails.getPatientId();
//
//        if (opConsultation.getFilePath() == null) {
//            System.out.println("No file path provided.");
//            return;
//        }
//
//        // Create Thymeleaf context
//        Context context = new Context();
//        context.setVariable("patientVitals", patientVitals);
//        context.setVariable("appointment", appointmentDetails);
//        context.setVariable("patient", patientDetails);
//        context.setVariable("hospitalName", hospitalName);
//        context.setVariable("hospitalId", hospitalId);
//        context.setVariable("observation", opConsultation.getObservations());
//        context.setVariable("prescriptionList", prescriptionDetailsList);
//
//        // Process the Thymeleaf template to generate HTML content
//        String processedHtml = templateEngine.process("index", context);
//
//        // Check if file path is provided
//        if (opConsultation.getFilePath() != null) {
//            // Create PDF from HTML content
//            Pdf pdf = new Pdf();
//            pdf.addPageFromString(processedHtml);
//            pdf.saveAs("merged/" + "temp" + patientDetails.getName() + ":" + appointmentDetails.getAppointment_id().toString() + ".pdf");
//            return;
//        }
//        // Create PDF from HTML content
//        Pdf pdf = new Pdf();
//        pdf.addPageFromString(processedHtml);
//        pdf.saveAs("temp/" + "temp" + patientDetails.getName() + ":" + appointmentDetails.getAppointment_id().toString() + ".pdf");
//
//        // Load the first PDF as a byte array
//        byte[] firstPdfBytes = Files.readAllBytes(Paths.get("temp/" + "temp" + patientDetails.getName() + ":" + appointmentDetails.getAppointment_id().toString() + ".pdf"));
//
//        // Load the second PDF or image based on the condition
//        byte[] secondFileBytes = null;
//        if (opConsultation.getFilePath() != null) {
//            String filePath = opConsultation.getFilePath();
//            if (FilenameUtils.isExtension(filePath.toLowerCase(), "pdf")) {
//                // Load the second PDF as a byte array
//                secondFileBytes = Files.readAllBytes(Paths.get(filePath));
//            } else if (FilenameUtils.isExtension(filePath.toLowerCase(), "png", "jpg", "jpeg", "gif")) {
//                // Handle image conversion and merging with the first PDF
//                secondFileBytes = convertImageToPdf(filePath);
//            } else {
//                System.out.println("Unsupported file format.");
//            }
//        }
//
//        // Merge the first PDF with the second PDF or image
//        mergePdfs(firstPdfBytes, secondFileBytes, patientDetails.getName(), appointmentDetails.getAppointment_id().toString());
//
//        // Clean up the temporary file
//        Files.deleteIfExists(Paths.get("temp/" + "temp" + patientDetails.getName() + ":" + appointmentDetails.getAppointment_id().toString() + ".pdf"));
//    }
//
//    // Method to convert image file to PDF byte array
//    private byte[] convertImageToPdf(String imagePath) throws IOException {
//        BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
//        if (bufferedImage == null) {
//            throw new IOException("Failed to load image: " + imagePath);
//        }
//
//        // Get document page size (A4 in this example)
//        float pageSizeWidth = 595; // Width of A4 page in points
//        float pageSizeHeight = 842; // Height of A4 page in points
//
//        // Calculate scaling factors
//        float pageContentWidth = pageSizeWidth - 72; // Assuming default left and right margins of 36 units each (72 units total)
//        float pageContentHeight = pageSizeHeight - 144; // Assuming default top and bottom margins of 72 units each (144 units total)
//        float imageWidth = bufferedImage.getWidth();
//        float imageHeight = bufferedImage.getHeight();
//
//        // Calculate scaling factors to fit within page content area while maintaining aspect ratio
//        float scaleWidth = 1f;
//        float scaleHeight = 1f;
//        if (imageWidth > pageContentWidth) {
//            scaleWidth = pageContentWidth / imageWidth;
//        }
//        if (imageHeight > pageContentHeight) {
//            scaleHeight = pageContentHeight / imageHeight;
//        }
//
//        // Choose the smaller scaling factor to maintain aspect ratio and fit within the page content area
//        float scaleFactor = Math.min(scaleWidth, scaleHeight);
//
//        // Resize the image
//        int newWidth = Math.round(imageWidth * scaleFactor);
//        int newHeight = Math.round(imageHeight * scaleFactor);
//        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
//        resizedImage.createGraphics().drawImage(bufferedImage, 0, 0, newWidth, newHeight, null);
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ImageIO.write(resizedImage, "png", outputStream); // Assuming PNG format for the image
//
//        return outputStream.toByteArray();
//    }
//
//    // Method to merge two PDF byte arrays
//    private void mergePdfs(byte[] firstPdfBytes, byte[] secondPdfBytes, String patientName, String appointmentId) throws IOException, DocumentException {
//        ByteArrayOutputStream mergedOutputStream = new ByteArrayOutputStream();
//        Document document = new Document();
//        PdfCopy copy = new PdfCopy(document, mergedOutputStream);
//        document.open();
//
//        // Add the first PDF
//        PdfReader firstPdfReader = new PdfReader(firstPdfBytes);
//        copy.addDocument(firstPdfReader);
//        firstPdfReader.close();
//
//        // Add the second PDF or image
//        if (secondPdfBytes != null) {
//            PdfReader secondPdfReader = new PdfReader(secondPdfBytes);
//            copy.addDocument(secondPdfReader);
//            secondPdfReader.close();
//        }
//
//        // Close the document
//        document.close();
//
//        // Save or handle merged PDF bytes as needed
//        byte[] mergedPdfBytes = mergedOutputStream.toByteArray();
//        FileOutputStream fileOutputStream = new FileOutputStream("merged/" + "mergeReport:" + patientName + ":" + appointmentId + ".pdf");
//        fileOutputStream.write(mergedPdfBytes);
//        fileOutputStream.close();
//    }
//}

    public String generatePdf(String Opid) throws IOException, InterruptedException, DocumentException {
        // Load data from repositories
        OpConsultation opConsultation = opConsultationRepository.findById(Integer.valueOf(Opid)).get();
        PatientVitals patientVitals = patientVitalsRepository.findByOpId(Integer.valueOf(Opid)).getFirst();
        List<PrescriptionDetails> prescriptionDetailsList = prescriptionDetailsRepository.findByOpConsultation(Integer.valueOf(Opid));
        AppointmentDetails appointmentDetails = opConsultation.getAppointmentDetails();
        PatientDetails patientDetails = appointmentDetails.getPatientId();

        // Create Thymeleaf context
        Context context = new Context();
        context.setVariable("patientVitals", patientVitals);
        context.setVariable("appointment", appointmentDetails);
        context.setVariable("patient", patientDetails);
        context.setVariable("hospitalName", hospitalName);
        context.setVariable("hospitalId", hospitalId);
        context.setVariable("observation", opConsultation.getObservations());
        context.setVariable("prescriptionList", prescriptionDetailsList);

        // Process the Thymeleaf template to generate HTML content
        String processedHtml = templateEngine.process("index", context);

        // Create PDF from HTML content
        Pdf pdf = new Pdf();
        pdf.addPageFromString(processedHtml);
        pdf.saveAs("report/" + "Report" + patientDetails.getName() + ":" + appointmentDetails.getAppointment_id().toString() + ".pdf");

        // Load the first PDF as a byte array
        byte[] reportBytes = Files.readAllBytes(Paths.get("report/" + "Report" + patientDetails.getName() + ":" + appointmentDetails.getAppointment_id().toString() + ".pdf"));

        // Clean up the temporary file
        Files.deleteIfExists(Paths.get("report/" + "Report" + patientDetails.getName() + ":" + appointmentDetails.getAppointment_id().toString() + ".pdf"));

        return Base64.getEncoder().encodeToString(reportBytes);
    }


}