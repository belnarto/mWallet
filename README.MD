Демо приложения доступно по следующему адресу: 
http://87.252.246.155:8080/mWallet

Technologies used :

    * JDK 1.8
    * Spring MVC, Security, WebSocket 5.1
    * Hibernate core, validator, ehcache 5.4
    * PostgreSQL 9.4
    * Hikari
    * JDBC
    * ServletAPI 4
    * JUnit 4
    * Maven
    * Lombok
    * Log4j2
    * IntelliJ IDEA
    * and others


The WEB URL structure:

| URL                                             | Method | Action                                             | Access    |
| ----------------------------------------------- | ------ | -------------------------------------------------- | ----------|
| /main                                           |  GET   | Display main page                                  | all       |
| /login                                          |  GET   | Display login form                                 | all       |
| /login                                          |  POST  | Perform login                                      | all       |
| /logout                                         |  POST  | Perform logout                                     | all       |
| /403                                            |  GET   | Display access denied page                         | all       |
| /users/addUser                                  |  GET   | Display add user form                              | all       |
| /users/addUser                                  |  POST  | Create user action                                 | all       |
| /users	                                      |  GET   | Listing, display all users                         | ADMIN     |                                               	                                  
| /users/{userId}                                 |  GET   | Display user inf form for {userId}                 |           |
|                                                 |        | -any {userId} allowed                              | ADMIN     |
|                                                 |        | -only {userId} for current user                    | DEFAULT   |
|/users/{userId}/updateUser	                      |  GET   | Display update user form for {userId}              |           |
|                                                 |        | -any {userId} allowed                              | ADMIN     |
|                                                 |        | -only {userId} for current user                    | DEFAULT   |
|/users/{userId}/updateUser	                      |  POST  | Update information for user {userId}               |           |
|                                                 |        | -any {userId} allowed                              | ADMIN     |
|                                                 |        | -only {userId} for current user                    | DEFAULT   |
|/users/{userId}/deleteUser	                      |  POST  | Delete user with {userId}                          |           |
|                                                 |        | -any {userId} allowed                              | ADMIN     |
|                                                 |        | -only {userId} for current user                    | DEFAULT   |
|/users/{userId}/wallets                          |  GET   | Display wallets for user {userId}                  |           |
|                                                 |        | -any {userId} allowed                              | ADMIN     |
|                                                 |        | -only {userId} for current user                    | DEFAULT   |
|/users/{userId}/wallets/addWallet                |  GET   | Add wallet form for user {userId}                  |           |
|                                                 |        | -any {userId} allowed                              | ADMIN     |
|                                                 |        | -only {userId} for current user                    | DEFAULT   |
|/users/{userId}/wallets/addWallet                |  POST  | Create wallet for user {userId}                    |           |
|                                                 |        | -any {userId} allowed                              | ADMIN     |
|                                                 |        | -only {userId} for current user                    | DEFAULT   |
|/users/{userId}/wallets/{walletId}/editWallet    |  GET   | Edit wallet form {walletId} form for user {userId} |           |
|                                                 |        | -any {userId} and {walletId} allowed               | ADMIN     |
|                                                 |        | -only {userId} and {walletId} for current user     | DEFAULT   |
|/users/{userId}/wallets/{walletId}/editWallet    |  POST  | Edit wallet {walletId} for user {userId}           |           |
|                                                 |        | -any {userId} and {walletId} allowed               | ADMIN     |
|                                                 |        | -only {userId} and {walletId} for current user     | DEFAULT   |                                     
|/users/{userId}/wallets/{walletId}/deleteWallet  |  POST  | Delete wallet {walletId} for user {userId}         |           |
|                                                 |        | -any {userId} and {walletId} allowed               | ADMIN     |
|                                                 |        | -only {userId} and {walletId} for current user     | DEFAULT   |
|/users/{userId}/wallets/{walletId}/addBalance    |  GET   | Add money form {walletId} form for user {userId}   |           |
|                                                 |        | -any {userId} and {walletId} allowed               | ADMIN     |
|                                                 |        | -only {userId} and {walletId} for current user     | DEFAULT   |
|/users/{userId}/wallets/{walletId}/addBalance    |  POST  | Add money {walletId} for user {userId}             |           |
|                                                 |        | -any {userId} and {walletId} allowed               | ADMIN     |
|                                                 |        | -only {userId} and {walletId} for current user     | DEFAULT   |
|/users/{userId}/wallets/{walletId}/reduceBalance |  GET   | Reduce money form {walletId} form for user {userId}|           |
|                                                 |        | -any {userId} and {walletId} allowed               | ADMIN     |
|                                                 |        | -only {userId} and {walletId} for current user     | DEFAULT   |                 
|/users/{userId}/wallets/{walletId}/reduceBalance |  POST  | Reduce money {walletId} for user {userId}          |           |
|                                                 |        | -any {userId} and {walletId} allowed               | ADMIN     |
|                                                 |        | -only {userId} and {walletId} for current user     | DEFAULT   |                     
