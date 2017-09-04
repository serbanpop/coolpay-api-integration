package com.currencycloud.integration.recipients;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(NON_NULL)
public class Recipient {

    private String id;
    private String name;

    public Recipient(String name) {
        this.name = name;
    }
}