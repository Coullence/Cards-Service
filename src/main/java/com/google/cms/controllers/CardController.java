package com.google.cms.controllers;

import com.google.cms.services.CardService;
import com.google.cms.models.Card;
import com.google.cms.utilities.Shared.AuthenticatedUser;
import com.google.cms.utilities.Shared.EntityResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/cards")
public class CardController {

    private final CardService cardService;
    private AuthenticatedUser authenticatedUser;

    public CardController(CardService cardService, AuthenticatedUser authenticatedUser) {
        this.cardService = cardService;
        this.authenticatedUser = authenticatedUser;
    }


    @PostMapping
    public ResponseEntity<?> createCard(@RequestBody Card card) {
        return cardService.createCard(card);
    }
    @GetMapping
    public ResponseEntity<?> findAllCards(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String status,
            @RequestParam(required = true) String dateFrom,
            @RequestParam(required = true) String dateTo,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false) String sort
    ) {
        return cardService.searchCards(name, color, status, dateFrom, dateTo, page, size, sort, authenticatedUser.getUser());
    }
    @GetMapping("/{cardId}")
    public ResponseEntity<?> findCardById(@PathVariable Long cardId) {
       return cardService.findCardById(authenticatedUser.getUser(),cardId);
    }
    @PutMapping("/{cardId}")
    public ResponseEntity<?> updateCard(@PathVariable Long cardId, @RequestBody Card card) {
        return cardService.updateCard(cardId, card);
    }
    @DeleteMapping("/{cardId}")
    public ResponseEntity<?> deleteCard(@PathVariable Long cardId) {
        return cardService.deleteCard(cardId);
    }

}
