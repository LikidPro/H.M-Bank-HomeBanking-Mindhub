
const { createApp } = Vue
createApp({
  data() {
    return {
      message: 'Hello Vue!',
      Clientes:[],
      clienteBuscado:[],
      todosLosClientes:[],
      url:"/api/clients",
      buscadorCliente:'',
      clienteSeleccionado:{},
   
      
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
            this.Clientes = Array.from(response.data)
            this.clienteSeleccionado=this.Clientes.filter(cliente => cliente.id == 1)
            this.todosLosClientes= this.Clientes
            console.log(this.Clientes);                               
        }                             
        )       
    },
    addClient(){
      this.cliente={firstName:this.elnombre,lastName:this.elapellido,email:this.elemail}
      this.arrayCliente.forEach((e)=> {
        if(e.email.includes(this.elemail)){
          this.cliente="";
          this.mensaje1=true
        }
      });       
      if(this.elnombre !="" && this.elapellido != "" && this.elemail != "" && this.cliente !=""  ){
        this.mensaje1=false
        this.postClient()
        this.elnombre=""
        this.elapellido=""
        this.elemail=""
        }
        
    },
    clienteParaImprimir(id){
        this.clienteSeleccionado= this.Clientes.filter(cliente => cliente.id == id)
        console.log(this.clienteSeleccionado[0].accounts);
    },
    borrarCuenta(id){
     let borrar= "/api/clients/current/accounts/"+ id +"/disabled"
            Swal.fire({
              text: "¿Desea eliminar esta cuenta?",
              icon: 'question',
              showCancelButton: true,
              confirmButtonColor: '#3085d6',
              cancelButtonColor: '#d33',
              confirmButtonText: 'Si!'
            }).then((result) => {
              if (result.isConfirmed) {
                axios.patch(borrar).then((res)=> Swal.fire({
                  position: 'center',
                  icon: 'success',
                  text: 'cuenta eliminada con exito',
                  showConfirmButton: false,
                  timer: 1500
                })).then(()=> {
                    window.location.reload()
                })
                .catch(error =>   Swal.fire({
                  text:error.response.data
                })) 
          }
          })
    


    },
    borrarTarjeta(id){
        console.log(id);

        let borrar= "/api/cards/"+ id +"/disabled"
        Swal.fire({
          text: "¿Desea eliminar esta Tarjeta?",
          icon: 'question',
          showCancelButton: true,
          confirmButtonColor: '#3085d6',
          cancelButtonColor: '#d33',
          confirmButtonText: 'Si!'
        }).then((result) => {
          if (result.isConfirmed) {
            axios.patch(borrar).then((res)=> Swal.fire({
              position: 'center',
              icon: 'success',
              text: 'tarjeta eliminada con exito',
              showConfirmButton: false,
              timer: 1500
            })).then(()=> {
                window.location.reload()
            })
            .catch(error =>   Swal.fire({
              text:error.response.data
            })) 
      }
      })

    },
    postClient(){
      axios.post(this.url,this.cliente).then(()=>{
        this.dataclient(this.url)
      })
    },
    deleteCliente(cliente){
      axios.delete(cliente._links.self.href).then(()=>{ this.dataclient(this.url)})
    },
    cambiarCliente(cliente){
     
        axios.put(cliente._links.self.href,{firstName:this.cambiarNombre,lastName:this.cambiarApellido, email:this.cambiarEmail}).then(()=>{ this.dataclient(this.url)})
      
    },
    modificarNombre(cliente){
    
      this.cambiarNombre=cliente.firstName
      this.cambiarApellido=cliente.lastName
      this.cambiarEmail=cliente.email
    },
  },
  computed:{
    filtraCliente(){
        this.Clientes = this.todosLosClientes 
        this.Clientes = this.Clientes.filter(cliente => cliente.email.toLowerCase().includes(this.buscadorCliente.toLowerCase()))
     
    }

  }
}).mount('#app')