package br.uepb.sca.controllers;

import br.uepb.sca.dtos.MelhorCaminhoDTO;
import br.uepb.sca.services.MelhorCaminhoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/melhor-caminho")
public class MelhorCaminhoController {

    private final MelhorCaminhoService melhorCaminhoService;

    public MelhorCaminhoController(MelhorCaminhoService melhorCaminhoService) {
        this.melhorCaminhoService = melhorCaminhoService;
    }

    /**
     * UC23: Retorna o cálculo do melhor caminho com estimativa em anos/semestres.
     */
    @GetMapping("/{alunoId}")
    public ResponseEntity<List<MelhorCaminhoDTO>> obterMelhorCaminho(
            @PathVariable Long alunoId,
            @RequestParam(defaultValue = "4") int qtdDesejada) {
        List<MelhorCaminhoDTO> sugestoes = melhorCaminhoService.calcularMelhorCaminho(alunoId, qtdDesejada);
        return ResponseEntity.ok(sugestoes);
    }
}
