import React, { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import { Password } from "@mui/icons-material";
import { GiHospital } from "react-icons/gi";
import axios from "../../api/axios";
import { useSearchParams } from "react-router-dom";

export default function ForgotPassword() {
  const {
    register,
    handleSubmit,
    reset,
    getValues,
    setValue,
    formState: { errors },
  } = useForm();
  const [searchParams, setSearchParams] = useSearchParams();
  const navigate = useNavigate();
  const [resetPassword, setResetPassword] = useState(false);
  const [id, setId] = useState(0);
  useEffect(() => {
    if (searchParams.get("token")) {
      const requestBody = {
        token: searchParams.get("token")
      };
      setId(searchParams.get("randomId"));
      const verifyToken = async () => {
        try {
          const resp = await axios.post(
            "/auth/verifyForgotPasswordToken",
            requestBody
          );
          // console.log(resp);
          if (
            resp.data == "Token Invalid" ||
            resp.data == "Verification Token Expired"
          ) {
            toast.error(resp.data);
            setResetPassword(false);
          } else {
            setResetPassword(true);
            setId(searchParams.get("randomId"));
            toast.success(resp.data);
          }
        } catch (error) {
          // console.log(error);
          toast.error(error.response.data);
        }
      };
      verifyToken();
    }
  }, []);

  const sendEmail = async () => {
    try {
      const requestBody = {
        email: getValues("email"),
      };
      // console.log(requestBody);
      const response = await axios.post(`/auth/forgotPassword`, requestBody);
      // console.log(response);
      toast.success("Email Sent");
    } catch (err) {
      if (!err?.response) {
        toast.error(`${err?.response}`);
      }
    }
  };

  const passwordReset = async () => {
    if (getValues("newPassword") === getValues("confirmPassword")) {
      try {
        const requestBody = {
          id: id,
          password: getValues("newPassword"),
        };
        const response = await axios.post(
          `/auth/resetForgotPassword`,
          requestBody
        );
        toast.success(`${response.data}`);
        navigate("/login");
      } catch (err) {
        toast.error(`${err?.response}`);
      }
    }
  };
  return (
    <div className="bg-[#02685A]  w-fill h-screen flex items-center justify-center">
      <div className="bg-white w-4/5 h-[50%] rounded-[10px]">
        <div className="bg-black w-[150px] h-[150px] rounded-[50%] ml-[50px] mt-[40px] text-white flex items-center justify-center text-[100px] ">
          <GiHospital />
        </div>
        {!resetPassword ? (
          <>
            <p className="flex items-center justify-center my-5 text-[19px]  font-semibold">
              Please enter the email address you used to register for the
              facility. A link will be sent to that email address.
            </p>
            <div className="flex items-center justify-center py-4">
              <input
                className="w-[30%] mr-[10px] rounded-[5px] mb-[5px]"
                type="email"
                placeholder="Enter registered Email"
                {...register("email", { required: true })}
              />
              <button
                // className="border border-[#5c5219] font-semibold mb-[5px] rounded-lg px-8 py-2 ml-10 text-white bg-[#FFA000]"
                className="mb-[5px] bg-[#FFA000] border-0 rounded-[5px] text-white p-[10px] font-semibold"
                onClick={() => sendEmail()}
              >
                Send Email
              </button>
            </div>
          </>
        ) : (
          <>
            <div className="flex items-center justify-center py-4">
              <input
                className="w-[30%] mr-[10px] rounded-[5px] mb-[5px]"
                type="password"
                {...register("newPassword", { required: true })}
                placeholder="Enter New Password"
              />
            </div>
            <div className="flex items-center justify-center py-4">
              <input
                className="w-[30%] mr-[10px] rounded-[5px] mb-[5px]"
                type="text"
                {...register("confirmPassword", { required: true })}
                placeholder="Confirm Password"
              />
            </div>
            <div className="flex items-center justify-center text-white ">
              <button
                // className="border rounded-lg border-[#22381e] px-5 py-2 bg-[#02685A] font-semibold"
                className="mb-[5px] bg-[#02685A] border-[#22381e] border rounded-[5px] text-white p-[10px] font-semibold"
                onClick={() => passwordReset()}
              >
                Reset Password
              </button>
            </div>
          </>
        )}
      </div>
    </div>
  );
}
