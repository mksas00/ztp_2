import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/products';

export const fetchProducts = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}`);
        return response.data;
    } catch (error) {
        console.error('Error fetching products:', error);
        throw error;
    }
};

export const fetchProductDetailsById = async (productId) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/${productId}`);
        return response.data;
    } catch (error) {
        console.error('Error fetching products:', error);
        throw error;
    }
};

export const createProduct = async (data) => {
    try {
        const response = await axios.post(`${API_BASE_URL}`, data);
        return response.data;
    } catch (error) {
        console.error('Error fetching products:', error);
        throw error;
    }
};

export const updateProduct = async (data) => {
    try {
        const response = await axios.put(`${API_BASE_URL}`, data);
        console.log("update")
        return response.data;
    } catch (error) {
        console.error('Error fetching products:', error);
        throw error;
    }
};

export const deleteProduct = async (productId) => {
    try {
        const response = await axios.delete(`${API_BASE_URL}/${productId}`);
        return response.data;
    } catch (error) {
        console.error('Error fetching products:', error);
        throw error;
    }
};