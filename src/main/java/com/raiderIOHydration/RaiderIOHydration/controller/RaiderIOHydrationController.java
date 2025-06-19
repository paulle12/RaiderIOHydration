package com.raiderIOHydration.RaiderIOHydration.controller;

import com.raiderIOHydration.RaiderIOHydration.dto.HydratedPlayer;
import com.raiderIOHydration.RaiderIOHydration.service.RaiderIOService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hydrate")
public class RaiderIOHydrationController {

    private final RaiderIOService raiderIOService;

    public RaiderIOHydrationController(RaiderIOService raiderIOService) {
        this.raiderIOService = raiderIOService;
    }

    @GetMapping("/character")
    public HydratedPlayer getHydration(
            @RequestParam String region,
            @RequestParam String realm,
            @RequestParam String name) {
        return raiderIOService.getRecentRuns(region, realm, name);
    }
}

// private final RaiderIOService raiderIOService;

// public RaiderIOController(RaiderIOService raiderIOService) {
// this.raiderIOService = raiderIOService;
// }

// @GetMapping("/character")
// public ResponseEntity<?> getRecentRuns(
// @RequestParam String region,
// @RequestParam String realm,
// @RequestParam String name) {
// var data = raiderIOService.getRecentRuns(region, realm, name);
// return ResponseEntity.ok(data);
// }
// }