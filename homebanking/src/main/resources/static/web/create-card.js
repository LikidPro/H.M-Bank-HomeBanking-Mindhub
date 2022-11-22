const { createApp } = Vue
  createApp({
    data() {
      return {
        tipoDeTarjeta:"",
        tipoDeColor:"",
        texto:"holaaa",
        url:"/api/client/current",
        cliente:{},
        añoQueExpira:new Date().getFullYear()+5,
        mesQueEstoy:new Date().getMonth(),
        numeroRandom: Math.round(Math.random()*10000),            
      }
   
    },
    created(){ 
        this.dataclient(this.url)    
        
    },
    methods: {
        
        dataclient(URL){
    
            axios
            .get(URL)
             .then(response => {              
                 this.cliente = response.data                                                        
             }                             
             )       
         },
         crearTarjeta(){
          let cardType=this.tipoDeTarjeta.split("o").join("")
          let cardColor=this.tipoDeColor
          axios.post('/api/clients/current/cards',"cardColor="+ cardColor.toUpperCase() +"&cardType=" +cardType.toUpperCase()).then((res)=> window.location.href="/web/cards.html")
         },
         expandirElMenu(){
          let menu = document.getElementById("contenedordelmenu")
          console.log(menu);
          menu.classList.toggle("contenedor-menu-altura")
        },
        desconectar(){
          axios.post('/api/logout').then(response => window.location.href= "/login/index.html")
        },
       
    },
    
    
  }).mount('#app')


