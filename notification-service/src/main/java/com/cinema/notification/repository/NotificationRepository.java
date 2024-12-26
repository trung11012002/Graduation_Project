package com.cinema.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cinema.notification.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    @Query("SELECT n FROM Notification n WHERE n.type = 'global' OR (n.type = 'only-user' AND n.user.id = :userId)")
    List<Notification> findByUserId(Integer userId);
}
