Feature: Editar datos de usuario
#mvn exec:java
Background:
  # chromium bajo linux; 
  # si usas google-chrome, puedes quitar toda la parte de executable
  * configure driver = { type: 'chrome', showDriverLog: true }
  * url baseUrl
  * def util = Java.type('karate.KarateTests')

Scenario: logear, elegir un tour e inscribirse al tour
    Given driver 'http://localhost:8080/login'
    #Iniciamos sesion
  * input('#username', 'SONIA')
  * input('#password', 'aa')
  * submit().click("[type=submit]")
  * match html('title') contains 'Perfil'
  * click("a[id=reportar]")
  * click("a[id=ver_perfil]")
  * driver.screenshot()
  * click("a[id=report]")
  * input('#motivo', 'Ha llegado tarde el guía')
  * input('#explicacion', 'blablbalbalbaa')
  * submit().click("[type=submit]")
  * driver.screenshot()