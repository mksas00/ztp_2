import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { createProduct } from "../Server Access/Api";
import { Form, Button } from 'react-bootstrap';

function AddProductPage() {

    const [product, setProduct] = useState({
        name: '',
        description: '',
        price: '',
        weight: '',
        category: '',
        quantity: '',
    });
    const navigate = useNavigate();

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setProduct((prevProduct) => ({
            ...prevProduct,
            [name]: value,
        }));
    };

    const handleCreate = async () => {
        try {
            await createProduct(product);
            navigate('/');
        } catch (error) {
            console.error('Error updating product:', error);
        }
    };

    return (
        <div className="container mt-5">
            <h2>Add Product</h2>
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

                <Button variant="primary" onClick={handleCreate}>
                    Add Product
                </Button>
            </Form>
        </div>
    );
}

export default AddProductPage;