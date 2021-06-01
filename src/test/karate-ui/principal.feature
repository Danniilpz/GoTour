Feature: browser automation 1
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
   #vamos a acceder a un tour
  * submit().click("[type=submit]")
  * match html('title') contains 'Perfil'
  * driver.screenshot()
  #accedemos al tour
  * click("a[id=tour_ref]")
  * match html('title') contains 'Tour'
  # hacemos una review sobre el mismo
  * input('#textoReview', 'Un treh porque el guia era mu soso aunque el tour ha sido mu bonito')
  * driver.screenshot()
  * click("[id=add-review]")
  * match html('title') contains 'Tour'
  * driver.screenshot()
  * click("a[id=salir]")
  # Nos deslogueamos y volvemos a loguear con el guia del tour
  * click("a[id=login]")
  * input('#username', 'vicky')
  * input('#password', 'aa')
  * submit().click("[type=submit]")
  * click("a[id=tourReviewGuia]")
  * driver.screenshot()
  # accedemos al tour
  * click("a[id=hacer_review_guia]")
  * match html('title') contains 'Valoracion a los usuarios'
  * value('#valoracion3', '')
  * input('#valoracion3', '1')
  * input('#textoReview3', 'Se ha portado fatal y ha molestado mucho')
  * driver.screenshot()
  * click("[id=review-user]")
  * driver.screenshot()