# Indication of Authorship #

In the following, each person is assigned a list of files.
In general most files were created in collaboration. 
Therefor only the main contributions are shown in the list below.

Note that due to the contribution withing the project some files occur at multiple contributors.

This applies fully to the "Frontend" component, as this was developed completely together 
by Michael Ulrich, Simon Ruttmann and Veronika Scheller after the disappearance of Robin Roecker.

### Simon Ruttmann ###

#### ContentService ####
* GroupAuthorizationInterceptor
* GroupAuthorizationInterceptorConfig
* KeycloakConfiguration
* SecurityConfig
* SwaggerConfiguration
* CategoryController
* ProcessingController
* SavingEntryController
* ContentServiceMapper

#### DocumentDatabaseService ####
* Category
* DocObjectIdUtil
* GroupDocument
* IGroupDocumentRepository
* EmbeddedDocumentIdentifier
* GroupDocumentService
* IEmbeddedDocumentIdentifier
* IGroupDocumentService

#### DtoAndValidation ####
* CategoryDTO
* GeneralGroupInformationDTO
* SavingEntryDTO
* InflationDTO
* BalanceProcessResultDTO
* FilterInformationDTO
* IntervalGroupDTO
* ProcessResultContainerDTO
* SortParameter
* TimeInterval
* GroupDTO
* InvitationDTO
* InviteDTO
* PersonDTO
* Pair
* CategoryValidator
* FilterInformationValidator
* GroupValidator
* InviteValidator
* IValidatable
* IValidator
* SavingEntryValidator
* ValidatedValue
* ValidatorFactory

#### RelationDatabaseModule ####
* Group
* Invitation
* InvitationCompoundId
* InvitationStatus
* KPerson
* Pair
* DatabaseService
* GroupRepository
* IDatabaseService
* InvitationRepository
* KeycloakRepository
* RepositoryDetachAdapterCustom
* RepositoryDetachAdapterCustomImpl

#### Frontend ####

Java Script Files:
* PrivateRoute
* Advertisement
* Chat
* Content
* User
* Auth
* Axios
* Diagram1
* Diagram2
* Diagram3
* CategoryEditingBar
* EntryCreationBar
* EntryTable
* Homepage
* NavigationBar
* SearchBar
* SettingsPopup
* UpdateSavingEntry
* ChatComponent
* GuestSite
* CategorySlice
* ContentSlice
* GroupInformationSlice
* Store
* UserSlice
* Util
* App
* Index

Sassy Cascading Style Sheets:
* chat.scss
* guestsite.scss
* homepage.scss
* styles.scss

#### Configurations ####

* Advertisementservice POM
* Chatservice POM
* Contentservice POM
* DocumentDatabaseService POM
* DtoAndValidation POM
* InflationService POM
* InMemoryDatabaseService POM
* RelationalDatabaseService POM
* UserService POM
* Keycloak.json
* gitIgnore
* package.json
* docker-compose.yml
* Userservice Dockerfile.yml
* Inflationservice Dockerfile.yml
* Advertisementservice Dockerfile.yml
* Contentservice Dockerfile.yml
* Chatservice Dockerfile.yml
* Mongo Dockerfile.yml
* Redis Dockerfile.yml
* PostgreSql Dockerfile.yml

### Veronika Scheller ###

#### ContentService ####
* GroupAuthorizationInterceptor
* GroupAuthorizationInterceptorConfig

#### RelationDatabaseModule ####
* Group
* Invitation
* KPerson
* DatabaseService
* KeycloakRepository

#### UserService ####
* KeycloakConfiguration
* SecurityConfig
* SwaggerConfiguration
* GroupController
* InvitationController
* UserController
* KeycloakService
* UserServiceMapper
* IUserManagementService
* MongoTestDataCreator
* UserManagementService
* UserServiceApplication

#### Frontend ####

Java Script Files:
* PrivateRoute
* Advertisement
* Chat
* Content
* User
* Auth
* Axios
* Diagram1
* Diagram2
* Diagram3
* CategoryEditingBar
* EntryCreationBar
* EntryTable
* Homepage
* NavigationBar
* SearchBar
* SettingsPopup
* UpdateSavingEntry
* ChatComponent
* GuestSite
* CategorySlice
* ContentSlice
* GroupInformationSlice
* Store
* UserSlice
* Util
* App
* Index

Sassy Cascading Style Sheets:
* chat.scss
* guestsite.scss
* homepage.scss
* styles.scss

#### Configurations ####

* Advertisementservice POM
* Chatservice POM
* Contentservice POM
* DocumentDatabaseService POM
* DtoAndValidation POM
* InflationService POM
* InMemoryDatabaseService POM
* RelationalDatabaseService POM
* UserService POM
* Keycloak.json
* gitIgnore
* package.json
* docker-compose.yml
* Userservice Dockerfile.yml
* Inflationservice Dockerfile.yml
* Advertisementservice Dockerfile.yml
* Contentservice Dockerfile.yml
* Chatservice Dockerfile.yml
* Mongo Dockerfile.yml
* Redis Dockerfile.yml
* PostgreSql Dockerfile.yml


### Michel Ulrich ###

#### AdvertisementService ####
* AdvertisementServiceController
* AdvertisementDto
* AdvertisementServiceService
* AdvertisementServiceApplication

#### ChatService ####
* RedisConfig
* WebsocketConfig
* PublisherController
* SubscriberController
* ChatMessage
* ChatMessagePayload
* IRedisChatPersistenceService
* RedisChatPersistenceService
* RedisPublisher
* RedisSubscriber
* SubscriptionService
* ChatServiceApplication

#### InflationService ####
* InflationServiceController
* InflationServiceService
* InflationServiceApplication

#### InMemoryDatabaseService ####
* RedisConfig
* AtomicIntegerModel
* IRedisDatabaseService
* RedisDatabaseService

#### Frontend ####

Java Script Files:
* PrivateRoute
* Advertisement
* Chat
* Content
* User
* Auth
* Axios
* Diagram1
* Diagram2
* Diagram3
* CategoryEditingBar
* EntryCreationBar
* EntryTable
* Homepage
* NavigationBar
* SearchBar
* SettingsPopup
* UpdateSavingEntry
* ChatComponent
* GuestSite
* CategorySlice
* ContentSlice
* GroupInformationSlice
* Store
* UserSlice
* Util
* App
* Index

Sassy Cascading Style Sheets:
* chat.scss
* guestsite.scss
* homepage.scss
* styles.scss

#### Configurations ####

* Advertisementservice POM
* Chatservice POM
* Contentservice POM
* DocumentDatabaseService POM
* DtoAndValidation POM
* InflationService POM
* InMemoryDatabaseService POM
* RelationalDatabaseService POM
* UserService POM
* Keycloak.json
* gitIgnore
* package.json
* docker-compose.yml
* Userservice Dockerfile.yml
* Inflationservice Dockerfile.yml
* Advertisementservice Dockerfile.yml
* Contentservice Dockerfile.yml
* Chatservice Dockerfile.yml
* Mongo Dockerfile.yml
* Redis Dockerfile.yml
* PostgreSql Dockerfile.yml

### Robin Röcker ###

#### Frontend ####

Java Script Files:
* EntryTable
* Homepage
* NavigationBar
* GuestSite
* App
* Index
* ChatComponent

#### Configurations ####
* gitIgnore
* package.json
