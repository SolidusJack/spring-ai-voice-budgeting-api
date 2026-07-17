package solidusjack.budgeting.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solidusjack.budgeting.model.Gasto;
import solidusjack.budgeting.repository.GastoRepository;
import jakarta.validation.Valid;
import solidusjack.budgeting.dto.GastoRequest;

@RestController
@RequestMapping("/api/gastos") 
public class GastoController {

    private final GastoRepository repository;

    public GastoController(GastoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Gasto> listarTodos() {
        return repository.findAll();
    }
    
    @PostMapping
    public Gasto cadastrar(@Valid @RequestBody GastoRequest dadosEntrada) {
        Gasto novoGasto = new Gasto(dadosEntrada.categoria(), dadosEntrada.valor());
        
        return repository.save(novoGasto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build(); 
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build(); 
    }
}