package com.example.patientHub;

import com.example.patientHub.exceptionHandler.PatientNotFoundException;
import com.example.patientHub.model.Patient;
import com.example.patientHub.repository.PatientRepository;
import com.example.patientHub.service.PatientService;
import com.example.patientHub.service.PatientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@EnableCaching
public class PatientHubApplicationTests {

	@InjectMocks
	private PatientServiceImpl patientService;

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
		when(patientRepository.findById(any())).thenReturn(Optional.empty());

		// Using assertThrows to verify that PatientNotFoundException is thrown
		assertThrows(PatientNotFoundException.class, () -> patientService.getPatientById(any()));
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
		// Arrange
		Long patientId = 1L;
		String updatedName = "UpdatedName";

		// Create a patient with the updated name
		Patient updatedPatient = new Patient();
		updatedPatient.setId(patientId);
		updatedPatient.setName(updatedName);

		// Mock the behavior of getPatientById
		when(patientRepository.findById(patientId)).thenReturn(Optional.of(new Patient()));

		// Mock the behavior of save
		when(patientRepository.save(any())).thenReturn(updatedPatient);

		// Act
		Patient result = patientService.updatePatient(updatedPatient);

		// Assert
		verify(patientRepository).findById(patientId);
		verify(patientRepository).save(any());

		// You can add additional assertions based on your specific requirements
		assertEquals(updatedName, result.getName());
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
