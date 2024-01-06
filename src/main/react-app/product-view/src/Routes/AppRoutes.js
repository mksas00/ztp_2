import {BrowserRouter, Route, Routes} from "react-router-dom";
import ProductsPage from "../Pages/ProductsPage";
import ProductEditPage from "../Pages/ProductEditPage";
import ProductDetailsPage from "../Pages/ProductDetailsPage";
import AddProductPage from "../Pages/AddProductPage";

export default function AppRoutes(){

    return (
            <BrowserRouter>
                <Routes>
                    <Route path="/" element ={<ProductsPage />}/>
                    <Route path="/editProduct/:productId" element ={<ProductEditPage />}/>
                    <Route path="/productDetails/:productId" element ={<ProductDetailsPage />}/>
                    <Route path="/addProduct" element ={<AddProductPage />}/>
                </Routes>
            </BrowserRouter>
    )

}