function getIndex(list, id) {
    for (let i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

let productApi = Vue.resource('/product{/id}');

Vue.component('product-form', {
    props: ['products', 'productAttr'],
    data: function () {
        return {
            name: '',
            id: ''
        }
    },
    watch: {
        productAttr: function(newVal, oldVal) {
            this.name = newVal.name;
            this.id = newVal.id;
        }
    },
    template:
            '<div>' +
                '<input type="text" placeholder="new product" v-model="name" />' +
                '<input type="button" value="Save" @click="save" />' +
            '</div>',
    methods: {
        save: function() {
            if (this.name.length > 0) {
                let product = {name: this.name};
                if(this.id){
                    productApi.update({id: this.id}, product).then(result =>
                            result.json().then(data => {
                                let index = getIndex(this.products, data.id);
                                this.products.splice(index, 1, data);
                                this.id = '';
                            }))
                } else {
                    productApi.save({}, product).then(result =>
                         result.json().then(data => {
                             this.products.push(data);
                         })
                     )
                }
                this.name = '';
            }
        }
    }
});

Vue.component('product-row', {
    props: ['product', 'editProduct', 'products'],
    template: '<div>' +
                '<i>({{ product.id }})</i> {{ product.name }}' +
                '<span style="position: absolute; right: 0">' +
                    '<input type="button" value="Edit" @click="edit" />' +
                    '<input type="button" value="X" @click="del" />' +
                '</span>' +
              '</div>',
    methods:{
       edit: function(){
           this.editProduct(this.product);
       },
       del: function(){
           productApi.remove({id: this.product.id}).then(result => {
               if(result.ok){
                   this.products.splice(this.products.indexOf(this.product), 1);
               }
           })
       }
    }
});

Vue.component('products-list', {
    props: ['products'],
    data: function(){
        return {
            product: null
        }
    },
    template:
        '<div style="position: relative; width: 300px;">' +
            '<product-form :products="products" :productAttr="product" />' +
            '<product-row v-for="product in products" ' + 
                ':key="product.id" :product="product" ' + 
                ':editProduct="editProduct" ' + 
                ':products="products" />' +
        '</div>',
    created: function() {
        productApi.get().then(result =>
            result.json().then(data =>
                data.forEach(product => this.products.push(product))
            )
        )
    },
    methods: {
        editProduct: function(product){
            this.product = product;
        }
    }
});

let app = new Vue({
  el: '#app',
  template: '<products-list :products="products" />',
  data: {
    products: []
  }
});