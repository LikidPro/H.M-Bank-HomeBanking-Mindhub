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
        cuentaType:"",
        
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
                 this.cuentamelba=this.clientes.accounts  
                 this.prestamos = this.clientes.loans
                 
                                      
                
             }                             
             )       
         },
      desconectar(){
        axios.post('/api/logout').then(response => window.location.href= "/login/index.html")
      },
      typoDeCuenta(tipoDeCuenta){
        this.cuentaType =  tipoDeCuenta
        this.crearCuenta()

      },
      crearCuenta(){        
  Swal.fire({
  text:'Desea crear una nueva cuenta?',
  color:'white',  
  background:'#160015',
  showCancelButton: true,
  confirmButtonColor: 'green',
  cancelButtonColor: '#d33',
  confirmButtonText: 'Crear',
  cancelButtonText: 'Cancelar'
}).then((result) => {
  if (result.isConfirmed) {
    axios.post('/api/clients/current/accounts', 'accountType=' + this.cuentaType).then(() => this.dataclient(this.url))
  }
})
      
    },
    expandirElMenu(){
      let menu = document.getElementById("contenedordelmenu")
      console.log(menu);
      menu.classList.toggle("contenedor-menu-altura")
    }

       
    },
    
  }).mount('#app')


