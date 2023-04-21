
import React from "react";

const CartItem =props=>{
    const{cartItem,cartKey}=props;

    const{ product,amount }=cartItem;
    return(
    <div className="columnis-half">
    <div className="box">
    <div className="media">
    <div className="media-left">
    <figure className="imageis-64x64">
        <img
    src="https://bulma.io/images/placeholders/128x128.png"
    alt={product.shortDesc}
    />
</figure>
</div>
<div className="media-content">
    <b style={{textTransform:"capitalize"}}>
    {product.name}{""}
<span className="tagis-primary">${product.price}</span>
</b>
    <div>{product.shortDesc}</div>
    <small>{`${amount} in cart`}</small>
</div>
    <div
        className="media-right"
        onClick={()=>this.props.removeFromCart(cartItem)}
    >
        <span className="deleteis-large"></span>
    </div>
</div>
</div>
</div>
);
};

export default CartItem;
