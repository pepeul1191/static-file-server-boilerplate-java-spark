Feature: Hour Reservate
  Hour Reservate

  Scenario: Hour Reservate
    Given Generar petición HTTP "hour/reservate" con headers
    Given Crear POST data hour-reservate
    When Ejecutar petición HTTP
    Then Se debe obtener un status code success 200
    Then Se debe obtener el id generado