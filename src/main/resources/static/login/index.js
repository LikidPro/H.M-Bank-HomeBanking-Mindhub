const { createApp } = Vue
  createApp({
    data() {
      return {
        email:"",
        contraseña:"",
        nombre:"",
        apellido:"",
        emailregistro:"",
        password:"",
        repetirpassword:"",      
        error : null, 
        codigoActivacion:"",
      }
    },
    created() {
           this.cerrarSesion()
        
    },
    methods: {
      cerrarSesion(){
        axios.post('/api/logout')

      },
       iniciarseccion(){
       let mail2="email=" + this.email
       let password="&password="+this.contraseña
        if(this.email != "" && this.contraseña != ""){
            axios.post('/api/log',mail2 + password ).then(response => {
                window.location.href= "/web/accounts.html"
             }).catch(error => this.error = error.response.status);
        }
       
       },
       registrarse(){
        if(this.nombre != "" && this.apellido != "" && this.emailregistro != "" && this.password != "" && this.repetirpassword != "" && this.password == this.repetirpassword && this.password.length > 7){
        let nombre= "firstName="+ this.nombre
        let apellido= "&lastName="+ this.apellido
        let mail = "&email="+ this.emailregistro
        let password= "&password="+ this.password
        this.email= this.emailregistro
        this.contraseña= this.password
        axios.post('/api/clients', nombre + apellido + mail + password).then(response => this.mandarEmail(this.emailregistro, response.data)
        ).catch(error => this.error = error.response.status);
      }
        },
        mandarEmail(email,response){
          let modal = document.getElementById('exampleModal')
          modal.classList.add('show')
          modal.style.display='block'
          modal.ariaRoleDescription="dialog"
          axios.post("/api/client/send/email", 'to='+email+ '&subject='+ 'Validacion de email'+ '&textMessage='+'Su codigo de activacion es  '+response)

        },
        validarCliente(){
          console.log(this.codigoActivacion);
          let modal = document.getElementById('exampleModal')         
          let texto= document.getElementById('texto-validacion-incorrecto')
          axios.patch("/api/client/validation", 'email='+this.emailregistro + '&activationCode='+ this.codigoActivacion).then(()=> Swal.fire({
              position: 'center',
              icon: 'success',
              text: 'Codigo correcto ya puede iniciar secion',
              showConfirmButton: false,
              timer: 1500
            })).catch(response => texto.innerText =response.data)
            modal.classList.remove('show')
            modal.style.display='none'
            modal.ariaRoleDescription="none"
         /*    .then(() => window.location.reload ) */
         
            
        },
        mostrarcontra(idContraseña,idOjoAbierto,idOjoCerrado){
          let contraseña = document.getElementById("contraseñainicio"+ idContraseña)
          let ojoAbierto= document.getElementById("mostrarojo1"+ idOjoAbierto)
          let ojoCerrado= document.getElementById("mostrarojo2"+ idOjoCerrado)
          if (contraseña.type === 'password') {
            contraseña.setAttribute('type', 'text');
            ojoAbierto.style.display= "none"
            ojoCerrado.style.display= "block"
           
        } else {
            contraseña.setAttribute('type', 'password');
            ojoAbierto.style.display= "block"
            ojoCerrado.style.display= "none"
        }
        }
        
       }
         
  }).mount('#app')

