
package io.geolocation.api;

import jakarta.annotation.Generated;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({
    "ip",
    "hostname",
    "continent_code",
    "continent_name",
    "country_code2",
    "country_code3",
    "country_name",
    "country_capital",
    "state_prov",
    "district",
    "city",
    "zipcode",
    "latitude",
    "longitude",
    "is_eu",
    "calling_code",
    "country_tld",
    "languages",
    "country_flag",
    "geoname_id",
    "isp",
    "connection_type",
    "organization",
    "asn",
    "currency",
    "time_zone"
})
@Generated("jsonschema2pojo")
public class IpGeoLocationApiResponse {

    @JsonbProperty("ip")
    private String ip;
    @JsonbProperty("hostname")
    private String hostname;
    @JsonbProperty("continent_code")
    private String continentCode;
    @JsonbProperty("continent_name")
    private String continentName;
    @JsonbProperty("country_code2")
    private String countryCode2;
    @JsonbProperty("country_code3")
    private String countryCode3;
    @JsonbProperty("country_name")
    private String countryName;
    @JsonbProperty("country_capital")
    private String countryCapital;
    @JsonbProperty("state_prov")
    private String stateProv;
    @JsonbProperty("district")
    private String district;
    @JsonbProperty("city")
    private String city;
    @JsonbProperty("zipcode")
    private String zipcode;
    @JsonbProperty("latitude")
    private String latitude;
    @JsonbProperty("longitude")
    private String longitude;
    @JsonbProperty("is_eu")
    private Boolean isEu;
    @JsonbProperty("calling_code")
    private String callingCode;
    @JsonbProperty("country_tld")
    private String countryTld;
    @JsonbProperty("languages")
    private String languages;
    @JsonbProperty("country_flag")
    private String countryFlag;
    @JsonbProperty("geoname_id")
    private String geonameId;
    @JsonbProperty("isp")
    private String isp;
    @JsonbProperty("connection_type")
    private String connectionType;
    @JsonbProperty("organization")
    private String organization;
    @JsonbProperty("asn")
    private String asn;
    @JsonbProperty("currency")
    private Currency currency;
    @JsonbProperty("time_zone")
    private TimeZone timeZone;

    @JsonbProperty("ip")
    public String getIp() {
        return ip;
    }

    @JsonbProperty("ip")
    public void setIp(String ip) {
        this.ip = ip;
    }

    @JsonbProperty("hostname")
    public String getHostname() {
        return hostname;
    }

    @JsonbProperty("hostname")
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @JsonbProperty("continent_code")
    public String getContinentCode() {
        return continentCode;
    }

    @JsonbProperty("continent_code")
    public void setContinentCode(String continentCode) {
        this.continentCode = continentCode;
    }

    @JsonbProperty("continent_name")
    public String getContinentName() {
        return continentName;
    }

    @JsonbProperty("continent_name")
    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    @JsonbProperty("country_code2")
    public String getCountryCode2() {
        return countryCode2;
    }

    @JsonbProperty("country_code2")
    public void setCountryCode2(String countryCode2) {
        this.countryCode2 = countryCode2;
    }

    @JsonbProperty("country_code3")
    public String getCountryCode3() {
        return countryCode3;
    }

    @JsonbProperty("country_code3")
    public void setCountryCode3(String countryCode3) {
        this.countryCode3 = countryCode3;
    }

    @JsonbProperty("country_name")
    public String getCountryName() {
        return countryName;
    }

    @JsonbProperty("country_name")
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @JsonbProperty("country_capital")
    public String getCountryCapital() {
        return countryCapital;
    }

    @JsonbProperty("country_capital")
    public void setCountryCapital(String countryCapital) {
        this.countryCapital = countryCapital;
    }

    @JsonbProperty("state_prov")
    public String getStateProv() {
        return stateProv;
    }

    @JsonbProperty("state_prov")
    public void setStateProv(String stateProv) {
        this.stateProv = stateProv;
    }

    @JsonbProperty("district")
    public String getDistrict() {
        return district;
    }

    @JsonbProperty("district")
    public void setDistrict(String district) {
        this.district = district;
    }

    @JsonbProperty("city")
    public String getCity() {
        return city;
    }

    @JsonbProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonbProperty("zipcode")
    public String getZipcode() {
        return zipcode;
    }

    @JsonbProperty("zipcode")
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @JsonbProperty("latitude")
    public String getLatitude() {
        return latitude;
    }

    @JsonbProperty("latitude")
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @JsonbProperty("longitude")
    public String getLongitude() {
        return longitude;
    }

    @JsonbProperty("longitude")
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @JsonbProperty("is_eu")
    public Boolean getIsEu() {
        return isEu;
    }

    @JsonbProperty("is_eu")
    public void setIsEu(Boolean isEu) {
        this.isEu = isEu;
    }

    @JsonbProperty("calling_code")
    public String getCallingCode() {
        return callingCode;
    }

    @JsonbProperty("calling_code")
    public void setCallingCode(String callingCode) {
        this.callingCode = callingCode;
    }

    @JsonbProperty("country_tld")
    public String getCountryTld() {
        return countryTld;
    }

    @JsonbProperty("country_tld")
    public void setCountryTld(String countryTld) {
        this.countryTld = countryTld;
    }

    @JsonbProperty("languages")
    public String getLanguages() {
        return languages;
    }

    @JsonbProperty("languages")
    public void setLanguages(String languages) {
        this.languages = languages;
    }

    @JsonbProperty("country_flag")
    public String getCountryFlag() {
        return countryFlag;
    }

    @JsonbProperty("country_flag")
    public void setCountryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }

    @JsonbProperty("geoname_id")
    public String getGeonameId() {
        return geonameId;
    }

    @JsonbProperty("geoname_id")
    public void setGeonameId(String geonameId) {
        this.geonameId = geonameId;
    }

    @JsonbProperty("isp")
    public String getIsp() {
        return isp;
    }

    @JsonbProperty("isp")
    public void setIsp(String isp) {
        this.isp = isp;
    }

    @JsonbProperty("connection_type")
    public String getConnectionType() {
        return connectionType;
    }

    @JsonbProperty("connection_type")
    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    @JsonbProperty("organization")
    public String getOrganization() {
        return organization;
    }

    @JsonbProperty("organization")
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @JsonbProperty("asn")
    public String getAsn() {
        return asn;
    }

    @JsonbProperty("asn")
    public void setAsn(String asn) {
        this.asn = asn;
    }

    @JsonbProperty("currency")
    public Currency getCurrency() {
        return currency;
    }

    @JsonbProperty("currency")
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @JsonbProperty("time_zone")
    public TimeZone getTimeZone() {
        return timeZone;
    }

    @JsonbProperty("time_zone")
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

}
