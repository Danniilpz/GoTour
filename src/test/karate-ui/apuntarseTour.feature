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
  * input('#username', 'vicky')
  * input('#password', 'aa')
  * submit().click("[type=submit]")
  * match html('title') contains 'Perfil'
  * delay(500)
   #vamos a la pagina de inicio para acceder a un tour
  * click("a[id=logo_box]")
  * match html('title') contains 'GoTour'
  * delay(500)
  * click("a[id=tour1]")
  * match html('title') contains 'Tour'
  * submit().click("input[id=contratar]")
  * match html('title') contains 'Pago'
  * submit().click("[type=submit]")
  * driver.screenshot()
  * click("a[id=perfil_ref]")
  * driver.screenshot()
