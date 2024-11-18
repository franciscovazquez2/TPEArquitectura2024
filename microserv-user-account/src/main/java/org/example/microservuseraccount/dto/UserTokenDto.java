package org.example.microservuseraccount.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTokenDto {
    private Long id;
    private String user;
    private String password;
    private List<AuthorityDto> roles;
}
