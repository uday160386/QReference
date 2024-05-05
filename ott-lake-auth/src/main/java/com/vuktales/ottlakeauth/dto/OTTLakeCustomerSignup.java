package com.vuktales.ottlakeauth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
        import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
        import lombok.Builder;
        import lombok.Data;
        import lombok.NoArgsConstructor;
        import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@ToString
@Table(name="ott-customers")
public class OTTLakeCustomerSignup {

    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private int statusCode;
    private String statusMessage;
}
