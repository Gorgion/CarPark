## Final description ##

A company allows a rental of their cars to employees. The company employees can apply for a rental of a car. A manager of the company evaluates the request and allows or cancels the rental. The rental is applied to a given date and can be canceled until is marked as active. The system offers a list of available cars for rent and also allows it's users to make a reservation of a specific car. Users and cars can be added, edited or removed only by administrator of the company.


## Preliminary description ##

The project should emulate a real situation in a company. The company employees can apply for rental of a car. The rental is applied to a given date. Based on such application, the system will offer list of free cars and the system allows reservation of a car.

## Use case diagram ##
![useCase.jpg](https://bitbucket.org/repo/8op8qp/images/2354194273-useCase.jpg)

## Class diagram ##
![classDiagram.jpg](https://bytebucket.org/JayDee8/pa165-car-park/raw/3eb587469281c6509b55f7ef61297e2bcb8d8571/res/classDiagram.jpg)


## Team ##
Rychlá rota

## Spuštění ##
Build aplikace se provede pomocí příkazu "mvn clean install".

Aplikace je spustitelná pomocí příkazu "mvn tomcat7:run" (v adresáři carpark-web). url: http://localhost:8080/pa165/

### REST klient ###
Rest klient se spouští pomocí příkazu "mvn tomcat7:run" (v adresáři carpark-webclient. url: http://localhost:8080/pa165/client
Pro entitu Office jsou dostupné tyto operace:

```
#!
přidat 
odebrat
```
Pro entitu User jsou dostupné tyto operace:

```
#!
přidat 
odebrat
```