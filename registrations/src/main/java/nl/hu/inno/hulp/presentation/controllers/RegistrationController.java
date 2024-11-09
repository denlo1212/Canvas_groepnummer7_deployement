package nl.hu.inno.hulp.presentation.controllers;


import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.application.RegistrationService;
import nl.hu.inno.hulp.application.exceptions.NotFoundException;
import nl.hu.inno.hulp.presentation.dtos.request.RegistrationRequestDto;
import nl.hu.inno.hulp.presentation.dtos.request.RegistrationUpdateDto;
import nl.hu.inno.hulp.presentation.dtos.response.RegistrationResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/registration")
@Transactional
public class RegistrationController {

    private final RegistrationService service;

    public RegistrationController(RegistrationService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public RegistrationResponseDto createRegistration(@RequestBody RegistrationRequestDto registrationRequestDto) throws NotFoundException {
        return service.newRegistration(registrationRequestDto);
    }

    @GetMapping("/{id}")
    public RegistrationResponseDto getRegistrationById(@PathVariable long id) throws NotFoundException {
        try {
            return service.getRegistration(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public RegistrationResponseDto updateRegistration(@PathVariable long id, @RequestBody RegistrationUpdateDto registrationUpdateDto) throws NotFoundException {
        try {
            return service.updateRegistration(id, registrationUpdateDto);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public RegistrationResponseDto deleteRegistration(@PathVariable long id) throws NotFoundException {
        try {
            return service.deleteRegistration(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}