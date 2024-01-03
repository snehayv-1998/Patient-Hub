package com.example.patientHub.controller;


import com.example.patientHub.model.Patient;
import com.example.patientHub.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@Api(tags = "Patient Controller", description = "Endpoints for managing patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    @ApiOperation("Get all patients")
    @ApiResponse(code = 200, message = "List of patients retrieved successfully")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("Get a patient by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Patient retrieved successfully"),
            @ApiResponse(code = 404, message = "Patient not found")
    })
    public ResponseEntity<Patient> getPatientById(
            @ApiParam(value = "ID of the patient", required = true) @PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }

    @PostMapping
    @ApiOperation("Create a new patient")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Patient created successfully"),
            @ApiResponse(code = 400, message = "Invalid input")
    })
    public ResponseEntity<Patient> createPatient(
            @ApiParam(value = "Patient object to be created", required = true) @RequestBody Patient patient) {
        Patient createdPatient = patientService.createPatient(patient);
        return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update an existing patient")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Patient updated successfully"),
            @ApiResponse(code = 404, message = "Patient not found"),
            @ApiResponse(code = 400, message = "Invalid input")
    })
    public ResponseEntity<Patient> updatePatient(
            @ApiParam(value = "ID of the patient to be updated", required = true) @PathVariable Long id,
            @ApiParam(value = "Updated patient information", required = true) @RequestBody Patient patient) {
        Patient updatedPatient = patientService.updatePatient(id, patient);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a patient by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Patient deleted successfully"),
            @ApiResponse(code = 404, message = "Patient not found")
    })
    public ResponseEntity<Void> deletePatient(
            @ApiParam(value = "ID of the patient to be deleted", required = true) @PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}