package com.life.ai.controller;

import com.life.ai.dto.LifeEventRequest;
import com.life.ai.model.User;
import com.life.ai.service.LifeEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/memory")
@RequiredArgsConstructor
public class LifeEventController {

    private final LifeEventService lifeEventService;

    @PostMapping(
            value = "/add",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> addMemory(
            @RequestPart("data") LifeEventRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @AuthenticationPrincipal User user
    ) {

        lifeEventService.addMemory(request, image, user);

        return ResponseEntity.ok("Memory + Image saved successfully");
    }
}
