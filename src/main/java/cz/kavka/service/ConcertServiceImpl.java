package cz.kavka.service;

import cz.kavka.dto.ConcertDto;
import cz.kavka.dto.mapper.ProjectMapper;
import cz.kavka.entity.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ConcertServiceImpl implements ConcertService{



    @Override
    public ConcertDto createConcert(ConcertDto concertDto) {
        return null;
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
