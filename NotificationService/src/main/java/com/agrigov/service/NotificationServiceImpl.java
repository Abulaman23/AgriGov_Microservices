package com.agrigov.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.agrigov.dto.NotificationRequest;
import com.agrigov.dto.NotificationResponse;
import com.agrigov.enums.NotificationStatus;
import com.agrigov.model.Notification;
import com.agrigov.repository.NotificationRepository;

import jakarta.transaction.Transactional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    @Transactional
    public NotificationResponse createNotification(NotificationRequest request) {
        // --- Setting Default Values ---
        if (request.getUserId() == null) {
            logger.info("User ID is null, setting default ID: 0");
            request.setUserId(0L);
        }

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            logger.info("Email is null/empty, setting default email: admin@agrigov.com");
            request.setEmail("gs180271@gmail.com");
        }
        // ------------------------------

        logger.info("Processing notification for User ID: {} with Email: {}", 
                    request.getUserId(), request.getEmail());

        Notification notification = mapToEntity(request);
        Notification savedNotification = notificationRepository.save(notification);
        
        logger.debug("Notification persisted with ID: {}", savedNotification.getNotificationId());

        // Use the (potentially defaulted) email from the request
        sendEmailNotification(request.getEmail(), request.getMessage());

        return mapToResponse(savedNotification);
    }

    private void sendEmailNotification(String recipientEmail, String messageContent) {
        try {
            logger.info("Initiating email delivery to: {}", recipientEmail);

            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(recipientEmail);
            mail.setSubject("AgriGov: New Notification");
            mail.setText(messageContent);

            mailSender.send(mail);
            logger.info("Email successfully sent to: {}", recipientEmail);
        } catch (Exception e) {
            logger.error("Failed to send email to {}: {}", recipientEmail, e.getMessage());
        }
    }

    @Override
    public Page<NotificationResponse> getUserNotifications(Long userId, Pageable pageable) {
        logger.info("Fetching notifications for User ID: {}", userId);
        return notificationRepository.findByUserId(userId, pageable).map(this::mapToResponse);
    }

    @Override
    @Transactional
    public NotificationResponse markAsRead(Long notificationId) {
        return notificationRepository.findById(notificationId).map(n -> {
            n.setStatus(NotificationStatus.READ);
            Notification updated = notificationRepository.save(n);
            logger.info("Notification {} marked as READ", notificationId);
            return mapToResponse(updated);
        }).orElseThrow(() -> new RuntimeException("Notification not found with ID: " + notificationId));
    }

    @Override
    public Page<NotificationResponse> getAllNotifications(Pageable pageable) {
        return notificationRepository.findAll(pageable).map(this::mapToResponse);
    }

    private Notification mapToEntity(NotificationRequest request) {
        Notification notification = new Notification();
        notification.setUserId(request.getUserId());
        notification.setEntityId(request.getEntityId());
        notification.setMessage(request.getMessage());
        notification.setCategory(request.getCategory());
        notification.setCreatedDate(LocalDateTime.now());
        notification.setStatus(NotificationStatus.SENT);
        return notification;
    }

    private NotificationResponse mapToResponse(Notification entity) {
        NotificationResponse res = new NotificationResponse();
        res.setNotificationId(entity.getNotificationId());
        res.setUserId(entity.getUserId());
        res.setEntityId(entity.getEntityId());
        res.setMessage(entity.getMessage());
        res.setCategory(entity.getCategory());
        res.setStatus(entity.getStatus());
        res.setCreatedDate(entity.getCreatedDate());
        return res;
    }
}