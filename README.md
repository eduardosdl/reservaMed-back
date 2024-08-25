# crud patients

## Rota
### Post
http://localhost:8081/patients


#### Requisição
```
{
"name": "vinicius rodrigues de lima",
"birthDate": "2002-01-01",
"cpf": "13762676404",
"cellPhone": "(81) 91234-5678",
"email": "vini.silva@example.com",
"city": "recife",
"street": "rua aldemar de oliveira 67",
"state": "PE",
"cep": "51350570"
}

```
# crud Doctor

## Rota
### GET
http://localhost:8081/doctors

#### Reponse

[]

## Rota
### POST
http://localhost:8081/add-doctor

#### Requisição

```
{
 "name": "dyeggo bezerra",
"crm": "122",
"specialty": "cardio",
"cellPhone": "123456789"
}

```
## Rota
### PUT
http://localhost:8081/update-doctor/{CRM}

#### Requisição

```
{
 "name": "dyeggo bezerra",
"crm": "122",
"specialty": "cardio",
"cellPhone": "123456789"
}

```
## Rota
### DELETE
http://localhost:8081/dell-doctor/{CRM}


