package com.example.attendancehistory.service.Impl;

import com.example.attendancehistory.dto.AttendanceLogDto;
import com.example.attendancehistory.entity.AttendanceLog;
import com.example.attendancehistory.repository.AttendanceLogRepository;
import com.example.attendancehistory.service.AttendanceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceLogServiceImpl implements AttendanceLogService {

    @Autowired
    private AttendanceLogRepository repository;

    @Override
    public AttendanceLog create(AttendanceLog log) {
        return repository.save(log);
    }

    @Override
    public List<AttendanceLog> findAllByMsv(String msv) {
        return repository.findAllByMsv(msv);
    }

    @Override
    public void update(Integer id, AttendanceLog log) {
        repository.save(log);
    }

    @Override
    public AttendanceLog retrieve(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<AttendanceLog> getLog() {
        String startDate = LocalDate.now().toString() + "00:00:00";
        String endDate = LocalDate.now().toString() + "23:59:59";
        List<AttendanceLog> list = repository.findByCurrentTime(startDate, endDate);
        return list;
    }

    @Override
    public AttendanceLogDto convertDtoToEntity(AttendanceLog dto) {
        AttendanceLogDto entity = new AttendanceLogDto();
        entity.setMsv(dto.getMsv());
        entity.setName(dto.getName());
        entity.setCurrentTime(dto.getCurrentTime());
        return entity;
    }
}
