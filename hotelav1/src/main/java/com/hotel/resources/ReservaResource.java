package com.hotel.resources;

import com.hotel.domains.dtos.ReservaDTO;
import com.hotel.services.ReservaService;
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
@RequestMapping("/api/reserva")
public class ReservaResource {
    private final ReservaService service;

    public ReservaResource(ReservaService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReservaDTO>> listAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping
    public ResponseEntity<Page<ReservaDTO>> list(
            @PageableDefault(size = 20, sort = "dataCheckIn") Pageable pageable) {
        List<ReservaDTO> all = service.findAll();
        Page<ReservaDTO> page = new PageImpl<>(all, pageable, all.size());
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> findById(@PathVariable Integer id) {
        ReservaDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ReservaDTO> create(@RequestBody @Validated(ReservaDTO.Create.class) ReservaDTO dto) {
        ReservaDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaDTO> update(
            @PathVariable Integer id,
            @RequestBody @Validated(ReservaDTO.Update.class) ReservaDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
