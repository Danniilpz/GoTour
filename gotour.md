# gotour

## contenido

~~~~
gotour/
gotour/pom.xml
gotour/leeme.html
gotour/diagrama.png
gotour/src
gotour/src/main
gotour/src/main/java
gotour/src/main/java/es
gotour/src/main/java/es/ucm
gotour/src/main/java/es/ucm/fdi
gotour/src/main/java/es/ucm/fdi/iw
gotour/src/main/java/es/ucm/fdi/iw/gotour
gotour/src/main/java/es/ucm/fdi/iw/gotour/WebConfiguration.java
gotour/src/main/java/es/ucm/fdi/iw/gotour/IwUserDetailsService.java
gotour/src/main/java/es/ucm/fdi/iw/gotour/AppConfig.java
gotour/src/main/java/es/ucm/fdi/iw/gotour/IwApplication.java
gotour/src/main/java/es/ucm/fdi/iw/gotour/control
gotour/src/main/java/es/ucm/fdi/iw/gotour/control/AdminController.java
gotour/src/main/java/es/ucm/fdi/iw/gotour/control/UserController.java
gotour/src/main/java/es/ucm/fdi/iw/gotour/control/MensajeController.java
gotour/src/main/java/es/ucm/fdi/iw/gotour/control/RootController.java
gotour/src/main/java/es/ucm/fdi/iw/gotour/control/TourController.java
gotour/src/main/java/es/ucm/fdi/iw/gotour/LocalData.java
gotour/src/main/java/es/ucm/fdi/iw/gotour/model
gotour/src/main/java/es/ucm/fdi/iw/gotour/model/Reporte.java
gotour/src/main/java/es/ucm/fdi/iw/gotour/model/Review.java
gotour/src/main/java/es/ucm/fdi/iw/gotour/model/Transferable.java
gotour/src/main/java/es/ucm/fdi/iw/gotour/model/Reserva.java
gotour/src/main/java/es/ucm/fdi/iw/gotour/model/Mensaje.java
gotour/src/main/java/es/ucm/fdi/iw/gotour/model/TourOfertado.java
gotour/src/main/java/es/ucm/fdi/iw/gotour/model/User.java
gotour/src/main/java/es/ucm/fdi/iw/gotour/model/Tour.java
gotour/src/main/java/es/ucm/fdi/iw/gotour/WebSocketConfig.java
gotour/src/main/java/es/ucm/fdi/iw/gotour/SecurityConfig.java
gotour/src/main/java/es/ucm/fdi/iw/gotour/LoginSuccessHandler.java
gotour/src/main/java/es/ucm/fdi/iw/gotour/WebSocketSecurityConfig.java
gotour/src/main/java/es/ucm/fdi/iw/gotour/StartupConfig.java
gotour/src/main/resources
gotour/src/main/resources/application-externaldb.properties
gotour/src/main/resources/Messages_es.properties
gotour/src/main/resources/import.sql
gotour/src/main/resources/application.properties
gotour/src/main/resources/Messages_en.properties
gotour/src/main/resources/templates
gotour/src/main/resources/templates/respuestasAdmin.html
gotour/src/main/resources/templates/tour.html
gotour/src/main/resources/templates/perfil-reportes.html
gotour/src/main/resources/templates/gracias-reporte-ha-sido-recibido.html
gotour/src/main/resources/templates/editarTour.html
gotour/src/main/resources/templates/crearInstancia.html
gotour/src/main/resources/templates/pago.html
gotour/src/main/resources/templates/admin
gotour/src/main/resources/templates/admin/gestion-reporte.html
gotour/src/main/resources/templates/admin/user-busqueda.html
gotour/src/main/resources/templates/admin/fragment
gotour/src/main/resources/templates/admin/fragment/nav.html
gotour/src/main/resources/templates/admin/fragment/footer.html
gotour/src/main/resources/templates/admin/reporte-usuario.html
gotour/src/main/resources/templates/admin/reportes-usuarios.html
gotour/src/main/resources/templates/admin/index.html
gotour/src/main/resources/templates/admin/assets
gotour/src/main/resources/templates/admin/assets/scss
gotour/src/main/resources/templates/admin/assets/scss/material-dashboard.css.map
gotour/src/main/resources/templates/admin/assets/scss/material-dashboard
gotour/src/main/resources/templates/admin/assets/scss/material-dashboard/bootstrap
gotour/src/main/resources/templates/admin/assets/scss/material-dashboard/bootstrap/scss
gotour/src/main/resources/templates/admin/assets/scss/material-dashboard/bootstrap/scss/bootstrap-reboot.css.map
gotour/src/main/resources/templates/admin/assets/scss/material-dashboard/bootstrap/scss/bootstrap-grid.css.map
gotour/src/main/resources/templates/admin/assets/scss/material-dashboard/bootstrap/scss/bootstrap-grid.css
gotour/src/main/resources/templates/admin/assets/scss/material-dashboard/bootstrap/scss/bootstrap.css
gotour/src/main/resources/templates/admin/assets/scss/material-dashboard/bootstrap/scss/bootstrap.css.map
gotour/src/main/resources/templates/admin/assets/scss/material-dashboard/bootstrap/scss/bootstrap-reboot.css
gotour/src/main/resources/templates/admin/assets/scss/material-dashboard.css
gotour/src/main/resources/templates/admin/users.html
gotour/src/main/resources/templates/fragments
gotour/src/main/resources/templates/fragments/head.html
gotour/src/main/resources/templates/fragments/imagenes-principales.html
gotour/src/main/resources/templates/fragments/nav.html
gotour/src/main/resources/templates/fragments/footer.html
gotour/src/main/resources/templates/registro.html
gotour/src/main/resources/templates/reporteUser.html
gotour/src/main/resources/templates/error
gotour/src/main/resources/templates/error/404.html
gotour/src/main/resources/templates/error/400.html
gotour/src/main/resources/templates/error/401.html
gotour/src/main/resources/templates/error/502.html
gotour/src/main/resources/templates/error/405.html
gotour/src/main/resources/templates/error/403.html
gotour/src/main/resources/templates/error/500.html
gotour/src/main/resources/templates/frags
gotour/src/main/resources/templates/frags/header.html
gotour/src/main/resources/templates/crearTour.html
gotour/src/main/resources/templates/reviewUsuarios.html
gotour/src/main/resources/templates/login.html
gotour/src/main/resources/templates/error.html
gotour/src/main/resources/templates/index.html
gotour/src/main/resources/templates/chat.html
gotour/src/main/resources/templates/reporteTour.html
gotour/src/main/resources/templates/perfil.html
gotour/src/main/resources/templates/user.html
gotour/src/main/resources/templates/editarDatos.html
gotour/src/main/resources/static
gotour/src/main/resources/static/js
gotour/src/main/resources/static/js/aos.js
gotour/src/main/resources/static/js/user.js
gotour/src/main/resources/static/js/jquery-ui.js
gotour/src/main/resources/static/js/jquery.magnific-popup.min.js
gotour/src/main/resources/static/js/popper.min.js
gotour/src/main/resources/static/js/.DS_Store
gotour/src/main/resources/static/js/mediaelement-and-player.min.js
gotour/src/main/resources/static/js/iwclient.js
gotour/src/main/resources/static/js/toures-perfil.js
gotour/src/main/resources/static/js/player.js
gotour/src/main/resources/static/js/tour.js
gotour/src/main/resources/static/js/main.js
gotour/src/main/resources/static/js/simple-datatables-2.1.10.map
gotour/src/main/resources/static/js/owl.carousel.min.js
gotour/src/main/resources/static/js/login.js
gotour/src/main/resources/static/js/stomp.js
gotour/src/main/resources/static/js/circleaudioplayer.js
gotour/src/main/resources/static/js/jquery.countdown.min.js
gotour/src/main/resources/static/js/bootstrap.min.js
gotour/src/main/resources/static/js/popper.js
gotour/src/main/resources/static/js/jquery.stellar.min.js
gotour/src/main/resources/static/js/jquery.min.js
gotour/src/main/resources/static/js/simple-datatables-2.1.10.min.js
gotour/src/main/resources/static/js/jquery-3.3.1.min.js
gotour/src/main/resources/static/js/slick.min.js
gotour/src/main/resources/static/js/botones-tour-perfil.js
gotour/src/main/resources/static/js/jquery-migrate-3.0.1.min.js
gotour/src/main/resources/static/js/bootstrap-datepicker.min.js
gotour/src/main/resources/static/images
gotour/src/main/resources/static/images/.DS_Store
gotour/src/main/resources/static/images/loader.gif
gotour/src/main/resources/static/img
gotour/src/main/resources/static/img/.DS_Store
gotour/src/main/resources/static/img/default_map.jpg
gotour/src/main/resources/static/img/estrellas.png
gotour/src/main/resources/static/img/git.png
gotour/src/main/resources/static/img/bg.jpg
gotour/src/main/resources/static/img/hero_bg_1.jpg
gotour/src/main/resources/static/img/5estrellas.png
gotour/src/main/resources/static/img/defaultuser.png
gotour/src/main/resources/static/img/default_tour.jfif
gotour/src/main/resources/static/img/unknown-user.jpg
gotour/src/main/resources/static/img/hero_bg_2.jpg
gotour/src/main/resources/static/admin
gotour/src/main/resources/static/admin/assets
gotour/src/main/resources/static/admin/assets/js
gotour/src/main/resources/static/admin/assets/js/core
gotour/src/main/resources/static/admin/assets/js/core/popper.min.js
gotour/src/main/resources/static/admin/assets/js/core/bootstrap-material-design.min.js
gotour/src/main/resources/static/admin/assets/js/core/jquery.min.js
gotour/src/main/resources/static/admin/assets/js/material-dashboard.js
gotour/src/main/resources/static/admin/assets/js/plugins
gotour/src/main/resources/static/admin/assets/js/plugins/jasny-bootstrap.min.js
gotour/src/main/resources/static/admin/assets/js/plugins/bootstrap-datetimepicker.min.js
gotour/src/main/resources/static/admin/assets/js/plugins/jquery.bootstrap-wizard.js
gotour/src/main/resources/static/admin/assets/js/plugins/chartist.min.js
gotour/src/main/resources/static/admin/assets/js/plugins/nouislider.min.js
gotour/src/main/resources/static/admin/assets/js/plugins/fullcalendar.min.js
gotour/src/main/resources/static/admin/assets/js/plugins/jquery.dataTables.min.js
gotour/src/main/resources/static/admin/assets/js/plugins/arrive.min.js
gotour/src/main/resources/static/admin/assets/js/plugins/jquery.tagsinput.js
gotour/src/main/resources/static/admin/assets/js/plugins/perfect-scrollbar.jquery.min.js
gotour/src/main/resources/static/admin/assets/js/plugins/sweetalert2.js
gotour/src/main/resources/static/admin/assets/js/plugins/bootstrap-notify.js
gotour/src/main/resources/static/admin/assets/js/plugins/bootstrap-selectpicker.js
gotour/src/main/resources/static/admin/assets/js/plugins/moment.min.js
gotour/src/main/resources/static/admin/assets/js/plugins/bootstrap-tagsinput.js
gotour/src/main/resources/static/admin/assets/js/plugins/jquery-jvectormap.js
gotour/src/main/resources/static/admin/assets/js/plugins/jquery.validate.min.js
gotour/src/main/resources/static/admin/assets/js/material-dashboard.js.map
gotour/src/main/resources/static/admin/assets/js/material-dashboard.min.js
gotour/src/main/resources/static/admin/assets/img
gotour/src/main/resources/static/admin/assets/img/favicon.png
gotour/src/main/resources/static/admin/assets/img/bg2.jpg
gotour/src/main/resources/static/admin/assets/img/sidebar-3.jpg
gotour/src/main/resources/static/admin/assets/img/cover.jpg
gotour/src/main/resources/static/admin/assets/img/sidebar-1.jpg
gotour/src/main/resources/static/admin/assets/img/city.jpg
gotour/src/main/resources/static/admin/assets/img/city-profile.jpg
gotour/src/main/resources/static/admin/assets/img/mask.png
gotour/src/main/resources/static/admin/assets/img/sidebar-2.jpg
gotour/src/main/resources/static/admin/assets/img/new_logo.png
gotour/src/main/resources/static/admin/assets/img/apple-icon.png
gotour/src/main/resources/static/admin/assets/img/sidebar-4.jpg
gotour/src/main/resources/static/admin/assets/css
gotour/src/main/resources/static/admin/assets/css/material-dashboard.min.css
gotour/src/main/resources/static/admin/assets/css/material-dashboard-rtl.css
gotour/src/main/resources/static/admin/assets/css/material-dashboard.css.map
gotour/src/main/resources/static/admin/assets/css/material-dashboard.css
gotour/src/main/resources/static/admin/assets/demo
gotour/src/main/resources/static/admin/assets/demo/demo.js
gotour/src/main/resources/static/admin/assets/demo/demo.css
gotour/src/main/resources/static/admin/assets/scss
gotour/src/main/resources/static/admin/assets/scss/**                <--- esto sobra (los css son lo importante, ésto son sus fuentes)
gotour/src/main/resources/static/css
gotour/src/main/resources/static/css/owl.theme.default.min.css
gotour/src/main/resources/static/css/style.css
gotour/src/main/resources/static/css/.DS_Store
gotour/src/main/resources/static/css/mediaelementplayer.css
gotour/src/main/resources/static/css/styles.css
gotour/src/main/resources/static/css/magnific-popup.css
gotour/src/main/resources/static/css/style_tour.css
gotour/src/main/resources/static/css/toures-perfil.css
gotour/src/main/resources/static/css/main.css
gotour/src/main/resources/static/css/progress-bar.css
gotour/src/main/resources/static/css/botones-tour-perfil.css
gotour/src/main/resources/static/css/chat.css
gotour/src/main/resources/static/css/bootstrap-datepicker.css
gotour/src/main/resources/static/css/jquery-ui.css
gotour/src/main/resources/static/css/aos.css
gotour/src/main/resources/static/css/animate.css
gotour/src/main/resources/static/css/owl.carousel.min.css
gotour/src/main/resources/static/css/fl-bigmug-line.css
gotour/src/main/resources/static/css/toures-perfil.css.map
gotour/src/main/resources/static/css/bootstrap
gotour/src/main/resources/static/css/bootstrap/.DS_Store
gotour/src/main/resources/static/css/bootstrap/_media.css
gotour/src/main/resources/static/css/bootstrap/mixins
gotour/src/main/resources/static/css/bootstrap/mixins/.DS_Store
gotour/src/main/resources/static/css/bootstrap/mixins/_reset-text.css
gotour/src/main/resources/static/css/bootstrap/mixins/_visibility.css
gotour/src/main/resources/static/css/bootstrap/mixins/_border-radius.css
gotour/src/main/resources/static/css/bootstrap/mixins/_text-hide.css
gotour/src/main/resources/static/css/bootstrap/mixins/_screen-reader.css
gotour/src/main/resources/static/css/bootstrap/utilities
gotour/src/main/resources/static/css/bootstrap/utilities/_stretched-link.css
gotour/src/main/resources/static/css/login.css
gotour/src/main/resources/static/css/simple-datatables-2.1.10.css
gotour/src/main/resources/static/css/bootstrap.min.css
gotour/src/main/resources/static/fonts
gotour/src/main/resources/static/fonts/.DS_Store
gotour/src/main/resources/static/fonts/flaticon-2
gotour/src/main/resources/static/fonts/flaticon-2/font
gotour/src/main/resources/static/fonts/flaticon-2/font/Flaticon.woff
gotour/src/main/resources/static/fonts/flaticon-2/font/_flaticon.scss
gotour/src/main/resources/static/fonts/flaticon-2/font/Flaticon.eot
gotour/src/main/resources/static/fonts/flaticon-2/font/flaticon.css
gotour/src/main/resources/static/fonts/flaticon-2/font/flaticon.html
gotour/src/main/resources/static/fonts/flaticon-2/font/Flaticon.svg
gotour/src/main/resources/static/fonts/flaticon-2/font/_flaticon.css
gotour/src/main/resources/static/fonts/flaticon-2/font/Flaticon.ttf
gotour/src/main/resources/static/fonts/flaticon-2/.DS_Store
gotour/src/main/resources/static/fonts/flaticon-2/license
gotour/src/main/resources/static/fonts/flaticon-2/license/license.pdf
gotour/src/main/resources/static/fonts/flaticon-2/backup.txt
gotour/src/main/resources/static/fonts/icomoon
gotour/src/main/resources/static/fonts/icomoon/style.css
gotour/src/main/resources/static/fonts/icomoon/fonts
gotour/src/main/resources/static/fonts/icomoon/fonts/icomoon.woff
gotour/src/main/resources/static/fonts/icomoon/fonts/icomoon.eot
gotour/src/main/resources/static/fonts/icomoon/fonts/icomoon.ttf
gotour/src/main/resources/static/fonts/icomoon/fonts/icomoon.svg
gotour/src/main/resources/static/fonts/icomoon/Read Me.txt
gotour/src/main/resources/static/fonts/icomoon/selection.json
gotour/src/main/resources/static/fonts/flaticon
gotour/src/main/resources/static/fonts/flaticon/font
gotour/src/main/resources/static/fonts/flaticon/font/Flaticon.woff
gotour/src/main/resources/static/fonts/flaticon/font/_flaticon.scss
gotour/src/main/resources/static/fonts/flaticon/font/Flaticon.eot
gotour/src/main/resources/static/fonts/flaticon/font/flaticon.css
gotour/src/main/resources/static/fonts/flaticon/font/flaticon.html
gotour/src/main/resources/static/fonts/flaticon/font/Flaticon.svg
gotour/src/main/resources/static/fonts/flaticon/font/_flaticon.css
gotour/src/main/resources/static/fonts/flaticon/font/Flaticon.ttf
gotour/src/main/resources/static/fonts/flaticon/license
gotour/src/main/resources/static/fonts/flaticon/license/license.pdf
gotour/src/main/resources/static/fonts/flaticon/backup.txt
gotour/src/main/resources/static/fonts/bigmug-line
gotour/src/main/resources/static/fonts/bigmug-line/.DS_Store
gotour/src/main/resources/static/fonts/bigmug-line/fl-bigmug-line.woff
gotour/src/main/resources/static/fonts/bigmug-line/fl-bigmug-line.svg
gotour/src/main/resources/static/fonts/bigmug-line/fl-bigmug-line.eot
gotour/src/main/resources/static/fonts/bigmug-line/fl-bigmug-line.ttf
gotour/src/main/resources/static/scss
gotour/src/main/resources/static/scss/**                                <---  otros cientos de ficheros que sobran
gotour/src/main/resources/application-default.properties
gotour/src/test
gotour/src/test/karate-ui
gotour/src/test/karate-ui/mensaje.feature
gotour/src/test/karate-ui/editarDatos.feature
gotour/src/test/karate-ui/secondary.feature
gotour/src/test/karate-ui/login.feature
gotour/src/test/karate-ui/principal.feature
gotour/src/test/java
gotour/src/test/java/karate
gotour/src/test/java/karate/KarateTests.java
gotour/src/test/java/karate/login
gotour/src/test/java/karate/login/login1.feature
gotour/src/test/java/karate/login/LoginRunner.java
gotour/src/test/java/karate/login/login2.feature
gotour/src/test/java/logback.xml
gotour/src/test/java/karate-config.js
gotour/src/test/java/es
gotour/src/test/java/es/ucm
gotour/src/test/java/es/ucm/fdi
gotour/src/test/java/es/ucm/fdi/iw
gotour/src/test/java/es/ucm/fdi/iw/gotour
gotour/src/test/java/es/ucm/fdi/iw/gotour/GotourApplicationTests.java
gotour/data
gotour/data/users
gotour/data/users/2
gotour/data/users/1
~~~~

## Corrección

Esta corrección no forma parte del enunciado del examen - pero te puede ser útil a la hora de realizarlo, y sirve de justificación a la nota final (último apartado de la corrección).

### entrega

* Seguís duplicando el `material-dashboard` - en concreto, sobra el que está en `templates`: `gotour/src/main/resources/templates/admin/assets/scss/material-dashboard` ya que los recursos estáticos deberían estar bajo `static`. Tampoco deberíais entregar los `.DS_Store` que genera MacOS, ni los `scss` usados para generar los `css`. También estais incluyendo demasiadas versiones de los ficheros con tipos de letra, y hasta algún pdf de licencia...

* me gusta vuestro leeme, y se agradecen las secciones "qué ha cambiado desde".

## funcionalidad

* Ésto tiene mal aspecto, aunque es fácil de corregir:

~~~{.java}
		model.addAttribute("reportesUserNumber", reportesTourNumber);
		model.addAttribute("reportesTourNumber", reportesUserNumber);
~~~

* Grandes mejoras con respecto a versión anterior. Enhorabuena.

* Me gusta el sistema para cambiar de rol en el nuevo perfil 

* Puedo cambiar idiomas, pero no editarlos ni quitarlos. La gente se equivoca - déjales rectificar.

* Si no tengo reportes bajo "mis reportes", muéstrame un mensaje "(no has reportado a nadie)" o similar. 

* Un usuario recién creado no debería tener valoración (porque ha asistido a 0 tours).

* Para pedir un número de tarjeta, no useis un campo "number" - usad un pattern: https://stackoverflow.com/a/59757039/15472. La fecha de caducidad debería también tener un patrón (en este caso, dd/mm o similar); y los números secretos tienen 3 cifras. Los números de tarjeta, desde el punto de vista del servidor, no pueden ser enteros: los int de java son de 32-bits-con-signo (máximo 2,147,483,647) - y las tarjetas de 13 a 16 cifras. Mejor String (desde un punto de vista de seguridad, NO DEBERÍAIS MANEJARLO; pero ya que lo hacéis, hacedlo algo mejor)

* El chat funciona. Está incluso bonito - pero sólo puedes verlo entrando en tu perfil, buscando el tour, y dando al icono de whatsapp. Es difícil de descubrir, y no te avisa si te llegan mensajes cuando estás en otra cosa. 

* No consigo reportar. Me sale siempre un "error 500", que debe de deberse a algún problema montando la plantilla. 

### diseño

* Buen uso de consultas (en lugar de ineficientes bucles).

* Mejorado el `user.js`, aunque vuestro `addReviewUser` sigue pareciéndose demasiado a C, con esas declaraciones de variables tan lejos de donde realmente se empiezan a usar.

### nota

Tenéis una nota base de 6.7: es posible hacer casi todo lo que prometía vuestra propuesta original, y además ha quedado una aplicación web bonita. Me sigue pareciendo difícil acceder a lo más importante (entrar siempre por "perfil" y luego ver el tour en el que estoy se me hace raro; no poder preguntar al guía antes de empezar un tour, también).

Podéis mejorar vuestra nota, subiendo a notable más alto o incluso sobresaliente, puliendo todavía más la experiencia de usuario. Por ejemplo, me gustaría poder saltar rápidamente a mis chats abiertos, sobre todo si tienen mensajes pendientes de leer. Y podeis corregir lo de los reportes con (asumo) poco esfuerzo adicional. Retocar la parte de pago para que no use enteros, o arreglar lo de la edición de idiomas, también deberían ser cambios sencillos.

## examen

Estas son las instrucciones del examen. Léelas con atención antes de nada, y pregunta cualquier duda sobre ellas cuanto antes. Después, verás las 6 preguntas de las que consta: A, B, C, D, E y F. No dejes ninguna pregunta sin contestar, aunque no consigas solucionarlas.

* Tienes acceso a Internet, pero *solamente* para consultar páginas o recursos que existían antes de comenzar el examen. Está *estrictamente prohibido* comunicarte con tus compañeros o con terceros. Puedes, por ejemplo, buscar documentación o ver preguntas y respuestas en StackOverflow. No puedes, por ejemplo, entrar en cualquier aplicación de mensajería o chat para comunicarte con terceros.

* Para tu entrega, usa exclusivamente los fuentes entregados con este enunciado. *No* uses fuentes descargados de GitHub, ni de ningún otro sitio sin consultarlo antes con el profesor.

* Recuerda que el objetivo de este examen no es mejorar la entrega, sino *demostrar que sabes bien cómo funciona*, y que te desenvuelves bien con los conceptos, herramientas y tecnologías vistas en la asignatura. 

* Si resuelves un ejercicio correctamente, no hace falta describir mucho cómo lo has conseguido: el código cambiado hablará por sí solo. Aun así, escribe por favor una frase en el fichero "markdown" (_tuproyecto.md_) del enunciado describiendo la idea de la solución.

* Si *no* te funciona algo, comenta, en el fichero "markdown" del enunciado (_tuproyecto.md_) qué has intentado, porqué crees que falla, y cómo lo intentarías solucionar si tuvieses más tiempo. Aquí sí es importante describir tu solución, ya que será la única forma en que podré valorar tus conocimientos sobre el tema.

### pregunta A

¿En qué partes de este proyecto has trabajado más? ¿En cuáles menos? Indica tu participación describiendo las 3 ó 4 partes más importantes de tu aplicación, y el porcentaje del total de cada parte al que consideras que has contribuído. Contesta directamente en el `.md`, debajo de este texto y antes de la siguiente pregunta.

Las partes en las que más he trabajado son las referentes a gestión de tours(edicion, inscripcion, búsqueda...), a usuarios (perfil, edicion...), al uso AJAX en algunos formularios, y a la interfaz en general (especialmente index, perfil de usuarios y páginas secundarias). Las partes en las que menos he trabajado son administración y el chat. Si divido las partes de la aplicacion en 4 partes. sería Tours(40-50%), Usuarios(40-50%), Administracion(5-10%), Chat(0-5%).

### pregunta B

Permite eliminar idiomas que dices hablar. Idealmente, hazlo con AJAX - pero si te ves mal de tiempo, con una consulta que recargue página bastaría. 

He creado un nuevo método en el controller para eliminar idiomas de usuarios, he añadido un boton en editarDatos, y con ajax lo he hecho funcionar.

### pregunta C

Añade, para cada chat activo en el que puede participar un usuario, un enlace en la parte superior de la pantalla, con una imagen muy pequeñita del tour correspondiente. Pulsar en este enlace debe llevar al chat de ese tour.

He añadido al nav un div que muestra un boton en forma de circulo con la portada del tour como fondo por cada tour al que el usuario esta suscrito, con un enlace al chat correspondiente.

### pregunta D

Escribe una prueba karate-ui donde se demuestre cómo funcionan los reportes. No hace falta que arregles lo que ahora me falla -- pero si me equivoco, y sencillamente es que lo he usado mal, tu prueba debe demostrar que sí funciona. Si el fallo es real y existe, basta con que tu prueba consiga llegar al punto de fallo.

He hecho la prueba karate-ui para probar los reportes de tours, y me ha salido todo correcto sin fallos.

### pregunta E

Añade la posibilidad, para los administradores, de ver también la lista de toures pasados, presentes y futuros (ordenados por tiempo), con enlaces a esos toures, fechas de creación, de inicio y final, guías, y número de plazas totales/ocupadas.

He creado una nueva consulta que obtiene los tours ordenados, y he añadido una nueva tabla en el panel de admin en la que se muestra la lista de tours.

### pregunta F

Retoca la parte de pagos para que use cadenas-de-enteros en lugar de enteros de 32 bits, y así la tarjeta 1234 5678 9012 3456 se considere válida; y para que la tarjeta 1234 5678 no se considere válida por no tener suficientes cifras. Ver enlace a cómo hacerlo desde el punto de vista html algo más arriba.

He cambiado el input de la tarjeta por un input con patrón, aunque no me funciona el envío (error 400). Sé que es porque el tipo del numTarjeta en la clase User es int, y ahora el input envia un String, pero no me da tiempo a cambiarlo.