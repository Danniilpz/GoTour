Feature: browser automation 2
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
   #vamos a la pagina de inicio para acceder a un tour
  * click("a[id=logo_box]")
  * match html('title') contains 'GoTour'
  #Accedemos a un tour
  * click("a[id=tour1]")
  * match html('title') contains 'Tour'
  #Reportamos
  * click("a[id=reportar]")
  * match html('title') contains 'Reportar Tour'

  * value('#motivo', 'No me gusta')
  * input('#reporte', 'Esta repetido')
  * driver.screenshot()
  * click("[type=submit]")
  * match html('title') contains 'Reporte recibido'
