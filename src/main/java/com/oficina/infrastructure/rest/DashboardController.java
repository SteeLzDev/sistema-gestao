package com.oficina.infrastructure.rest;


import com.oficina.application.port.DashboardService;
import com.oficina.infrastructure.rest.dto.DashboardDTO;
import com.oficina.infrastructure.rest.dto.RelatorioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {


    private final DashboardService dashboardService;

    @Autowired
    public DashboardController (DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<DashboardDTO>getDashboardData(){
        DashboardDTO dashboardData = dashboardService.getDashboardData();
        return ResponseEntity.ok(dashboardData);
    }
    @GetMapping("relatorio/diario")
    public ResponseEntity<RelatorioDTO>getRelatorioDiario() {
        return ResponseEntity.ok(dashboardService.getRelatorioDiario());
    }

    @GetMapping("/relatorio/semanal")
    public ResponseEntity<RelatorioDTO>getRelatorioSemanal(){
        return ResponseEntity.ok(dashboardService.getRelatorioSemanal());
    }

    @GetMapping("/relatorio/mensal")
    public ResponseEntity<RelatorioDTO>getRelatorioMensal() {
        return ResponseEntity.ok(dashboardService.getRelatorioMensal());
    }

    @GetMapping("/relatorio/anual")
    public ResponseEntity<RelatorioDTO>getRelatorioAnual(){
        return ResponseEntity.ok(dashboardService.getRelatorioAnual());
    }

    @GetMapping("/relatorio/periodo")
    public ResponseEntity<RelatorioDTO>getRelatorioPorPeriodo(
        @RequestParam String dataInicio,
        @RequestParam String dataFim){
        return ResponseEntity.ok(dashboardService.getRelatorioPorPeriodo(dataInicio, dataFim));
    }


}
