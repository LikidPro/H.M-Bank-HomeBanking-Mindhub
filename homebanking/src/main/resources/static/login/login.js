const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');
const botonImagen = document.getElementById('hacetecliente');
const imagen= document.getElementById('imagenparalogin');
const botonregistarse = document.getElementById('botonregistrarse')

signUpButton.addEventListener('click', (e) => {
    e.preventDefault()
	container.classList.add("right-panel-active");
    imagen.classList.add("imagenlogin2")
});

signInButton.addEventListener('click', (e) => {
    e.preventDefault()
	container.classList.remove("right-panel-active");
    imagen.classList.remove("imagenlogin2")
});


