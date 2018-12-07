Feature: Login Access
  Login Access

  Scenario: Login Acces UserPassSystemID OK
    Given Generar petición HTTP "hour/reservate" con headers
    Given Crear POST data hour-reservate
    When Ejecutar petición HTTP Form Data
    Then Se debe obtener un status code success 200
    Then Se debe obtener estado "active"