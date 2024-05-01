import axios from '../api/axios';
import useAuth from './useAuth';

const useRefreshToken = () => {
    const { setAuth } = useAuth();

    const refresh = async () => {
        const response = await axios.post('/auth/refreshtoken', {
            withCredentials: true
        });
        setAuth(prev => {
            // console.log(JSON.stringify(prev));
            // console.log(response.data.token);
            return { ...prev, accessToken: response.data.token }
        });
        return response.data.token;
    }
    return refresh;
};

export default useRefreshToken;