
const { createApp } = Vue
createApp({
  data() {
    return {
      message: 'Hello Vue!',
      arrayCliente:[],
      url:"http://localhost:8080/rest/clients",
      laurl:null,
      elnombre:"",
      elapellido:"",
      elemail:"",
      cambiarNombre:"",
      cambiarApellido:"",
      cambiarEmail:"",
      cliente:{},
      mensaje1:false,
      arrayid:[]         
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
            this.laurl=response
            this.arrayCliente = response.data._embedded.clients
            console.log(this.arrayCliente);
            /* this.arrayid= this.arrayCliente.map(id => id._links.self.href.split("http://localhost:8080/rest/clients/")) */
            

           

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

  }
}).mount('#app')