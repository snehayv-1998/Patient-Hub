package com.example.patientHub.service;

import com.example.patientHub.exceptionHandler.PatientNotFoundException;
import com.example.patientHub.model.Patient;
import com.example.patientHub.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService{
    @Autowired
    private PatientRepository patientRepository;

    @Override
    @Cacheable("patients")
    public List<Patient> getAllPatients() {
        return  patientRepository.findAll();
    }

    @Override
    @Cacheable(value = "patients")
    public Patient getPatientById(Long id) {

        Optional<Patient> patient= patientRepository.findById(id);
        if (patient.isPresent()) return patient.get();
        throw new PatientNotFoundException("Invalid Patient id!!");
    }

    @Override
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    @CachePut(cacheNames = "patients")
    public Patient updatePatient(Patient patient) {
        Patient patientData = getPatientById(patient.getId());
        patientData.setName(patient.getName());
        return patientRepository.save(patientData);
    }

    @Override
    @CacheEvict(cacheNames = "patients", allEntries = true)
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}
