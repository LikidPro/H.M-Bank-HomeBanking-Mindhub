const { createApp } = Vue
  createApp({
    data() {
      return {
        clientes:[],
        cuentas:[],
        clientemelba:{},
        url:"/api/client/current",
        cuentamelba:[],
        prestamos:[],
        tarjetas:[],
        tarjetasCredito:[],
        tarjetasDebito:[],
        id:"",
        date: new Date(),
        datecompleta:"",
        datefalsa:new Date("2027-11-16")
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
                 this.clientes = response.data
                 this.tarjetas = this.clientes.cards
                 this.tarjetasCredito = this.tarjetas.filter(card => card.cardType =="CREDIT")
                 this.tarjetasDebito = this.tarjetas.filter(card => card.cardType =="DEBIT")   
                 this.datecompleta= this.date.getFullYear() + "-" + (this.date.getMonth() + 1) + "-" +  (this.date.getDate() + 1)                                                                   
             }                             
             )       
         },
         girarcard(valor){  
          this.id = valor.id          
          let div2= document.getElementById("tarjetaatras"+this.id)
          let div= document.getElementById("tarjetafrente"+this.id)    
          div.classList.toggle("girar")
          div2.classList.toggle("voltear")
         },
         convertir(fecha){
          let año= fecha.slice(2,4)
         
          let mes= fecha.slice(5,7)
          return mes + "/" + año
         },
         desconectar(){
          axios.post('/api/logout').then(response => window.location.href= "/login/index.html")
        }, 
        expandirElMenu(){
          let menu = document.getElementById("contenedordelmenu")
        
          menu.classList.toggle("contenedor-menu-altura")
        },
        mostrarDebito(){
          let credito = document.getElementById('tarjetaDeCredito')
          let debito = document.getElementById('tarjetaDeDebito')
          let botonCredito = document.getElementById('botonCredito')
          let botonDebito = document.getElementById('botonDebito')
          credito.classList.add('tarjetadesactivada')
          debito.classList.remove('tarjetadesactivada')
          botonCredito.classList.remove('botonactivo')
          botonCredito.classList.add('btn-grad')
          botonDebito.classList.add('botonactivo')
          botonDebito.classList.remove('btn-grad')
        },
        mostrarCredito(){
          let credito = document.getElementById('tarjetaDeCredito')
          let debito = document.getElementById('tarjetaDeDebito')
          let botonCredito = document.getElementById('botonCredito')
          let botonDebito = document.getElementById('botonDebito')
          credito.classList.remove('tarjetadesactivada')
          debito.classList.add('tarjetadesactivada')
          botonCredito.classList.add('botonactivo')
          botonCredito.classList.remove('btn-grad')
          botonDebito.classList.remove('botonactivo')
          botonDebito.classList.add('btn-grad')
        
        },
        irCrearTarjeta(){
          window.location.href="/web/create-card.html"
        },
        eliminarCard(id){ 
          Swal.fire({
            text: "¿Desea eliminar esta tarjeta?",
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Si!'
          }).then((result) => {
            if (result.isConfirmed) {
              axios.patch('/api/clients/current/cards/delete', "id="+ id).then((res)=> Swal.fire({
                position: 'center',
                icon: 'success',
                text: 'Tarjeta eliminada con exito',
                showConfirmButton: false,
                timer: 1500
              })).then(() => this.dataclient(this.url)) 
              .catch(error =>   Swal.fire({
                text:error.response.data
              })) 
      
        }
        })} 
      }
           
       
    },
    
  
  ).mount('#app')


