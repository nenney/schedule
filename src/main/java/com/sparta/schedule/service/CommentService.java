package com.sparta.schedule.service;

import com.sparta.schedule.repository.*;
import com.sparta.schedule.repository.Comment;
import com.sparta.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Comment addComment(Long scheduleId, String content, String userId) {
        // 선택한 일정이 DB에 저장되어 있는지 확인
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        // 댓글 내용이 비어 있는지 확인
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용을 입력해주세요.");
        }

        // 댓글 작성 시간 설정
        LocalDateTime createdAt = LocalDateTime.now();

        // 댓글 저장
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUserId(userId);
        comment.setSchedule(schedule);
        comment.setCreatedAt(Timestamp.valueOf(createdAt));

        return commentRepository.save(comment);
    }
}