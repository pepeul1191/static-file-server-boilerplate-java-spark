require 'httparty'
require 'json'

Given("Generar petición HTTP {string} con headers") do |url|
  @url = CONSTANTS[:BASE_URL] + url
  @headers = {
    CONSTANTS[:CSRF][:key] => CONSTANTS[:CSRF][:value],
    'Content-Type' => 'application/x-www-form-urlencoded',
    'charset' => 'utf-8'
  }
end

When("Ejecutar petición HTTP") do
  @response = HTTParty.post(
    @url,
    headers: @headers,
    body: 'data=' + URI.escape(@data.to_json, Regexp.new("[^#{URI::PATTERN::UNRESERVED}]")),
  )
end

When("Ejecutar petición HTTP Form Data") do
  data = ''
  @data.each do |key, value|
    data = data + key.to_s + '=' + URI.escape(value.to_s, Regexp.new("[^#{URI::PATTERN::UNRESERVED}]")) + '&'
  end
  #puts data
  @response = HTTParty.post(
    @url,
    headers: @headers,
    body: data
  )
end

When("Ejecutar petición HTTP Query Params") do
  data = ''
  @data.each do |key, value|
    data = data + key.to_s + '=' + URI.escape(value.to_s, Regexp.new("[^#{URI::PATTERN::UNRESERVED}]")) + '&'
  end
  puts @url + '?' + data
  @response = HTTParty.get(
    @url + '?' + data,
    headers: @headers,
  )
end

Then("Se debe obtener un status code success {int}") do |status_code|
  if SHOW_RESPONSE
    puts @response.code
    puts "0 ++++++++++++++++++++"
    puts @response.body
    puts "1 ++++++++++++++++++++"
  end
  expect(@response.code).to be == status_code
end

Then("Se debe obtener el id mongo generado") do
  if @response.code != 200
    fail('Error, se obtuvo un response code 404')
  else
    rpta = JSON.parse(@response.body)
    mongo_id = rpta['mensaje'][1]
    expect(mongo_id.length).to be == 24
  end
end

Then("No se debe obtener el id generado") do
  if @response.code != 200
    fail('Error, se obtuvo un response code 404')
  else
    rpta = JSON.parse(@response.body)
    nuevos = rpta['mensaje'][1].length
    expect(nuevos).to be == 0
  end
end
