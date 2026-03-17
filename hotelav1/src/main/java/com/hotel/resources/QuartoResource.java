package com.hotel.resources;

import com.hotel.domains.dtos.QuartoDTO;
import com.hotel.services.QuartoService;
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
@RequestMapping("/api/quarto")
public class QuartoResource {
    private final QuartoService service;

    public QuartoResource(QuartoService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<QuartoDTO>> listAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping
    public ResponseEntity<Page<QuartoDTO>> list(
            @PageableDefault(size = 20, sort = "numero") Pageable pageable) {
        List<QuartoDTO> all = service.findAll();
        Page<QuartoDTO> page = new PageImpl<>(all, pageable, all.size());
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuartoDTO> findById(@PathVariable Integer id) {
        QuartoDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<QuartoDTO> create(@RequestBody @Validated(QuartoDTO.Create.class) QuartoDTO dto) {
        QuartoDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuartoDTO> update(
            @PathVariable Integer id,
            @RequestBody @Validated(QuartoDTO.Update.class) QuartoDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
