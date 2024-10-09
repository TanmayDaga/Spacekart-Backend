package net.in.spacekart.backend.controllers.apis.v1;

import net.in.spacekart.backend.database.entities.UserAuthentication;
import net.in.spacekart.backend.payloads.get.allocationTime.AllocationTimePrivateDto;
import net.in.spacekart.backend.payloads.get.allocationTime.AllocationTimePublicDTO;
import net.in.spacekart.backend.payloads.post.allocationTime.AllocationTimeSave;
import net.in.spacekart.backend.payloads.put.allocationTime.AllocationTimePut;
import net.in.spacekart.backend.services.UtilsService;
import net.in.spacekart.backend.services.entityServices.AllocationTimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/booking")
public class BookingController {


    AllocationTimeService allocationTimeService;
    UtilsService utilsService;

    public BookingController(AllocationTimeService allocationTimeService, UtilsService utilsService) {
        this.allocationTimeService = allocationTimeService;
        this.utilsService = utilsService;
    }

    @PostMapping
    public ResponseEntity<?> saveBooking(@RequestBody AllocationTimeSave allocationTimeSave, Authentication authentication) {
        UserAuthentication userAuthentication = (UserAuthentication) authentication.getPrincipal();

        if (Objects.equals(userAuthentication.getUsername(), utilsService.getUsernameSpacePublicId(allocationTimeSave.getSpaceId()))) {
            return new ResponseEntity<>("You cannot book your own space", HttpStatus.BAD_REQUEST);
        }
        if (allocationTimeService.isBooked(allocationTimeSave.getSpaceId(), allocationTimeSave.getStartTime(), allocationTimeSave.getEndTime())) {
            return new ResponseEntity<>("There is booking for specific timing", HttpStatus.BAD_REQUEST);
        }
        ;
        allocationTimeService.save(userAuthentication.getUsername(), allocationTimeSave.getSpaceId(), allocationTimeSave.getStartTime(), allocationTimeSave.getEndTime(), "Booked");
        return new ResponseEntity<>("Booked", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me/{month}/{year}")
    public ResponseEntity<?> getMyBooking(Authentication authentication, @PathVariable("month") Integer month, @PathVariable("year") Integer year) {
        if ((month > 12 || month < 1) || (year > 2050 || year < 2000)) {
            return new ResponseEntity<>("Invalid month/year", HttpStatus.BAD_REQUEST);
        }
        UserAuthentication userAuthentication = (UserAuthentication) authentication.getPrincipal();
        List<AllocationTimePublicDTO> allocationTimePublicDTOList = allocationTimeService.findPublicDTOByRenterId(userAuthentication.getUsername(), month, year);
        return new ResponseEntity<>(allocationTimePublicDTOList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me/{spaceId}/{month}/{year}")
    public ResponseEntity<?> getMySpaceBooking(Authentication authentication, @PathVariable("spaceId") String spaceId, @PathVariable("month") Integer month, @PathVariable("year") Integer year) {
        if ((month > 12 || month < 1) || (year > 2050 || year < 2000)) {
            return new ResponseEntity<>("Invalid month/year", HttpStatus.BAD_REQUEST);
        }
        UserAuthentication userAuthentication = (UserAuthentication) authentication.getPrincipal();
        if(userAuthentication.getUsername().equals(utilsService.getUsernameSpacePublicId(spaceId))) {
            List<AllocationTimePrivateDto> allocationTimePrivateDtos = allocationTimeService.findPrivateDTOByRenterId(spaceId, month, year);
            return new ResponseEntity<>(allocationTimePrivateDtos, HttpStatus.OK);
        }
        return new ResponseEntity<>("Authorisation Error", HttpStatus.UNAUTHORIZED);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{spaceId}/{month}/{year}")
    public ResponseEntity<?> getSpaceBookings(Authentication authentication, @PathVariable("spaceId") String spaceId, @PathVariable("month") Integer month, @PathVariable("year") Integer year) {
        if ((month > 12 || month < 1) || (year > 2050 || year < 2000)) {
            return new ResponseEntity<>("Invalid month/year", HttpStatus.BAD_REQUEST);
        }
        if ((spaceId == null) || (spaceId.isEmpty())) {
            return new ResponseEntity<>("Invalid spaceId", HttpStatus.BAD_REQUEST);
        }
       List<AllocationTimePublicDTO> allocationTimePrivateDtos = allocationTimeService.findPublicAllocationTimeDTOBySpaceId(spaceId, month, year);
            return new ResponseEntity<>(allocationTimePrivateDtos, HttpStatus.OK);

    }




    //To be closed for now
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{publicId}")
    public ResponseEntity<?> updateAllocationTime(@RequestBody AllocationTimePut allocationTimePut, @PathVariable("publicId") String publicId, Authentication authentication) {
        UserAuthentication userAuthentication = (UserAuthentication) authentication.getPrincipal();
        if (Objects.equals(utilsService.getUsernameAllocationTimePublicId(publicId), userAuthentication.getUsername())) {
            allocationTimeService.update(publicId, allocationTimePut.getStatus(), allocationTimePut.getStartTime(), allocationTimePut.getEndTime());
            return new ResponseEntity<>("Status Updated", HttpStatus.OK);

        }
        return new ResponseEntity<>("Unauthorised access", HttpStatus.UNAUTHORIZED);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{publicId}")
    public ResponseEntity<?> deleteAllocationTime(@PathVariable("publicId") String publicId, Authentication authentication) {
        UserAuthentication userAuthentication = (UserAuthentication) authentication.getPrincipal();
        if (Objects.equals(utilsService.getUsernameAllocationTimePublicId(publicId), userAuthentication.getUsername())) {
            allocationTimeService.delete(publicId);
            return new ResponseEntity<>("Status Deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Unauthorised access", HttpStatus.UNAUTHORIZED);
    }

}
