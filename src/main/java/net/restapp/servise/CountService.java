package net.restapp.servise;

import net.restapp.model.WorkingHours;

import java.math.BigDecimal;

public interface CountService {
    BigDecimal calculatePaymentOfEvent(WorkingHours workingHours);
}
