package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.PatientRequest;
import PBL6.example.UNIME.dto.response.PatientResponse;
import PBL6.example.UNIME.entity.User;


import java.util.List;

public interface PatientService {

    /**
     * Retrieves a list of all patients.
     *
     * @return a list of {@code PatientResponse} objects representing all patients.
     */
    public List<PatientResponse> getAllPatients();

    /**
     * Retrieves a specific patient by their ID.
     *
     * @param patient_id the unique identifier of the patient.
     * @return a {@code PatientResponse} object containing the patient's details.
     */
    public PatientResponse getPatientById(Integer patient_id);

    /**
     * Updates an existing patient's information based on their username.
     *
     * @param patient_username the username of the patient to update.
     * @param request the {@code PatientRequest} object containing the updated patient information.
     * @return the updated {@code PatientResponse} object.
     */
    public PatientResponse updatePatient(String patient_username, PatientRequest request);

    /**
     * Deletes a patient from the system by their ID.
     *
     * @param patient_id the unique identifier of the patient to delete.
     */
    public User changePatientStatus(Integer patient_id);

    /**
     * Retrieves the information of the currently logged-in patient based on their username.
     *
     * @param Username the username of the patient.
     * @return a {@code PatientResponse} object containing the patient's information.
     */
    public PatientResponse getMyInfo(String Username);

    /**
     * Creates a new patient in the system.
     *
     * @param request the {@code PatientRequest} object containing the patient's information.
     * @return the created {@code PatientResponse} object.
     */
    public PatientResponse createPatient(PatientRequest request);

    /**
     * Retrieves a list of IDs of patients who have at least one appointment.
     *
     * @return a list of patient IDs who have appointments.
     */
    public List<Integer> getPatientsHaveAppointment();


}
