package cz.kavka.service;

import cz.kavka.dto.ConcertDto;

import java.util.List;

public interface ConcertService {

    void createConcert(ConcertDto concertDto);

    ConcertDto getConcert(Long id);

    List<ConcertDto> getAllConcerts();

    void editConcert(ConcertDto concertDto, Long id);

    void deleteConcert(Long id);


}
