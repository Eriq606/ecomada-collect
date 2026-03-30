package mg.ecomada.collect.recompense;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/recompenses")
@RequiredArgsConstructor
@Tag(name = "Recompenses", description = "Gestion des recompenses")
public class RecompenseController {
    private final RecompenseService service;

    @GetMapping
    public CollectionModel<EntityModel<RecompenseDto>> getAll() {
        List<EntityModel<RecompenseDto>> items = service.findAll().stream()
                .map(d -> EntityModel.of(d, linkTo(methodOn(RecompenseController.class).getById(d.getId())).withSelfRel()))
                .toList();
        return CollectionModel.of(items, linkTo(methodOn(RecompenseController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<RecompenseDto> getById(@PathVariable Long id) {
        RecompenseDto dto = service.findById(id);
        return EntityModel.of(dto,
                linkTo(methodOn(RecompenseController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(RecompenseController.class).getAll()).withRel("recompenses"));
    }

    @PostMapping
    public ResponseEntity<EntityModel<RecompenseDto>> create(@RequestBody RecompenseDto dto) {
        RecompenseDto created = service.create(dto);
        return ResponseEntity.created(linkTo(methodOn(RecompenseController.class).getById(created.getId())).toUri())
                .body(EntityModel.of(created, linkTo(methodOn(RecompenseController.class).getById(created.getId())).withSelfRel()));
    }

    @PutMapping("/{id}")
    public EntityModel<RecompenseDto> update(@PathVariable Long id, @RequestBody RecompenseDto dto) {
        return EntityModel.of(service.update(id, dto),
                linkTo(methodOn(RecompenseController.class).getById(id)).withSelfRel());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
