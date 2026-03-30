package mg.ecomada.collect.collecteur;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/collecteurs")
@RequiredArgsConstructor
@Tag(name = "Collecteurs", description = "Gestion des collecteurs")
public class CollecteurController {
    private final CollecteurService service;

    @GetMapping
    public CollectionModel<EntityModel<CollecteurDto>> getAll() {
        List<EntityModel<CollecteurDto>> items = service.findAll().stream()
                .map(d -> EntityModel.of(d, linkTo(methodOn(CollecteurController.class).getById(d.getId())).withSelfRel()))
                .toList();
        return CollectionModel.of(items, linkTo(methodOn(CollecteurController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<CollecteurDto> getById(@PathVariable Long id) {
        CollecteurDto dto = service.findById(id);
        return EntityModel.of(dto,
                linkTo(methodOn(CollecteurController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(CollecteurController.class).getAll()).withRel("collecteurs"));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<CollecteurDto>> create(@RequestBody CollecteurDto dto) {
        CollecteurDto created = service.create(dto);
        return ResponseEntity.created(linkTo(methodOn(CollecteurController.class).getById(created.getId())).toUri())
                .body(EntityModel.of(created, linkTo(methodOn(CollecteurController.class).getById(created.getId())).withSelfRel()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<CollecteurDto> update(@PathVariable Long id, @RequestBody CollecteurDto dto) {
        return EntityModel.of(service.update(id, dto),
                linkTo(methodOn(CollecteurController.class).getById(id)).withSelfRel());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
