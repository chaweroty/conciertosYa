import axios from "axios";

class UserService{
    static BASE_URL = "http://localhost:8080"

    static async login(email, password){
        try{
            const response = await axios.post(${UserService.BASE_URL}/auth/login, {email, password})
            return response.data;

        }catch(err){
            throw err;
        }
    }

    static async register(userData) {
        try {
            const response = await axios.post(${UserService.BASE_URL}/auth/register, userData);
            return response.data;
        } catch (err) {
            throw err;
        }
    }
    

    //CRUD methods artists
    static async getAllUsers(token){
        try {
            const response = await axios.get(${UserService.BASE_URL}/admin/get-all-users, {
                headers: { Authorization: Bearer ${token} }
            });
            return response.data;
        } catch (err) {
            if (err.response) {
                console.error(Error: ${err.response.status} - ${err.response.data.message});
            }
            throw err;
        }
    }
      //CRUD methods artists
    static async getAllartists(token){
        try {
            const response = await axios.get(${UserService.BASE_URL}/artists/get-all, {
                headers: { Authorization: Bearer ${token} }
            });
            return response.data;
        } catch (err) {
            if (err.response) {
                console.error(Error: ${err.response.status} - ${err.response.data.message});
            }
            throw err;
        }
    }
    static async createArtist(token){
        try {
            const response = await axios.get(${UserService.BASE_URL}/artists/add, {
                headers: { Authorization: Bearer ${token} }
            });
            return response.data;
        } catch (err) {
            if (err.response) {
                console.error(Error: ${err.response.status} - ${err.response.data.message});
            }
            throw err;
        }
    }
    static async updateArtist(token){
        try {
            const response = await axios.get(${UserService.BASE_URL}/artists/update/{artistId}, {
                headers: { Authorization: Bearer ${token} }
            });
            return response.data;
        } catch (err) {
            if (err.response) {
                console.error(Error: ${err.response.status} - ${err.response.data.message});
            }
            throw err;
        }
    }
    static async deleteArtist(token){
        try {
            const response = await axios.get(${UserService.BASE_URL}/artists/delete/{artistId}, {
                headers: { Authorization: Bearer ${token} }
            });
            return response.data;
        } catch (err) {
            if (err.response) {
                console.error(Error: ${err.response.status} - ${err.response.data.message});
            }
            throw err;
        }
    }


    //CRUD methods Concerts
    static async getAllconcerts(token){
        try {
            const response = await axios.get(${UserService.BASE_URL}/events/get-all, {
                headers: { Authorization: Bearer ${token} }
            });
            return response.data;
        } catch (err) {
            if (err.response) {
                console.error(Error: ${err.response.status} - ${err.response.data.message});
            }
            throw err;
        }
    }
    static async createConcert(token){
        try {
            const response = await axios.get(${UserService.BASE_URL}/events/add, {
                headers: { Authorization: Bearer ${token} }
            });
            return response.data;
        } catch (err) {
            if (err.response) {
                console.error(Error: ${err.response.status} - ${err.response.data.message});
            }
            throw err;
        }
    }
    static async updateConcert(token){
        try {
            const response = await axios.get(${UserService.BASE_URL}/events/update/{eventId}, {
                headers: { Authorization: Bearer ${token} }
            });
            return response.data;
        } catch (err) {
            if (err.response) {
                console.error(Error: ${err.response.status} - ${err.response.data.message});
            }
            throw err;
        }
    }
    static async deleteConcert(token){
        try {
            const response = await axios.get(${UserService.BASE_URL}/events/delete/{eventId}, {
                headers: { Authorization: Bearer ${token} }
            });
            return response.data;
        } catch (err) {
            if (err.response) {
                console.error(Error: ${err.response.status} - ${err.response.data.message});
            }
            throw err;
        }
    }
    
    
    //CRUD methods Places
    static async getAllplaces(token){
        try {
            const response = await axios.get(${UserService.BASE_URL}/places/get-all, {
                headers: { Authorization: Bearer ${token} }
            });
            return response.data;
        } catch (err) {
            if (err.response) {
                console.error(Error: ${err.response.status} - ${err.response.data.message});
            }
            throw err;
        }
    }

    static async createPlace(token){
        try {
            const response = await axios.get(${UserService.BASE_URL}/places/add, {
                headers: { Authorization: Bearer ${token} }
            });
            return response.data;
        } catch (err) {
            if (err.response) {
                console.error(Error: ${err.response.status} - ${err.response.data.message});
            }
            throw err;
        }
    }
    static async updatePlace(token){
        try {
            const response = await axios.get(${UserService.BASE_URL}/places/update/{eventId}, {
                headers: { Authorization: Bearer ${token} }
            });
            return response.data;
        } catch (err) {
            if (err.response) {
                console.error(Error: ${err.response.status} - ${err.response.data.message});
            }
            throw err;
        }
    }
    static async deletePlace(token){
        try {
            const response = await axios.get(${UserService.BASE_URL}/places/delete/{eventId}, {
                headers: { Authorization: Bearer ${token} }
            });
            return response.data;
        } catch (err) {
            if (err.response) {
                console.error(Error: ${err.response.status} - ${err.response.data.message});
            }
            throw err;
        }
    }

    static async getYourProfile(token){
        try{
            const response = await axios.get(${UserService.BASE_URL}/adminuser/get-profile, 
            {
                headers: {Authorization: Bearer ${token}}
            })
            return response.data;
        }catch(err){
            throw err;
        }
    }

    static async getUserById(userId, token){
        try{
            const response = await axios.get(${UserService.BASE_URL}/admin/get-users/${userId}, 
            {
                headers: {Authorization: Bearer ${token}}
            })
            return response.data;
        }catch(err){
            throw err;
        }
    }

    static async deleteUser(userId, token){
        try{
            const response = await axios.delete(${UserService.BASE_URL}/admin/delete/${userId}, 
            {
                headers: {Authorization: Bearer ${token}}
            })
            return response.data;
        }catch(err){
            throw err;
        }
    }

 


    static async updateUser(userId, userData, token){
        try{
            const response = await axios.put(${UserService.BASE_URL}/admin/update/${userId}, userData,
            {
                headers: {Authorization: Bearer ${token}}
            })
            return response.data;
        }catch(err){
            throw err;
        }
    }

    /**AUTHENTICATION CHECKER */
    static logout(){
        localStorage.removeItem('token')
        localStorage.removeItem('role')
    }

    static isAuthenticated(){
        const token = localStorage.getItem('token')
        return !!token
    }

    static isAdmin(){
        const role = localStorage.getItem('role')
        return role === 'ADMIN'
    }

    static isUser(){
        const role = localStorage.getItem('role')
        return role === 'USER'
    }

    static adminOnly(){
        return this.isAuthenticated() && this.isAdmin();
    }

}

export default UserService;