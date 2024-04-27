import axios from "../../api/axios";
import { useForm } from "react-hook-form";
import useAuth from "../../hooks/useAuth";
import { useEffect, useState } from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";

const Login = () => {
  const { setAuth, persist, setPersist } = useAuth();

  const navigate = useNavigate();
  const location = useLocation();
  const from = location.state?.from?.pathname || "/";

  const [errMsg, setErrMsg] = useState("");

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm();

  const onSubmit = async (data) => {
    try {
      const response = await axios.post("/auth/login", JSON.stringify(data), {
        headers: { "Content-Type": "application/json" },
        withCredentials: true,
      });
      console.log(JSON.stringify(response?.data));
      const accessToken = response?.data?.token;
      setAuth({ accessToken });
      navigate(from, { replace: true });
    } catch (err) {
      if (!err?.response) {
        setErrMsg("No Server Response");
      } else if (err.response?.status === 400) {
        setErrMsg("Missing Username or Password");
      } else if (err.response?.status === 401) {
        setErrMsg("Unauthorized");
      } else {
        setErrMsg("Login Failed");
      }
    }
  };

  const togglePersist = () => {
    setPersist((prev) => !prev);
  };

  useEffect(() => {
    localStorage.setItem("persist", persist);
  }, [persist]);

  return (
    <div className="bg-[#02685A]  w-fill h-screen flex items-center justify-center">
      <div className="bg-white w-4/5 h-[70%] rounded-[10px]">
        <div className="bg-black w-[150px] h-[150px] rounded-[50%] ml-[50px] mt-[40px] text-white flex items-center justify-center text-3xl">
          Logo
        </div>
        <div className="bg-black w-full h-[493px] flex">
          <div className="bg-white pl-[25%] w-[60%] flex-col">
            <p className="text-3xl pb-[20px]">Welcome!</p>
            <form onSubmit={handleSubmit(onSubmit)}>
              <div>
                <p className="text-[#7F8C8D] pb-[10px]">Username</p>
                <input
                  type="text"
                  name="username"
                  className="w-[100%] mr-[10px] rounded-[5px] mb-[5px]"
                  {...register("username", { required: true })}
                />
                {errors.username && errors.username.type === "required" && (
                  <p className="errorMsg">Username is required.</p>
                )}
              </div>
              <div>
                <p className="text-[#7F8C8D] pb-[10px]">Password</p>
                <input
                  type="password"
                  className="w-[100%] mr-[10px] rounded-[5px] mb-[5px]"
                  {...register("password", { required: true })}
                />
                {errors.password && errors.password.type === "required" && (
                  <p className="errorMsg">Password is required.</p>
                )}
              </div>
              <div className="persistCheck m-[5px]">
                <input
                  type="checkbox"
                  id="persist"
                  onChange={togglePersist}
                  checked={persist}
                />
                <span className="text-[#7F8C8D] ml-[5px] pb-[10px]">
                  Remember Me
                </span>
              </div>
              <a href={""} className="text-[#308EDC]">
                Forget Password?
              </a>
              <button
                className="mt-[20px] bg-[#02685A] border-0 rounded-[5px] w-[100%] text-white p-[10px]"
                type="submit"
              >
                LOGIN
              </button>
            </form>
          </div>
          <div className="bg-white w-[40%]">
            <img src="./bg.png" className="h-full" />
          </div>
        </div>
      </div>
    </div>
  );
};
export default Login;
