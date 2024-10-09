package com.example.attendancescore.service.Impl;

import com.example.attendancescore.entity.AttendanceScore;
import com.example.attendancescore.repository.AttendanceScoreRepository;
import com.example.attendancescore.service.AttendanceScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceScoreServiceImpl implements AttendanceScoreService {

    @Autowired
    private AttendanceScoreRepository repository;

    @Override
    public void update(AttendanceScore score) {
        repository.save(score);
    }

    @Override
    public AttendanceScore retrieve(String msv) {
        return repository.findAttendanceScoreByMsv(msv);
    }

    @Override
    public List<AttendanceScore> getAll() {
        return repository.findAll();
    }

    @Override
    public AttendanceScore create(AttendanceScore entity) {
        return repository.save(entity);
    }
}
