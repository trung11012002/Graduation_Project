package com.example.attendance.controller;

import com.example.attendance.client.LogServiceClient;
import com.example.attendance.client.ScoreServiceClient;
import com.example.attendance.client.StudentServiceClient;
import com.example.attendance.dto.AttendanceLog;
import com.example.attendance.dto.AttendanceScore;
import com.example.attendance.dto.Student;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Key;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.json.JSONObject;

@RestController
@RequestMapping("attendance")
public class AttendanceController {
    @Autowired
    private LogServiceClient logServiceClient;
    @Autowired
    private ScoreServiceClient scoreServiceClient;
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    @Autowired
    StudentServiceClient studentServiceClient;

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getEmailFromToken(HttpServletRequest request) {
        try {
            String bearerToken = request.getHeader("Authorization");
            if (bearerToken.startsWith("Bearer ")) {
                bearerToken = bearerToken.replaceAll("Bearer ", "").trim();
            }
            Claims claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(bearerToken).getBody();
            return claims.get("sub").toString();
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("all")
    public ResponseEntity<?> getAllByStudent(HttpServletRequest request){
        String email = getEmailFromToken(request);
        Student student = studentServiceClient.findByEmail(email).getBody();
        List<AttendanceLog> list = logServiceClient.getAllByStudent(student.getMsv()).getBody();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("info")
    public ResponseEntity<?> getInfo(HttpServletRequest request){
        String email = getEmailFromToken(request);
        Student student = studentServiceClient.findByEmail(email).getBody();
        return ResponseEntity.ok().body(student);
    }

    @PostMapping("/compare")
    public ResponseEntity<?> attendance(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        try {
            String email = getEmailFromToken(request);
            if (email == null || email.isEmpty()) {
                return ResponseEntity.badRequest().body("Email is required");
            }
            if (multipartFile == null || multipartFile.isEmpty()) {
                return ResponseEntity.badRequest().body("File is required");
            }

            // lưu tạm thơ file( gửi đi là dạng file thì request service cảu python cũng phải dạng file
            Path tempFile = Files.createTempFile("temp", multipartFile.getOriginalFilename());
            multipartFile.transferTo(tempFile.toFile());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new FileSystemResource(tempFile.toFile()));
            body.add("email", email);

            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange("http://localhost:5001/compare", HttpMethod.POST, entity, String.class);

            // Delete the temporary file
            Files.delete(tempFile);

            JSONObject jsonResponse = new JSONObject(response.getBody());
            // Get the "verified" value
            boolean isVerified = jsonResponse.getBoolean("verified");
            //  lấy  mã  sinh vieen hien tai
            Student a = studentServiceClient.findByEmail(email).getBody();
            String msv = a.getMsv();


            // Check  trả về service
            if (isVerified) {
                // Check trùng
                List<AttendanceLog> logs = logServiceClient.getAllByStudent(msv).getBody();
                for (AttendanceLog log : logs) {
                    if (log.getCurrentTime().toLocalDate().equals(LocalDate.now())) {
                        return ResponseEntity.status(HttpStatus.SC_CONFLICT).body("Bạn đã điểm danh hôm nay");
                    }
                }

                AttendanceLog newLog = new AttendanceLog();
                // check trun
                newLog.setMsv(msv);
                newLog.setName(a.getName());
                newLog.setCurrentTime(LocalDateTime.now());
                //  lưu db(new log dạng request body)
                logServiceClient.create(newLog);

                // lấy bảng điểm msv hiện tại
                // solve attendance score
                AttendanceScore attendanceScore = scoreServiceClient.retrieve(msv).getBody();
                if(attendanceScore == null){
                    AttendanceScore newEntity = new AttendanceScore();
                    newEntity.setMsv(a.getMsv());
                    newEntity.setAttendanceScore(1);
                    newEntity.setStatus(0);
                    newEntity.setBaseScore(10);
                    newEntity.setName(a.getName());
                    AttendanceScore attendanceScore1 = scoreServiceClient.create(newEntity).getBody();
                }else{
                    int baseScore = attendanceScore.getBaseScore();
                    int currentScore = attendanceScore.getAttendanceScore();
                    double dieuKien = 0.8 * baseScore;
                    if (currentScore < baseScore) {
                        currentScore += 1;
                    }
                    if(currentScore >= (int)dieuKien)
                    {
                        attendanceScore.setStatus(1);
                    }
                    else {
                        attendanceScore.setStatus(0);
                    }
                    attendanceScore.setAttendanceScore(currentScore);
                    scoreServiceClient.update(attendanceScore);
                }
                return ResponseEntity.ok("Điểm danh thành công");
            } else {
                return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body("Điểm danh thất bại(không khớp ảnh)");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}