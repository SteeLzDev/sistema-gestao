package com.oficina.application.service;

import com.oficina.application.port.DashboardService;
import com.oficina.domain.model.Atendimento;
import com.oficina.domain.model.FilaAtendimento;
import com.oficina.domain.model.ItemVenda;
import com.oficina.domain.model.Produto;
import com.oficina.domain.model.Venda;
import com.oficina.infrastructure.persistence.repository.AtendimentoRepository;
import com.oficina.infrastructure.persistence.repository.FilaAtendimentoRepository;
import com.oficina.infrastructure.persistence.repository.ProdutoRepository;
import com.oficina.infrastructure.persistence.repository.VendaRepository;
import com.oficina.infrastructure.rest.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class DashboardServiceImpl implements DashboardService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final FilaAtendimentoRepository filaAtendimentoRepository;
    private final AtendimentoRepository atendimentoRepository;

    private static final int LIMITE_ESTOQUE_BAIXO = 5;

    @Autowired
    public DashboardServiceImpl(
            VendaRepository vendaRepository,
            ProdutoRepository produtoRepository,
            FilaAtendimentoRepository filaAtendimentoRepository,
            AtendimentoRepository atendimentoRepository) {
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
        this.filaAtendimentoRepository = filaAtendimentoRepository;
        this.atendimentoRepository = atendimentoRepository;
    }

    @Override
    public DashboardDTO getDashboardData() {
        DashboardDTO dashboardDTO = new DashboardDTO();

        // Vendas recentes (últimos 30 dias)
        LocalDateTime dataInicio = LocalDateTime.now().minusDays(30);
        List<Venda> vendasRecentes = vendaRepository.findByDataHoraAfterOrderByDataHoraDesc(dataInicio);

        VendasRecentesDTO vendasRecentesDTO = new VendasRecentesDTO();
        vendasRecentesDTO.setTotal(vendasRecentes.size());

        List<VendaResumoDTO> vendasResumoList = vendasRecentes.stream()
                .limit(5) // Limitar a 5 vendas para o dashboard
                .map(this::mapVendaToVendaResumoDTO)
                .collect(Collectors.toList());

        vendasRecentesDTO.setItens(vendasResumoList);
        dashboardDTO.setVendasRecentes(vendasRecentesDTO);

        // Estoque
        List<Produto> produtos = produtoRepository.findAll();
        List<Produto> produtosBaixoEstoque = produtoRepository.findByQuantidadeLessThanEqual(LIMITE_ESTOQUE_BAIXO);

        EstoqueDTO estoqueDTO = new EstoqueDTO();
        estoqueDTO.setTotal(produtos.size());
        estoqueDTO.setBaixoEstoque(produtosBaixoEstoque.size());

        List<ProdutoResumoDTO> produtosResumoList = produtosBaixoEstoque.stream()
                .limit(5) // Limitar a 5 produtos para o dashboard
                .map(this::mapProdutoToProdutoResumoDTO)
                .collect(Collectors.toList());

        estoqueDTO.setItens(produtosResumoList);
        dashboardDTO.setEstoque(estoqueDTO);

        // Fila de atendimento
        List<FilaAtendimento> clientesAguardando = filaAtendimentoRepository.findByStatus("AGUARDANDO");
        List<FilaAtendimento> clientesEmAtendimento = filaAtendimentoRepository.findByStatus("EM_ATENDIMENTO");

        FilaResumoDTO filaResumoDTO = new FilaResumoDTO();
        filaResumoDTO.setAguardando(clientesAguardando.size());
        filaResumoDTO.setEmAtendimento(clientesEmAtendimento.size());

        List<ClienteFilaDTO> clientesFilaList = new ArrayList<>();
        clientesFilaList.addAll(clientesAguardando.stream()
                .limit(3) // Limitar a 3 clientes aguardando
                .map(this::mapFilaAtendimentoToClienteFilaDTO)
                .collect(Collectors.toList()));

        clientesFilaList.addAll(clientesEmAtendimento.stream()
                .limit(2) // Limitar a 2 clientes em atendimento
                .map(this::mapFilaAtendimentoToClienteFilaDTO)
                .collect(Collectors.toList()));

        filaResumoDTO.setItens(clientesFilaList);
        dashboardDTO.setFila(filaResumoDTO);

        // Serviços
        List<Atendimento> servicosPendentes = atendimentoRepository.findByStatus("PENDENTE");
        List<Atendimento> servicosConcluidos = atendimentoRepository.findByStatusAndInicioBetween(
                "CONCLUIDO",
                LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                LocalDateTime.of(LocalDate.now(), LocalTime.MAX));

        ServicosDTO servicosDTO = new ServicosDTO();
        servicosDTO.setPendentes(servicosPendentes.size());
        servicosDTO.setConcluidos(servicosConcluidos.size());

        List<ServicoResumoDTO> servicosResumoList = servicosPendentes.stream()
                .limit(5) // Limitar a 5 serviços para o dashboard
                .map(this::mapAtendimentoToServicoResumoDTO)
                .collect(Collectors.toList());

        servicosDTO.setItens(servicosResumoList);
        dashboardDTO.setServicos(servicosDTO);

        return dashboardDTO;
    }

    @Override
    public RelatorioDTO getRelatorioDiario() {
        LocalDateTime dataInicio = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime dataFim = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        LocalDateTime dataInicioAnterior = dataInicio.minusDays(1);
        LocalDateTime dataFimAnterior = dataFim.minusDays(1);

        return gerarRelatorio("diário", dataInicio, dataFim, dataInicioAnterior, dataFimAnterior);
    }

    @Override
    public RelatorioDTO getRelatorioSemanal() {
        LocalDate hoje = LocalDate.now();
        LocalDate inicioSemana = hoje.minusDays(hoje.getDayOfWeek().getValue() - 1);

        LocalDateTime dataInicio = LocalDateTime.of(inicioSemana, LocalTime.MIN);
        LocalDateTime dataFim = LocalDateTime.of(hoje, LocalTime.MAX);

        LocalDateTime dataInicioAnterior = dataInicio.minusWeeks(1);
        LocalDateTime dataFimAnterior = dataFim.minusWeeks(1);

        return gerarRelatorio("semanal", dataInicio, dataFim, dataInicioAnterior, dataFimAnterior);
    }

    @Override
    public RelatorioDTO getRelatorioMensal() {
        LocalDate hoje = LocalDate.now();
        LocalDate inicioMes = hoje.withDayOfMonth(1);

        LocalDateTime dataInicio = LocalDateTime.of(inicioMes, LocalTime.MIN);
        LocalDateTime dataFim = LocalDateTime.of(hoje, LocalTime.MAX);

        LocalDateTime dataInicioAnterior = dataInicio.minusMonths(1);
        LocalDateTime dataFimAnterior = dataFim.minusMonths(1);

        return gerarRelatorio("mensal", dataInicio, dataFim, dataInicioAnterior, dataFimAnterior);
    }

    @Override
    public RelatorioDTO getRelatorioAnual() {
        LocalDate hoje = LocalDate.now();
        LocalDate inicioAno = hoje.withDayOfYear(1);

        LocalDateTime dataInicio = LocalDateTime.of(inicioAno, LocalTime.MIN);
        LocalDateTime dataFim = LocalDateTime.of(hoje, LocalTime.MAX);

        LocalDateTime dataInicioAnterior = dataInicio.minusYears(1);
        LocalDateTime dataFimAnterior = dataFim.minusYears(1);

        return gerarRelatorio("anual", dataInicio, dataFim, dataInicioAnterior, dataFimAnterior);
    }

    @Override
    public RelatorioDTO getRelatorioPorPeriodo(String dataInicioStr, String dataFimStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate dataInicioDate = LocalDate.parse(dataInicioStr, formatter);
        LocalDate dataFimDate = LocalDate.parse(dataFimStr, formatter);

        LocalDateTime dataInicio = LocalDateTime.of(dataInicioDate, LocalTime.MIN);
        LocalDateTime dataFim = LocalDateTime.of(dataFimDate, LocalTime.MAX);

        // Calcular período anterior com a mesma duração
        long dias = Duration.between(dataInicio, dataFim).toDays();

        LocalDateTime dataInicioAnterior = dataInicio.minusDays(dias);
        LocalDateTime dataFimAnterior = dataInicio.minusSeconds(1);

        return gerarRelatorio("personalizado", dataInicio, dataFim, dataInicioAnterior, dataFimAnterior);
    }

    /**
     * Gera um relatório para o período especificado
     */
    private RelatorioDTO gerarRelatorio(String periodo, LocalDateTime dataInicio, LocalDateTime dataFim,
                                        LocalDateTime dataInicioAnterior, LocalDateTime dataFimAnterior) {
        RelatorioDTO relatorio = new RelatorioDTO();
        relatorio.setPeriodo(periodo);
        relatorio.setDataInicio(dataInicio.toLocalDate());
        relatorio.setDataFim(dataFim.toLocalDate());

        // Buscar vendas do período atual
        List<Venda> vendasPeriodo = vendaRepository.findByDataHoraBetweenOrderByDataHoraDesc(dataInicio, dataFim);

        // Buscar vendas do período anterior para comparação
        List<Venda> vendasPeriodoAnterior = vendaRepository.findByDataHoraBetweenOrderByDataHoraDesc(
                dataInicioAnterior, dataFimAnterior);

        // Resumo de vendas
        ResumoVendasDTO resumoVendas = new ResumoVendasDTO();
        resumoVendas.setQuantidadeVendas(vendasPeriodo.size());

        BigDecimal valorTotalVendas = vendasPeriodo.stream()
                .map(Venda::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        resumoVendas.setValorTotal(valorTotalVendas);

        // Calcular ticket médio
        if (!vendasPeriodo.isEmpty()) {
            BigDecimal ticketMedio = valorTotalVendas.divide(
                    new BigDecimal(vendasPeriodo.size()), 2, RoundingMode.HALF_UP);
            resumoVendas.setTicketMedio(ticketMedio);
        } else {
            resumoVendas.setTicketMedio(BigDecimal.ZERO);
        }

        // Calcular crescimento em relação ao período anterior
        BigDecimal valorTotalAnterior = vendasPeriodoAnterior.stream()
                .map(Venda::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (valorTotalAnterior.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal crescimento = valorTotalVendas
                    .subtract(valorTotalAnterior)
                    .multiply(new BigDecimal(100))
                    .divide(valorTotalAnterior, 2, RoundingMode.HALF_UP);
            resumoVendas.setCrescimento(crescimento);
        } else {
            resumoVendas.setCrescimento(BigDecimal.ZERO);
        }

        relatorio.setResumoVendas(resumoVendas);

        // Produtos mais vendidos
        Map<Long, ProdutoVendidoDTO> produtosMap = new HashMap<>();

        for (Venda venda : vendasPeriodo) {
            for (ItemVenda item : venda.getItens()) {
                Produto produto = item.getProduto();
                Long produtoId = produto.getId();

                if (!produtosMap.containsKey(produtoId)) {
                    ProdutoVendidoDTO produtoDTO = new ProdutoVendidoDTO();
                    produtoDTO.setId(produtoId);
                    produtoDTO.setNome(produto.getNome());
                    produtoDTO.setQuantidade(0);
                    produtoDTO.setValorTotal(BigDecimal.ZERO);
                    produtosMap.put(produtoId, produtoDTO);
                }

                ProdutoVendidoDTO produtoDTO = produtosMap.get(produtoId);
                produtoDTO.setQuantidade(produtoDTO.getQuantidade() + item.getQuantidade());

                BigDecimal subtotal = item.getPrecoUnitario().multiply(new BigDecimal(item.getQuantidade()));
                produtoDTO.setValorTotal(produtoDTO.getValorTotal().add(subtotal));
            }
        }

        List<ProdutoVendidoDTO> produtosMaisVendidos = new ArrayList<>(produtosMap.values());
        produtosMaisVendidos.sort(Comparator.comparing(ProdutoVendidoDTO::getQuantidade).reversed());

        relatorio.setProdutosMaisVendidos(produtosMaisVendidos.stream().limit(10).collect(Collectors.toList()));

        // Serviços realizados
        List<Atendimento> atendimentosConcluidos = atendimentoRepository.findByStatusAndInicioBetween(
                "CONCLUIDO", dataInicio, dataFim);

        Map<String, ServicoRealizadoDTO> servicosMap = new HashMap<>();

        for (Atendimento atendimento : atendimentosConcluidos) {
            String tipoServico = atendimento.getServico(); // Usando servico em vez de descricao

            if (!servicosMap.containsKey(tipoServico)) {
                ServicoRealizadoDTO servicoDTO = new ServicoRealizadoDTO();
                servicoDTO.setTipo(tipoServico);
                servicoDTO.setQuantidade(0);
                servicoDTO.setValorTotal(BigDecimal.ZERO);
                servicoDTO.setTempoMedioAtendimento(0);
                servicosMap.put(tipoServico, servicoDTO);
            }

            ServicoRealizadoDTO servicoDTO = servicosMap.get(tipoServico);
            servicoDTO.setQuantidade(servicoDTO.getQuantidade() + 1);

            // Como não temos dataFim, não podemos calcular o tempo de atendimento
            // Vamos deixar como zero por enquanto
            servicoDTO.setTempoMedioAtendimento(0);
        }

        List<ServicoRealizadoDTO> servicosRealizados = new ArrayList<>(servicosMap.values());
        servicosRealizados.sort(Comparator.comparing(ServicoRealizadoDTO::getQuantidade).reversed());

        relatorio.setServicosRealizados(servicosRealizados);

        // Vendas por dia
        Map<String, BigDecimal> vendasPorDia = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Venda venda : vendasPeriodo) {
            String dataVenda = venda.getDataHora().format(formatter);

            if (!vendasPorDia.containsKey(dataVenda)) {
                vendasPorDia.put(dataVenda, BigDecimal.ZERO);
            }

            vendasPorDia.put(dataVenda, vendasPorDia.get(dataVenda).add(venda.getValorTotal()));
        }

        relatorio.setVendasPorDia(vendasPorDia);

        return relatorio;
    }

    /**
     * Mapeia uma Venda para VendaResumoDTO
     */
    private VendaResumoDTO mapVendaToVendaResumoDTO(Venda venda) {
        VendaResumoDTO dto = new VendaResumoDTO();
        dto.setId(venda.getId());
        dto.setCliente(venda.getCliente());
        dto.setDataHora(venda.getDataHora());
        dto.setValorTotal(venda.getValorTotal());
        return dto;
    }

    /**
     * Mapeia um Produto para ProdutoResumoDTO
     */
    private ProdutoResumoDTO mapProdutoToProdutoResumoDTO(Produto produto) {
        ProdutoResumoDTO dto = new ProdutoResumoDTO();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setQuantidade(produto.getQuantidade());
        dto.setPreco(produto.getPreco());
        return dto;
    }

    /**
     * Mapeia um FilaAtendimento para ClienteFilaDTO
     */
    private ClienteFilaDTO mapFilaAtendimentoToClienteFilaDTO(FilaAtendimento filaAtendimento) {
        ClienteFilaDTO dto = new ClienteFilaDTO();
        dto.setId(filaAtendimento.getId());
        dto.setNome(filaAtendimento.getNome());
        dto.setServico(filaAtendimento.getServico());
        dto.setStatus(filaAtendimento.getStatus());
        dto.setChegada(filaAtendimento.getChegada());
        return dto;
    }

    /**
     * Mapeia um Atendimento para ServicoResumoDTO
     */
    private ServicoResumoDTO mapAtendimentoToServicoResumoDTO(Atendimento atendimento) {
        ServicoResumoDTO dto = new ServicoResumoDTO();
        dto.setId(atendimento.getId());
        dto.setCliente(atendimento.getNome()); // Usando o campo nome em vez de cliente.getNome()
        dto.setDescricao(atendimento.getServico()); // Usando o campo servico em vez de descricao
        dto.setStatus(atendimento.getStatus());
        dto.setDataInicio(atendimento.getInicio()); // Usando o campo inicio em vez de dataInicio
        dto.setDataFim(null); // A entidade existente não tem dataFim
        return dto;
    }
}

