package com.vironit.mwallet.controller.rest;

import com.vironit.mwallet.controller.rest.exception.ResourceNotFoundException;
import com.vironit.mwallet.controller.rest.exception.UserRestControllerException;
import com.vironit.mwallet.controller.rest.exception.UserValidationErrorException;
import com.vironit.mwallet.model.dto.UserRestDto;
import com.vironit.mwallet.model.dto.UserRestDtoWithoutPassword;
import com.vironit.mwallet.model.entity.User;
import com.vironit.mwallet.service.UserService;
import com.vironit.mwallet.service.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "unused"})
@Log4j2
@RestController
@RequestMapping("/api/v1")
class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping(value = "/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserRestDtoWithoutPassword>> findAllUsers(
            @RequestParam Map<String, String> allParams) {

        if (allParams.isEmpty()) {
            List<UserRestDtoWithoutPassword> users = userService.findAll().stream()
                    .map(user -> userMapper.toRestDtoWithoutPassword(user))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(users, HttpStatus.OK);
        }

        if (allParams.containsKey("namePart")) {
            List<UserRestDtoWithoutPassword> users = userService
                    .findAllByNamePart(allParams.get("namePart")).stream()
                    .map(user -> userMapper.toRestDtoWithoutPassword(user))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(users, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("Duplicates")
    @PostMapping(value = "/users")
    public ResponseEntity<UserRestDtoWithoutPassword> createUser(@Valid @RequestBody UserRestDto userRestDto,
                                                                 BindingResult bindingResult)
            throws UserRestControllerException {

        if (bindingResult.hasErrors()) {
            throw new UserValidationErrorException(bindingResult.getAllErrors());
        }
        try {
            User user = userMapper.toEntity(userRestDto);
            userService.save(user);
            return new ResponseEntity<>(userMapper.toRestDtoWithoutPassword(user),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error during user saving", e);
            throw new UserRestControllerException("Error during user saving", e);
        }
    }

    @PutMapping(value = "/users")
    public ResponseEntity putUsers() {
        return new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping(value = "/users")
    public ResponseEntity deleteUsers() {
        return new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @GetMapping(value = "/users/{userId}")
    @PreAuthorize("@securityServiceImpl.checkUserId(authentication,#userId) or hasRole('ADMIN')")
    public ResponseEntity<UserRestDtoWithoutPassword> findUserById(@PathVariable("userId") int userId)
            throws ResourceNotFoundException {

        User user = userService.findById(userId);
        if (user != null) {
            return new ResponseEntity<>(userMapper.toRestDtoWithoutPassword(user), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @PostMapping(value = "/users/{userId}")
    public ResponseEntity postUserById(@PathVariable("userId") int userId) {
        return new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PutMapping(value = "/users/{userId}")
    @PreAuthorize("@securityServiceImpl.checkUserId(authentication,#userId) or hasRole('ADMIN')")
    public ResponseEntity updateUser(@PathVariable("userId") int userId,
                                     @Valid @RequestBody UserRestDto userRestDto,
                                     BindingResult bindingResult)
            throws UserRestControllerException, ResourceNotFoundException {

        if (userService.findById(userId) == null) {
            throw new ResourceNotFoundException();
        }
        if (userId != userRestDto.getId()) {
            bindingResult.addError(new ObjectError("id", "user ids don't match."));
        }
        if (bindingResult.hasErrors()) {
            throw new UserValidationErrorException(bindingResult.getAllErrors());
        }
        try {
            User user = userMapper.toEntity(userRestDto);
            userService.update(user);
            return new ResponseEntity<>(userMapper.toRestDtoWithoutPassword(user),
                    HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error during user updating", e);
            throw new UserRestControllerException("Error during user updating", e);
        }
    }

    @DeleteMapping(value = "/users/{userId}")
    @PreAuthorize("@securityServiceImpl.checkUserId(authentication,#userId) or hasRole('ADMIN')")
    public ResponseEntity deleteUser(@PathVariable("userId") int userId) throws ResourceNotFoundException {

        User user = userService.findById(userId);
        if (user != null) {
            userService.delete(user);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException();
        }
    }

}
