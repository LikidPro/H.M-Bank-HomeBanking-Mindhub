const { createApp } = Vue
  createApp({
    data() {
      return {
        client:{},
        cuenta:{},
        transacciones:[],
        todasLasTransacciones:[],
        id : (new URLSearchParams(location.search)).get("id"),
        url:"/api/client/current",
        periodo:"",
        fechaHoy: null,
        nombreCuenta:"",
       
      
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
                 this.client = response.data
                 this.cuenta= this.client.accounts.filter(cuenta => cuenta.id == this.id)
                 this.transacciones = this.cuenta[0].transaction
                 this.nombreCuenta=this.cuenta[0].number
                 console.log(this.nombreCuenta);
                 this.transacciones.sort((a,b)=> a.id-b.id).reverse()
                 this.todasLasTransacciones=this.transacciones
                 const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
                 const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))
                            
             }                             
             )       
         },
         desconectar(){
          axios.post('/api/logout').then(response => window.location.href= "/login/index.html")
        },
        expandirElMenu(){
          let menu = document.getElementById("contenedordelmenu")
          console.log(menu);
          menu.classList.toggle("contenedor-menu-altura")
        },
        descargarPdf(){
         axios.patch('/api/clients/current/accounts/transaction/pdf', 'time=' + this.periodo  + '&number='+ this.nombreCuenta).then(()=> window.location.href = "/api/pdf")
        }
       
       
    },
    computed:{
      filtroTransaccionesPorFecha(){
        this.fechaHoy = new Date()
        this.transacciones= this.todasLasTransacciones
        console.log(this.periodo);
        if(this.periodo == "Ultima Semana"){
          this.fechaHoy.setDate(this.fechaHoy.getDate()- 7)
          this.transacciones= this.todasLasTransacciones.filter(transacciones => new Date(transacciones.date.slice(3,5) + "-" + transacciones.date.slice(0,2) + "-" + transacciones.date.slice(6,10))  > this.fechaHoy)
         /*  this.fechaHoy = this.fechaHoy.getDate()+ "-" + (this.fechaHoy.getMonth()- 1) + "-" + this.fechaHoy.getFullYear() + " " + this.fechaHoy.getHours() + ":" + this.fechaHoy.getMinutes() + ":" + this.fechaHoy.getSeconds(); */
        
          console.log(this.fechaHoy);
          console.log(this.cuenta[0].number);
        }
        if(this.periodo == "Ultimo mes"){
          this.fechaHoy.setMonth(this.fechaHoy.getMonth() - 1)
          console.log("mes");
          this.transacciones= this.todasLasTransacciones.filter(transacciones => new Date(transacciones.date.slice(3,5) + "-" + transacciones.date.slice(0,2) + "-" + transacciones.date.slice(6,10))  > this.fechaHoy)
         /*  this.fechaHoy = this.fechaHoy.getDate()+ "-" + (this.fechaHoy.getMonth()- 1) + "-" + this.fechaHoy.getFullYear() + " " + this.fechaHoy.getHours() + ":" + this.fechaHoy.getMinutes() + ":" + this.fechaHoy.getSeconds(); */

        }
        if(this.periodo == "Ultimo semestre"){
          this.fechaHoy.setMonth(this.fechaHoy.getMonth() - 6)
          console.log("semestre");
          this.transacciones= this.todasLasTransacciones.filter(transacciones => new Date(transacciones.date.slice(3,5) + "-" + transacciones.date.slice(0,2) + "-" + transacciones.date.slice(6,10))  > this.fechaHoy)
       /*    this.fechaHoy = this.fechaHoy.getDate()+ "-" + (this.fechaHoy.getMonth()- 1) + "-" + this.fechaHoy.getFullYear() + " " + this.fechaHoy.getHours() + ":" + this.fechaHoy.getMinutes() + ":" + this.fechaHoy.getSeconds(); */

        }
        if(this.periodo == "Ultimo año"){
          this.fechaHoy.setFullYear(this.fechaHoy.getFullYear() - 1)
          this.transacciones= this.todasLasTransacciones.filter(transacciones => new Date(transacciones.date.slice(3,5) + "-" + transacciones.date.slice(0,2) + "-" + transacciones.date.slice(6,10))  > this.fechaHoy)
/*           this.fechaHoy = this.fechaHoy.getDate()+ "-" + (this.fechaHoy.getMonth()- 1) + "-" + this.fechaHoy.getFullYear() + " " + this.fechaHoy.getHours() + ":" + this.fechaHoy.getMinutes() + ":" + this.fechaHoy.getSeconds(); */

        }
      }

    }
    
  }).mount('#app')


