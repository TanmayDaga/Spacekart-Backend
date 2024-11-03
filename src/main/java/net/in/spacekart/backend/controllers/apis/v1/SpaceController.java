package net.in.spacekart.backend.controllers.apis.v1;


import jakarta.validation.Valid;
import net.in.spacekart.backend.database.entities.UserAuthentication;
import net.in.spacekart.backend.payloads.get.space.SpaceDetailsPrivateDto;
import net.in.spacekart.backend.payloads.get.space.SpaceSummaryDtoPublic;
import net.in.spacekart.backend.payloads.post.space.SpaceCreateDto;
import net.in.spacekart.backend.payloads.put.space.SpacePutPrivateDto;
import net.in.spacekart.backend.services.MediaService;
import net.in.spacekart.backend.services.UtilsService;
import net.in.spacekart.backend.services.entityServices.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/spaces")
public class SpaceController {

    private  final SpaceService spaceService;

    private  MediaService mediaService;
    private final  UtilsService utilsService;

    @Autowired
    public  SpaceController(SpaceService spaceService, MediaService mediaService, UtilsService utilsService) {
        this.spaceService = spaceService;
        this.mediaService = mediaService;
        this.utilsService = utilsService;
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<?> insertSpace(Authentication authentication,@Valid @ModelAttribute SpaceCreateDto spaceCreateDto) {

        UserAuthentication user = (UserAuthentication) authentication.getPrincipal();
        String spaceId = spaceService.insertByUserName(user.getUsername(), spaceCreateDto);

        return new ResponseEntity<String>(spaceId ,HttpStatus.OK);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{spaceId}")
    public ResponseEntity<?> getSpace(@PathVariable String spaceId) {
        System.out.println(spaceId);
        return new ResponseEntity<  >(spaceService.getSpaceDetailsDtoPublicBySpaceId(spaceId), HttpStatus.OK);
    }



    @PreAuthorize("hasRole('USER')")
    @GetMapping("/summary/{spaceId}")
    public ResponseEntity<?> getSpaceTypeSummary(@PathVariable String spaceId) {
        SpaceSummaryDtoPublic spaceSummaryDtoPublic = spaceService.getSpaceSummaryDtoPublicBySpaceId(spaceId);
        return  new ResponseEntity<>(spaceSummaryDtoPublic, HttpStatus.OK);
    }


    @GetMapping("/list/{pageNumber}/{pageSize}")
    public ResponseEntity<List<String>> getSpaces(@PathVariable Integer pageNumber,@PathVariable Integer pageSize, Authentication authentication){
        if(pageNumber < 1 ||  pageSize < 1 || pageSize >15){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        PageRequest pageableRequest = PageRequest.of(pageNumber-1,pageSize);
        return new ResponseEntity<>(spaceService.getListOfSpaces(pageableRequest).stream().toList(), HttpStatus.OK);

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list/me")
    public ResponseEntity<?> getSpacesMe(Authentication authentication,@RequestParam("name") Integer showName){
        UserAuthentication user = (UserAuthentication) authentication.getPrincipal();
        if(showName == 0){
            return new ResponseEntity<>(spaceService.getSpaceIdsFromUsername(user.getUsername()),HttpStatus.OK);

        }
        return new ResponseEntity<>(spaceService.getSpaceListDtoByUserId(user.getUsername()), HttpStatus.OK);

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me/{spaceId}")
    public ResponseEntity<?> privateSpace(Authentication authentication,@PathVariable String spaceId){
        UserAuthentication user = (UserAuthentication) authentication.getPrincipal();
        if(user.getUsername().equals(utilsService.getUsernameSpacePublicId(spaceId))){
            return new ResponseEntity<>(spaceService.getSpaceDetailsPrivateBySpaceId(spaceId), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/me/{spaceId}")
    public ResponseEntity<?> changeDetails(Authentication authentication, @ModelAttribute @Valid SpacePutPrivateDto spacePutPrivateDto,@PathVariable String spaceId){
        UserAuthentication user = (UserAuthentication) authentication.getPrincipal();
        if(user.getUsername().equals(utilsService.getUsernameSpacePublicId(spaceId))){
            spaceService.putSpace(spaceId, spacePutPrivateDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/me/{spaceId}")
    public ResponseEntity<?> deleteSpace(@PathVariable String spaceId, Authentication authentication){
        UserAuthentication user = (UserAuthentication) authentication.getPrincipal();
        if(user.getUsername().equals(utilsService.getUsernameSpacePublicId(spaceId))){

            spaceService.deleteSpace(spaceId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);


    }




}


