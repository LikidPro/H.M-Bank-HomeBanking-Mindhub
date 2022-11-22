
const { createApp } = Vue
createApp({
  data() {
    return {
      clientes: [],
      url: "/api/client/current",
      nombre: "",
      apellido: "",
      contraseña: "",
      repetirContraseña: "",
      asd: "",

    }

  },
  created() {
    this.dataclient(this.url)

  },
  methods: {
    dataclient(URL) {

      axios
        .get(URL)
        .then(response => {
          this.clientes = response.data
        }
        )
    },

    desconectar() {
      axios.post('/api/logout').then(response => window.location.href = "/login/index.html")
    },
    expandirElMenu() {
      let menu = document.getElementById("contenedordelmenu")

      menu.classList.toggle("contenedor-menu-altura")
    },
    imagenColocar() {
      let file = document.getElementById("file")
      let image = document.getElementById("imagen-perfil")
      let formdata = new FormData()
      console.log(file.files[0]);
      formdata.append("image", file.files[0])
      let config = {
        headers: {
          Authorization: "Client-ID 13a3327ad483faa"
        }
      }
      console.log(formdata);
  /*      fetch("https://api.imgur.com/3/image/", {
        method:"POST",
        headers: {
          Authorization: "Client-ID 13a3327ad483faa"
        },
        body:formdata
      }).then(data => data.json()).then(data => console.log(data))
        .catch(error => console.log(error.data)) 
      }, */
   
  axios.post("https://api.imgur.com/3/upload",formdata,config).then(response => console.log(response))
    },
    cambiarDatos() {
      let textoNombre = document.getElementById("texto-nombre")
      let textoApellido = document.getElementById("texto-apellido")
      let textoContraseña = document.getElementById("texto-contraseña")
      let textoRepetir = document.getElementById("texto-repetir")
      textoNombre.innerText = ''
      textoApellido.innerText = ''
      textoContraseña.innerText = ''
      textoRepetir.innerText = ''
      if (this.nombre == '') {
        textoNombre.innerText = 'Ingrese un nombre'
      }
      if (this.apellido == '') {
        textoApellido.innerText = 'Ingrese un apellido'
      }
      if (this.contraseña == '') {
        textoContraseña.innerText = 'Ingrese una contraseña'
      }
      if (this.repetirContraseña == '') {
        textoRepetir.innerText = 'Ingrese una contraseña'
      }
      if (this.nombre != '' && this.apellido != '' && this.contraseña != '' & this.repetirContraseña != '' && this.contraseña == this.repetirContraseña) {
        Swal.fire({
          text: "¿Desea cambiar sus datos?",
          icon: 'question',
          showCancelButton: true,
          confirmButtonColor: '#3085d6',
          cancelButtonColor: '#d33',
          confirmButtonText: 'Si!'
        }).then((result) => {
          if (result.isConfirmed) {
            axios.patch("/api/client/current", 'firstName=' + this.nombre + '&lastName=' + this.apellido + '&password=' + this.contraseña).then((res) => Swal.fire({
              position: 'center',
              icon: 'success',
              text: 'Datos cambiados con exito',
              showConfirmButton: false,
              timer: 1500
            })).then(() => window.location.href = "/web/accounts.html")
              .catch(error => Swal.fire({
                text: error.response.data
              }))

          }
        })


      }
    }

  }
},

).mount('#app')
