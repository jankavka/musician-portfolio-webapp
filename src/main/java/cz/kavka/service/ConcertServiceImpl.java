package cz.kavka.service;

import cz.kavka.dto.ConcertDto;
import cz.kavka.dto.mapper.ConcertMapper;
import cz.kavka.entity.repository.ConcertRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

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
        var concertEntity = concertRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityNotFoundExceptionMessage(SERVICE_NAME, id)));

        concertEntity.setFormattedTime(formatTime(concertEntity.getStartDateTime()));

        return concertMapper.toDto(concertEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ConcertDto> getAllConcerts() {
        var allConcerts = concertRepository.findAll();
        allConcerts.forEach(concertEntity -> concertEntity
                .setFormattedTime(formatTime(concertEntity.getStartDateTime())));

        return allConcerts
                .stream()
                .map(concertMapper::toDto)
                .sorted(Comparator.comparing(ConcertDto::startDateTime))
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

    public String formatTime(LocalDateTime time) {
        return DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)
                .withLocale(Locale.of("cs"))
                .withZone(ZoneId.of("Europe/Prague"))
                .format(time);
    }
}
