import React, {Component} from "react";
import ProductItem from "./ProductItem";
import withContext from "../withContext";
import axios from 'axios';

class ProductList extends Component {
    constructor(props) {
      super(props);
      this.state = {
        products: []
      };
    }
  
    async componentDidMount() {
        const {user} = this.props.context;
      axios
        .get("http://localhost:8080/api/product/all", {
          headers: {
            Authorization: `Bearer ${user.token}`
          }
        })
        .then(res => {
          this.props.setProducts(res.data);
        })
        .catch(err => {
          console.log(err);
        });
    }

    updateProducts = (products) => {
      this.setState({ products });
    };
  
    render() {
      const { products } = this.props.context;
      return (
        <>
          <div className="hero is-info">
            <div className="hero-body container">
              <h4 className="title">Welcome to Five Layers Store</h4>
            </div>
          </div>
          <br />
          <div className="container">
            <div className="column columns is-multiline">
              {products && products.length ? (
                products.map((product, index) => (
                  <ProductItem
                    product={product}
                    key={index}
                    addToCart={this.props.context.addToCart}
                    image={product.image}
                  />
                ))
              ) : (
                <div className="column">
                  <span className="title has-text-grey-light">
                    No products found!
                  </span>
                </div>
              )}
            </div>
          </div>
        </>
      );
    }
  }
  
  export default withContext(ProductList);
  
