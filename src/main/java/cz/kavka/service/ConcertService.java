package cz.kavka.service;

import cz.kavka.dto.ConcertDto;

import java.util.List;

public interface ConcertService {

    ConcertDto createConcert(ConcertDto concertDto);

    ConcertDto getConcert(Long id);

    List<ConcertDto> getAllConcerts();

    void editConcert(Long id);

    void deleteConcert(Long id);


}
