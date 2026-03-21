package cz.kavka.dto.mapper;

import cz.kavka.dto.ProjectDto;
import cz.kavka.entity.ProjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectDto toDto(ProjectEntity projectEntity);

    ProjectEntity toEntity(ProjectDto projectDto);

    void updateEntity(@MappingTarget ProjectEntity target, ProjectDto source);
}
