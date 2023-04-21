// ""
import React from "react";
import withContext from "../withContext";

const ProductItem = props => {
    const { product } = props;
    return (
        <div className=" column is-half">
            <div className="box">
                <div className="media">
                    <div className="media-left">
                        <figure className="image is-64x64">
                            <img
                                src="https://bulma.io/images/placeholders/128x128.png"
                                alt={product.name}
                            />
                        </figure>
                    </div>
                    <div className="media-content">
                        <b style={{ textTransform: "capitalize" }}>
                            {product.name}{" "}
                            <span className="tag is-primary">${product.price}</span>
                        </b>

                        <div><small>{product.color}</small></div>
                        <div><small>{product.category?product.category.name:""}</small></div>
                        <div><small>{product.vendor?product.vendor.username:""}</small></div>
                        {product.quantity > 0 ? (
                            <small>{product.quantity + " Available"}</small>
                        ) : (
                            <small className="has-text-danger">Out Of Stock</small>
                        )}
                        <div className="is-clearfix">
                            <button
                                className="button is-small is-outlined is-primary   is-pulled-right"
                                onClick={() =>
                                    props.addToCart({
                                        id: product.id,
                                        product,
                                        amount: 1
                                    })
                                }
                            >
                                Add to Cart
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ProductItem;
