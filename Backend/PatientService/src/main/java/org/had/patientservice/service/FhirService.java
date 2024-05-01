package org.had.patientservice.service;


import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.support.DefaultProfileValidationSupport;
import ca.uhn.fhir.parser.DataFormatException;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.SingleValidationMessage;
import ca.uhn.fhir.validation.ValidationResult;
import com.itextpdf.text.DocumentException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.had.patientservice.entity.AppointmentDetails;
import org.had.patientservice.entity.CareContexts;
import org.had.patientservice.entity.OpConsultation;
import org.had.patientservice.entity.PatientDetails;
import org.had.patientservice.repository.AppointmentRepository;
import org.had.patientservice.repository.OpConsultationRepository;
import org.had.patientservice.repository.PatientDetailsRepository;
import org.hl7.fhir.common.hapi.validation.support.*;
import org.hl7.fhir.common.hapi.validation.validator.FhirInstanceValidator;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Service
public class FhirService {

    @Autowired
    private PatientDetailsRepository patientDetailsRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private OpConsultationRepository opConsultationRepository;

    @Autowired
    private PDFService pdfService;

    @Value("${ngrok.Uri}")
    private String ngrokUri;

    @Value("${hospital.id}")
    private String hospitailId;

    @Value("${hospital.name}")
    private String hospitalName;


    static FhirContext ctx = FhirContext.forR4();

    static FhirInstanceValidator fhirInstanceValidator;

    static FhirValidator validator;

    public FhirService() throws IOException {

        NpmPackageValidationSupport npmValidationSupport = new NpmPackageValidationSupport(ctx);
        // If you possess a package with a distinct name, kindly modify the package name here correspondingly.
        npmValidationSupport.loadPackageFromClasspath("package.tgz");

        // Create a chain that will hold our modules
        ValidationSupportChain validationsupportchain = new ValidationSupportChain(

                npmValidationSupport, new DefaultProfileValidationSupport(ctx),
                new InMemoryTerminologyServerValidationSupport(ctx), new CommonCodeSystemsTerminologyService(ctx),
                new SnapshotGeneratingValidationSupport(ctx));

        CachingValidationSupport validationSupport = new CachingValidationSupport(validationsupportchain);

        validator = ctx.newValidator();
        fhirInstanceValidator = new FhirInstanceValidator(validationSupport);
        validator.registerValidatorModule(fhirInstanceValidator);
    }


    public Composition getCompositionFromCareContext(CareContexts careContext, PatientDetails patientDetails, AppointmentDetails appointmentDetails, OpConsultation opConsultation) {

        Composition composition = new Composition();
        composition.setId(careContext.getCareContextReference());
        //SETTING record status as FINAL
        composition.setStatus(Composition.CompositionStatus.FINAL);
        //SETTING TYPE OF RECORD
        composition.setType(new CodeableConcept(new Coding(
                "http://snomed.info/sct",
                "371530004",
                "Clinical Consultation Report"))); // SETTING record type

        //SETTING SUBJECT REFERENCE
        Reference patientReference = new Reference("Patient/"+"patient-"+patientDetails.getMrn());
        patientReference.setDisplay(patientDetails.getName());
        composition.setSubject(patientReference);

        //SETTING AUTHOR REFERENCE
        Reference practitionerReference = new Reference("Practitioner/"+"practitioner-"+appointmentDetails.getDoctor_id());
        practitionerReference.setDisplay("Dr. "+appointmentDetails.getDoctorName());
        composition.setAuthor(Collections.singletonList(practitionerReference));

        // Set Custodian - Organization which maintains the composition
        Reference referenceCustodian = new Reference();
        referenceCustodian.setReference("Hospital/hip-"+hospitailId);
        referenceCustodian.setDisplay(hospitalName);
        composition.setCustodian(referenceCustodian);

        //SETTING ENCOUNTER REFERENCE
        Reference encounterReference = new Reference("Encounter/"+"encounter-"+appointmentDetails.getAppointment_id());
        composition.setEncounter(encounterReference);


        //SETTING DATE
        try {
            composition.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(appointmentDetails.getDate()));
        } catch (Exception e) {            // Access other properties of the Patient if needed
            composition.setDate(new Date());
        };

        //SETTING TITLE
        composition.setTitle("OP Consultation On: "+appointmentDetails.getDate()+" By Dr. "+appointmentDetails.getDoctorName() +" for "+patientDetails.getName());

        //SETTING SECTION
        Composition.SectionComponent sectionComponent = new Composition.SectionComponent();
        List<Reference> referenceList = new ArrayList<>();

