package com.github.AlexanderSobko.MatteoSweetsBot.models;

import java.util.List;

public record ValidationErrorResponse(List<Violation> violations) {

}
