package org.had.patientservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.had.accountservice.exception.MyWebClientException;
import org.had.patientservice.dto.AppointmentDto;
import org.had.patientservice.entity.*;
import org.had.patientservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private OpConsultationRepository opConsultationRepository;

    @Autowired
    private PatientVitalsRepository patientVitalsRepository;
    
    @Autowired
    private PatientDetailsRepository patientDetailsRepository;

    @Autowired
    private PrescriptionDetailsRepository prescriptionDetailsRepository;

    @Autowired
    private WebClient webClient;



    @Autowired
    private ResourceLoader resourceLoader;


    public void createNewAppointment(AppointmentDto appointmentDto){
        AppointmentDetails appointmentDetails = new AppointmentDetails();
        appointmentDetails.setDoctor_id(appointmentDto.getDoctor_id());
        appointmentDetails.setDoctor_name(appointmentDto.getDoctor_name());

        PatientDetails patientDetails = patientDetailsRepository.findById(appointmentDto.getPatient_id())
                .orElseThrow(() -> new RuntimeException("Parent not found"));
        appointmentDetails.setPatientId(patientDetails);

        appointmentDetails.setDate(appointmentDto.getDate());
        appointmentDetails.setTime(appointmentDto.getTime());
        appointmentDetails.setNotes(appointmentDto.getNotes());

        AppointmentDetails appointmentId = appointmentRepository.save(appointmentDetails);

        OpConsultation opConsultation = new OpConsultation();
        opConsultation.setAppointmentDetails(appointmentId);

        OpConsultation opConsultationId = opConsultationRepository.save(opConsultation);

        addPatientVitals(opConsultationId, appointmentDto);

    }

    public String addPatientVitals(OpConsultation opConsultation, AppointmentDto appointmentDto){
        PatientVitals patientVitals = new PatientVitals();
        patientVitals.setWeight(appointmentDto.getWeight());
        patientVitals.setHeight(appointmentDto.getHeight());
        patientVitals.setAge(appointmentDto.getAge());
        patientVitals.setTemperature(appointmentDto.getTemperature());
        patientVitals.setBlood_pressure_systolic(appointmentDto.getBlood_pressure_systolic());
        patientVitals.setBlood_pressure_distolic(appointmentDto.getBlood_pressure_distolic());
        patientVitals.setPulse_rate(appointmentDto.getPulse_rate());
        patientVitals.setRespiration_rate(appointmentDto.getRespiration_rate());
        patientVitals.setBlood_sugar(appointmentDto.getBlood_sugar());
        patientVitals.setCholesterol(appointmentDto.getCholesterol());
        patientVitals.setTriglyceride(appointmentDto.getTriglyceride());

        patientVitals.setOpConsultation(opConsultation);

        patientVitalsRepository.save(patientVitals);

        return "successfully added vitals";
    }

    public ResponseEntity<?> getAppointmentDetails(Integer id){
        if(patientDetailsRepository.findById(id).isPresent()){
            PatientDetails patientDetails = patientDetailsRepository.findById(id).get();
            if(appointmentRepository.findAllByPatientId(patientDetails).isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Appointment found for this patient");
            }
            List<AppointmentDetails> appointmentDetails = appointmentRepository.findAllByPatientId(patientDetails);
            return ResponseEntity.ok(appointmentDetails);
        }
        return ResponseEntity.badRequest().body("Patient Not found");
    }


    public ResponseEntity<?> getPatientVitals(String id) {
        int opId = Integer.parseInt(id);
        List<PatientVitals> patientVitalsList = patientVitalsRepository.findByOpId(opId);
        if (!patientVitalsList.isEmpty()) {
            PatientVitals patientVitals = patientVitalsList.getFirst();
            return ResponseEntity.ok(patientVitals);
        }
        return ResponseEntity.badRequest().body("Patient Vitals Not found");
    }


    public ResponseEntity<?> completeAppointment(JsonNode jsonNode) {
        try {
            JsonNode prescriptionArray = jsonNode.get("prescription");
            Integer opId = jsonNode.get("opId").asInt();
            Integer patientId = jsonNode.get("patientId").asInt();
            String Observations = jsonNode.get("observations").asText();
            Optional<OpConsultation> opConsultationOptional = opConsultationRepository.findById(opId);
            if (opConsultationOptional.isPresent()) {
                OpConsultation opConsultation = opConsultationOptional.get();
                if (prescriptionArray != null && prescriptionArray.isArray()) {
                    for (JsonNode prescriptionObject : prescriptionArray) {
                        String drug = prescriptionObject.get("drug").asText();
                        String instructions = prescriptionObject.get("instructions").asText();
                        Integer dosage = prescriptionObject.get("dosage").asInt();
                        Integer duration = prescriptionObject.get("duration").asInt();
                        Integer frequency = prescriptionObject.get("frequency").asInt();

                        PrescriptionDetails prescriptionDetails = new PrescriptionDetails();
                        prescriptionDetails.setDrug(drug);
                        prescriptionDetails.setInstructions(instructions);
                        prescriptionDetails.setDosage(dosage);
                        prescriptionDetails.setFrequency(frequency);
                        prescriptionDetails.setDuration(duration);
                        prescriptionDetails.setOpConsultation(opConsultation);
                        prescriptionDetailsRepository.save(prescriptionDetails);
                    }
                    opConsultation.setObservations(Observations);
                    opConsultationRepository.save(opConsultation);

                    String res = linkCareContext(opId+"", patientId);
                    System.out.println(res);



                    Optional<AppointmentDetails> appointmentDetailsOptional = appointmentRepository.findById(opId);
                    if (appointmentDetailsOptional.isPresent()) {
                        AppointmentDetails appointmentDetails = appointmentDetailsOptional.get();
                        appointmentDetails.setStatus("Completed");
                        System.out.println("Updated Status");
                        appointmentRepository.save(appointmentDetails);
                    }
                    else {
                        System.out.println("Appointment Not found");
                    }


                   return ResponseEntity.ok("Prescription Record saved");
                } else {
                    return ResponseEntity.badRequest().body("Invalid Prescription Array");
                }
            } else {
                return ResponseEntity.badRequest().body("OpConsultation with id " + opId + " not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    private String linkCareContext(String opId, Integer patientId) {
        PatientDetails patientDetails =  patientDetailsRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        String accessToken = patientDetails.getLinkToken();
        System.out.println(accessToken);
        var values = new HashMap<String, String>() {{
            put("opId",opId);
            put("accessToken", accessToken);
        }};
        String requestBody = null;
        var objectMapper = new ObjectMapper();
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return webClient.post().uri("http://127.0.0.1:9008/abdm/consent/linkCareContext")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new MyWebClientException(errorBody, clientResponse.statusCode().value()))))
                .bodyToMono(String.class).block();
    }

    public ResponseEntity<?> getPrescription(JsonNode jsonNode) {
        Integer appointmentId = jsonNode.get("appointment_id").asInt();
        List<PrescriptionDetails> prescriptionDetailsList = prescriptionDetailsRepository.findByOpConsultation(appointmentId);
        return ResponseEntity.ok(prescriptionDetailsList);
    }

    public String handleFileUpload(MultipartFile file, String folderName) {
        if (file.isEmpty()) {
            return "No file uploaded";
        }

        try {
            // Specify the directory where you want to save the file on the server
            String currentDirectory = System.getProperty("user.dir");
            System.out.println("Current directory: " + currentDirectory);
            String uploadDir = currentDirectory + "/files/";

            // Create the directory if it doesn't exist
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File folder = new File(uploadDir + folderName);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File destFile = new File(folder, Objects.requireNonNull(file.getOriginalFilename()));
            file.transferTo(destFile);
            return "File uploaded successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload file";
        }
    }

    public ResponseEntity<?> getImagesFromFolder(String folderName) {

        List<FileSystemResource> imageList = new ArrayList<>();
        try {
            // Specify the directory where the files are stored on the server
            String currentDirectory = System.getProperty("user.dir");
            String uploadDir = currentDirectory + "/files/";

            // Create a File object representing the folder
            File folder = new File(uploadDir + folderName);

            // Check if the folder exists
            if (folder.exists() && folder.isDirectory()) {
                // Get the list of files in the folder
                File[] files = folder.listFiles();
                if (files != null) {
                    // Iterate over the files and add image files to the imageList
                    for (File file : files) {
                        if (file.isFile() && isImageFile(file)) {
                            imageList.add(new FileSystemResource(file));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(imageList);
        if (!imageList.isEmpty()) {
            // Return the list of image files as the response
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(imageList);
        } else {
            // If no image files were found, return a 404 Not Found response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    private boolean isImageFile(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif") || fileName.endsWith(".bmp");
    }

    public void saveFile(String fileName, byte[] fileData) throws IOException {
        Path filePath = Paths.get(getResourceFilePath() + fileName);
        Files.write(filePath, fileData);
    }
    public byte[] getFile(String fileName) throws IOException {
        Path filePath = Paths.get(getResourceFilePath() + fileName);
        return Files.readAllBytes(filePath);
    }
    private String getResourceFilePath() throws IOException {
        return resourceLoader.getResource("classpath:static/files/").getFile().getAbsolutePath() + "/";
    }
}
