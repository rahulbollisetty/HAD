import React, { useState } from "react";
import { useForm } from "react-hook-form";
import toast from "react-hot-toast";
import useAxiosPrivate from "../../../hooks/useAxiosPrivate";
import encryptData from "../../../utils/encryptData";
function AbhaRegister() {
  const {
    register,
    handleSubmit,
    reset,
    getValues,
    formState: { errors },
  } = useForm();

  const {
    register: register1,
    handleSubmit: handleSubmit1,
    reset: reset1,
    getValues: getValues1,
    formState: { errors: errors1 },
  } = useForm();

  const {
    register: register2,
    handleSubmit: handleSubmit2,
    reset: reset2,
    getValues: getValues2,
    formState: { errors: errors2 },
  } = useForm();

  const axiosPrivate = useAxiosPrivate();
  const [txn, setTxn] = useState("");
  const [isMobileOTPreq, setMobileOTP] = useState(false);
  const [showOtpInput, setShowOtpInput] = useState(false);
  const [showMobileInput, setShowMobileInput] = useState(false);
  return (
    <div className="grid grid-cols-2 gap-5 text-[#7B7878] font-medium	text-xl mt-8">
      <div>
        <div className="flex flex-col">
          <p className="mr-48 text-sm">Aadhaar Number</p>
          <div className="relative flex w-full">
            <input
              className="rounded-md pr-32 w-full"
              type="text"
              name=""
              {...register("aadhaar", {
                required: "Required",
                pattern: {
                  value: /^[0-9]+$/,
                  message: "Only Numbers are allowed",
                },
              })}
            />
            <button
              className="!absolute p-1 bg-[#006666] text-white right-1 top-[3px] rounded"
              onClick={handleSubmit(async () => {
                const data = {
                  aadhaar: encryptData(getValues("aadhaar")),
                };
                try {
                  const resp = await axiosPrivate.post(
                    "/patient/aadhaarOTPinit",
                    data
                  );
                  console.log(resp);
                  setTxn(resp.data.txnId);
                  setShowOtpInput(true);
                  setShowMobileInput(true);
                  toast.success(resp.data.message);
                } catch (err) {
                  if (err?.response?.data) {
                    if (err?.response?.data?.loginId)
                      toast.error(err.response.data.loginId);
                    else toast.error(err.response.data.error.message);
                  }
                }
              })}
            >
              Send OTP
            </button>
          </div>
          <p className="errorMsg">{errors.aadhaar?.message}</p>
        </div>
      </div>

      <div>
        <div className="flex flex-col">
          <p className="mr-48 text-sm">Mobile</p>
          <div className="relative flex w-full">
            <input
              className={`rounded-md pr-32 w-full ${
                showMobileInput
                  ? ""
                  : "bg-gray-200 text-gray-500 cursor-not-allowed"
              }`}
              disabled={!showMobileInput}
              type="text"
              name=""
              id=""
              
              {...register1("mobile", {
                required: "Required",
                pattern: {
                  value: /^[0-9]+$/,
                  message: "Only Numbers are allowed",
                },
              })}
            />
            {isMobileOTPreq && (
              <button
                className="!absolute p-1 bg-[#006666] text-white right-1 top-[3px] rounded"
                onClick={handleSubmit1(async () => {
                  const data = {
                    loginId: encryptData(getValues1("mobile")),
                    txnId: txn,
                  };
                  const headers = {
                    "Content-Type": "application/json",
                  };
                  try {
                    const resp = await axiosPrivate.post(
                      "/patient/mobileOTPinit",
                      data,
                      { headers }
                    );
                    console.log(resp);
                    setTxn(resp.data.txnId);
                    setShowOtpInput(true);
                    toast.success(resp.data.message);
                  } catch (err) {
                    if (err?.response?.data) {
                      if (err?.response?.data?.loginId)
                        toast.error(err.response.data.loginId);
                      else toast.error(err.response.data.error.message);
                    }
                  }
                })}
              >
                Send OTP
              </button>
            )}
          </div>
          <p className="errorMsg">{errors1.mobile?.message}</p>
          <p className="errorMsg">{errors2.mobile?.message}</p>
        </div>
      </div>
      <div>
        <div className="flex flex-col">
          <p className="mr-48 text-sm">OTP</p>
          <input
            className={`rounded-md w-72 ${
              showOtpInput ? "" : "bg-gray-200 text-gray-500 cursor-not-allowed"
            }`}
            type="text"
            name=""
            disabled={!showOtpInput}
            id=""
            {...register2("otp", {
              required: "Required",
              pattern: {
                value: /^[0-9]+$/,
                message: "Only Numbers are allowed",
              },
            })}
          />
          <p className="errorMsg">{errors2.otp?.message}</p>
        </div>
      </div>
      <div>
        <div className="flex h-full items-end justify-center w-72">
          <button
            type="submit"
            className="w-40 p-2 bg-[#006666] text-white rounded-md"
            onClick={handleSubmit2(async () => {
              var data = null;
              var path = null;
              if (isMobileOTPreq) {
                data = {
                  otp: encryptData(getValues2("otp")),
                  txnId: txn,
                };
                path = "/patient/mobileOTPverify";
              } else {
                data = {
                  otp: encryptData(getValues2("otp")),
                  mobileNumber: getValues1("mobile"),
                  transactionId: txn,
                };
                path = "/patient/aadharOTPverify";
              }
              const headers = {
                "Content-Type": "application/json",
              };
              try {
                const resp = await axiosPrivate.post(path, data, { headers });
                console.log(resp.data.isNew);
                console.log(resp);
                setTxn(resp.data.txnId);
                if (isMobileOTPreq) {
                  setMobileOTP(false);
                  setShowMobileInput(false);
                  reset2({otp:""});
                  setShowOtpInput(false);
                  toast.success(resp.data.message);
                } else {
                  if (resp.data?.isNew===true) {
                    toast.success("Send OTP to mobile to link it to ABHA");
                    setMobileOTP(true);
                    setShowMobileInput(true);
                    reset2({otp:""});
                    setShowOtpInput(false);
                  } else {
                    setMobileOTP(false);
                    setShowMobileInput(false);
                    reset2({otp:""});
                    setShowOtpInput(false);
                    toast.success(resp.data.message);
                  }
                }
              } catch (err) {
                if (err?.response?.data) {
                  if (err?.response?.data?.loginId)
                    toast.error(err.response.data.loginId);
                  else toast.error(err.response.data.error.message);
                }
              }
            })}
          >
            Confirm
          </button>
        </div>
      </div>
      <div>
        <div className="flex flex-col">
          <p className="text-sm">Select ID</p>
          <select className="w-full rounded-md" name="bloodGroup" id="">
            <option value="" disabled>
              Select ID
            </option>
          </select>
        </div>
      </div>
    </div>
  );
}

export default AbhaRegister;
