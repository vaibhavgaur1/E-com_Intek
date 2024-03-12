package com.e_commerce.Dto;

import com.e_commerce.entity.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private Long id;
    private String flatNumberOrHouseNumber;
    private String area;
    private String landMark;
    private String city;
    private String state;
    private String addressType;
    private int userId;
}
