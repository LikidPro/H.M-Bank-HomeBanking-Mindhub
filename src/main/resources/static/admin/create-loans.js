const { createApp } = Vue
createApp({
  data() {
    return {
      client:{},
      prestamos:[],
      cuotas:[],
      cuotasSeleccionadas:"",
      montoSeleccionado:"",
      tipoDePrestamo:"",
      cantidadDeCuotas:[],
      url:"/api/loans",
      urlClient:"/api/client/current"

    
   
      
    }
   
  },
  created() {
 this.dataLoans(this.url)
 this.dataClient(this.urlClient)
  
  },
  methods: {
    dataClient(url){
      axios.get(url)
      .then(res=> {
        this.client= res.data
      })
    },
    dataLoans(url){
      axios.get(url)
      .then(res=> {
        this.prestamos=res.data
       /*   let arrayDeCuotas = this.prestamos.map(prestamo => prestamo.payments)
        arrayDeCuotas.forEach(cuota => {
          if(!this.cuotas.includes(cuota)){
            this.cuotas.push(cuota)
          }
          
        }); */
        this.cuotas=[ [
          12,
          24,
          36,
          48,
          60
        ],[
          6,
          12,
          24,
          36
          ],
          [
            6,
            12,
            24
            ]]
        

      
        
      })
    },
    crearPrestamo(){
    
      let arrayDeCuotas = this.cuotasSeleccionadas.slice(1,-1).substring(0,this.cuotasSeleccionadas.length - 1).split(',')
      this.cantidadDeCuotas = arrayDeCuotas.map(cuota => parseInt(cuota))
      console.log(this.cantidadDeCuotas);
      axios.post("/api/loans/admin", 'name='+ this.tipoDePrestamo + '&maxAmount=' + this.montoSeleccionado + "&payments=" + this.cantidadDeCuotas).then(res => console.log("creado"))
    },
   
  
  },
  computed:{
   

  }
}).mount('#app')