let productApi = Vue.resource('/product{/id}');

Vue.component('product-form', {
    props: ['products'],
    data: function () {
        return {
            name: ''
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
                productApi.save({}, product).then(result =>
                     result.json().then(data => {
                         this.products.push(data);
                         this.name = '';
                     })
                 )
            }
        }
    }
});

Vue.component('product-row', {
    props: ['product'],
    template: '<div><i>({{ product.id }})</i> {{ product.name }}</div>'
});

Vue.component('products-list', {
    props: ['products'],
    template:
        '<div>' +
            '<product-form :products="products" />' +
            '<product-row v-for="product in products" :key="product.id" :product="product" />' +
        '</div>',
    created: function() {
        productApi.get().then(result =>
            result.json().then(data =>
                data.forEach(product => this.products.push(product))
            )
        )
    }
});

let app = new Vue({
  el: '#app',
  template: '<products-list :products="products" />',
  data: {
    products: []
  }
});