package com.example.attendancehistory.service;

import com.example.attendancehistory.dto.AttendanceLogDto;
import com.example.attendancehistory.entity.AttendanceLog;

import java.util.List;

public interface AttendanceLogService {
    AttendanceLog create(AttendanceLog log);
    List<AttendanceLog> findAllByMsv(String email);
    void update(Integer id, AttendanceLog log);
    AttendanceLog retrieve(Integer id);
    List<AttendanceLog> getLog();
    public AttendanceLogDto convertDtoToEntity(AttendanceLog dto) ;

}