            Reference reportReference = new Reference("Report/" + "Report-" +appointmentDetails.getAppointment_id());
            reportReference.setDisplay("OP Consultation Report Document");
            referenceList.add(reportReference);

        //SETTING Attachment
        if (opConsultation.getFilePath() != null){
            Reference attachmentReference = new Reference("Attachment/" + "Attachment-" +appointmentDetails.getAppointment_id());
            attachmentReference.setDisplay("OP Consultation Attachment");
            referenceList.add(attachmentReference);
        }

        sectionComponent.setEntry(referenceList);
        composition.setSection(Collections.singletonList(sectionComponent));

        return composition;
    }

    public Patient getPatient(PatientDetails patientDetails) throws ParseException {
        Patient patient = new Patient();
        //SETTING ID
        patient.setId("patient-"+patientDetails.getMrn());

        // Setting Name
        HumanName name = new HumanName();
        name.setText(patientDetails.getName());
        patient.addName(name);

        patient.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").parse(patientDetails.getDob()));
        //SETTING IDENTIFIER
        Identifier identifier = new Identifier();
        identifier.setType((new CodeableConcept(new Coding(
                "https://nrces.in/ndhm/fhir/r4/CodeSystem/ndhm-identifier-type-code",
                "ABHA",
                "Ayushman Bharat Health Account (ABHA) ID"))));
        identifier.setSystem("https://healthid.ndhm.gov.in");
        identifier.setValue(patientDetails.getAbhaAddress());
        patient.setIdentifier(Collections.singletonList(identifier));

        return patient;
    }
    public Practitioner getPractitioner(AppointmentDetails appointmentDetails)  {
        Practitioner practitioner = new Practitioner();
        //SETTING ID
        practitioner.setId("practitioner-"+appointmentDetails.getDoctor_id());

        // Setting Name
        HumanName name = new HumanName();
        name.setText("Dr. "+appointmentDetails.getDoctorName());
        practitioner.addName(name);

        //SETTING IDENTIFIER
        Identifier identifier = new Identifier();
        identifier.setType((new CodeableConcept(new Coding(
                "http://terminology.hl7.org/CodeSystem/v2-0203",
                "MD",
                "Medical License number"))));
        identifier.setSystem("https://nhpr.abdm.gov.in/home");
        identifier.setValue(appointmentDetails.getDoctorRegNumber());
        practitioner.setIdentifier(Collections.singletonList(identifier));
        return practitioner;
    }

    public DocumentReference getReportDocumentReference(AppointmentDetails appointmentDetails,OpConsultation opConsultation) throws DocumentException, IOException, InterruptedException {

        String pdfContent = pdfService.generatePdf(opConsultation.getOp_id().toString());

        DocumentReference documentReference = new DocumentReference();
        documentReference.setId("Report-" +appointmentDetails.getAppointment_id());
        documentReference.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/DocumentReference");
        documentReference.setStatus(Enumerations.DocumentReferenceStatus.CURRENT);
        documentReference.setDocStatus(DocumentReference.ReferredDocumentStatus.FINAL);
        documentReference.setSubject(new Reference().setReference("Patient/"+ "patient-"+appointmentDetails.getPatientId().getMrn().toString()));
        documentReference
                .setType(new CodeableConcept(new Coding("http://snomed.info/sct", "371530004", "Clinical consultation report"))
                        .setText("Clinical consultation report"));
        documentReference.getContent()
                .add(new DocumentReference.DocumentReferenceContentComponent(new Attachment().setContentType("application/pdf")
                        .setLanguage("en-IN").setTitle("Clinical consultation report")
                        .setCreationElement(new DateTimeType("2019-05-29T14:58:58.181+05:30"))
                        .setDataElement(new Base64BinaryType(pdfContent))));
        return documentReference;
    }

    public DocumentReference getAttachmentDocumentReference(OpConsultation opConsultation,AppointmentDetails appointmentDetails) throws DocumentException, IOException, InterruptedException {
        DocumentReference documentReference = new DocumentReference();
        documentReference.setId("Attachment-" +appointmentDetails.getAppointment_id());
        documentReference.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/DocumentReference");
        documentReference.setStatus(Enumerations.DocumentReferenceStatus.CURRENT);
        documentReference.setDocStatus(DocumentReference.ReferredDocumentStatus.FINAL);
        documentReference.setSubject(new Reference().setReference("Patient/"+ "patient-"+appointmentDetails.getPatientId().getMrn().toString()));
        documentReference
                .setType(new CodeableConcept(new Coding("http://snomed.info/sct", "423876004", "Clinical document"))
                        .setText("Appointment Clinical Attachment"));
        String filePath = opConsultation.getFilePath();
        if (FilenameUtils.isExtension(filePath.toLowerCase(), "pdf")) {
            File file = new File(filePath);
            byte[] pdfBytes = FileUtils.readFileToByteArray(file);
            String pdfContent = Base64.getEncoder().encodeToString(pdfBytes);
            documentReference.getContent()
                    .add(new DocumentReference.DocumentReferenceContentComponent(new Attachment().setContentType("application/pdf")
                            .setLanguage("en-IN").setTitle("Appointment Clinical Attachment")
                            .setDataElement(new Base64BinaryType(pdfContent))));
        }
         else if (FilenameUtils.isExtension(filePath.toLowerCase(), "png", "jpg", "jpeg", "gif")) {
            File imageFile = new File(filePath);
            byte[] imageData = Files.readAllBytes(imageFile.toPath());
            String imageContent = Base64.getEncoder().encodeToString(imageData);
            documentReference.getContent()
                    .add(new DocumentReference.DocumentReferenceContentComponent(new Attachment().setContentType("image/jpeg")
                            .setLanguage("en-IN").setTitle("Appointment Clinical Attachment")
                            .setDataElement(new Base64BinaryType(imageContent))));
        }

        return documentReference;
    }

    public Encounter getEncounter(AppointmentDetails appointmentDetails) throws ParseException {
        Encounter encounter = new Encounter();
        //SETTING ID
        encounter.setId("encounter-"+appointmentDetails.getAppointment_id());
        encounter.setStatus(Encounter.EncounterStatus.FINISHED);
        // Set encounter reason text
        CodeableConcept reasonCodeableConcept = new CodeableConcept();
        reasonCodeableConcept.setText(appointmentDetails.getNotes()); // Set the reason text
        encounter.addReasonCode(reasonCodeableConcept);

        String dateTimeString = appointmentDetails.getDate() +" " + appointmentDetails.getTime();
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = inputFormat.parse(dateTimeString);
//        SimpleDateFormat outputFormat = new SimpleDateFormat("EEE MMM dd HH:mm zzz yyyy", Locale.ENGLISH);
//        outputFormat.setTimeZone(TimeZone.getTimeZone("IST"));


        Period period = new Period();
        period.setStart(date);
        period.setEnd(date);
        encounter.setPeriod(period);

        encounter.setClass_(new Coding(
                "http://terminology.hl7.org/CodeSystem/v3-ActCode",
                "AMB",
                "ambulatory"));
        return encounter;
    }

    public  Organization getCustodian() {
        Organization organization = new Organization();
        organization.setId("hip-"+hospitailId);
        organization.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Organization");
        organization.getIdentifier()
                .add(new Identifier()
                        .setType(new CodeableConcept(
                                new Coding("http://terminology.hl7.org/CodeSystem/v2-0203", "PRN", "Provider number")))
                        .setSystem("https://facility.ndhm.gov.in").setValue(hospitailId));
        organization.setName(hospitalName);
//        List<ContactPoint> list = new ArrayList<ContactPoint>();
//        ContactPoint contact1 = new ContactPoint();
//        contact1.setSystem(ContactPoint.ContactPointSystem.PHONE).setValue("+91 243 2634 3632").setUse(ContactPoint.ContactPointUse.WORK);
//        ContactPoint contact2 = new ContactPoint();
//        contact2.setSystem(ContactPoint.ContactPointSystem.EMAIL).setValue("contact@facility.uvw.org").setUse(ContactPoint.ContactPointUse.WORK);
//        list.add(contact1);
//        list.add(contact2);qq
//        organization.setTelecom(list);
        return organization;
    }

    public Bundle convertCareContextToBundle(CareContexts careContext) {

        PatientDetails patientDetails = patientDetailsRepository.findByAbhaAddress(careContext.getPatientSbx()).get();
        String careContextReference = careContext.getCareContextReference();
        Integer appointmentId = Integer.parseInt(careContextReference.substring(careContextReference.lastIndexOf('.') + 1));
        AppointmentDetails appointmentDetails = appointmentRepository.findById(appointmentId).get();
        OpConsultation opConsultation = opConsultationRepository.findByAppointmentDetails(appointmentDetails).get();

        try{
            Bundle bundle = new Bundle();

            //SETTING ID of the Bundle
            String UUIDCode = UUID.randomUUID().toString();
            bundle.setId(UUIDCode);

            //SETTING META data of the Bundle
            bundle.setMeta(new Meta().setVersionId("1"));

            //SETTING Identifiers
            bundle.setIdentifier(new Identifier()
                    .setSystem("https://hspsbx.abdm.gov.in")
                    .setValue(hospitailId));

            //SETTING Type of bundle
            bundle.setType(Bundle.BundleType.DOCUMENT);

            //SETTING DATE TIME of Bundle
            bundle.setTimestamp(new Date());

            //CREATING ENTRY list
            List<Bundle.BundleEntryComponent> bundleEntryComponentList = new ArrayList<>();

            //CREATING FIRST COMPONENT=COMPOSITION COMPONENT
            Bundle.BundleEntryComponent compositionEntry = new Bundle.BundleEntryComponent();
            compositionEntry.setFullUrl("Composition/" + "comp-" + careContext.getCareContextReference());
            compositionEntry.setResource(getCompositionFromCareContext(careContext,patientDetails,appointmentDetails, opConsultation));

            //SETTING COMPOSITION ENTRY
            bundleEntryComponentList.add(compositionEntry);

            //SETTING PATIENT ENTRY
            Bundle.BundleEntryComponent patientEntry = new Bundle.BundleEntryComponent();
            patientEntry.setFullUrl("Patient/" + "patient-" + appointmentDetails.getPatientId().getMrn());
            Patient patient = getPatient(patientDetails);
            patientEntry.setResource(patient);
            bundleEntryComponentList.add(patientEntry);

            //SETTING PRACTITIONER ENTRY
            Bundle.BundleEntryComponent practitionerEntry = new Bundle.BundleEntryComponent();
            practitionerEntry.setFullUrl("Practitioner/" + "practitioner-" + appointmentDetails.getDoctor_id());
            practitionerEntry.setResource(getPractitioner(appointmentDetails));
            bundleEntryComponentList.add(practitionerEntry);

            Bundle.BundleEntryComponent hospital = new Bundle.BundleEntryComponent();
            hospital.setFullUrl("Hospital/" + "hip-" + hospitailId);
            hospital.setResource(getCustodian());
            bundleEntryComponentList.add(hospital);

            //SETTING ENCOUNTER ENTRY
            Bundle.BundleEntryComponent encounterEntry = new Bundle.BundleEntryComponent();
            encounterEntry.setFullUrl("Encounter/" + "encounter-" + appointmentDetails.getAppointment_id());
            encounterEntry.setResource(getEncounter(appointmentDetails));
            bundleEntryComponentList.add(encounterEntry);

            //SETTING Report
                Bundle.BundleEntryComponent reportEntry = new Bundle.BundleEntryComponent();
                reportEntry.setFullUrl("Report/" + "Report-" +appointmentDetails.getAppointment_id());
                reportEntry.setResource(getReportDocumentReference(appointmentDetails,opConsultation));
                bundleEntryComponentList.add(reportEntry);

            //SETTING Attachment
            if (opConsultation.getFilePath() != null){
                    Bundle.BundleEntryComponent attachmentEntry = new Bundle.BundleEntryComponent();
                    attachmentEntry.setFullUrl("Attachment/" + "Attachment-" + appointmentDetails.getAppointment_id());
                    attachmentEntry.setResource(getAttachmentDocumentReference(opConsultation,appointmentDetails));
                    bundleEntryComponentList.add(attachmentEntry);
                }

            //SETTING ENTRY TO THE CREATED BUNDLE ENTRY
            bundle.setEntry(bundleEntryComponentList);
            return bundle;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



        public boolean validate(IBaseResource resource)
    {
        // Validate
        ValidationResult result = validator.validateWithResult(resource);

        // The result object now contains the validation results
        for (SingleValidationMessage next : result.getMessages()) {
            System.out.println(next.getSeverity().name() + " : " + next.getLocationString() + " " + next.getMessage());
        }

        return result.isSuccessful();
    }


    public String connvertCareContextToJsonString(CareContexts careContext) throws DataFormatException,IOException {
        Bundle OPConsultNoteBundle = convertCareContextToBundle(careContext);

        if(validate(OPConsultNoteBundle)) {
            IParser parser;
            parser = ctx.newJsonParser();

            parser.setPrettyPrint(true);

            // Serialize populated bundle
            String serializeBundle = parser.encodeResourceToString(OPConsultNoteBundle);
            return serializeBundle.replaceAll("\\n", "");
        }
        else{
            System.out.println("Failed to validate populated OPConsultNote bundle");
            return null;
        }
    }



}
