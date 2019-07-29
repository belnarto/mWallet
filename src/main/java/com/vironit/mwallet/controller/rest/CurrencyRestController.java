package com.vironit.mwallet.controller.rest;

import com.vironit.mwallet.controller.rest.exception.CurrencyRestControllerException;
import com.vironit.mwallet.controller.rest.exception.CurrencyValidationErrorException;
import com.vironit.mwallet.controller.rest.exception.ResourceNotFoundException;
import com.vironit.mwallet.models.dto.CurrencyDto;
import com.vironit.mwallet.models.entity.Currency;
import com.vironit.mwallet.services.CurrencyService;
import com.vironit.mwallet.services.mapper.CurrencyMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "unused"})
@RestController
@Log4j2
@RequestMapping("/api/v1")
class CurrencyRestController {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CurrencyMapper currencyMapper;

    @GetMapping(value = "/currencies")
    public ResponseEntity<List<CurrencyDto>> findAllCurrencies() {
        List<CurrencyDto> currencies = currencyService.findAll().stream()
                .map(currency -> currencyMapper.toDto(currency))
                .collect(Collectors.toList());
        return new ResponseEntity<>(currencies, HttpStatus.OK);
    }

    @PostMapping(value = "/currencies")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity createCurrency(@Valid @RequestBody CurrencyDto currencyDto,
                                         BindingResult bindingResult)
            throws CurrencyRestControllerException {

        if (bindingResult.hasErrors()) {
            throw new CurrencyValidationErrorException(bindingResult.getAllErrors());
        }

        try {
            Currency currency = currencyMapper.toEntity(currencyDto);
            currencyService.save(currency);
            return new ResponseEntity<>(currencyMapper.toDto(currency),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error during currency saving", e);
            throw new CurrencyRestControllerException("Error during currency saving", e);
        }
    }

    @PutMapping(value = "/currencies")
    public ResponseEntity putCurrencies() {
        return new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping(value = "/currencies")
    public ResponseEntity deleteCurrencies() {
        return new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @GetMapping(value = "/currencies/{currencyId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CurrencyDto> getCurrency(@PathVariable("currencyId") int currencyId)
            throws ResourceNotFoundException {

        Currency currency = currencyService.findById(currencyId);
        if (currency != null) {
            return new ResponseEntity<>(currencyMapper.toDto(currency), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @PostMapping(value = "/currencies/{currencyId}")
    public ResponseEntity postCurrency(@PathVariable("currencyId") int currencyId) {
        return new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PutMapping(value = "/currencies/{currencyId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateCurrency(@PathVariable("currencyId") int currencyId,
                                         @Valid @RequestBody CurrencyDto currencyDto,
                                         BindingResult bindingResult)
            throws CurrencyRestControllerException, ResourceNotFoundException {

        if (currencyService.findById(currencyId) == null) {
            throw new ResourceNotFoundException();
        }
        if (currencyId != currencyDto.getId()) {
            bindingResult.addError(new ObjectError("id", "currency ids don't match."));
        }

        if (bindingResult.hasErrors()) {
            throw new CurrencyValidationErrorException(bindingResult.getAllErrors());
        }

        try {
            Currency currency = currencyMapper.toEntity(currencyDto);
            currencyService.update(currency);
            return new ResponseEntity<>(currencyMapper.toDto(currency),
                    HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error during currency updating", e);
            throw new CurrencyRestControllerException("Error during currency updating", e);
        }
    }

    @DeleteMapping(value = "/currencies/{currencyId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteCurrency(@PathVariable("currencyId") int currencyId)
            throws ResourceNotFoundException {

        Currency currency = currencyService.findById(currencyId);
        if (currency != null) {
            currencyService.delete(currency);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException();
        }
    }
}
