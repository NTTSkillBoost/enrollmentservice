package br.com.nttdata.nttskillboost.activitymanagementservice.adapters.gateway;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class ProgressEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${progress.queue.name}")
    private String progressQueue;

    public ProgressEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendProgressEvent(UUID studentId, UUID courseId, String status) {

        Map<String, Object> payload = new HashMap<>();
        payload.put("studentId", studentId.toString());
        payload.put("courseId", courseId.toString());
        payload.put("status", status);
        payload.put("startDate", LocalDateTime.now().toString());
        payload.put("completionDate", null);
        payload.put("accumulatedPoints", 0);

        rabbitTemplate.convertAndSend(progressQueue, payload);
    }
}