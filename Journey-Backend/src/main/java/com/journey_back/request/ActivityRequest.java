package com.journey_back.request;

import java.time.LocalDateTime;

public record ActivityRequest(String title, LocalDateTime date) {
}
