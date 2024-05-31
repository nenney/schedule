package com.sparta.schedule.service;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sparta.schedule.controller.ScheduleRequestDTO;
import com.sparta.schedule.repository.Schedule;
import com.sparta.schedule.repository.ScheduleRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    // 할일 생성
    public Schedule createSchedule(ScheduleRequestDTO dto) {
        var newSchedule = dto.toEntity();
        return scheduleRepository.save(newSchedule);
    }

    // 할일 단건 조회
    public Schedule getSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(IllegalArgumentException::new);
    }

    // 할일 전체 조회
    public List<Schedule> getSchedules() {
        return scheduleRepository.findAll(Sort.by("createdAt").descending());
    }

    // 할일 수정
    public Schedule updateSchedule(Long scheduleId, ScheduleRequestDTO dto) {
        Schedule schedule = checkPWAndGetSchedule(scheduleId, dto.getPassword());

        schedule.setTitle(dto.getTitle());
        schedule.setContent(dto.getContent());
        schedule.setUserName(dto.getUserName());

        return scheduleRepository.save(schedule);
    }

    private Schedule checkPWAndGetSchedule(Long scheduleId, String password) {
        Schedule schedule = getSchedule(scheduleId);

        // 비밀번호 체크
        if (schedule.getPassword() != null
                && !Objects.equals(schedule.getPassword(), password)) {
            throw new IllegalArgumentException();
        }
        return schedule;
    }

    public void deleteSchedule(Long scheduleId, String password) {
        Schedule schedule = checkPWAndGetSchedule(scheduleId, password);

        scheduleRepository.delete(schedule);
    }
}