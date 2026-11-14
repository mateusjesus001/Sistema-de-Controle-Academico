package br.uepb.sca.controllers;

import br.uepb.sca.dtos.LancamentoNotaDTO;
import br.uepb.sca.services.DiarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diarios")
public class DiarioController {

    private final DiarioService diarioService;

    public DiarioController(DiarioService diarioService) {
        this.diarioService = diarioService;
    }

    @PostMapping("/nota")
    public ResponseEntity<String> lancarNota(@RequestBody LancamentoNotaDTO dto) {
        diarioService.lancarNota(dto);
        return ResponseEntity.ok("Nota lançada com sucesso!");
    }

    @PostMapping("/{diarioId}/fechar")
    public ResponseEntity<String> fecharDiario(@PathVariable Long diarioId) {
        boolean sucesso = diarioService.fecharDiario(diarioId);
        if (sucesso) {
            return ResponseEntity.ok("Diário fechado com sucesso!");
        } else {
            return ResponseEntity.badRequest().body("Não foi possível fechar o diário.");
        }
    }
}
