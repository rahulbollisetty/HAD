import React from "react";
import useAxiosPrivate from "../../../hooks/useAxiosPrivate";
import { toast } from "react-toastify";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

export default function VerifyEmail() {
  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();
  useEffect(() => {
    const windowValues = window.location.search;
    const requestParams = new URLSearchParams(windowValues);
    let role = requestParams.get("role");
    const token = requestParams.get("token");
    const isHeadDoctor = requestParams.get("flag");
    const requestBody = {
      role: role,
      token: token,
      isHeadDoctor: isHeadDoctor,
    };
    const verifyToken = async () => {
      try {
        const resp = await axiosPrivate.post(
          "http://127.0.0.1:9005/auth/verifyEmail",
          requestBody
        );
        console.log(resp);
        if (
          resp.data == "Invalid Token" ||
          resp.data == "Verification Token Expired"
        ) {
          toast.error(resp.data);
        } else {
          toast.success(resp.data);
          if (role === "HEAD_DOCTOR") role = role.slice(5);
          navigate(
            `/register/${role.toLocaleLowerCase()}/?isHeadDoctor=${isHeadDoctor}`
          );
        }
      } catch (error) {
        console.log(error);
        toast.error(error.response.data);
      }
    };
    verifyToken();
  }, []);

  return <div>Wait till the token gets verified</div>;
}
