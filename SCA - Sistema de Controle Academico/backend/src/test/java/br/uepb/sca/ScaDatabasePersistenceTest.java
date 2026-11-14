package br.uepb.sca;

import br.uepb.sca.dtos.CursoDTO;
import br.uepb.sca.models.Curso;
import br.uepb.sca.models.enums.Turno;
import br.uepb.sca.repositories.CursoRepository;
import br.uepb.sca.repositories.DisciplinaRepository;
import br.uepb.sca.services.AdminService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ScaDatabasePersistenceTest {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private AdminService adminService;

    @Test
    public void testarPersistenciaRealNoBancoDeDados() {
        // 1. Testa listagem de cursos criados pelo seed
        List<Curso> cursosIniciais = cursoRepository.findAll();
        System.out.println(">>> Cursos cadastrados no banco: " + cursosIniciais.size());
        Assertions.assertFalse(cursosIniciais.isEmpty(), "O banco de dados deve conter os cursos iniciais.");

        // 2. Testa adição de novo curso pelo AdminService
        CursoDTO novoCurso = new CursoDTO();
        novoCurso.setCodigo("MED.01");
        novoCurso.setNome("Medicina");
        novoCurso.setDepartamento("Departamento de Ciências Médicas");
        novoCurso.setTurno(Turno.INTEGRAL);

        CursoDTO cursoSalvo = adminService.criarCurso(novoCurso);
        Assertions.assertNotNull(cursoSalvo.getId(), "O ID do curso deve ser gerado pelo banco de dados.");

        // 3. Consulta de volta para garantir persistência física
        Curso doBanco = cursoRepository.findById(cursoSalvo.getId()).orElse(null);
        Assertions.assertNotNull(doBanco, "O curso deve ser encontrado diretamente no banco de dados.");
        Assertions.assertEquals("Medicina", doBanco.getNome());
        System.out.println(">>> Curso persistido com sucesso! ID: " + doBanco.getId() + " - " + doBanco.getNome());

        // 4. Testa remoção do curso
        adminService.removerCurso(cursoSalvo.getId());
        boolean aindaExiste = cursoRepository.findById(cursoSalvo.getId()).isPresent();
        Assertions.assertFalse(aindaExiste, "O curso deve ter sido removido fisicamente do banco de dados.");
        System.out.println(">>> Curso removido com sucesso do banco de dados!");
    }
}
