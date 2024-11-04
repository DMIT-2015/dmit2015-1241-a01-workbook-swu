package dmit2015.mapper;

import dmit2015.dto.CountryDto;
import dmit2015.entity.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * This MapStruct interface contains methods on how to map a Jakarta Persistence entity to a DTO
 * (Data Transfer Object) and a method on how to map a DTO to a JPA entity.
 * <p>
 * The following code snippets shows how to call that class-level methods.
 * {@snippet :
 * //Country newCountryEntity = CountryMapper.INSTANCE.toEntity(newCountryDto);
 * //CountryDto newCountryDto = CountryMapper.INSTANCE.toDto(newCountryEntity);
 * }
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CountryMapper {

    CountryMapper INSTANCE = Mappers.getMapper( CountryMapper.class );

    // You only need to specify property names that are not the same in the source and target.
     @Mappings({
         @Mapping(target = "id", source = "countryId"),
         @Mapping(target = "name", source = "countryName"),
         @Mapping(target = "regionId", source = "region.id"),
         @Mapping(target = "regionName", source = "region.regionName"),
     })
    CountryDto toDto(Country entity);

    // You only need to specify property names that are not the same in the source and target.
     @Mappings({
         @Mapping(target = "countryId", source = "id"),
         @Mapping(target = "countryName", source = "name")
     })
    Country toEntity(CountryDto dto);

}