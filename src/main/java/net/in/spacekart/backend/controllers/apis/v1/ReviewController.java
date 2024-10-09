package net.in.spacekart.backend.controllers.apis.v1;


import jakarta.validation.Valid;
import net.in.spacekart.backend.database.entities.UserAuthentication;
import net.in.spacekart.backend.payloads.post.review.ReviewCreateDto;
import net.in.spacekart.backend.services.UtilsService;
import net.in.spacekart.backend.services.entityServices.ReviewService;
import net.in.spacekart.backend.services.entityServices.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/reviews")
public class ReviewController {


    private final ReviewService reviewService;
    private final UtilsService utilsService;
    private final UserService userService;

    public ReviewController(ReviewService reviewService, UtilsService utilsService, UserService userService) {
        this.reviewService = reviewService;
        this.utilsService = utilsService;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<?> createReview(@Valid @ModelAttribute ReviewCreateDto review, Authentication auth) {

        UserAuthentication userAuthentication = (UserAuthentication) auth.getPrincipal();
        if(userAuthentication.getUsername().equals(utilsService.getUsernameSpacePublicId(review.getSpaceId()))){
            return new ResponseEntity<>("You cannot review your own space",HttpStatus.FORBIDDEN);
        }
        reviewService.insertReview(review, userAuthentication.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me/{reviewId}")
    public ResponseEntity<?> getReviewDetails(@PathVariable String reviewId, Authentication auth) {
        UserAuthentication userAuthentication = (UserAuthentication) auth.getPrincipal();
        if (userAuthentication.getUsername().equals(reviewService.getUsernameFromReviewId(reviewId))) {
            return new ResponseEntity<>(reviewService.getReviewDetailsPrivate(reviewId), HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{reviewPublicId}")
    public ResponseEntity<?> getReview(@PathVariable String reviewPublicId) {
        return new ResponseEntity<>(reviewService.getReviewDetailsPublic(reviewPublicId), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list/{spaceId}/{pageNumber}/{pageSize}")
    public ResponseEntity<?> getSpaces(@PathVariable String spaceId, @PathVariable Integer pageNumber, @PathVariable Integer pageSize) {
        if (pageNumber < 1 || pageSize < 1 || pageSize > 15) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        PageRequest pageableRequest = PageRequest.of(pageNumber - 1, pageSize);
        return new ResponseEntity<>(reviewService.getAllReviewsBySpace(spaceId, pageableRequest).stream().toList(), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/me/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable String reviewId, Authentication auth) {
        UserAuthentication userAuthentication = (UserAuthentication) auth.getPrincipal();
        if (userAuthentication.getUsername().equals(reviewService.getUsernameFromReviewId(reviewId))) {
            reviewService.deleteReview(reviewId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
