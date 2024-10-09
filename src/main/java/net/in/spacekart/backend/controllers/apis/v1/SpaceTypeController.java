package net.in.spacekart.backend.controllers.apis.v1;

import jakarta.validation.Valid;
import net.in.spacekart.backend.payloads.delete.spacetype.SpaceTypeDeleteDto;
import net.in.spacekart.backend.payloads.post.spaceType.SpaceTypeCreateDto;
import net.in.spacekart.backend.payloads.put.SpaceTypeUpdateDto;
import net.in.spacekart.backend.services.UtilsService;
import net.in.spacekart.backend.services.entityServices.SpaceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/spacetypes")
public class SpaceTypeController {


    private final SpaceTypeService spaceTypeService;
    private final UtilsService utilsService;

    @Autowired
    public SpaceTypeController(SpaceTypeService spaceTypeService, UtilsService utilsService) {
        this.spaceTypeService = spaceTypeService;
        this.utilsService = utilsService;
    }


    @PostMapping
    public ResponseEntity<?> saveSpaceType(@Valid @ModelAttribute SpaceTypeCreateDto spaceTypeDto) {
        spaceTypeService.save(spaceTypeDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> listSpaceTypes() {
        return new ResponseEntity<>(spaceTypeService.findAll(), HttpStatus.OK);
    }


    @DeleteMapping
    public ResponseEntity<?> delete(@ModelAttribute @Valid SpaceTypeDeleteDto spaceTypeDto) {
        spaceTypeService.delete(spaceTypeDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@ModelAttribute @Valid SpaceTypeUpdateDto spaceTypeDto) {
        spaceTypeService.update(spaceTypeDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/names")
    public ResponseEntity<?> getSpaceTypeNames(){
        return new ResponseEntity<>(spaceTypeService.getNamesList(), HttpStatus.OK);
    }


}
