package cz.kavka.service;

import cz.kavka.dto.AboutMeDto;

public interface AboutMeService {

    AboutMeDto getInfo();

    void editInfo(AboutMeDto source);
}
