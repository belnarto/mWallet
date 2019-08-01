package com.vironit.mwallet.controller.rest;

import com.vironit.mwallet.controller.rest.exception.TransactionRestControllerException;
import com.vironit.mwallet.controller.rest.exception.TransactionValidationErrorException;
import com.vironit.mwallet.controller.rest.exception.WalletRestControllerException;
import com.vironit.mwallet.controller.rest.exception.WalletValidationErrorException;
import com.vironit.mwallet.model.dto.TransactionRestDto;
import com.vironit.mwallet.model.dto.WalletDto;
import com.vironit.mwallet.model.entity.Transaction;
import com.vironit.mwallet.model.entity.Wallet;
import com.vironit.mwallet.service.TransactionService;
import com.vironit.mwallet.service.mapper.TransactionMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "unused"})
@RestController
@Log4j2
@RequestMapping("/api/v1")
public class TransactionRestController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionMapper transactionMapper;

    @PostMapping(value = "/transactions")
    @PreAuthorize("hasRole('DEFAULT') or hasRole('ADMIN')")
    public ResponseEntity<TransactionRestDto> createTransaction(@Valid @RequestBody TransactionRestDto transactionRestDto,
                                                                BindingResult bindingResult)
            throws TransactionRestControllerException {

        if (bindingResult.hasErrors()) {
            throw new TransactionValidationErrorException(bindingResult.getAllErrors());
        }

        try {
            //Wallet wallet = walletMapper.toEntity(walletRestDtoWithUserAndCurrencyId);
            //transactionService.save(transaction);
            //return new ResponseEntity<>(transaction,
            //        HttpStatus.CREATED);
            Transaction transaction = transactionMapper.toEntity(transactionRestDto);
            return new ResponseEntity<>(transactionMapper.toDto(transaction), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error during wallet saving", e);
            throw new TransactionRestControllerException("Error during transaction saving", e);
        }
    }

}
