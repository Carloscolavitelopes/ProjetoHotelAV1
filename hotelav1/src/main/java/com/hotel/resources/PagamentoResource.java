package com.hotel.resources;

import com.hotel.domains.dtos.PagamentoDTO;
import com.hotel.services.PagamentoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@Validated
@RestController
@RequestMapping("/api/pagamento")
public class PagamentoResource {
    private final PagamentoService service;

    public PagamentoResource(PagamentoService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PagamentoDTO>> listAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping
    public ResponseEntity<Page<PagamentoDTO>> list(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        List<PagamentoDTO> all = service.findAll();
        Page<PagamentoDTO> page = new PageImpl<>(all, pageable, all.size());
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> findById(@PathVariable Integer id) {
        PagamentoDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO> create(@RequestBody @Validated(PagamentoDTO.Create.class) PagamentoDTO dto) {
        PagamentoDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDTO> update(
            @PathVariable Integer id,
            @RequestBody @Validated(PagamentoDTO.Update.class) PagamentoDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
