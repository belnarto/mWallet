package com.vironit.mwallet.controller.rest;

import com.vironit.mwallet.controller.rest.exception.ResourceNotFoundException;
import com.vironit.mwallet.controller.rest.exception.TransactionRestControllerException;
import com.vironit.mwallet.controller.rest.exception.TransactionValidationErrorException;
import com.vironit.mwallet.model.attribute.TransactionStatus;
import com.vironit.mwallet.model.dto.TransactionRestDto;
import com.vironit.mwallet.model.entity.Transaction;
import com.vironit.mwallet.service.TransactionService;
import com.vironit.mwallet.service.mapper.TransactionMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "unused"})
@RestController
@Log4j2
@RequestMapping("/api/v1")
public class TransactionRestController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionMapper transactionMapper;

    @GetMapping(value = "/transactions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TransactionRestDto>> findAllTransactions(
            @RequestParam Map<String, String> allParams) {

        if (allParams.isEmpty()) {
            List<TransactionRestDto> transactions = transactionService.findAll().stream()
                    .map(transaction -> transactionMapper.toDto(transaction))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/transactions")
    @PreAuthorize("hasRole('DEFAULT') or hasRole('ADMIN')")
    public ResponseEntity<TransactionRestDto> createTransaction(
            @Valid @RequestBody TransactionRestDto transactionRestDto,
            BindingResult bindingResult)
            throws TransactionRestControllerException {

        if (bindingResult.hasErrors()) {
            throw new TransactionValidationErrorException(bindingResult.getAllErrors());
        }

        try {
            Transaction transaction = transactionMapper.toEntity(transactionRestDto);
            transactionService.save(transaction);
            return new ResponseEntity<>(transactionMapper.toDto(transaction), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error during transaction saving", e);
            throw new TransactionRestControllerException("Error during transaction saving", e);
        }
    }

    @PutMapping(value = "/transactions")
    public ResponseEntity putTransactions() {
        return new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping(value = "/transactions")
    public ResponseEntity deleteTransactions() {
        return new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @GetMapping(value = "/transactions/{transactionId}")
    @PreAuthorize("@securityServiceImpl.checkTransactionId(authentication,#transactionId) or hasRole('ADMIN')")
    public ResponseEntity<TransactionRestDto> findTransactionById(
            @PathVariable("transactionId") int transactionId)
            throws ResourceNotFoundException {

        Transaction transaction = transactionService.findById(transactionId);
        if (transaction != null) {
            return new ResponseEntity<>(transactionMapper.toDto(transaction), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @PostMapping(value = "/transactions/{transactionId}")
    public ResponseEntity postTransactionById(@PathVariable("transactionId") int transactionId) {
        return new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PutMapping(value = "/transactions/{transactionId}")
    public ResponseEntity putTransactionById(@PathVariable("transactionId") int transactionId) {
        return new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping(value = "/transactions/{transactionId}")
    public ResponseEntity deleteTransactionById(@PathVariable("transactionId") int transactionId) {
        return new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PatchMapping(value = "/transactions/{transactionId}")
    @PreAuthorize("@securityServiceImpl.checkTransactionId(authentication,#transactionId) or hasRole('ADMIN')")
    public ResponseEntity<TransactionRestDto> createTransaction(
            @PathVariable("transactionId") int transactionId,
            @RequestParam Map<String, String> allParams)
            throws TransactionRestControllerException, ResourceNotFoundException {

        Transaction transaction = transactionService.findById(transactionId);

        if (transaction == null) {
            throw new ResourceNotFoundException();
        }

        if (allParams.isEmpty()) {
            throw new TransactionValidationErrorException("Patching field must be specified");
        }

        if (allParams.containsKey("status")) {
            TransactionStatus newStatus = TransactionStatus.valueOf(allParams.get("status"));
            transaction.setStatus(newStatus);
            transactionService.update(transaction);
            return new ResponseEntity<>(transactionMapper.toDto(transaction), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
