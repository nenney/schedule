package com.sparta.schedule.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.schedule.CommonResponse;
import com.sparta.schedule.repository.Schedule;
import com.sparta.schedule.service.ScheduleService;
import lombok.AllArgsConstructor;

@RequestMapping("/v1.0/schedule")
@RestController
@AllArgsConstructor
public class ScheduleController {

    public final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<CommonResponse<ScheduleResponseDTO>> postSchedule(@RequestBody ScheduleRequestDTO dto) {
        Schedule schedule = scheduleService.createSchedule(dto);
        ScheduleResponseDTO response = new ScheduleResponseDTO(schedule);
        return ResponseEntity.ok()
                .body(CommonResponse.<ScheduleResponseDTO>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("생성이 완료 되었습니다.")
                        .data(response)
                        .build());
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<CommonResponse<ScheduleResponseDTO>> getSchedule(@PathVariable Long scheduleId) {
        Schedule schedule = scheduleService.getSchedule(scheduleId);
        ScheduleResponseDTO response = new ScheduleResponseDTO(schedule);
        return ResponseEntity.ok()
                .body(CommonResponse.<ScheduleResponseDTO>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("단건 조회가 완료 되었습니다.")
                        .data(response)
                        .build());
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<ScheduleResponseDTO>>> getSchedules() {
        List<Schedule> schedules = scheduleService.getSchedules();
        List<ScheduleResponseDTO> response = schedules.stream()
                .map(ScheduleResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok()
                .body(CommonResponse.<List<ScheduleResponseDTO>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("목록 조회이 완료 되었습니다.")
                        .data(response)
                        .build());
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<CommonResponse<ScheduleResponseDTO>> putSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleRequestDTO dto) {
        Schedule schedule = scheduleService.updateSchedule(scheduleId, dto);
        ScheduleResponseDTO response = new ScheduleResponseDTO(schedule);
        return ResponseEntity.ok()
                .body(CommonResponse.<ScheduleResponseDTO>builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("수정이 완료 되었습니다.")
                        .data(response)
                        .build());
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<CommonResponse> deleteSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleRequestDTO dto) {
        scheduleService.deleteSchedule(scheduleId, dto.getPassword());
        return ResponseEntity.ok().body(CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .msg("삭제가 완료 되었습니다.")
                .build());
    }
}