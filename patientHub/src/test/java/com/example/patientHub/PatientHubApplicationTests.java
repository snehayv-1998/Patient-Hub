package com.example.patientHub;

import com.example.patientHub.exceptionHandler.PatientNotFoundException;
import com.example.patientHub.model.Patient;
import com.example.patientHub.repository.PatientRepository;
import com.example.patientHub.service.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@EnableCaching
public class PatientHubApplicationTests {

	@Autowired
	private PatientService patientService;

	@MockBean
	private PatientRepository patientRepository;
	@Autowired
	private CacheManager cacheManager;

	@Test
	public void testGetAllPatients() {
		// Mock data
		Patient patient1 = new Patient(1L, "John Doe");
		Patient patient2 = new Patient(2L, "Jane Doe");
		List<Patient> patients = Arrays.asList(patient1, patient2);

		// Mock repository response
		when(patientRepository.findAll()).thenReturn(patients);

		// Call the service method
		List<Patient> result = patientService.getAllPatients();

		// Assertions
		assertEquals(2, result.size());
		assertEquals(patient1, result.get(0));
		assertEquals(patient2, result.get(1));
	}

	@Test
	public void testGetPatientById() {
		// Mock data
		Long patientId = 1L;
		Patient patient = new Patient(patientId, "John Doe");

		// Mock repository response
		when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

		// Call the service method
		Patient result = patientService.getPatientById(patientId);

		// Assertions
		assertNotNull(result);
		assertEquals(patient, result);
	}

	@Test
	public void testGetPatientByIdNotFound() {
		// Mocking repository behavior
		when(patientRepository.findById(1L)).thenReturn(Optional.empty());

		// Using assertThrows to verify that PatientNotFoundException is thrown
		assertThrows(PatientNotFoundException.class, () -> patientService.getPatientById(1L));
	}

	@Test
	public void testCreatePatient() {
		// Mock data
		Patient patientToCreate = new Patient(null, "New Patient");
		Patient createdPatient = new Patient(1L, "New Patient");

		// Mock repository response
		when(patientRepository.save(patientToCreate)).thenReturn(createdPatient);

		// Call the service method
		Patient result = patientService.createPatient(patientToCreate);

		// Assertions
		assertNotNull(result);
		assertEquals(createdPatient, result);
	}

	@Test
	public void testUpdatePatient() {
		// Mock data
		Long patientId = 1L;
		Patient existingPatient = new Patient(patientId, "Existing Patient");
		Patient updatedPatient = new Patient(patientId, "Updated Patient");

		// Mock repository response
		when(patientRepository.findById(patientId)).thenReturn(Optional.of(existingPatient));
		when(patientRepository.save(updatedPatient)).thenReturn(updatedPatient);

		// Call the service method
		Patient result = patientService.updatePatient(patientId, updatedPatient);

		// Assertions
		assertNotNull(result);
		assertEquals(updatedPatient, result);
	}

	@Test
	public void testUpdatePatientNotFound() {
		// Mock data
		Long patientId = 1L;
		Patient updatedPatient = new Patient(patientId, "Updated Patient");

		// Mock repository response for not found case
		when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

		// Call the service method
		Patient result = patientService.updatePatient(patientId, updatedPatient);

		// Assertions
		assertNull(result);
	}

	@Test
	public void testDeletePatient() {
		// Mock data
		Long patientId = 1L;

		// Call the service method
		patientService.deletePatient(patientId);

		// Verify that the repository's deleteById method was called with the correct ID
		verify(patientRepository).deleteById(patientId);
	}

}
