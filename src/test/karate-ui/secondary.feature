Feature: browser automation 2
#mvn exec:java
Background:
  # chromium bajo linux; 
  # si usas google-chrome, puedes quitar toda la parte de executable
  * configure driver = { type: 'chrome', showDriverLog: true }
  * url baseUrl
  * def util = Java.type('karate.KarateTests')

Scenario: Registrarse, crear un tour y editar la direccion de correo nueva
    Given driver 'http://localhost:8080/registro'
  * input('#nombre', 'Manolo')
  * input('#apellidos', 'Ejemplo')
  * input('#email', 'email@ejemplo1')
  * input('#password', 'patata')
  * input('#username', 'Aragorn')
  * input('#respuestaseguridad', 'Alpargato')
  * input('#telefono', '111222333')
  #En este punto hemos introducido todos los datos necesarios para crear un usuario
  * driver.screenshot() 
  * submit().click("[type=submit]")
  * input('#username', 'Aragorn')
  * input('#password', 'patata')
  * submit().click("[type=submit]")
  # Nos metemos en nuestro perfil
  * match html('title') contains 'Perfil'
  * click("a[id=crearTour]")
  * driver.screenshot()
  # A continuacion creamos un tour
  * input('#pais', 'España')
  * input('#ciudad', 'Madrid')
  * input('#lugar', 'Sol')
  * input('#titulo', 'Tour de Ejemplo')
  * input('#descripcion', 'Tour muy bonito que se hace para empezar a probar la funcionalidad de crearTours')
  * driver.screenshot()
  * value('#fechaInicial', script("const d = new Date(); d.setHours(10, 0, 0, 0); d.setDate(d.getDate()+1); d.toISOString().replace(/Z/, '')"))
  * input('#fechaFinal', script("const d2 = new Date(); d2.setHours(10, 0, 0, 0); d2.setDate(d2.getDate()+2); d2.toISOString().replace(/Z/, '')"))
  * driver.screenshot()
  * input('#maxTuristas', '15')
  * input('#precio', '10')
  * driver.screenshot()
  * click("[type=submit]")
  #No funciona porque las fechas son strings y el controlador necesita tipo date


