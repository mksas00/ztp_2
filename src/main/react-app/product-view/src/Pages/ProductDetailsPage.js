// Import statements
import React, {useEffect, useState} from 'react';
import {useNavigate, useParams} from 'react-router-dom';
import {deleteProduct, fetchProductDetailsById} from "../Server Access/Api";
import {Button, Card, ListGroup} from 'react-bootstrap'; // Assuming you have Bootstrap installed and imported

function ProductDetailsPage() {
    const {productId} = useParams();
    const [product, setProduct] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchDetails = async () => {
            try {
                const data = await fetchProductDetailsById(productId);
                setProduct(data);
            } catch (error) {
                setError(error)
                console.error('Error fetching product:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchDetails();
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

    const handleEdit = (productId) => {
        navigate(`/editProduct/${productId}`)
    }

    const handleDelete = async (productId) => {
        await deleteProduct(productId)
        navigate(`/`)
    }

    return (
        <div className="container mt-5">
            <h2 className="mb-4">Product Details</h2>
            <Card className="shadow">
                <Card.Header as="h5">{product.name}</Card.Header>
                <Card.Body>
                    <ListGroup variant="flush">
                        <ListGroup.Item>
                            <strong>Description:</strong> {product.description}
                        </ListGroup.Item>
                        <ListGroup.Item>
                            <strong>Price:</strong> ${product.price}
                        </ListGroup.Item>
                        <ListGroup.Item>
                            <strong>Weight:</strong> {product.weight} kgs
                        </ListGroup.Item>
                        <ListGroup.Item>
                            <strong>Category:</strong> {product.category}
                        </ListGroup.Item>
                        <ListGroup.Item>
                            <strong>Quantity:</strong> {product.quantity}
                        </ListGroup.Item>
                    </ListGroup>
                </Card.Body>
            </Card>
            <div className="d-flex justify-content-center mt-3">
                <Button className="m-auto"
                        onClick={() => handleEdit(product.id)}
                >
                    Edit
                </Button>
                <Button className="m-auto"
                        onClick={() => handleDelete(product.id)}
                >
                    Delete
                </Button>
            </div>
        </div>
    );
}

export default ProductDetailsPage;