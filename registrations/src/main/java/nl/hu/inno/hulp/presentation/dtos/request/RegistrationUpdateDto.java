package nl.hu.inno.hulp.presentation.dtos.request;

import nl.hu.inno.hulp.domain.enums.RegistrationStatus;

public class RegistrationUpdateDto {

    private RegistrationStatus status;

    public RegistrationUpdateDto(RegistrationStatus status) {
        this.status = status;
    }

    protected RegistrationUpdateDto() {
    }


    public RegistrationStatus getStatus() {
        return status;
    }

}

