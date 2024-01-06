import '../App.css';
import {deleteProduct, fetchProducts} from "../Server Access/Api";
import React, {useEffect, useState} from 'react';
import {Button} from 'react-bootstrap';
import {useNavigate} from 'react-router-dom';

function ProductsPage() {

    const navigate = useNavigate();
    const [products, setProducts] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);


    const fetchData = async () => {
        try {
            const data = await fetchProducts();
            setProducts(data);
        } catch (error) {
            setError(error)
            console.error('Error fetching products:', error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchData().then();
        if (!products) {
            return <div>Loading...</div>;
        }
    }, []);

    if (error) {
      if(error.code === "ERR_NETWORK") {
        return (<div style={{display: 'flex', justifyContent: 'center', marginTop: '25%'}}><h1>There's an error with the
          server connection please, be patient.</h1></div>);
      }
    }

    const handleEdit = (productId) => {
        navigate(`/editProduct/${productId}`)
    }

    const handleShowDetails = (productId) => {
        navigate(`/productDetails/${productId}`)
    }

    const handleDelete = async (productId) => {
        await deleteProduct(productId)
        fetchData()
    }

    const handleAddProduct = async () => {
        navigate(`/addProduct`)
    }

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div className="ProductsPage">
            <div className="container mt-5">
                <div style={{display: "flex", justifyContent: "space-between"}}>
                    <h2>Product List</h2>
                    <div className="align-self-center align-items-center">
                        <Button
                            variant="success"
                            onClick={() => handleAddProduct()}
                        >
                            Add Product
                        </Button>
                    </div>
                </div>
                <table class="table">
                    <thead class="table-dark">
                    <tr>
                        <th className="text-center">ID</th>
                        <th className="text-center">Name</th>
                        <th className="text-center">Price</th>
                        <th className="text-center">Category</th>
                        <th className="text-center">Quantity</th>
                        <th className="text-center">Delete</th>
                        <th className="text-center">Edit</th>
                        <th className="text-center">Details</th>
                    </tr>
                    </thead>
                    <tbody>
                    {products.map(product => (
                        <tr key={product.id}>
                            <td className="text-center">{product.id}</td>
                            <td className="text-center">{product.name}</td>
                            <td className="text-center">{product.price} zł</td>
                            <td className="text-center">{product.category}</td>
                            <td className="text-center">{product.quantity}</td>
                            <td className="text-center">
                                <Button
                                    variant="danger"
                                    onClick={() => handleDelete(product.id)}
                                >
                                    Delete
                                </Button>
                            </td>
                            <td className="text-center">
                                <Button
                                    onClick={() => handleEdit(product.id)}
                                >
                                    Edit
                                </Button>
                            </td>

                            <td className="text-center">
                                <Button
                                    onClick={() => handleShowDetails(product.id)}
                                >
                                    Show Details
                                </Button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}


export default ProductsPage;
