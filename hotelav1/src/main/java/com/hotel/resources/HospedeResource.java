package com.hotel.resources;

import com.hotel.domains.dtos.HospedeDTO;
import com.hotel.services.HospedeService;
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
@RequestMapping("/api/hospede")
public class HospedeResource {
    private final HospedeService service;

    public HospedeResource(HospedeService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<HospedeDTO>> listAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping
    public ResponseEntity<Page<HospedeDTO>> list(
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        List<HospedeDTO> all = service.findAll();
        Page<HospedeDTO> page = new PageImpl<>(all, pageable, all.size());
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospedeDTO> findById(@PathVariable Integer id) {
        HospedeDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<HospedeDTO> create(@RequestBody @Validated(HospedeDTO.Create.class) HospedeDTO dto) {
        HospedeDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HospedeDTO> update(
            @PathVariable Integer id,
            @RequestBody @Validated(HospedeDTO.Update.class) HospedeDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
