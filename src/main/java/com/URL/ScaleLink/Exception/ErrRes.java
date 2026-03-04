package com.URL.ScaleLink.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrRes {

    private LocalDateTime timeStamp;
    private int Status;
    private String errMsg;

    public ErrRes(int value, String message) {

    }
}
