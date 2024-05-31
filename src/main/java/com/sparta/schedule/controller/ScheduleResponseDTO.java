package com.sparta.schedule.controller;

import java.time.LocalDateTime;

import com.sparta.schedule.repository.Schedule;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleResponseDTO {

    private Long scheduleId;
    private String title;

    private String content;

    private String userName;

    private LocalDateTime createdAt;

    public ScheduleResponseDTO(Schedule schedule) {
        this.scheduleId = schedule.getScheduleId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.userName = schedule.getUserName();
        this.createdAt = schedule.getCreatedAt();
    }
}