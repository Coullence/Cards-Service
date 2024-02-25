package com.google.cms.services;


import com.google.cms.models.User;
import com.google.cms.repositories.UserRepository;
import com.google.cms.models.Card;
import com.google.cms.repositories.CardRepo;
import com.google.cms.utilities.CONSTANTS;
import com.google.cms.utilities.ERole;
import com.google.cms.utilities.Shared.AuditTrailService;
import com.google.cms.utilities.Shared.EntityResponse;
import com.google.cms.utilities.Shared.ResponseService;
import com.google.cms.utilities.exception.CustomExceptions;
import com.google.cms.utilities.requests_proxy.UserRequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CardService {
    private final UserRepository userRepository;
    private final ResponseService responseService;
    private final AuditTrailService auditTrailService;
    private final CardRepo cardRepo;

    public CardService(UserRepository userRepository, ResponseService responseService, AuditTrailService auditTrailService, CardRepo cardRepo) {
        this.userRepository = userRepository;
        this.responseService = responseService;
        this.auditTrailService = auditTrailService;
        this.cardRepo = cardRepo;
    }
    public ResponseEntity<EntityResponse> createCard(Card card) {
        try {
            if (card.getName()==null||card.getName().trim().isBlank()||card.getName().trim().isEmpty()){
                throw new IllegalArgumentException("Name is required!");
            }
            if (card.getColor()==null||card.getColor().trim().isEmpty()||card.getColor().trim().isBlank()){
                card.setColor("#0000FF");
            }
            if (card.getStatus()==null||card.getStatus().trim().isBlank()||card.getStatus().isEmpty()){
                card.setStatus(CONSTANTS.TODO);
            }
            validateCardColor(card.getColor());
            Optional<User> user = userRepository.findByUsername(UserRequestContext.getCurrentUser());
            card.setUser(user.orElse(null));
            Card savedCard = cardRepo.save(auditTrailService.POSTAudit(card));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(responseService.response(HttpStatus.CREATED, "CREATED SUCCESSFULLY!", savedCard));
        } catch (DataAccessException e) {
            log.error("Database access error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(responseService.response(HttpStatus.INTERNAL_SERVER_ERROR, "Database error occurred.", null));
        } catch (IllegalArgumentException e) {
            log.error("Invalid argument error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(responseService.response(HttpStatus.BAD_REQUEST, "Invalid argument: " + e.getMessage(), null));
        } catch (Exception e) {
            log.error("Unexpected error: " + e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(responseService.response(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.", null));
        }
    }
    private void validateCardColor(String color) {
        try{
            if (color != null && !color.matches("^#[A-Za-z0-9]{6}$")) {
                throw new IllegalArgumentException("Color should conform to the format '#RRGGBB'!");
            }
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid argument: " + e.getMessage());
        }

    }
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.username == #user.username")
    public ResponseEntity<?> findAllCard(@AuthenticationPrincipal User user) {
        try {
            List<Card> cardsList;

            if (user.getRoles().stream().anyMatch(role -> role.getName().equals(ERole.ROLE_ADMIN.toString()))) {
                cardsList = cardRepo.findByDeletedFlag('N');
            } else {
                cardsList = cardRepo.findByUserAndDeletedFlag(user, 'N');
            }

            if (!cardsList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.FOUND).body(cardsList);
            } else {
                throw new CustomExceptions.NotFoundException("No cards found");
            }
        } catch (CustomExceptions.NotFoundException e) {
            // Handle or log the NotFoundException
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Handle or log other exceptions
            log.error("An error occurred while processing the request.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PreAuthorize("hasRole('ADMIN') or authentication.principal.username == #user.username")
    public ResponseEntity<Object> findCardById(@AuthenticationPrincipal User user, Long id) {
        try {
            Optional<Card> cardOptional = cardRepo.findById(id);
            if (cardOptional.isPresent()) {
                boolean isAdmin = user.getRoles().stream()
                        .anyMatch(role -> role.getName().equals(ERole.ROLE_ADMIN.toString()));

                if (isAdmin || cardOptional.get().getUser().equals(user)) {
                    return ResponseEntity.status(HttpStatus.FOUND).body(cardOptional.get());
                } else {
                    throw new CustomExceptions.UnauthorizedException("Unauthorized access");
                }
            } else {
                throw new CustomExceptions.NotFoundException("Card not found");
            }
        } catch (CustomExceptions.JwtTokenExpiredException e) {
            // Handle JwtTokenExpiredException
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT token is expired: " + e.getMessage());
        }
        catch (CustomExceptions.UnauthorizedException e) {
            // Handle or log the UnauthorizedException
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (CustomExceptions.NotFoundException e) {
            // Handle or log the NotFoundException
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Handle or log other exceptions
            log.error("An error occurred while processing the request.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PreAuthorize("hasRole('ADMIN') or authentication.principal.username == #user.username")
    public ResponseEntity<Object> searchCards(
            String name, String color, String status,
            String dateFrom, String dateTo, int page, int size, String sort,
            @AuthenticationPrincipal User user
    ) {
        if (name==null || name.trim().isEmpty() || name.trim().isBlank()){
            name = "%";
        }
        if (status==null || status.trim().isEmpty() || status.trim().isBlank()){
            status = "%";
        }
        if (color==null || color.trim().isEmpty() || color.trim().isBlank()){
            color = "%";
        }
        if (sort==null || sort.trim().isEmpty() || sort.trim().isBlank()){
            sort = "id";
        }
        try {
            System.out.println("--------------------------");
            user.getRoles().stream().forEach(p -> {
                System.out.println("rOLE IS"+p.getName());
            });

            Page<Card> cardPage;
            if (user.getRoles().stream().anyMatch(role -> role.getName().equals(ERole.ROLE_ADMIN.toString()))) {
                System.out.println("is admin");
                // Admin can see all cards
                cardPage = cardRepo.findByNameAndColorAndStatusAndDeletedFlagAndPostedTimeBetweenOrderByPostedTimeDesc(
                        name, color, status, 'N', parseDate(dateFrom), parseDate(dateTo), PageRequest.of(page, size, Sort.by(sort)));
            } else {
                // Non-admin users can only see their own cards
                cardPage = cardRepo.findByUserAndNameAndColorAndStatusAndDeletedFlagAndPostedTimeBetweenOrderByPostedTimeDesc(
                        user.getId(), name, color, status, 'N', parseDate(dateFrom), parseDate(dateTo), PageRequest.of(page, size, Sort.by(sort)));
            }
            if (cardPage.hasContent()) {
                return ResponseEntity.status(HttpStatus.FOUND).body(cardPage.getContent());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            log.error("Error in searchCards: " + e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    private LocalDate parseDate(String dateString) {
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    public ResponseEntity<Object> updateCard(Long cardId, Card card) {
        try {
            if (card.getName()==null||card.getName().trim().isBlank()||card.getName().trim().isEmpty()){
                throw new CustomExceptions.NotAcceptableException("Name is required!");
            }
            if (card.getColor()==null||card.getColor().trim().isEmpty()||card.getColor().trim().isBlank()){
                card.setColor("#0000FF");
            }
            if (card.getStatus()==null||card.getStatus().trim().isBlank()||card.getStatus().isEmpty()){
                card.setStatus(CONSTANTS.TODO);
            }
            Optional<Card> cardOptional = cardRepo.findById(cardId);
            if (cardOptional.isPresent()) {
                Card existingCard = cardOptional.get();
                // Update description
                existingCard.setDescription(card.getDescription());
                // Update color
                existingCard.setColor(card.getColor());
                // Update status if it is one of the allowed values
                if (Arrays.asList("To Do", "In Progress", "Done").contains(card.getStatus())) {
                    existingCard.setStatus(card.getStatus());
                }

                Card updatedCard = cardRepo.save(auditTrailService.MODIFYAudit(existingCard));

                return ResponseEntity.status(HttpStatus.OK).body(updatedCard);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }  catch (CustomExceptions.NotAcceptableException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    public ResponseEntity<Object> deleteCard(Long id) {
        try {
            Optional<Card> cardOptional = cardRepo.findById(id);
            if (cardOptional.isPresent()) {
                Card card = cardOptional.get();
                card.setDeletedFlag('Y'); // Soft delete by setting a flag
                cardRepo.save(auditTrailService.DELETEAudit(card));
                throw new CustomExceptions.ResourceDeletedException("Card has been deleted");
            } else {
                throw new CustomExceptions.NotFoundException("Card not found");
            }
        } catch (CustomExceptions.ResourceDeletedException e) {
            // Handle or log the ResourceDeletedException
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Card has been deleted");
        } catch (CustomExceptions.NotFoundException e) {
            // Handle or log the NotFoundException
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card not found");
        } catch (Exception e) {
            // Handle or log other exceptions
            log.error("An error occurred while processing the request.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

}
