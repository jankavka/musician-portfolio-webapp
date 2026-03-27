package cz.kavka.service;

import cz.kavka.dto.ConcertDto;
import cz.kavka.dto.mapper.ConcertMapper;
import cz.kavka.entity.repository.ConcertRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cz.kavka.service.exception.message.ExceptionMessage.entityNotFoundExceptionMessage;

@RequiredArgsConstructor
@Service
public class ConcertServiceImpl implements ConcertService {

    private final ConcertMapper concertMapper;

    private final ConcertRepository concertRepository;

    private static final String SERVICE_NAME = "koncert";


    @Transactional
    @Override
    public void createConcert(ConcertDto concertDto) {
        var entityToSave = concertMapper.toEntity(concertDto);
        concertRepository.save(entityToSave);
    }

    @Transactional(readOnly = true)
    @Override
    public ConcertDto getConcert(Long id) {
        return concertMapper.toDto(concertRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityNotFoundExceptionMessage(SERVICE_NAME, id))));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ConcertDto> getAllConcerts() {
        return concertRepository
                .findAll()
                .stream()
                .map(concertMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public void editConcert(ConcertDto concertDto, Long id) {
        var entityToUpdate = concertRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityNotFoundExceptionMessage(SERVICE_NAME, id)));
        concertMapper.updateEntity(entityToUpdate, concertDto);


    }

    @Transactional
    @Override
    public void deleteConcert(Long id) {
        if (concertRepository.existsById(id)) {
            concertRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(entityNotFoundExceptionMessage(SERVICE_NAME, id));
        }
    }
}
