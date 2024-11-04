package dmit2015.dto;

import lombok.Data;

@Data
public class CountryDto {

    private String id;
    private String name;
    private Long regionId;
    private String regionName;

}
