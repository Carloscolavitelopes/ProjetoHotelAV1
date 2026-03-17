package com.hotel.resources;

import com.hotel.domains.dtos.ConsumoDTO;
import com.hotel.services.ConsumoService;
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
@RequestMapping("/api/consumo")
public class ConsumoResource {
    private final ConsumoService service;

    public ConsumoResource(ConsumoService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ConsumoDTO>> listAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping
    public ResponseEntity<Page<ConsumoDTO>> list(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        List<ConsumoDTO> all = service.findAll();
        Page<ConsumoDTO> page = new PageImpl<>(all, pageable, all.size());
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsumoDTO> findById(@PathVariable Integer id) {
        ConsumoDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ConsumoDTO> create(@RequestBody @Validated(ConsumoDTO.Create.class) ConsumoDTO dto) {
        ConsumoDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsumoDTO> update(
            @PathVariable Integer id,
            @RequestBody @Validated(ConsumoDTO.Update.class) ConsumoDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
