import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/seats';

export const getAllSeats = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/get-all`);
        return response.data.ourSeatsList; // Ajusta según la estructura de tu DTO
    } catch (error) {
        console.error('Error fetching seats:', error);
        return [];
    }
};

export const addSeat = async (seat) => {
    try {
        const response = await axios.post(`${API_BASE_URL}/add`, seat);
        return response.data;
    } catch (error) {
        console.error('Error adding seat:', error);
        throw error;
    }
};
