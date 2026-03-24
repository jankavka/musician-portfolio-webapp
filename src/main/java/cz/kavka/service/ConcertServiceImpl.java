package cz.kavka.service;

import cz.kavka.dto.ConcertDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ConcertServiceImpl implements ConcertService {


    @Override
    public void createConcert(ConcertDto concertDto) {
    }

    @Override
    public ConcertDto getConcert(Long id) {
        return null;
    }

    @Override
    public List<ConcertDto> getAllConcerts() {
        return List.of();
    }

    @Override
    public void editConcert(Long id) {

    }

    @Override
    public void deleteConcert(Long id) {

    }
}
