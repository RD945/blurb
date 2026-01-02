package com.had0uken.blurb.payload.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponse extends Response implements Serializable {
    @Serial
    @JsonIgnore
    private static final long serialVersionUID = 6037589586457714671L;
    private String token;

    @JsonIgnore
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.OK;
    }
}
