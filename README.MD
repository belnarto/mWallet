http://87.252.246.155:8080/

Статус:
    Модели данных:
        User - builder pattern
        others - POJO
    DAO:
        CurrencyDaoImpl - JDBC
        others - Hibernate
    Servlets:
        раздел currencies сделан на сервлетах.
        часть сделана через web.xml, часть через аннотации.
    Spring MVC:
        разделы users и wallets

Technologies used :
    * JDK 1.8
    * Maven
    * IntelliJ IDEA
    
The URI structure :

URI	                                                Method	Action                                  Access level
/main                                               GET     Display main page                       all
/login                                              GET     Display login form                      all
/login                                              POST    Perform login                           all
/logout                                             POST    Perform logout                          all
/403                                                GET     Display access denied page              all
/users/addUser	                                    GET	    Display add user form                   all
/users/addUser	                                    POST	Create user action                      all
                            
/users	                                            GET	    Listing, display all users              ADMIN
                
/users/{userId}	                                    GET	    Display user inf form for {userId}       
                                                            any {userId} allowed                    ADMIN
                                                            only {userId} for current user          DEFAULT
                            
/users/{userId}/updateUser	                        GET	    Display update user form for {userId}   
                                                            any {userId} allowed                    ADMIN
                                                            only {userId} for current user          DEFAULT
                            
/users/{userId}/updateUser	                        POST	Update information for user {userId}       
                                                            any {userId} allowed                    ADMIN
                                                            only {userId} for current user          DEFAULT
                            
/users/{userId}/deleteUser	                        POST	delete user with {userId}       
                                                            any {userId} allowed                    ADMIN
                                                            only {userId} for current user          DEFAULT
                    
/wallets???	                                        GET	    Listing, display all wallets            ADMIN    
                
+/users/{userId}/wallets                             GET	    Display wallets for user {userId}       
                                                            any {userId} allowed                    ADMIN
                                                            only {userId} for current user          DEFAULT
                
+/users/{userId}/wallets/addWallet                   GET	    Add wallet form for user {userId}       
                                                            any {userId} allowed                    ADMIN
                                                            only {userId} for current user          DEFAULT
                
+/users/{userId}/wallets/addWallet                   POST    Create wallet for user {userId}       
                                                            any {userId} allowed                    ADMIN
                                                            only {userId} for current user          DEFAULT
    
/users/{userId}/wallets/{walletId}/editWallet       GET	    Edit wallet form {walletId}
                                                            form for user {userId}       
                                                            any {userId} and {walletId} allowed     ADMIN
                                                            only {userId} and {walletId} 
                                                            for current user                        DEFAULT

/users/{userId}/wallets/{walletId}/editWallet       POST    Edit wallet {walletId}
                                                            for user {userId}       
                                                            any {userId} and {walletId} allowed     ADMIN
                                                            only {userId} and {walletId} 
                                                            for current user                        DEFAULT                                                            

/users/{userId}/wallets/{walletId}/deleteWallet     POST    Delete wallet {walletId}
                                                            for user {userId}       
                                                            any {userId} and {walletId} allowed     ADMIN
                                                            only {userId} and {walletId} 
                                                            for current user                        DEFAULT

/users/{userId}/wallets/{walletId}/addBalance       GET	    Add money form {walletId}
                                                            form for user {userId}       
                                                            any {userId} and {walletId} allowed     ADMIN
                                                            only {userId} and {walletId} 
                                                            for current user                        DEFAULT

/users/{userId}/wallets/{walletId}/addBalance       POST    Add money {walletId}
                                                            for user {userId}       
                                                            any {userId} and {walletId} allowed     ADMIN
                                                            only {userId} and {walletId} 
                                                            for current user                        DEFAULT

/users/{userId}/wallets/{walletId}/reduceBalance    GET	    Reduce money form {walletId}
                                                            form for user {userId}       
                                                            any {userId} and {walletId} allowed     ADMIN
                                                            only {userId} and {walletId} 
                                                            for current user                        DEFAULT

/users/{userId}/wallets/{walletId}/reduceBalance    POST    Reduce money {walletId}
                                                            for user {userId}       
                                                            any {userId} and {walletId} allowed     ADMIN
                                                            only {userId} and {walletId} 
                                                            for current user                        DEFAULT
                                                                                                                        
