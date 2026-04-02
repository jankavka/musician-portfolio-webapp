package cz.kavka.service;

import cz.kavka.dto.ContactDto;

public interface ContactService {

    void updateContacts(ContactDto contactDto);

    ContactDto getContacts();

}
