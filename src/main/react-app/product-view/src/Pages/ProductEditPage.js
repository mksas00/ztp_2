import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { fetchProductDetailsById, updateProduct } from "../Server Access/Api"; // Update the path based on your actual project structure
import { Form, Button } from 'react-bootstrap'; // Import Bootstrap Form and Button components

function ProductEditPage() {
    const { productId } = useParams();
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const [product, setProduct] = useState({
        name: '',
        description: '',
        price: '',
        weight: '',
        category: '',
        quantity: '',
    });
    const navigate = useNavigate();

    useEffect(() => {
        const fetchProductDetails = async () => {
            try {
                const data = await fetchProductDetailsById(productId);
                setProduct(data);
            } catch (error) {
                setError(error)
                console.error('Error fetching products:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchProductDetails();
    }, [productId]);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        if (error.code === "ERR_NETWORK") {
            return (
                <div style={{display: 'flex', justifyContent: 'center', marginTop: '25%'}}><h1>There's an error with the
                    server connection please, be patient.</h1></div>);
        } else if (error.code === "ERR_BAD_REQUEST") {
            return (
                <div style={{display: 'flex', justifyContent: 'center', marginTop: '25%'}}><h1>A product with given id doesn't exist.</h1></div>);
        }
    }


    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setProduct((prevProduct) => ({
            ...prevProduct,
            [name]: value,
        }));
    };

    const handleUpdate = async () => {
        try {
            await updateProduct(product);
            navigate('/');
        } catch (error) {
            console.error('Error updating product:', error);
        }
    };

    return (
        <div className="container mt-5">
            <h2>Edit Product</h2>
            <Form>
                <Form.Group className="mb-3" controlId="formName">
                    <Form.Label>Name</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Enter product name"
                        name="name"
                        value={product.name}
                        required
                        onChange={handleInputChange}
                    />
                </Form.Group>

                <Form.Group className="mb-3" controlId="formDescription">
                    <Form.Label>Description</Form.Label>
                    <Form.Control
                        as="textarea"
                        placeholder="Enter product description"
                        name="description"
                        value={product.description}
                        required
                        onChange={handleInputChange}
                    />
                </Form.Group>

                <Form.Group className="mb-3" controlId="formPrice">
                    <Form.Label>Price</Form.Label>
                    <Form.Control
                        type="number"
                        placeholder="Enter product description"
                        name="price"
                        value={product.price}
                        required
                        onChange={handleInputChange}
                    />
                </Form.Group>

                <Form.Group className="mb-3" controlId="formWeight">
                    <Form.Label>Weight</Form.Label>
                    <Form.Control
                        type="number"
                        placeholder="Enter product description"
                        name="weight"
                        value={product.weight}
                        required
                        onChange={handleInputChange}
                    />
                </Form.Group>

                <Form.Group className="mb-3" controlId="formCategory">
                    <Form.Label>Category</Form.Label>
                    <Form.Control
                        as="textarea"
                        placeholder="Enter product description"
                        name="category"
                        value={product.category}
                        required
                        onChange={handleInputChange}
                    />
                </Form.Group>

                <Form.Group className="mb-3" controlId="formQuantity">
                    <Form.Label>Quantity</Form.Label>
                    <Form.Control
                        type="number"
                        placeholder="Enter product description"
                        name="quantity"
                        value={product.quantity}
                        required
                        onChange={handleInputChange}
                    />
                </Form.Group>

                <Button variant="primary" onClick={handleUpdate}>
                    Update Product
                </Button>
            </Form>
        </div>
    );
}

export default ProductEditPage;