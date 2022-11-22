const { createApp } = Vue
  createApp({
    data() {
      return {
        urlCuentas:"/api/clients/current/accounts",
        urlPrestamos:"/api/loans",
        urlCliente:"/api/client/current",
        cliente:{},
        tipoDePrestamo:"",
        cuentas:[],
        prestamos:[],
        prestamoHipotecario:{},
        prestamoPersonal:{},
        prestamoAutomotriz:{},
        cuentaDestino:"",
        montoMaximo:"",
        cuotas:[],
        cantidadDeCuotas:"",
        montoSeleccionado:"",
        cuentaDestino:"",
        montoDeCuotas:0,
        idLoan:"",

       
      }
   
    },
    created() { 
        this.dataCuentas(this.urlCuentas) 
        this.dataPrestamos(this.urlPrestamos)   
        this.dataClient(this.urlCliente)
        
    },
    methods: {
      dataClient(URL){
        axios.get(URL).then(response => {
          this.cliente = response.data
        })
      },
        
        dataCuentas(URL){
    
            axios
            .get(URL)
             .then(response => {              
                 this.cuentas = response.data                                                                               
             }                             
             )       
         },
         dataPrestamos(URL){
            axios.get(URL)
            .then(response => {
                this.prestamos = response.data.sort((a,b)=> a.id-b.id)
          
            })
         },
      
         expandirElMenu(){
          let menu = document.getElementById("contenedordelmenu")
         
          menu.classList.toggle("contenedor-menu-altura")
        },
        desconectar(){
          axios.post('/api/logout').then(response => window.location.href= "/login/index.html")
        }, 
        realizarPrestamo(id){
          let texto1 = document.getElementById("textoCuotas")
          let texto2 = document.getElementById("textoMonto")
          let texto3 = document.getElementById("textoCuenta")
          texto1.innerText = ""
          texto2.innerText = ""
          texto3.innerText = ""
          if(this.cantidadDeCuotas == ""){
            
            texto1.innerText = "Seleccione la cantidad de cuotas"
          }
          if(this.montoSeleccionado == ""){
          
            texto2.innerText = "Elija el monto a solicitar"
          }
          if(this.cuentaDestino == ""){
           
            texto3.innerText = "Seleccione la cuenta de destino"
          }
          if(this.cantidadDeCuotas != "" && this.montoSeleccionado != "" && this.cuentaDestino != ""){
            Swal.fire({
              text: "¿Desea solicitar el prestamo?",
              icon: 'question',
              showCancelButton: true,
              confirmButtonColor: '#3085d6',
              cancelButtonColor: '#d33',
              confirmButtonText: 'Si!'
            }).then((result) => {
              if (result.isConfirmed) {
                 axios.post('/api/loans',{idLoan:this.idLoan,amount:this.montoSeleccionado,payments:this.cantidadDeCuotas,numberAccountsDestiny:this.cuentaDestino}).then((res)=> Swal.fire({
                  position: 'center',
                  icon: 'success',
                  text: 'Prestamo realizado con exito',
                  showConfirmButton: false,
                  timer: 1500
                })).then(() => window.location.href = "/web/accounts.html" ) 
                .catch(error =>   Swal.fire({
                  text:error.response.data
                })) 
                 
              }
            })                              
          }
        },                               
          },
          computed:{
            mostrarCuotas(){
              if(this.tipoDePrestamo != ""){
                let prestamo = this.prestamos.find(prestamo => prestamo.name == this.tipoDePrestamo)
                this.cuotas = prestamo.payments.map(payment => payment)   
                this.montoMaximo = prestamo.maxAmount     
                this.idLoan = prestamo.id        
              }           
            },
            cambiarMontoCoutas(){
              if(this.tipoDePrestamo == "Hipotecario" && this.cantidadDeCuotas == 12){
                this.montoDeCuotas = ((this.montoSeleccionado * 120) / 100)  / this.cantidadDeCuotas
              }
              if(this.tipoDePrestamo == "Hipotecario" && this.cantidadDeCuotas == 24){
                this.montoDeCuotas = ((this.montoSeleccionado * 122) / 100)  / this.cantidadDeCuotas
              }
              if(this.tipoDePrestamo == "Hipotecario" && this.cantidadDeCuotas == 36){
                this.montoDeCuotas = ((this.montoSeleccionado * 124) / 100)  / this.cantidadDeCuotas
              }
              if(this.tipoDePrestamo == "Hipotecario" && this.cantidadDeCuotas == 48){
                this.montoDeCuotas = ((this.montoSeleccionado * 128) / 100)  / this.cantidadDeCuotas
              }
              if(this.tipoDePrestamo == "Hipotecario" && this.cantidadDeCuotas == 60){
                this.montoDeCuotas = ((this.montoSeleccionado * 130) / 100)  / this.cantidadDeCuotas
              }
              if(this.tipoDePrestamo == "Automotriz" && this.cantidadDeCuotas == 6){
                this.montoDeCuotas = ((this.montoSeleccionado * 115) / 100)  / this.cantidadDeCuotas
              }
              if(this.tipoDePrestamo == "Automotriz" && this.cantidadDeCuotas == 12){
                this.montoDeCuotas = ((this.montoSeleccionado * 117) / 100)  / this.cantidadDeCuotas
              }
              if(this.tipoDePrestamo == "Automotriz" && this.cantidadDeCuotas == 24){
                this.montoDeCuotas = ((this.montoSeleccionado * 120) / 100)  / this.cantidadDeCuotas
              }
              if(this.tipoDePrestamo == "Automotriz" && this.cantidadDeCuotas == 36){
                this.montoDeCuotas = ((this.montoSeleccionado * 122) / 100)  / this.cantidadDeCuotas
              }
              if(this.tipoDePrestamo == "Personal" && this.cantidadDeCuotas == 6){
                this.montoDeCuotas = ((this.montoSeleccionado * 110) / 100)  / this.cantidadDeCuotas
              }
              if(this.tipoDePrestamo == "Personal" && this.cantidadDeCuotas == 12){
                this.montoDeCuotas = ((this.montoSeleccionado * 112) / 100)  / this.cantidadDeCuotas
              }
              if(this.tipoDePrestamo == "Personal" && this.cantidadDeCuotas == 24){
                this.montoDeCuotas = ((this.montoSeleccionado * 114) / 100)  / this.cantidadDeCuotas
              }
              if(this.tipoDePrestamo == "Personal" && this.cantidadDeCuotas == 36){
                this.montoDeCuotas = ((this.montoSeleccionado * 118) / 100)  / this.cantidadDeCuotas
              }         
            }
            
          }                       
  }).mount('#app')


