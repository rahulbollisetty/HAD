package org.had.consentservice.service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class FhirService {

    static FhirContext ctx = FhirContext.forR4();

    public JsonNode convertJsonToBundle(String jsonString) {
        IParser parser = ctx.newJsonParser();
        Bundle bundle = parser.parseResource(Bundle.class, jsonString);
        Map<String,Object> root = new HashMap<>();
        // Getting composition
        Bundle.BundleEntryComponent bundleEntryComponent =  bundle.getEntryFirstRep();
        Composition composition = (Composition) bundleEntryComponent.getResource();

        // Getting patient Details
        String patientReference = composition.getSubject().getReference();
        String patientId = patientReference.split("/")[1];
        Patient patient = findPatientById(bundle, patientId);
        if (patient != null) {
//            System.out.println("Patient Name: " + patient.getName().get(0).getNameAsSingleString());
            String name = patient.getName().get(0).getText();
            root.put("name", name);
            String patientSbx = patient.getIdentifier().get(0).getValue();
            root.put("patientSbx", patientSbx);
        } else {
            log.error("Patient not found in the Bundle.");
        }

        // Getting Practitioner Details
        String doctorReference = composition.getAuthor().get(0).getReference();
        String doctorId = doctorReference.split("/")[1];
        Practitioner practitioner = findPractitionerById(bundle, doctorId);
        if (practitioner != null) {
            String name = practitioner.getName().get(0).getText();
            root.put("doctorName", name);
            String doctorRegNo = practitioner.getIdentifier().get(0).getValue();
            root.put("doctorRegNo", doctorRegNo);

        } else {
            log.error("Practitioner not found in the Bundle.");
        }

        // Getting Encounter Details
        String encounterReference = composition.getEncounter().getReference();
        String encounterId = encounterReference.split("/")[1];
        Encounter encounter = findEncounterById(bundle, encounterId);
        if (encounter != null) {
            String reason = encounter.getReasonCode().get(0).getText();
            root.put("reason", reason);
            String appointmentDate = encounter.getPeriod().getStart().toString();
            root.put("appointmentDate", appointmentDate);

        } else {
            log.error("Encounter not found in the Bundle.");
        }

        // Getting Encounter Details
        String custodianReference = composition.getCustodian().getReference();
        String custodianId = custodianReference.split("/")[1];
        Organization organization = findCustodianById(bundle, custodianId);
        if (organization != null) {
            String hospitalId = organization.getIdentifier().get(0).getValue();
            root.put("hospitalId", hospitalId);
            String hospitalName = organization.getName();
            root.put("hospitalName", hospitalName);
        } else {
            log.error("Encounter not found in the Bundle.");
        }

        String reportReference = composition.getSection().get(0).getEntry().get(0).getReference();
        String reportId = reportReference.split("/")[1];
        DocumentReference reportDocument = findDocumentById(bundle, reportId);
        if (reportDocument != null) {
           Map<String,Object> report  = new HashMap<>();
           report.put("contentType", reportDocument.getContent().get(0).getAttachment().getContentType());
           report.put("data", reportDocument.getContent().get(0).getAttachment().getData());
           report.put("title", reportDocument.getContent().get(0).getAttachment().getTitle());
           root.put("reportDocument", report);
        } else {
            log.error("Encounter not found in the Bundle.");
        }

        String attachmentReference = composition.getSection().get(0).getEntry().get(1).getReference();
        String attachmentId = attachmentReference.split("/")[1];
        DocumentReference attachmentDocument = findDocumentById(bundle, attachmentId);
        if (attachmentDocument != null) {
            Map<String,Object> reportAttachment = new HashMap<>();
            reportAttachment.put("contentType", attachmentDocument.getContent().get(0).getAttachment().getContentType());
            reportAttachment.put("data", attachmentDocument.getContent().get(0).getAttachment().getData());
            reportAttachment.put("title", attachmentDocument.getContent().get(0).getAttachment().getTitle());
            root.put("attachment", reportAttachment);
        } else {
            log.error("Encounter not found in the Bundle.");
        }

        // Create ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        // Convert Map to JsonNode
        JsonNode jsonNode = objectMapper.valueToTree(root);

        return jsonNode;
    }


    // Helper method to find a Patient resource by ID in the Bundle
    private static Patient findPatientById(Bundle bundle, String patientId) {
        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            if (entry.hasResource() && entry.getResource() instanceof Patient) {
                Patient patient = (Patient) entry.getResource();
                if (patient.getIdElement().getIdPart().equals(patientId)) {
                    return patient;
                }
            }
        }
        return null;
    }

    // Helper method to find a Practitioner resource by ID in the Bundle
    private static Practitioner findPractitionerById(Bundle bundle, String doctorId) {
        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            if (entry.hasResource() && entry.getResource() instanceof Practitioner) {
                Practitioner practitioner = (Practitioner) entry.getResource();
                if (practitioner.getIdElement().getIdPart().equals(doctorId)) {
                    return practitioner;
                }
            }
        }
        return null;
    }

    // Helper method to find a Encounter resource by ID in the Bundle
    private static Encounter findEncounterById(Bundle bundle, String encounterId) {
        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            if (entry.hasResource() && entry.getResource() instanceof Encounter) {
                Encounter encounter = (Encounter) entry.getResource();
                if (encounter.getIdElement().getIdPart().equals(encounterId)) {
                    return encounter;
                }
            }
        }
        return null;
    }

    // Helper method to find a Encounter resource by ID in the Bundle
    private static Organization findCustodianById(Bundle bundle, String custodianId) {
        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            if (entry.hasResource() && entry.getResource() instanceof Organization) {
                Organization organization = (Organization) entry.getResource();
                if (organization.getIdElement().getIdPart().equals(custodianId)) {
                    return organization;
                }
            }
        }
        return null;
    }

    // Helper method to find a Document resource by ID in the Bundle
    private static DocumentReference findDocumentById(Bundle bundle, String documentId) {
        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            if (entry.hasResource() && entry.getResource() instanceof DocumentReference) {
                DocumentReference documentReference = (DocumentReference) entry.getResource();
                if (documentReference.getIdElement().getIdPart().equals(documentId)) {
                    return documentReference;
                }
            }
        }
        return null;
    }
}
