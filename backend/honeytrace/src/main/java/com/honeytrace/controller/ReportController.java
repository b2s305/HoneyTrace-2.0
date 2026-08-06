package com.honeytrace.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.honeytrace.entity.Attacker;
import com.honeytrace.report.PdfReportGenerator;
import com.honeytrace.service.DashboardService;

@Controller
public class ReportController {

    private final DashboardService dashboardService;

    public ReportController(DashboardService dashboardService) {

        this.dashboardService = dashboardService;

    }

    @GetMapping("/reports")
    public String reports() {

        return "reports";

    }

    @GetMapping("/report/download")
    public ResponseEntity<byte[]> downloadReport() {

        try {

            List<Attacker> attackers =
                    dashboardService.getAllAttackers();

            byte[] pdf =
                    PdfReportGenerator.generate(attackers);

            return ResponseEntity.ok()

                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=HoneyTrace_Report.pdf")

                    .contentType(MediaType.APPLICATION_PDF)

                    .body(pdf);

        }

        catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.internalServerError().build();

        }

    }

}