Цель:
	Создать мобильный кошелёк в учебных целях.
	Освоить необходимые технологии и концепции.
	
Бизнес логика:
	Пользователь может иметь множество кошельков или ни одного.
	Кошельки возможны с различной валютой.
	Пользователь не может иметь более одно кошелька с одинаковой валютой.
	Баланс кошелька не может быть отрицательным.
	Пользователь может: 
		добавлять новые кошельки, 
		менять названия своих существующих кошельков,
		удалять свои существующие кошельки.
	Пользователь может:
		переводить средства между своими счетами
		переводить средства на карты других банков
		пополнять баланс кошелька используя ID
		производить платежи в различных валютах со своих кошельков (валюта кошельков может быть любой)
	Пользователь имеет доступ к истории операций по каждому из своих кошельков 
	
Пользователь:
	Под пользователем понимаем только физ лиц.
	Пользователь имеет следующую информацию о себе:
		ID
		Имя
		Фамилия
		Паспортные данные
		Телефон
		email
		Дату заведения в системе
		Список продуктов
		Список аккаунтинга по услугам
	Пользователь имеет 0 или более услуг компании (в текущей реализации только один 
										продукт возможен - кошелёк)
	
	
Продукт Кошелёк:
	У продукта кошелька следующая информация присутствует:
		ID
		Name
		Валюта
		Дату заведения в системе
	В будущем возможны другие продукты компании (кредиты, депозиты ...)
		
Продукт аккаунтинг:
	Эта сущность нужна для ведения учёта переменных параметров по используемым клиентом продуктам.
	У аккаунтинга по продукту присутствуют:
		Id
		UserId
		ProductId
		UpdatedAt
	Используем обычный double, а не BigDecimal, так как проект учебный, можем позволить не строгую точность.
	
	
Интерфейс конвертации:
	Производит конвертацию денежных средст (ДС) между различными валютами
	
Интерфейс функциональности кошелька:
	Производит перевод ДС между кошельками
	Производит перевод ДС на карты других банков
	Обрабатывает запросы на пополнение баланса
	Обрабатывает запросы на произведение оплаты
	Ведёт и предоставляет историческую информацию по кошельку
	
	
Предполагаемая реализация:
	
	для пользователей:
	Интерфейс User для пользователя с методами общими для любых пользователей setId, getId, 
		setMsisdn, getMsisdn, setEmail, getEmail, getCreatedAt, addProduct, getProducts
	От этого интерфейса наследуется интерфейс IndividualUser и добавляет методов setFirstName, 
		getFirstName, setLastName, getLastName, setPassportId, getPassportId
	Реализует IndividualUser класс IndividualUserClass.
	При таком подходе мы сможем добавлять методы в любое время как всем юзерам, так и только физ лицам.
	Если потребуется заводить новых клиентов, например, юр лиц, то сделаем новый интерфейс и класс для юр лиц.
	Не абстрактный класс, так как в будущем не будем хранить информацию о клиенте в объекте, а будем в БД.
	Т. е. переменных класса не будет.
	
	для продуктов:
	Интерфейс Product для продуктов с методами общими для любых продуктов setId, getId, 
		setName, getName, getCreatedAt
	От этого интерфейса наследуется интерфейс Purse и добавляет методов setСurrency, 
		getСurrency	
	Реализует Purse класс PurseClass.
	
	для продукт аккаунтинг:
	Интерфейс ProductAccounting для продуктов с методами общими для любых продуктов setId, getId, 
		setUserId, getUserId, setProductId, getProductId, getUpdatedAt
	От этого интерфейса наследуется интерфейс PurseAccounting и добавляет методов addBalance, 
		reduceBalance, getBalance
	Реализует PurseAccounting класс PurseAccountingClass.
	
	Интерфейс конвертации:
	Создадим интерфейс СurrencyConverter с методом convert
	Реализуем СurrencyConverter в СurrencyConverterClass
	
	
	для движка кошелька:
	Создадим интерфейс PurseEngine с методами tranferToPurse, transferToCard, 
		addToPurse, payWithPurse, getPurseReport
	Реализуем PurseEngine в PurseEngineClass
	
	пока не прикрутим базу создадим класс, который будет являться хранилищем наших продуктов, 
		клиентов и аккаунтинга.