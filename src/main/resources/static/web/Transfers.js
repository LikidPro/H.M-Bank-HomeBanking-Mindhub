const { createApp } = Vue
  createApp({
    data() {
      return {
        url:"/api/client/current",
        cliente:{},   
        cuentas: [] ,
        tipoDeDestino:"" , 
        cuentaSeleccionada:"",
        numeroCuentaTercero: "",
        montoATransferir:"",
        descripcionDeTranferencia:"",
        cuentaQueTransfiere:"",
        montoMaximoATransferir:"",
       
      }
   
    },
    created() { 
        this.dataclient(this.url)    
        
    },
    methods: {
        
        dataclient(URL){
    
            axios
            .get(URL)
             .then(response => {              
                 this.cliente = response.data   
                 this.cuentas = this.cliente.accounts                                                     
             }                             
             )       
         },
        realizarTrans(){  
          let textoCuentaTransferir= document.getElementById('textoCuentaTransferir') 
          let montoCuentaTransferir= document.getElementById('montoCuentaTransferir')
          let descripcionDeTranferencia = document.getElementById('descripcionDeTranferencia')
          let cuentaQueTransfiere = document.getElementById('cuentaQueTransfiere')
          textoCuentaTransferir.innerText = ''
          montoCuentaTransferir.innerText = ''
          descripcionDeTranferencia.innerText = ''
          cuentaQueTransfiere.innerText = ''
          if(this.cuentaSeleccionada == ''){
           
             textoCuentaTransferir.innerText = 'Indique la cuenta a la que desea transferir'
       
          }
          if(this.montoATransferir == ''){
          
            montoCuentaTransferir.innerText = 'Indique el monto que desea transferir'
          
          }
          if(this.descripcionDeTranferencia == ''){
            
            descripcionDeTranferencia.innerText = 'Indique descripcion a la transferencia'
           
          }
          if(this.cuentaQueTransfiere == ''){
          
            cuentaQueTransfiere.innerText = 'Indique la cuenta  que realiza la transferencia'
           
          }
         if(this.cuentaSeleccionada != '' && this.montoATransferir != '' && this.descripcionDeTranferencia != '' && this.cuentaQueTransfiere != ''){
          Swal.fire({
            text: "¿Desea realizar la transferencia?",
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Si, realizar!'
          }).then((result) => {
            if (result.isConfirmed) {
              axios.post('/api/transactions',"amonunt="+ this.montoATransferir +"&description=" + this.descripcionDeTranferencia + "&number=" + this.cuentaQueTransfiere  + "&numberDestiny=" + this.cuentaSeleccionada.toUpperCase() ).then((res)=> Swal.fire({
                position: 'center',
                icon: 'success',
                text: 'Transaccion realizada con exito',
                showConfirmButton: false,
                timer: 1500
              })).then(() => window.location.reload() ) 
              .catch(error => Swal.fire(error.response.data))
            }
          })      
          
         }},
         expandirElMenu(){
          let menu = document.getElementById("contenedordelmenu")
         
          menu.classList.toggle("contenedor-menu-altura")
        },
        desconectar(){
          axios.post('/api/logout').then(response => window.location.href= "/login/index.html")
        },
        montoMaximo(){
          if(this.cuentaQueTransfiere != ''){
          let monto = this.cuentas.find(cuenta => cuenta.number == this.cuentaQueTransfiere)
          this.montoMaximoATransferir = monto.ballance
        }
        }

       
    },
    
    
  }).mount('#app')


