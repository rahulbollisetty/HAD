import axios from "../api/axios";
import useAuth from "./useAuth";

const useLogout = () => {
  const { auth, setAuth } = useAuth();
  const logout = async () => {
    setAuth({});
    localStorage.setItem("isLogged", false);
    try {
      const response = await axios.post(
        "/auth/signout",
        {},
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${auth?.accessToken}`,
          },
          withCredentials: true, // Include credentials (cookies) in the request
        }
      );
    } catch (err) {
      console.error(err);
    }
  };

  return logout;
};

export default useLogout;
