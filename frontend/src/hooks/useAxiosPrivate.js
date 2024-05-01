import { axiosPrivate } from "../api/axios";
import { useEffect } from "react";
import useRefreshToken from "./useRefreshToken";
import useAuth from "./useAuth";
import axios from "../api/axios"
import { toast } from "react-toastify";
const useAxiosPrivate = () => {
  const refresh = useRefreshToken();
  const { auth, setAuth } = useAuth();

  useEffect(() => {
    const sd = async () => {
        try{
            const newAccessToken = await axios.post(
              "/auth/isLogged",
              {},
              {
                headers: {
                  "Content-Type": "application/json",
                  Authorization: `Bearer ${auth?.accessToken}`,
                },
                withCredentials: true, // Include credentials (cookies) in the request
              }
            );
        }
        catch (error){
            console.log(error);
            setAuth({});
            await localStorage.setItem("isLogged",false);
            toast.info("Logged out due to session active in other device");
        }
    };
    sd();
  }, []);

  useEffect(() => {
    const requestIntercept = axiosPrivate.interceptors.request.use(
      (config) => {
        if (!config.headers["Authorization"]) {
          config.headers["Authorization"] = `Bearer ${auth?.accessToken}`;
        }
        return config;
      },
      (error) => Promise.reject(error)
    );

    const responseIntercept = axiosPrivate.interceptors.response.use(
      (response) => response,
      async (error) => {
        console.log(error)
        const prevRequest = error?.config;
        if (error?.response?.status === 403 && !prevRequest?.sent) {
          prevRequest.sent = true;
          const newAccessToken = await refresh();
          if(newAccessToken === null){
            return Promise.reject(error);
          }
          prevRequest.headers["Authorization"] = `Bearer ${newAccessToken}`;
          return axiosPrivate(prevRequest);
        }
        return Promise.reject(error);
      }
    );

    return () => {
      axiosPrivate.interceptors.request.eject(requestIntercept);
      axiosPrivate.interceptors.response.eject(responseIntercept);
    };
  }, [auth, refresh]);

  return axiosPrivate;
};

export default useAxiosPrivate;
