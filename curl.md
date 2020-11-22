curl for MealRestController

get all:

curl http://localhost:8080/topjava/rest/meals

get by id:

curl http://localhost:8080/topjava/rest/meals/100002

delete:

curl -X DELETE http://localhost:8080/topjava/rest/meals/100006

update:

curl -X PUT http://localhost:8080/topjava/rest/meals/100005 -H 'content-type: application/json;charset=utf-8' -d '{"id": "100005", "dateTime" : "2020-01-30T11:11:11", "description" : "updatedMeal", "calories" : 555, "user" : null}'

create:

curl -X POST http://localhost:8080/topjava/rest/meals -H 'content-type: application/json;charset=utf-8' -d '{"dateTime" : "2020-01-30T22:22:22", "description" : "newMeal", "calories" : 222, "user" : null}'

filter:

curl http://localhost:8080/topjava/rest/meals/filter?start=2020-01-30T13:00&end=2020-01-30T20:00
