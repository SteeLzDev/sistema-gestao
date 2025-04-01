package com.oficina.application.port;


import com.oficina.infrastructure.rest.dto.DashboardDTO;
import com.oficina.infrastructure.rest.dto.RelatorioDTO;

public interface DashboardService {

    DashboardDTO getDashboardData();

    RelatorioDTO getRelatorioDiario();

    RelatorioDTO getRelatorioSemanal();

    RelatorioDTO getRelatorioMensal();

    RelatorioDTO getRelatorioAnual();

    RelatorioDTO getRelatorioPorPeriodo(String datInicio, String dataFim);


}
