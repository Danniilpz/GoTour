$("#add-languaje").on("click", addLanguaje);

function csrfHeader(){
  const token = $("meta[name='_csrf']").attr("content");
  const header = $("meta[name='_csrf_header']").attr("content");

  $(document).ajaxSend(function (e, xhr, options) {
    xhr.setRequestHeader(header, token);
  });
}

function addLanguaje() {
  csrfHeader();
  const dataString = 'idioma=' + $("#idioma").val();
  $.ajax({
    url: '/user/addLanguaje',
    type: "POST",
    data: dataString,
    success: function (respuesta) {

      var listaIdiomas = $("#lista-idiomas");
      console.log("Se lee bien el idioma")
      listaIdiomas.append(
        '<div>' +
        '<p class="idiomas">' + $("#idioma").val() + '</p>' +
        '</div>'
      );
      $("#idioma").val("");
    },
    error: function () {
      console.log("No se ha podido obtener la información");
    }
  });
}

$("#add-review").on("click", addReview);

function addReview() {
  csrfHeader();
  const val =$('input:radio[name=valoracion]:checked').val();
  const text = $("#textoReview").val();
  const instancia = $("#elegida").val();
  $.ajax({
    url: '/tour/' + instancia + '/valorar',
    type: "POST",
    data: {
      valoracion: val,
      textoReview: text
    },
    success: function (respuesta) {
      console.log("Review añadida")
      $("#form_valorar").hide()
    },
    error: function () {
      console.log("No se ha podido realizar la review ", instancia, valoracion, textoReview);
    }
  });
}



$("[name=review-user]").on("click", addReviewUser);

function addReviewUser() {
  csrfHeader();
  let val;
  let text;
  let tour;
  let user = [];
  let userbueno;
  let textoprueba;
  let patata;
  $(".usuario").each(function () {
    user.push(this.value);
  });

  user.forEach(function (e) {
    patata = e;
    textoprueba = $("#textoReview" + patata).val();
    if (textoprueba == "") {
      console.log("El usuario no es ", patata)

    } else {
      console.log("El usuario es ", patata)
      userbueno = patata;
    }
  });
  val = $("#valoracion" + userbueno).val();
  text = $("#textoReview" + userbueno).val();
  tour = $("#tour").val();
  $.ajax({
    url: '/tour/' + tour + '/valorarUser/' + userbueno,
    type: "POST",
    data: {
      valoracion: val,
      textoReview: text
    },
    success: function (respuesta) {
      console.log("Review añadida")
      $("#esconderTurista" + userbueno).remove()
    },
    error: function () {
      console.log("No se ha podido realizar la review ", tour, user, valoracion, textoReview);
    }
  });
}

$("[name=username]").on("keyup", validarUser); //Esto para validar usuarios

function validarUser() {
  let username = document.getElementById("username")

  if (username.value != '') {
    $.ajax({
      url: '/validar',
      type: 'GET',
      data: {
        username: username.value
      },
      success: function (response) {
        console.log("ha funcionado");
        if (response > 0) {
          console.log("Nombre ocupado ", response);
          username.setCustomValidity("Ese nombre de usuario ya esta cogido");

        } else {
          console.log("Nombre ocupado ", response);
          username.setCustomValidity("");

        }
      }
    });
  } else {
    console.log("No ha funcionado y porque username esta vacio");
    $("#username_response").html("");
  }

}