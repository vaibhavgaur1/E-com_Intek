package com.e_commerce.entity;

import com.e_commerce.Dto.AddressDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter@Setter
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String flatNumberOrHouseNumber;
    private String area;
    private String landMark;
    private String city;
    private String state;
    private String addressType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonBackReference
    private User user;

    public AddressDto getDto(){
        return AddressDto.builder()
                .id(id)
                .flatNumberOrHouseNumber(flatNumberOrHouseNumber)
                .area(area)
                .landMark(landMark)
                .city(city)
                .state(state)
                .addressType(addressType)
                .userId(user.getId())
                .build();
    }

}
