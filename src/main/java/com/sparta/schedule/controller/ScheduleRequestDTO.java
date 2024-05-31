package com.sparta.schedule.controller;

import com.sparta.schedule.repository.Schedule;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequestDTO {

    private String title;

    private String content;

    private String userName;

    private String password;

    public Schedule toEntity() {
        return Schedule.builder()
                .title(title)
                .content(content)
                .userName(userName)
                .password(password)
                .build();
    }
}