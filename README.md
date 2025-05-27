

![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/moonlightmoth/keyzz-backend/maven.yml)

# Keyzz-backend
<!-- ABOUT THE PROJECT -->
## About The Project

Keyzz-backend is main backend service for Keyzz application. This piece of code is responsible for accessing database of records where main business info is stored. Service depends on auth microservice [keyzz-auth](https://github.com/moonlightmoth/keyzz-auth).

### Built with
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)


### API reference
For API reference check [here](https://api.moonlightmoth.ru/keyzz-backend/swagger-ui/index.html)

<!-- GETTING STARTED -->
## How to run
  
### Build
```
git clone https://github.com/moonlightmoth/keyzz-backend.git
cd keyzz-backend
mvn clean compile package
```
Compiled jar will be inside target directory.

### Docker
```
git clone https://github.com/moonlightmoth/keyzz-backend.git
docker build --tag <your_tag> .
```

### Usage

In order to work properly keyzz-backend needs to be connected to postgresql database named with following schema: 

```
main_db=# \d records
                                        Table "public.records"
   Column   |            Type             | Collation | Nullable |              Default
------------+-----------------------------+-----------+----------+------------------------------------
 id         | integer                     |           | not null | nextval('record_id_seq'::regclass)
 address_id | integer                     |           | not null |
 note       | character varying(4095)     |           | not null |
 user_id    | integer                     |           |          |
 timestamp  | timestamp without time zone |           | not null |
Indexes:
    "record_pkey" PRIMARY KEY, btree (id)
Foreign-key constraints:
    "record_address_fkey" FOREIGN KEY (address_id) REFERENCES addresses(id)
    "record_user_id_fkey" FOREIGN KEY (user_id) REFERENCES users(id)


main_db=# \d users
                                    Table "public.users"
 Column  |         Type          | Collation | Nullable |              Default
---------+-----------------------+-----------+----------+-----------------------------------
 id      | integer               |           | not null | nextval('users_id_seq'::regclass)
 login   | character varying(30) |           | not null |
 name    | character varying(30) |           | not null |
 surname | character varying(30) |           | not null |
Indexes:
    "users_pkey" PRIMARY KEY, btree (id)
    "login_uq" UNIQUE CONSTRAINT, btree (login)
Referenced by:
    TABLE "records" CONSTRAINT "record_user_id_fkey" FOREIGN KEY (user_id) REFERENCES users(id)

main_db=# \d addresses
                                   Table "public.addresses"
 Column  |          Type          | Collation | Nullable |               Default
---------+------------------------+-----------+----------+-------------------------------------
 id      | integer                |           | not null | nextval('address_id_seq'::regclass)
 address | character varying(127) |           | not null |
Indexes:
    "address_pkey" PRIMARY KEY, btree (id)
Referenced by:
    TABLE "records" CONSTRAINT "record_address_fkey" FOREIGN KEY (address_id) REFERENCES addresses(id)


```


Secrets management implemented using enviromental variables:
```
KEYZZ_DATABASE_URL=jdbc:postgresql://<url> # database url to connect
KEYZZ_DATABASE_USERNAME=<database user>
KEYZZ_DATABASE_PASSWORD=<database password>
KEYZZ_AUTH_SERVER_URL=<url to keyzz-auth service>
```


<!-- CONTACT -->
## Contact

* vasilev.iv.dev@mail.ru
* [Telegram](https://t.me/moonlightmoth)
<p align="right">(<a href="#readme-top">back to top</a>)</p>

