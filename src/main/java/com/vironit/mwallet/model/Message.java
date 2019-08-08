package com.vironit.mwallet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String time = LocalTime.now().toString();
    private String content;
    private String sender;
    private String receiver;

}