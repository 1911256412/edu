package com.he.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeException extends RuntimeException {
    private Integer  code;
    private String msg;
}
