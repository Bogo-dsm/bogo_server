package org.example.bogo.domain.template.presentation.dto;

import lombok.RequiredArgsConstructor;
import org.example.bogo.domain.template.presentation.dto.request.AddConstraintRequest;
import org.example.bogo.domain.template.service.ConstraintService;
import org.example.bogo.global.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/constraint")
@RequiredArgsConstructor
public class ConstraintController {
    private final ConstraintService constraintService;
    @PostMapping("/set")
    public ResponseEntity<APIResponse<?>> post (
            @RequestBody AddConstraintRequest request) {
        return ResponseEntity.ok()
                .body(constraintService.post(request));
    }

}
